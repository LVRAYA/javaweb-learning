package com.itheima.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itheima.context.BaseContext;
import com.itheima.pojo.Result;
import com.itheima.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.HashMap;
import java.util.Map;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    private final JwtUtils jwtUtils;
    private final ObjectMapper objectMapper;

    public LoginInterceptor(JwtUtils jwtUtils, ObjectMapper objectMapper) {
        this.jwtUtils = jwtUtils;
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1. 从请求头获取 token
        String token = request.getHeader("token");

        // 2. 校验 token
        Claims claims = jwtUtils.parseToken(token);
        if (token == null || token.isEmpty() || claims == null) {
            Result result = Result.error("NOT_LOGIN");
            String json = objectMapper.writeValueAsString(result);

            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(json);
            return false;
        }

        // 3. 校验通过 → 将用户信息存入 ThreadLocal，线程内任意位置都可取出
        Map<String, Object> user = new HashMap<>();
        user.put("id", claims.get("id"));
        user.put("username", claims.get("username"));
        user.put("name", claims.get("name"));
        BaseContext.set(user);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        // 请求结束，清理 ThreadLocal，防止内存泄漏
        BaseContext.remove();
    }
}