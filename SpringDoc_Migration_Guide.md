# Springfox Swagger 迁移到 SpringDoc OpenAPI 3 技术指南

## 1. 概述

本文档详细介绍了如何将基于 Spring Boot 的项目从 Springfox Swagger 迁移到 SpringDoc OpenAPI 3，特别是针对 Spring Boot 3.x 和 JDK 21 的现代化升级场景。

### 1.1 迁移背景

- **Springfox Swagger** 已停止维护，不支持 Spring Boot 3.x
- **Spring Boot 3.x** 迁移到 Jakarta EE，而 Springfox 仍使用 javax 命名空间
- **SpringDoc OpenAPI** 是当前推荐的解决方案，支持 OpenAPI 3 规范

### 1.2 迁移目标

- 移除所有 Springfox 依赖和注解
- 使用 SpringDoc OpenAPI 3 替代
- 保持 API 文档功能完整性
- 确保与 JDK 21 和 Spring Boot 3.x 兼容

## 2. 准备工作

### 2.1 项目兼容性检查

在开始迁移前，确认项目版本：

```xml
<!-- 确保 Spring Boot 版本 >= 3.0.0 -->
<spring-boot.version>3.2.0</spring-boot.version>
<!-- 确保 Java 版本 >= 17 (推荐 JDK 21) -->
<java.version>21</java.version>
```

### 2.2 备份项目

```bash
git checkout -b backup-pre-migration
```

## 3. 依赖更新

### 3.1 主 POM 文件更新

移除 Springfox 依赖：

```xml
<!-- 移除 -->
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-boot-starter</artifactId>
    <version>${swagger.version}</version>
</dependency>
```

添加 SpringDoc 依赖：

```xml
<properties>
    <!-- 推荐使用 2.2.0 或更高版本 -->
    <springdoc.openapi.version>2.2.0</springdoc.openapi.version>
</properties>

<dependencies>
    <!-- SpringDoc OpenAPI3依赖 -->
    <dependency>
        <groupId>org.springdoc</groupId>
        <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
        <version>${springdoc.openapi.version}</version>
    </dependency>
</dependencies>
```

### 3.2 模块级 POM 文件更新

在每个使用 Swagger 的模块（如 admin 模块）中执行相同操作：

```xml
<!-- 在 ruoyi-admin/pom.xml 中 -->
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
</dependency>
```

## 4. 配置类更新

### 4.1 移除旧配置类

删除基于 Springfox 的配置类（如 `SwaggerConfig.java`）：

```bash
rm src/main/java/com/yourcompany/config/SwaggerConfig.java
```

### 4.2 创建新配置类

创建基于 OpenAPI 3 的配置类：

```java
package com.yourcompany.config;

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
 */
@Configuration
public class OpenApiConfig
{
    @Autowired
    private RuoYiConfig ruoyiConfig;

    @Value("${springdoc.api-docs.enabled:true}")
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
                .title("您的API标题")
                .description("API描述")
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
```

## 5. 注解替换对照表

### 5.1 控制器级别注解

| Springfox Swagger | SpringDoc OpenAPI 3 |
|-------------------|---------------------|
| `@Api(tags = "标签名")` | `@Tag(name = "标签名", description = "描述")` |

### 5.2 方法级别注解

| Springfox Swagger | SpringDoc OpenAPI 3 |
|-------------------|---------------------|
| `@ApiOperation(value = "描述")` | `@Operation(summary = "描述")` |
| `@ApiImplicitParam(...)` | `@Parameter(description = "...", required = true)` |
| `@ApiImplicitParams({...})` | 多个 `@Parameter` 或使用 `@Parameters({@Parameter(...)})` |

### 5.3 模型类注解

| Springfox Swagger | SpringDoc OpenAPI 3 |
|-------------------|---------------------|
| `@ApiModel(value = "模型名", description = "描述")` | `@Schema(name = "模型名", description = "描述")` |
| `@ApiModelProperty(value = "字段描述")` | `@Schema(description = "字段描述")` |

### 5.4 请求体注解

| Springfox Swagger | SpringDoc OpenAPI 3 |
|-------------------|---------------------|
| `@ApiParam` | `@Parameter` |
| 特定请求体注解 | `@io.swagger.v3.oas.annotations.parameters.RequestBody` |

## 6. 代码示例

### 6.1 控制器迁移示例

