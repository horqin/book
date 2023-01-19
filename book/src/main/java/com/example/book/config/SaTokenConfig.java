package com.example.book.config;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.filter.SaServletFilter;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SaTokenConfig {

    @Value("${properties.whitelist}")
    private String whitelist;

    @Bean
    public SaServletFilter saServletFilter() {
        return new SaServletFilter()
                .addInclude("/**")
                // 跨域检查
                .setBeforeAuth(auth -> SaHolder.getResponse().setHeader("X-XSS-Protection", "1; mode=block"))
                // 认证检查
                .setAuth(auth -> SaRouter.match("/**").notMatch(whitelist).check(StpUtil::checkLogin))
                // 异常处理
                .setError(e -> SaResult.error(e.getMessage()));
    }
}
