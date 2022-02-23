package com.javaschool.microecare.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@Configuration
public class RequestLoggingFilterConfig {
    //TODO:
    // 1) mask password in payload and parameters
    // 2) add jsession id to logs properly
    // 3) log payload for patch (now it's logged in parameters, but payload is empty)
    // https://frandorado.github.io/spring/2018/11/15/log-request-response-with-body-spring.html
    // https://logback.qos.ch/manual/mdc.html

    @Bean
    public CommonsRequestLoggingFilter logFilter() {
        CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
        filter.setIncludeQueryString(true);
        filter.setIncludeClientInfo(true);
        filter.setIncludePayload(true);
        filter.setMaxPayloadLength(10000);
        filter.setIncludeHeaders(false);
        filter.setAfterMessagePrefix("REQUEST DATA : ");
        return filter;
    }
}
