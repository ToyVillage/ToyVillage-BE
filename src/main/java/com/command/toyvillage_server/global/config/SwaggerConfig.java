package com.command.toyvillage_server.global.config;

import com.command.toyvillage_server.global.swagger.SwaggerProperties;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(SwaggerProperties.class)
public class SwaggerConfig {
    private static final String SECURITY_SCHEME_NAME = "bearerAuth";
    private static final String SWAGGER_ROLE = "SWAGGER";
    private static final String[] SWAGGER_PATHS = {
            "/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**"
    };

    private final SwaggerProperties swaggerProperties;
    private final BCryptPasswordEncoder passwordEncoder;
    private final CorsConfigurationSource corsConfigurationSource;

    @Bean
    @Order(1)
    public SecurityFilterChain swaggerSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                .securityMatcher(SWAGGER_PATHS)
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth.anyRequest().hasRole(SWAGGER_ROLE))
                .authenticationManager(swaggerAuthenticationManager())
                .httpBasic(basic -> basic.authenticationEntryPoint(swaggerAuthenticationEntryPoint()))
                .build();
    }

    /**
     * 기본 BasicAuthenticationEntryPoint 는 sendError(401) 로 /error 에 포워딩하는데,
     * /error 는 메인 JWT 체인을 타서 403 으로 덮어써진다.
     * 그래서 포워딩 없이 401 + WWW-Authenticate 만 직접 내려 브라우저 로그인 창이 뜨게 한다.
     */
    private AuthenticationEntryPoint swaggerAuthenticationEntryPoint() {
        return (request, response, authException) -> {
            response.setHeader("WWW-Authenticate", "Basic realm=\"ToyVillage Swagger\"");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        };
    }

    private AuthenticationManager swaggerAuthenticationManager() {
        UserDetails swaggerUser = User.builder()
                .username(swaggerProperties.getUsername())
                .password(passwordEncoder.encode(swaggerProperties.getPassword()))
                .roles(SWAGGER_ROLE)
                .build();

        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider(new InMemoryUserDetailsManager(swaggerUser));
        provider.setPasswordEncoder(passwordEncoder);

        return new ProviderManager(provider);
    }

    @Bean
    public GroupedOpenApi webApi() {
        return GroupedOpenApi.builder()
                .group("web")
                .displayName("Web")
                .packagesToScan("com.command.toyvillage_server.domain.web")
                .build();
    }

    @Bean
    public GroupedOpenApi appApi() {
        return GroupedOpenApi.builder()
                .group("app")
                .displayName("App")
                .packagesToScan("com.command.toyvillage_server.domain.app")
                .build();
    }

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .servers(List.of(new Server().url("/")))
                .info(new Info()
                        .title("ToyVillage API")
                        .description("ToyVillage 웹/앱 서버 API 문서")
                        .version("v1"))
                .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME))
                .components(new Components()
                        .addSecuritySchemes(SECURITY_SCHEME_NAME,
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")));
    }
}
