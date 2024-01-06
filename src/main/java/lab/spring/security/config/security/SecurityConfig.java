package lab.spring.security.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable) // REST API는 csrf 보안이 필요 없으므로 비활성화

                .sessionManagement(sessionManagement -> sessionManagement
                        // 세션을 사용하지 않기 때문에 STATELESS 로 설정
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeRequests(authorizeRequests -> authorizeRequests
                        // 토큰 발급 주소는 누구나 접근 가능
                        .requestMatchers("/sign-api/sign-in", "/sign-api/sign-up",
                                "/sign-api/exception").permitAll() // 가입 및 로그인 주소는 허용'
                        .requestMatchers(HttpMethod.GET, "/board/**").permitAll() // board로 시작하는 Get 요청은 허용
                        .requestMatchers("/test").permitAll()
                        // 그 외의 요청은 인증된 회원만 접근 가능
                        .anyRequest().authenticated())
//                .and()
//                .exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler())
//                .and()
//                .exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint())

                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
                        UsernamePasswordAuthenticationFilter.class) // JWT Token 필터를 id/password 인증 필터 이전에 추가
                .build();
    }
}