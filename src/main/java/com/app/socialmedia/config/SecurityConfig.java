package com.app.socialmedia.config;

import com.app.socialmedia.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.beans.factory.annotation.Value;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserService userService;

    @Value("${jwt.token.secret}")
    private String secretKey;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.httpBasic().disable() //ui 인증 disable (jwt token으로 인증 받을 것임)
                .csrf().disable()
                .cors().and()
                .authorizeRequests()
                .antMatchers("/api/v1/users/**").permitAll() //회원가입, 로그인은 항상 접근 가능
                .antMatchers(HttpMethod.POST, "/api/v1/posts").authenticated() // 글작성 post 요청이 왔을 때 인증된 사용자인지 확인
                .antMatchers(HttpMethod.GET, "/api/v1/posts").permitAll() // 글 리스트 조회 항상 접근 가능
                .antMatchers(HttpMethod.GET, "/api/v1/posts/{postId}").permitAll() //글 한 건 조회 항상 접근 가능

                .antMatchers(HttpMethod.POST, "/api/v1/posts/{postId}/comments").authenticated() // 댓글 작성 post 요청이 왔을 때 인증된 사용자인지 확인
                .antMatchers(HttpMethod.PUT, "/api/v1/posts/{postId}/comments").authenticated() // 댓글 수정
                .antMatchers(HttpMethod.GET, "/api/v1/posts/{postId}/comments").permitAll() //댓글 조회 누구나 가능

                .antMatchers(HttpMethod.POST, "/api/v1/posts/{postId}/likes").authenticated() // 좋아요 누르기
                .antMatchers(HttpMethod.GET, "/api/v1/posts/{postId}/likes").permitAll() // 좋아요 개수 조회
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(new JwtTokenFilter(userService, secretKey), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
