package ru.danilspirin.publishingcompany.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Optional;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((request) -> request
                        .requestMatchers("/books/create").hasAnyRole("ADMIN", "EDITOR")
                        .requestMatchers("/books/*", "/books").hasAnyRole("ADMIN", "MANAGER","EDITOR")
                        .requestMatchers("/books/*/edit", "/books/create", "/writers/**","/contracts/**")
                            .hasAnyRole("ADMIN", "EDITOR")
                        .requestMatchers("/customers/**","/orders/**").hasAnyRole("ADMIN", "MANAGER")
                        .requestMatchers("/report/*", "/report", "/").authenticated()
                        .requestMatchers("/auth").permitAll()
                        .anyRequest().permitAll()
                )
                .formLogin((form) -> form
                        .loginPage("/auth")
                        .defaultSuccessUrl("/")
                        .failureUrl("/auth?error")
                )
                .logout((logout) -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/auth")
                );

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return username -> {
            Optional<User> user = userRepository.findByUsername(username);
            if (user.isPresent()) {
                return user.get();
            }

            throw new UsernameNotFoundException("Пользователь не найден");
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
