# RuoYi-Vue-Plus-Xiaowei

### 开启 RuoYi-Vue 的现代化进化之路 (JDK 8 → 17 → 21)

> **声明**：本项目不是简单的版本搬运，而是一场关于 RuoYi-Vue 的"架构手术"。我们旨在探索在不破坏若依原生生态的前提下，如何平滑引入 **JDK 21、虚拟线程、Spring Boot 3** 等前沿技术。

---

## 🚀 为什么会有这个项目？

很多开发者在面对 RuoYi-Vue 二开时，常被困在 JDK 8 的舒适区，想升级却怕"满屏报红"，想引入新技术却不知从何下手。

**Ruoyi-Vue-Plus-Xiaowei** 是公众号 **《SpringBoot之小韦》** 的官方连载项目。我们将通过这个仓库，手把手带你完成：

* **[已完成]** 核心架构的深度解密与模块化治理。
* **[进行中]** 从 JDK 8 到 JDK 17 的平滑兼容性迁移。
* **[计划中]** 迈向 JDK 21，全面拥抱虚拟线程（Virtual Threads）与高性能内核。
* **[计划中]** 引入多租户、数据脱敏、前后端用户分离等企业级增强插件。

---

## 🌟 核心特色

* **平滑演进**：通过 Git Tag 记录每一个关键节点，你可以随时回溯到升级的任何阶段。
* **硬核重构**：不改前端、不动底座，仅通过后端内核的"无痛手术"实现性能倍增。
* **企业实战**：集成"前后端用户分离"、"设备登录控制"、"行为验证码"等高频私活需求。
* **教学导向**：代码提交记录（Commit Message）即是教程，配合公众号文章食用效果极佳。

---

## 🛠️ 技术栈升级路线

### 已完成升级
* **JDK 17 兼容性改造**：完成核心依赖对齐，解决 OSHI 库 API 变更、Springfox 兼容性等问题
* **Spring Boot 2.7.18**：适配最新 LTS 版本，确保安全性与性能
* **Logback 1.2.12**：降级以确保 JDK 17 兼容性
* **Tomcat 9.0.82**：修正版本兼容性问题
* **注解处理器兼容性**：解决 `javax.annotation.meta.When.MAYBE` 等兼容性问题

### 计划升级
* **JDK 21 & 虚拟线程集成**：全面拥抱现代化 Java 特性
* **Spring Boot 3.x 迁移**：迁移到 Jakarta 命名空间
* **虚拟线程性能优化**：利用虚拟线程提升 I/O 密集型操作性能
* **响应式编程支持**：引入 WebFlux 支持高并发场景

---

## 📈 进化里程碑

### 2025 基建版
* ✅ Spring Security 深度解析与定制
* ✅ 用户分离架构设计
* ✅ 多端登录控制实现
* ✅ 企业级权限治理

### 2026 进化版（当前）
* ✅ **Phase 1**: [JDK 17 兼容性改造](https://github.com/loveseaone/Ruoyi-Vue-Plus-Xiaowei) —— 已完成核心依赖对齐
* 🔄 **Phase 2**: [Jakarta 命名空间迁移] —— 筹备中
* 📋 **Phase 3**: [JDK 21 & 虚拟线程集成] —— 终极目标
* 📋 **Phase 4**: [响应式编程支持] —— 规划中

---

## 📚 学习资源

### JDK 升级指南
* [JDK 1.8 到 JDK 17 升级操作手册](./md/chapters/jdk-upgrade-guide.md) - 详细记录了升级过程中的所有步骤和解决方案
* [Spring Security 设计实践](./md/chapters/spring-security-design-practice.md) - 深入解析权限框架设计
* [框架设计原则](./md/chapters/framework-design-principles.md) - 企业级架构设计准则

---

## 🚀 快速开始

### 环境要求
* JDK 17+
* Maven 3.6+
* MySQL 5.7+ / 8.0+
* Redis

### 项目启动
1. 克隆项目：
   ```bash
   git clone https://github.com/loveseaone/Ruoyi-Vue-Plus-Xiaowei.git
   ```

2. 导入数据库脚本（位于 sql 目录）

3. 修改配置文件中的数据库连接信息

4. 启动项目：
   ```bash
   cd RuoYi-Vue-Plus-Xiaowei
   mvn clean install
   mvn spring-boot:run
   ```

---

## 🤝 贡献与支持

如果你觉得这个项目对你有帮助，请点一个 **Star**，这是我持续更新的最大动力！

* **作者**：小韦
* **公众号**：**SpringBoot之小韦**（扫码获取《若依平滑升级一键 CheckList》）
* **技术交流**：欢迎提交 Issue 或 Pull Request，一起构建最现代化的若依生态。

---

## 📄 许可证

本项目基于 MIT 许可证，允许商业使用，但请保留版权信息。

---

> **RuoYi-Vue-Plus-Xiaowei** - 让若依项目跟上时代步伐，成为现代化企业级开发的坚实基础。

---

## 📚 Swagger文档访问

### 访问方式

在Ruoyi-Vue-Plus-Xiaowei项目中，访问Swagger文档有以下几种方式：

#### 1. 通过前端界面访问（推荐）

在项目的前端界面中，您可以直接通过菜单访问Swagger文档：
- 登录系统后，在左侧菜单中找到 `系统工具` → `系统接口`，点击即可访问Swagger界面。

#### 2. 直接URL访问

根据配置，您可以通过以下URL直接访问Swagger文档：
- `http://localhost:8080/swagger-ui/index.html` （后端直接访问）

### 注意事项

- 项目中Swagger功能默认是关闭的，如需开启，请在 `ruoyi-admin/src/main/resources/application.yml` 文件中修改配置：
  ```yaml
  swagger:
    # 是否开启swagger
    enabled: true  # 将此项从 false 改为 true
  ```

- 访问时请确保后端服务已启动（默认端口8080）

---

## 📞 联系方式

<div align="center">
  <table>
    <tr>
      <td align="center">
        <strong>个人微信</strong><br>
        <img src="./doc/images/personal-wechat.jpg" width="200" alt="个人微信">
      </td>
      <td align="center">
        <strong>微信公众号</strong><br>
        <img src="./doc/images/wechat-mp.jpg" width="200" alt="微信公众号">
      </td>
    </tr>
  </table>
</div>
