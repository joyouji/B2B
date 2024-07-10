package security.loginsecurity.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class WebSecurityConfig {

    private final UserDetailsService userDetailsService;

    // 정적 리소스에 대한 보안 요구 사항을 무시
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/login", "/signup", "/member", "/forgot_password","/reset_password").permitAll()  // 로그인, 회원가입 등의 경로는 인증 없이 접근 허용
                        .anyRequest().authenticated())  // 그 외 모든 요청은 인증 요구
                .formLogin(form -> form
                        .loginPage("/login")  // 사용자 정의 로그인 페이지
                        .loginProcessingUrl("/login")
                        .usernameParameter("email")

                        .defaultSuccessUrl("/home", true)
                        .permitAll())  // 로그인 성공 시 리다이렉트할 페이지

                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))  // 로그아웃 처리 경로
                        .logoutSuccessUrl("/login")  // 로그아웃 성공 시 리다이렉트할 페이지
                        .invalidateHttpSession(true)  // 세션 무효화
                        .deleteCookies("JSESSIONID")
                        .clearAuthentication(true))  // 쿠키 삭제
                .csrf(csrf -> csrf.disable())  // CSRF 보호 비활성화
                .build();
    }
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        // 비밀번호 인코더 설정 (BCryptPasswordEncoder 사용)
        authProvider.setPasswordEncoder(bCryptPasswordEncoder());
        return authProvider;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }





}
