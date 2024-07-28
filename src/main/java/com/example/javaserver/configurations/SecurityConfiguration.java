package com.example.javaserver.configurations;

import com.example.javaserver.entrypoints.ProblemDetailsAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {
    @Bean
    SecurityFilterChain appSecurity(HttpSecurity http,
                                    ProblemDetailsAuthenticationEntryPoint entryPoint) throws Exception {
        http
                .authorizeHttpRequests((authorize) ->
                                authorize.anyRequest().authenticated()
//                                .requestMatchers("**/auth/**").permitAll()
//                                .requestMatchers("**/health/**").authenticated()
                )
                .oauth2ResourceServer((oauth2) -> oauth2
                        .authenticationEntryPoint(entryPoint)
                        .jwt(Customizer.withDefaults())
                );
        return http.build();
    }
}
