package ru.fsv67.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {

        security.exceptionHandling(except -> except
                        .accessDeniedPage("/access")
                )
                .authorizeHttpRequests(config -> config
                        .requestMatchers("/ui/issuance").hasAuthority("admin")
                        .requestMatchers("/ui/reader").hasAuthority("reader")
                        .requestMatchers("/ui/book").authenticated()
                        .anyRequest().permitAll()
                )
                .formLogin(Customizer.withDefaults());
        return security.build();
    }
}
