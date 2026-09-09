package com.command.toyvillage_server.global.config;

import com.command.toyvillage_server.global.security.jwt.JwtTokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final ObjectMapper objectMapper;
    private final JwtTokenProvider jwtTokenProvider;

    @Value("${cors.allowed-origins.main}")
    private String prodUrl;

    @Value("${cors.allowed-origins.dev}")
    private String stagUrl;

    @Value("${cors.allowed-origins.local}")
    private String localUrl;

//    @Value("${cors.allowed-origins.vercel-url}") // 버셀로 지환이가 배포하면 추가할 예정
//    private String vercelUrl;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    protected SecurityFilterChain configure(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                    // web auth
                    .requestMatchers(
                            "/web/auth/login", "/web/auth/signup", "/web/auth/reissue",
                            "/web/auth/password", "/web/auth/password/verification",
                            "/web/auth/password/verification/confirm"
                    ).permitAll()

                    // app auth
                    .requestMatchers("/app/auth/login", "/app/auth/reissue").permitAll()
                    .requestMatchers(HttpMethod.PATCH, "/app/auth/password")
                            .hasAnyRole("APP_ADMIN", "EMPLOYEE")
                    .requestMatchers("/app/admin", "/app/admin/**").hasRole("APP_ADMIN")

                    // task
                    .requestMatchers(HttpMethod.GET, "/tasks/*")
                            .hasAnyRole("APP_ADMIN", "EMPLOYEE")
                    .requestMatchers("/tasks", "/tasks/**")
                            .hasRole("APP_ADMIN")

                    // faq
                    .requestMatchers(HttpMethod.GET, "/faq", "/faq/**").permitAll()
                    .requestMatchers("/faq", "/faq/**").hasRole("WEB_ADMIN")

                    // file
                    .requestMatchers(HttpMethod.POST, "/file").permitAll()

                    // gallery
                    .requestMatchers(HttpMethod.GET, "/gallery", "/gallery/**").permitAll()
                    .requestMatchers("/gallery", "/gallery/**").hasRole("WEB_ADMIN")

                    // news
                    .requestMatchers(HttpMethod.GET, "/news", "/news/**").permitAll()
                    .requestMatchers("/news", "/news/**").hasRole("WEB_ADMIN")

                    // events
                    .requestMatchers(HttpMethod.GET, "/events", "/events/**").permitAll()
                    .requestMatchers("/events", "/events/**").hasRole("WEB_ADMIN")

                    // partnership
                    .requestMatchers(HttpMethod.POST, "/partnership").permitAll()
                    .requestMatchers("/partnership", "/partnership/**").hasRole("WEB_ADMIN")

                    // animal
                    .requestMatchers(HttpMethod.GET, "/animal", "/animal/**").permitAll()
                    .requestMatchers("/animal", "/animal/**").hasRole("WEB_ADMIN")

                    // popup
                    .requestMatchers(HttpMethod.GET, "/popup", "/popup/**").permitAll()
                    .requestMatchers("/popup", "/popup/**").hasRole("WEB_ADMIN")

                    // team settings
                    .requestMatchers("/team", "/team/**").hasRole("APP_ADMIN")
                    .requestMatchers("/join-team", "/join-team/**").hasRole("APP_ADMIN")

                    // reservation
                    .requestMatchers(HttpMethod.GET, "/reservation/employee", "/reservation/employee/**")
                            .hasRole("EMPLOYEE")
                    .requestMatchers("/reservation", "/reservation/**")
                            .hasRole("APP_ADMIN")

                    // close day
                    .requestMatchers(HttpMethod.GET, "/close-day", "/close-day/**").permitAll()
                    .requestMatchers("/close-day", "/close-day/**").hasRole("APP_ADMIN")

                    // open time
                    .requestMatchers(HttpMethod.GET, "/open-time", "/open-time/**").permitAll()
                    .requestMatchers("/open-time", "/open-time/**").hasRole("APP_ADMIN")

                    // notice
                    .requestMatchers(HttpMethod.GET, "/notice", "/notice/**")
                            .hasAnyRole("APP_ADMIN", "EMPLOYEE")
                    .requestMatchers("/notice", "/notice/**").hasRole("APP_ADMIN")

                    // documents
                    .requestMatchers(HttpMethod.GET, "/documents", "/documents/**")
                            .hasAnyRole("APP_ADMIN", "EMPLOYEE")
                    .requestMatchers("/documents", "/documents/**").hasRole("APP_ADMIN")

                    // animal manage
                    .requestMatchers(HttpMethod.GET, "/animal-manage", "/animal-manage/**")
                            .hasAnyRole("APP_ADMIN", "EMPLOYEE")
                    .requestMatchers("/animal-manage", "/animal-manage/**").hasRole("APP_ADMIN")

                    // feed log
                    .requestMatchers(HttpMethod.GET, "/feedLog").hasRole("APP_ADMIN")

                    // work log
                    .requestMatchers(HttpMethod.POST, "/work-log/template").hasRole("APP_ADMIN")
                    .requestMatchers(HttpMethod.DELETE, "/work-log/template/**").hasRole("APP_ADMIN")
                    .requestMatchers(HttpMethod.GET, "/work-log/template", "/work-log/template/**")
                            .hasAnyRole("APP_ADMIN", "EMPLOYEE")
                    .requestMatchers(HttpMethod.GET, "/work-log").hasRole("APP_ADMIN")
                    .requestMatchers(HttpMethod.GET, "/work-log/employee").hasRole("EMPLOYEE")
                    .requestMatchers(HttpMethod.GET, "/work-log/**")
                            .hasAnyRole("APP_ADMIN", "EMPLOYEE")
                    .requestMatchers(HttpMethod.DELETE, "/work-log/**")
                            .hasAnyRole("APP_ADMIN", "EMPLOYEE")
                    .requestMatchers("/work-log", "/work-log/**").hasRole("EMPLOYEE")

                    // work report
                    .requestMatchers(HttpMethod.PATCH,
                            "/work-report/approve/**", "/work-report/reject/**")
                            .hasRole("APP_ADMIN")
                    .requestMatchers(HttpMethod.GET,
                            "/work-report", "/work-report/detail/**")
                            .hasRole("APP_ADMIN")
                    .requestMatchers("/work-report", "/work-report/**")
                            .hasRole("EMPLOYEE")

                    .anyRequest().authenticated()
                )
                .with(new SecurityFilterConfig(jwtTokenProvider, objectMapper), Customizer.withDefaults())
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of(
                prodUrl, stagUrl, localUrl, "https://toyvillage.vercel.app/", "https://toyvillage.kr/", "http://localhost:5173/"
        ));

        configuration.setAllowedMethods(Arrays.asList("OPTIONS", "GET", "POST", "PUT", "PATCH", "DELETE"));
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
