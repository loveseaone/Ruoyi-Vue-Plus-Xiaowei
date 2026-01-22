package com.ruoyi.web.core.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ruoyi.common.config.RuoYiConfig;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

/**
 * OpenAPI3 配置
 * 
 * @author ruoyi
 */
@Configuration
public class OpenApiConfig
{
    /** 系统基础配置 */
    @Autowired
    private RuoYiConfig ruoyiConfig;

    /** 是否开启openapi */
    @Value("${swagger.enabled:true}")
    private boolean enabled;

    @Bean
    public OpenAPI createApi()
    {
        if (!enabled) {
            return null;
        }
        
        OpenAPI openApi = new OpenAPI();
        
        // 设置API信息
        openApi.info(new Info()
                .title("标题：若依管理系统_接口文档")
                .description("描述：用于管理集团旗下公司的人员信息,具体包括XXX,XXX模块...")
                .contact(new Contact().name(ruoyiConfig.getName()))
                .version(ruoyiConfig.getVersion()));
        
        // 设置安全方案
        openApi.components(new Components()
                .addSecuritySchemes("Authorization", 
                    new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")
                        .in(SecurityScheme.In.HEADER)
                        .name("Authorization")))
            .addSecurityItem(new SecurityRequirement().addList("Authorization"));
        
        return openApi;
    }
}