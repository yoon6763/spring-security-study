package lab.spring.security.security;

import lab.spring.security.handler.MyAuthenticationFailureHandler;
import lab.spring.security.handler.MyAuthenticationSuccessHandler;
import lab.spring.security.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.GenericFilterBean;

@Configuration
@EnableWebSecurity
@Slf4j
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                // 토큰을 사용하는 방식이기 때문에 csrf 는 disable 처리
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)

                .sessionManagement(sessionManagement -> sessionManagement
                        // 세션을 사용하지 않기 때문에 STATELESS 로 설정
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeRequests(authorizeRequests -> authorizeRequests
                        // 토큰 발급 주소는 누구나 접근 가능
                        .requestMatchers("/members/login").permitAll()
                        .requestMatchers("/members/join").permitAll()
                        .requestMatchers("/test").permitAll()
                        // 그 외의 요청은 인증된 회원만 접근 가능
                        .anyRequest().permitAll())

//                .oauth2Login()
//                .userInfoEndpoint()
//                .userService(oAuth2UserService())
//                .and()
//                .and()
//
//                .oauth2Login()
//                .userInfoEndpoint()
//                .userService(customOAuth2UserService)
//                .and()
//                .successHandler(new MyAuthenticationSuccessHandler())
//                .failureHandler(new MyAuthenticationFailureHandler())
//
//                .and()
//                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
                .build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
