package com.whomeow.whomeow.config;

import com.whomeow.whomeow.WhomeowApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackageClasses = WhomeowApplication.class)
public class FeignConfiguration {
}
