package com.itheima.exception;

import com.itheima.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 兜底：一切未被单独捕获的异常，避免返回 Spring 默认的 JSON 错误格式
     */
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        log.error("系统异常：", e);
        return Result.error("系统异常，请联系管理员");
    }

    /**
     * 请求参数类型转换失败，比如 gender=abc 无法转为 Short
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public Result handleTypeMismatch(MethodArgumentTypeMismatchException e) {
        log.warn("参数类型错误 — 参数名：{}，期望类型：{}", e.getName(), e.getRequiredType());
        return Result.error("请求参数格式错误");
    }

    /**
     * 请求体的 JSON 格式有误，比如少了引号、字段类型不匹配
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Result handleNotReadable(HttpMessageNotReadableException e) {
        log.warn("请求体 JSON 解析失败：{}", e.getMessage());
        return Result.error("请求体格式错误");
    }
}