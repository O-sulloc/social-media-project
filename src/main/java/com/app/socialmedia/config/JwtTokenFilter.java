package com.app.socialmedia.config;

import com.app.socialmedia.service.UserService;
import com.app.socialmedia.utils.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter {

    private final UserService userService;
    private final String secretKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String authorization = request.getHeader(HttpHeaders.AUTHORIZATION); // 헤더에서 토큰 가져오기
        log.info("authorization:{}", authorization); // 토큰 찍어보기

        if (authorization == null || !authorization.startsWith("Bearer ")) {
            // 토큰 없거나 토큰이 Bearer로 시작하지 않으면 쫓아내기

            log.error("no authorization"); // 에러 찍어주고
            filterChain.doFilter(request, response); // 필터 거치게하고?
            return; // 쫓아내기
        }

        // authorization null 아니면 token 빼오기
        String token = authorization.split(" ")[1]; // authorization의 첫번째가 token

        // token이 만료됐는지 유효한지 확인하기
        if (JwtTokenUtil.isExpired(token, secretKey)) {
            //isExpired가 true라면 즉, 만료된 토큰이라면 쫓아냄

            log.error("만료된 token"); // 에러 찍어주고
            filterChain.doFilter(request, response); // 필터 거치게 하고?
            return; // 쫓아내기
        }

        // token에서 user_id 꺼내기
        String userName = JwtTokenUtil.getUserName(token, secretKey);
        //Long userId = JwtTokenUtil.getUserId(token, secretKey);
        log.info("userName:{}", userName);
        //log.info("userId:{}", userId);

        // 권한 부여
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userName, null, List.of(new SimpleGrantedAuthority("USER")));

        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }
}
