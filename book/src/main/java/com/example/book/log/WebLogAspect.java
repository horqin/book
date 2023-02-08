package com.example.book.log;

import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.example.book.utils.Requests;
import com.google.gson.Gson;
import lombok.Data;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Aspect
@Component
@Order(1)
public class WebLogAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebLogAspect.class);

    @Pointcut("execution(public * com.example.book.controller.*.*(..))")
    public void webLog() {
    }

    @Around("webLog()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        WebLog webLog = new WebLog();

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();

        // 访问 IP
        webLog.setIp(Requests.getRequestIp(request));
        // 访问 ID
        if (StpUtil.isLogin()) {
            webLog.setId(StpUtil.getLoginIdAsLong());
        }
        // 访问方法
        webLog.setMethod(request.getMethod());
        // 访问路径
        webLog.setUrl(request.getRequestURL().toString());

        long start = System.currentTimeMillis();
 
        Object params = joinPoint.getArgs();
        Object result;
        try {
            result = joinPoint.proceed();
        } catch (RuntimeException e) {
            result = SaResult.error(e.getMessage());
        }

        // 访问参数
        webLog.setParam(new Gson().toJson(params));
        // 访问耗时
        webLog.setSpend(System.currentTimeMillis() - start);
        // 访问结果
        webLog.setResult(new Gson().toJson(result));

        LOGGER.info(new Gson().toJson(webLog));

        return result;
    }

    @Data
    static public class WebLog {
        private String  ip;
        private Long    id;
        private String  method;
        private String  url;
        private String  param;
        private Long    spend;
        private String  result;
    }
}
