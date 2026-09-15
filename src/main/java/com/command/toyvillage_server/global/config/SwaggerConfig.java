package com.command.toyvillage_server.global.config;

import com.command.toyvillage_server.global.swagger.SwaggerProperties;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

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

    @Bean
    @Order(1)
    public SecurityFilterChain swaggerSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                .securityMatcher(SWAGGER_PATHS)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth.anyRequest().hasRole(SWAGGER_ROLE))
                .authenticationManager(swaggerAuthenticationManager())
                .httpBasic(Customizer.withDefaults())
                .build();
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
    public OpenAPI openAPI() {
        return new OpenAPI()
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
