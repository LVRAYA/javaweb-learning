# 需求文档 — TLIAS 智能学习辅助系统

## 1. 功能需求

### 1.1 部门管理

| 编号 | 功能 | 描述 |
|------|------|------|
| F-DEPT-01 | 部门列表查询 | 查询所有部门，返回部门 ID、名称、创建/修改时间 |
| F-DEPT-02 | 按 ID 查询部门 | 根据部门 ID 查询单个部门详情 |
| F-DEPT-03 | 添加部门 | 新增部门，需提供部门名称 |
| F-DEPT-04 | 修改部门 | 根据 ID 修改部门名称 |
| F-DEPT-05 | 删除部门 | 根据 ID 删除部门（级联删除关联员工） |

### 1.2 员工管理

| 编号 | 功能 | 描述 |
|------|------|------|
| F-EMP-01 | 员工分页查询 | 支持按姓名、性别、入职日期范围进行条件分页查询 |
| F-EMP-02 | 按 ID 查询员工 | 根据员工 ID 查询单个员工详情 |
| F-EMP-03 | 添加员工 | 新增员工，包含用户名、姓名、性别、部门、职位、入职日期等 |
| F-EMP-04 | 修改员工 | 根据 ID 修改员工信息 |
| F-EMP-05 | 批量删除员工 | 根据 ID 数组批量删除员工 |

### 1.3 登录认证

| 编号 | 功能 | 描述 |
|------|------|------|
| F-AUTH-01 | 用户登录 | 用户名 + 密码登录，成功返回 JWT 令牌 |
| F-AUTH-02 | JWT 鉴权 | 后续请求需在 Header 携带 `token`，拦截器校验有效性 |
| F-AUTH-03 | 未登录拦截 | 未携带有效令牌时返回 `{code:0, msg:"NOT_LOGIN"}` |

### 1.4 文件上传

| 编号 | 功能 | 描述 |
|------|------|------|
| F-UPLOAD-01 | 图片上传 | 支持 multipart/form-data 上传图片到阿里云 OSS |
| F-UPLOAD-02 | 返回 URL | 上传成功后返回图片的 OSS 访问 URL |

---

## 2. 非功能需求

### 2.1 统一响应格式

所有接口返回统一 JSON 结构：

```json
{
  "code": 1,      // 1=成功, 0=失败
  "msg": "success",
  "data": {}
}
```

### 2.2 分页查询

- 员工列表支持分页，默认页码=1，每页=10 条
- 使用 PageHelper 插件实现物理分页
- 返回 `{total, rows}` 结构

### 2.3 全局异常处理

- 使用 `@RestControllerAdvice` 统一处理异常
- 异常时返回 `{code:0, msg: "错误描述"}`

### 2.4 日志记录

- Controller 层使用 Slf4j 记录关键操作日志
- 参数绑定日志、登录日志、CRUD 操作日志

### 2.5 操作日志持久化（DeptLog）

- 部门操作（新增/删除）时自动记录日志到数据库
- 使用 AOP 或 service 层手动记录

---

## 3. 技术约束

| 约束项 | 要求 |
|--------|------|
| 开发语言 | Java 17+ |
| 框架 | Spring Boot 3.5.x |
| 数据库 | MySQL 8.0+ |
| ORM | MyBatis 3.x |
| 分页 | PageHelper 2.x |
| 认证 | JWT（JJWT 0.12.x, HS256） |
| 对象存储 | 阿里云 OSS |
| 构建工具 | Maven 3.6+ |

---

## 4. 数据库表

### dept（部门表）

| 字段 | 类型 | 说明 |
|------|------|------|
| id | int | 主键，自增 |
| name | varchar | 部门名称 |
| create_time | datetime | 创建时间 |
| update_time | datetime | 修改时间 |

### emp（员工表）

| 字段 | 类型 | 说明 |
|------|------|------|
| id | int | 主键，自增 |
| username | varchar | 用户名 |
| password | varchar | 密码 |
| name | varchar | 姓名 |
| gender | smallint | 性别（1=男, 2=女） |
| image | varchar | 头像 URL |
| job | smallint | 职位（1=班主任,2=讲师,3=学工主管,4=教研主管,5=咨询师） |
| entrydate | date | 入职日期 |
| dept_id | int | 部门 ID（外键关联 dept） |
| create_time | datetime | 创建时间 |
| update_time | datetime | 修改时间 |

### dept_log（操作日志表）

| 字段 | 类型 | 说明 |
|------|------|------|
| id | int | 主键，自增 |
| create_time | datetime | 操作时间 |
| description | varchar | 操作描述 |