**迁移前（Springfox）：**
```java
@Api("用户信息管理")
@RestController
@RequestMapping("/test/user")
public class TestController {
    
    @ApiOperation("获取用户列表")
    @GetMapping("/list")
    public R<List<UserEntity>> userList() {
        // ...
    }
    
    @ApiOperation("获取用户详细")
    @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "int", paramType = "path")
    @GetMapping("/{userId}")
    public R<UserEntity> getUser(@PathVariable Integer userId) {
        // ...
    }
}
```

**迁移后（SpringDoc）：**
```java
@Tag(name = "用户信息管理", description = "用户信息管理")
@RestController
@RequestMapping("/test/user")
public class TestController {
    
    @Operation(summary = "获取用户列表")
    @GetMapping("/list")
    public R<List<UserEntity>> userList() {
        // ...
    }
    
    @Operation(summary = "获取用户详细")
    @GetMapping("/{userId}")
    public R<UserEntity> getUser(@Parameter(description = "用户ID", required = true) @PathVariable Integer userId) {
        // ...
    }
}
```

### 6.2 模型类迁移示例

**迁移前（Springfox）：**
```java
@ApiModel(value = "UserEntity", description = "用户实体")
class UserEntity {
    @ApiModelProperty("用户ID")
    private Integer userId;
    // ...
}
```

**迁移后（SpringDoc）：**
```java
@Schema(name = "UserEntity", description = "用户实体")
class UserEntity {
    @Schema(description = "用户ID")
    private Integer userId;
    // ...
}
```

## 7. 配置文件更新

### 7.1 application.yml 更新

**迁移前：**
```yaml
# Swagger配置
swagger:
  enabled: true
  pathMapping: /dev-api
```

**迁移后：**
```yaml
# OpenAPI配置
springdoc:
  swagger-ui:
    enabled: true  # 启用Swagger UI
  api-docs:
    enabled: true  # 启用API文档
  show-actuator: true
  # 可选配置
  paths-to-match: # 限制文档显示的路径
    - /api/**
```

### 7.2 资源处理器更新

如果在资源处理器中配置了 Swagger 资源路径，需要更新：

```java
@Override
public void addResourceHandlers(ResourceHandlerRegistry registry) {
    // 旧的 Springfox 路径
    // .addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/")
    
    // 新的 SpringDoc 路径
    registry.addResourceHandler("/swagger-ui/**")
            .addResourceLocations("classpath:/META-INF/resources/webjars/springdoc-openapi-ui/")
            .setCacheControl(CacheControl.maxAge(Duration.ofHours(5)).cachePublic());
}
```

## 8. 常见问题及解决方案

### 8.1 依赖冲突

**问题：** 迁移后出现依赖冲突或类找不到错误
**解决方案：** 
- 彻底移除所有 Springfox 相关依赖
- 清理 Maven 仓库缓存
- 使用 `mvn dependency:tree` 检查依赖树

### 8.2 注解导入错误

**问题：** 旧注解仍被导入
**解决方案：** 
- 使用 IDE 的查找替换功能全局搜索
- 确保导入的是 `io.swagger.v3.oas.annotations` 包

### 8.3 时间单位问题

**问题：** `TimeUnit` 在 Spring Boot 3 中使用不兼容
**解决方案：** 
- 使用 `java.time.Duration` 替代 `TimeUnit`
- 例如：`Duration.ofHours(5)` 替代 `TimeUnit.HOURS`

### 8.4 访问路径变化

**问题：** 迁移后无法访问 API 文档
**解决方案：** 
- Springfox: `/swagger-ui.html` 或 `/swagger-ui/`
- SpringDoc: `/swagger-ui.html` 或 `/swagger-ui/index.html`
- API 文档: `/v3/api-docs` (取代 `/v2/api-docs`)

## 9. 测试验证

### 9.1 构建测试

```bash
mvn clean compile
```

### 9.2 启动测试

```bash
mvn spring-boot:run
```

### 9.3 访问验证

访问以下 URL 验证迁移成功：
- Swagger UI: `http://localhost:8080/swagger-ui/index.html`
- API 文档: `http://localhost:8080/v3/api-docs`

## 10. 总结

Springfox 到 SpringDoc 的迁移是现代化 Spring Boot 应用的必要步骤。虽然迁移过程中需要更新大量注解和配置，但 SpringDoc 提供了更好的性能、更活跃的维护和对最新 Spring Boot 版本的更好支持。

通过遵循本指南，您可以顺利完成迁移，同时享受 OpenAPI 3 规范带来的优势。

---
*本文档基于 Ruoyi-Vue-Plus-Xiaowei 项目迁移实践经验编写*