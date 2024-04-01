package com.example.spirngsecurityinaction.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/", "/login").permitAll()
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .requestMatchers("/my/**").hasAnyRole("ADMIN","USER")
                        .anyRequest().authenticated()
                );
        /*
         * permitAll - 모든 사용자에게 로그인 하지 않아도 허용
         * hasRole - 특정한 규칙이 있어야 접근 허용
         * hasAnyRole - 여러가지 규칙 설정
         * authenticated - 로그인만 진행하면 접근 허용
         * denyAll - 모든 이용자 접근 비허용
         * */
        return httpSecurity.build();
    }
}
