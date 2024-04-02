package com.example.spirngsecurityinaction.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    public RoleHierarchy roleHierarchy(){
        RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();

        hierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER\n" +
                "ROLE_B > BOLE_A");

        return hierarchy;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                /*
                 - permitAll - 모든 사용자에게 로그인 하지 않아도 허용
                 - hasRole - 특정한 규칙이 있어야 접근 허용
                 - hasAnyRole - 여러가지 규칙 설정
                 - authenticated - 로그인만 진행하면 접근 허용
                 - denyAll - 모든 이용자 접근 비허용
                 * */
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/", "/login", "/loginProc", "/signUp", "/signUpProc").permitAll()
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .requestMatchers("/my/**").hasAnyRole("ADMIN", "USER")
                        .anyRequest().authenticated()
                );

//        httpSecurity
//                .csrf((auth) -> auth.disable()
//                );

        httpSecurity
                .logout((auth) -> auth.logoutUrl("/logout")
                        .logoutSuccessUrl("/"));

        httpSecurity
                .formLogin((auth) -> auth.loginPage("/login")
                        .loginProcessingUrl("/loginProc")
                        .permitAll()
                );

        httpSecurity
                /*
                 - maximumSession() : 숫자 만큼 다중 로그인 허용 개수
                 - maxSessionPreventsLogin() : true || false 다중 로그인 개수를 초과하였을 경우 처리 방법
                 * true -> 초과시 새로운 로그인 차단
                 * false -> 초과시 기존 세션 하나 삭제
                 * */
                .sessionManagement((auth) -> auth
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(true)
                );

//        httpSecurity
//                // - httpBasic 방식 인증을 위한 설정
//                .httpBasic(Customizer.withDefaults());

        httpSecurity
                /*
                - sessionManagement().sessionFixation().none() : 로그인 시 세션 정보 변경 안함
                - sessionManagement().sessionFixation().newSession() : 로그인 시 세션 새로 생성
                - sessionManagement().sessionFixation().changeSessionId() : 로그인 시 동일한 세션에 대한 id 변경
                * */
                .sessionManagement((auth) -> auth
                        .sessionFixation().changeSessionId());


        return httpSecurity.build();
    }
}
