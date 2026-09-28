package com.command.toyvillage_server.global.security.jwt;

import com.command.toyvillage_server.domain.app.auth.admin.domain.AppAdmin;
import com.command.toyvillage_server.domain.app.auth.admin.domain.repository.AppAdminRepository;
import com.command.toyvillage_server.domain.app.auth.admin.exception.AppAdminNotFoundException;
import com.command.toyvillage_server.domain.web.auth.admin.domain.RefreshToken;
import com.command.toyvillage_server.domain.web.auth.admin.domain.repository.RefreshTokenRepository;
import com.command.toyvillage_server.domain.web.auth.admin.domain.repository.WebAdminRepository;
import com.command.toyvillage_server.domain.web.auth.admin.exception.ExpiredTokenException;
import com.command.toyvillage_server.domain.web.auth.admin.exception.InvalidTokenException;
import com.command.toyvillage_server.domain.web.auth.admin.exception.WebAdminNotFoundException;
import com.command.toyvillage_server.domain.web.auth.admin.presentation.dto.response.TokenResponse;
import com.command.toyvillage_server.global.security.auth.AppAdminDetailsService;
import com.command.toyvillage_server.global.security.auth.WebAdminDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    private static final String CLAIM_TYPE = "type";
    private static final String CLAIM_ROLE = "role";
    private static final String ACCESS_TYPE = "access";
    private static final String REFRESH_TYPE = "refresh";
    private static final String WEB_SCOPE = "WEB";
    private static final String APP_SCOPE = "APP";
    private static final String WEB_ADMIN_ROLE = "WEB_ADMIN";

    private final JwtProperties jwtProperties;
    private final WebAdminRepository webAdminRepository;
    private final AppAdminRepository appAdminRepository;
    private final WebAdminDetailsService webAdminDetailsService;
    private final AppAdminDetailsService appAdminDetailsService;
    private final RefreshTokenRepository refreshTokenRepository;

    public TokenResponse receiveWebToken(String email) {
        webAdminRepository.findByEmail(email)
                .orElseThrow(() -> WebAdminNotFoundException.EXCEPTION);

        return TokenResponse.of(
                createAccessToken(email, WEB_ADMIN_ROLE, false),
                createRefreshToken(email, WEB_ADMIN_ROLE, false)
        );
    }

    public TokenResponse receiveAppToken(String username) {
        AppAdmin appAdmin = appAdminRepository.findByUsernameAndDeleteStatusFalse(username)
                .orElseThrow(() -> AppAdminNotFoundException.EXCEPTION);

        return TokenResponse.of(
                createAccessToken(username, appAdmin.getRole().name(), true),
                createRefreshToken(username, appAdmin.getRole().name(), true)
        );
    }

    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);
        String role = claims.get(CLAIM_ROLE, String.class);

        UserDetails userDetails = WEB_ADMIN_ROLE.equals(role)
                ? webAdminDetailsService.loadUserByUsername(claims.getSubject())
                : appAdminDetailsService.loadUserByUsername(claims.getSubject());

        return new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );
    }

    public Claims getWebRefreshClaims(String token) {
        return getRefreshClaims(token, false);
    }

    public Claims getAppRefreshClaims(String token) {
        return getRefreshClaims(token, true);
    }

    public String getWebRefreshTokenKey(String email) {
        return WEB_SCOPE + ":" + email;
    }

    public String getAppRefreshTokenKey(String username) {
        return APP_SCOPE + ":" + username;
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(jwtProperties.getHeader());

        if (StringUtils.hasText(bearerToken)
                && bearerToken.startsWith(jwtProperties.getPrefix())
                && bearerToken.length() > jwtProperties.getPrefix().length() + 1) {
            return bearerToken.substring(jwtProperties.getPrefix().length() + 1);
        }

        return null;
    }

    private String createAccessToken(String subject, String role, boolean appToken) {
        return createToken(subject, role, ACCESS_TYPE, appToken);
    }

    private String createRefreshToken(String subject, String role, boolean appToken) {
        String refreshToken = createToken(subject, role, REFRESH_TYPE, appToken);
        String tokenKey = appToken
                ? getAppRefreshTokenKey(subject)
                : getWebRefreshTokenKey(subject);

        refreshTokenRepository.save(
                RefreshToken.builder()
                        .tokenKey(tokenKey)
                        .token(refreshToken)
                        .timeToLive(getRefreshExpiration(appToken))
                        .build()
        );

        return refreshToken;
    }

    private String createToken(String subject, String role, String type, boolean appToken) {
        Date now = new Date();

        return Jwts.builder()
                .subject(subject)
                .claim(CLAIM_TYPE, type)
                .claim(CLAIM_ROLE, role)
                .issuedAt(now)
                .expiration(new Date(now.getTime() + getExpiration(type, appToken)))
                .signWith(getSigningKey(appToken), Jwts.SIG.HS512)
                .compact();
    }

    private Claims getClaims(String token) {
        try {
            return parseClaims(token, false);
        } catch (ExpiredJwtException e) {
            throw ExpiredTokenException.EXCEPTION;
        } catch (JwtException ignored) {
            try {
                return parseClaims(token, true);
            } catch (ExpiredJwtException e) {
                throw ExpiredTokenException.EXCEPTION;
            } catch (JwtException e) {
                throw InvalidTokenException.EXCEPTION;
            }
        }
    }

    private Claims getRefreshClaims(String token, boolean appToken) {
        try {
            Claims claims = parseClaims(token, appToken);

            if (!REFRESH_TYPE.equals(claims.get(CLAIM_TYPE, String.class))) {
                throw InvalidTokenException.EXCEPTION;
            }
            return claims;
        } catch (ExpiredJwtException e) {
            throw ExpiredTokenException.EXCEPTION;
        } catch (JwtException e) {
            throw InvalidTokenException.EXCEPTION;
        }
    }

    private Claims parseClaims(String token, boolean appToken) {
        return Jwts.parser()
                .verifyWith(getSigningKey(appToken))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private long getExpiration(String type, boolean appToken) {
        return REFRESH_TYPE.equals(type)
                ? getRefreshExpiration(appToken)
                : appToken
                        ? jwtProperties.getAppAccessExpiration()
                        : jwtProperties.getWebAccessExpiration();
    }

    private long getRefreshExpiration(boolean appToken) {
        return appToken
                ? jwtProperties.getAppRefreshExpiration()
                : jwtProperties.getWebRefreshExpiration();
    }

    private SecretKey getSigningKey(boolean appToken) {
        String secretKey = appToken
                ? jwtProperties.getAppSecretKey()
                : jwtProperties.getWebSecretKey();

        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }
}
