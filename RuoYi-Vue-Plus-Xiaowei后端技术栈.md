# RuoYi-Vue-Plus-Xiaowei 后端技术栈

## 项目概述

RuoYi-Vue-Plus-Xiaowei 是一个基于 Spring Boot 3.x 的现代化企业级管理系统，是在若依(RuoYi)基础上进行深度优化和升级的版本。该项目专注于将传统技术栈升级至现代化标准，支持 Java 17+，并逐步引入 JDK 21、虚拟线程等前沿技术。

## 核心技术栈

### 1. 基础框架

| 技术 | 版本 | 描述 |
|------|------|------|
| Spring Boot | 3.2.0 | 应用程序核心框架 |
| Spring Framework | 6.1.0 | 企业级应用开发框架 |
| Spring Security | 6.2.0 | 安全认证和授权框架 |
| Spring MVC | 6.1.0 | Web 应用开发框架 |
| Spring AOP | 6.1.0 | 面向切面编程 |
| Spring Data Redis | 3.2.0 | Redis 数据访问框架 |

### 2. Java 版本

| 技术 | 版本 | 描述 |
|------|------|------|
| Java | 17+ | 主要开发语言，支持 Java 17 及更高版本 |
| JVM | 支持虚拟线程 | 面向未来的并发处理能力 |

### 3. 数据持久层

| 技术 | 版本 | 描述 |
|------|------|------|
| MyBatis | 3.0.3 | ORM 框架，支持 Spring Boot 3.x |
| MyBatis-Spring-Boot-Starter | 3.0.3 | MyBatis 与 Spring Boot 整合 |
| PageHelper | 2.0.0 | 分页插件，支持 Spring Boot 3.x |
| Druid | 1.2.23 | 数据库连接池 |
| MySQL Driver | 8.0.33 | MySQL 数据库驱动 |
| MySQL | 5.7+/8.0+ | 关系型数据库 |

### 4. 规范与协议

| 技术 | 版本 | 描述 |
|------|------|------|
| Jakarta EE | 10 | 企业级 Java 规范（从 javax 迁移至 jakarta） |
| Servlet API | 6.0 | Web 应用规范 |
| JPA | 3.x | Java 持久化 API |
| JAX-B | 3.x | XML 绑定 API |

### 5. Web 容器

| 技术 | 版本 | 描述 |
|------|------|------|
| Tomcat | 10.1.16 | Web 容器，支持 Jakarta EE 10 |

### 6. 安全框架

| 技术 | 版本 | 描述 |
|------|------|------|
| Spring Security | 6.2.0 | 身份认证和授权框架 |
| JWT | 0.12.6 | JSON Web Token，用于身份验证 |
| JJWT | 2.0+ | JWT 实现库 |
| Kaptcha | 2.3.3 | 验证码生成工具 |

### 7. 缓存技术

| 技术 | 版本 | 描述 |
|------|------|------|
| Redis | - | 分布式缓存 |
| Lettuce | - | Redis 客户端 |
| Commons Pool2 | - | 对象池技术 |

### 8. 数据处理

| 技术 | 版本 | 描述 |
|------|------|------|
| Fastjson2 | 2.0.43 | 阿里 JSON 解析器 |
| Jackson | 2.15+ | JSON 数据绑定 |
| Apache POI | 4.1.2 | Excel 文件处理 |
| Hutool | - | Java 工具集合 |

### 9. 日志系统

| 技术 | 版本 | 描述 |
|------|------|------|
| SLF4J | - | 日志门面 |
| Logback | 1.4.14 | 日志实现 |

### 10. 系统监控

| 技术 | 版本 | 描述 |
|------|------|------|
| OSHI | 6.8.3 | 系统和硬件信息获取 |
| UserAgentUtils | 1.21 | 客户端信息解析 |

### 11. 开发工具

| 技术 | 版本 | 描述 |
|------|------|------|
| Maven | 3.6+ | 项目构建工具 |
| Lombok | - | 代码简化工具 |
| Validation | - | 参数校验框架 |

### 12. API 文档

| 技术 | 版本 | 描述 |
|------|------|------|
| Springfox | 3.0.0 | Swagger 集成，支持 OpenAPI 3 |

### 13. 定时任务

| 技术 | 版本 | 描述 |
|------|------|------|
| Quartz | - | 作业调度框架 |

### 14. 其他工具

| 技术 | 版本 | 描述 |
|------|------|------|
| Apache Commons Lang3 | - | 通用工具类 |
| Apache Commons IO | 2.11.0 | IO 工具类 |
| Velocity | 2.3 | 代码生成模板引擎 |

## 项目架构特点

### 1. 模块化设计
- ruoyi-admin: 系统入口模块
- ruoyi-common: 通用工具模块
- ruoyi-framework: 框架核心模块
- ruoyi-system: 系统业务模块
- ruoyi-generator: 代码生成模块
- ruoyi-quartz: 定时任务模块

### 2. 技术升级路径
- **已完成**: JDK 8 → JDK 17 兼容性改造
- **已完成**: Spring Boot 2.7.18 → 3.2.0 迁移
- **已完成**: 从 javax → jakarta 命名空间迁移
- **规划中**: JDK 21 & 虚拟线程集成
- **规划中**: 响应式编程支持（WebFlux）

### 3. 安全特性
- 基于 JWT 的无状态认证
- Spring Security 权限控制
- XSS 防护机制
- 防止重复提交
- 访问频率限制

### 4. 性能优化
- Redis 缓存集成
- Druid 连接池优化
- 分页查询优化
- 静态资源压缩

## 技术优势

1. **现代化架构**: 使用最新的 Spring Boot 3.x 和 Jakarta EE 10 规范
2. **高性能**: 支持虚拟线程，优化并发处理能力
3. **安全性**: 完善的安全框架和防护措施
4. **可扩展性**: 模块化设计，易于扩展和维护
5. **兼容性**: 保持与原版若依的兼容性
6. **易用性**: 提供丰富的工具和便捷的开发体验