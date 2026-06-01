# TLIAS 智能学习辅助系统

基于 Spring Boot 3 + MyBatis 的员工与部门管理系统，提供 RESTful API，支持 JWT 登录认证、阿里云 OSS 文件上传等功能。

## 技术栈

| 技术 | 版本/说明 |
|------|-----------|
| Java | 17 |
| Spring Boot | 3.5.14 |
| MyBatis | 3.0.4 (Spring Boot Starter) |
| MySQL | 8.x |
| PageHelper | 2.1.1 (分页插件) |
| JJWT | 0.12.6 (JWT 令牌) |
| Lombok | 最新 |
| 阿里云 OSS | 3.18.4 |

## 功能特性

- **部门管理** — 部门的增删改查
- **员工管理** — 员工的条件分页查询、增删改查
- **登录认证** — 基于 JWT 的用户登录，拦截器校验
- **文件上传** — 支持图片上传至阿里云 OSS
- **统一响应** — 统一的 JSON 响应格式 `{code, msg, data}`
- **全局异常处理** — `@RestControllerAdvice` 统一异常捕获

## 项目结构

```
src/main/java/com/itheima/
├── SpringbootMybatisCrudApplication.java  # 启动类
├── config/
│   ├── GlobalBindingAdvice.java           # 全局参数绑定
│   └── WebMvcConfigurer.java              # 拦截器注册
├── context/
│   └── BaseContext.java                   # ThreadLocal 上下文
├── controller/
│   ├── DeptController.java                # 部门接口
│   ├── EmpController.java                 # 员工接口
│   ├── LoginController.java               # 登录接口
│   └── UploadController.java              # 文件上传接口
├── exception/
│   └── GlobalExceptionHandler.java        # 全局异常处理
├── filter/
│   └── DemoFilter.java                    # 过滤器
├── interceptor/
│   └── LoginInterceptor.java              # 登录拦截器
├── mapper/
│   ├── DeptMapper.java / EmpMapper.java   # MyBatis Mapper 接口
│   ├── PeopleMapper.java
│   └── DeptLogMapper.java
├── pojo/
│   ├── Dept.java / Emp.java               # 实体类
│   ├── PageBean.java                      # 分页结果封装
│   └── Result.java                        # 统一响应
├── service/
│   ├── DeptService.java / EmpService.java  # 服务接口
│   └── impl/                              # 服务实现
└── utils/
    ├── AliOSSProperties.java              # OSS 配置属性
    ├── AliOSSUtils.java                   # OSS 上传工具
    └── JwtUtils.java                      # JWT 工具
```

## 快速开始

### 环境要求

- JDK 17+
- MySQL 8.0+
- Maven 3.6+

### 配置步骤

1. **克隆项目**

```bash
git clone https://github.com/LVRAYA/javaweb-learning.git
cd javaweb-learning/springboot-mybatis-crud
```

2. **配置数据库**

创建 `application.yml`（可参考模板，注意不要提交真实密钥）：

```yaml
server:
  port: 8080

spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/db01
    username: root
    password: 你的密码

mybatis:
  type-aliases-package: com.itheima.pojo
  mapper-locations: classpath:com/itheima/mapper/*.xml
  configuration:
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
    map-underscore-to-camel-case: true

# 阿里云 OSS（如需文件上传功能）
aliyun:
  oss:
    endpoint: oss-cn-beijing.aliyuncs.com
    bucketName: 你的Bucket
    accessKeyId: 你的AK
    accessKeySecret: 你的SK

# JWT
jwt:
  secret: 你的密钥（至少32字节）
  expiration: 43200000
```

3. **初始化数据库**

```sql
CREATE DATABASE IF NOT EXISTS db01;
-- 具体表结构参见 src/main/resources/com/itheima/mapper/*.xml 中的 SQL
```

4. **启动项目**

```bash
mvn spring-boot:run
```

5. **访问**

- 服务地址：`http://localhost:8080`
- API 文档：`src/main/resources/static/api接口文档.md`
- 文件上传测试页：`http://localhost:8080/upload.html`

## API 概览

> 详细文档见 [api接口文档.md](src/main/resources/static/api接口文档.md)

| 方法 | 路径 | 说明 |
|------|------|------|
| POST | `/login` | 用户登录，返回 JWT |
| GET | `/depts` | 查询所有部门 |
| GET | `/depts/{id}` | 按 ID 查询部门 |
| POST | `/depts` | 添加部门 |
| PUT | `/depts` | 修改部门 |
| DELETE | `/depts/{id}` | 删除部门 |
| GET | `/emps` | 条件分页查询员工 |
| GET | `/emps/{id}` | 按 ID 查询员工 |
| POST | `/emps` | 添加员工 |
| PUT | `/emps` | 修改员工 |
| DELETE | `/emps/{ids}` | 批量删除员工 |
| POST | `/upload` | 上传图片到 OSS |

> 除 `/login` 外，所有接口需在请求头携带 `token: <JWT令牌>`
