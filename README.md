权限管理系统

基于 React + Spring Boot + Spring Security + JWT 的 权限管理系统。

# 项目简介

一个基于RBAC（Role-Based Access Control）模型的通用权限控制系统。管理员可以编辑角色、权限和用户信息，查看登录情况。用户可以使用无权限要求或权限较低的管理模块，更新个人资料。

![image-20230611230304066](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20230611230304066.png)

# 技术选型

## 前端

- React 18
- Ant Design Pro 5.x 脚手架
- Ant Design & Procomponents 组件库
- Umi 4 前端框架
- OpenAPI 前端代码生成

## 后端

- Java Spring Boot
- MySQL 数据库
- Redis 数据库
- MyBatis-Plus 及 MyBatis X 自动生成
- Spring Security 权限控制
- JWT token 鉴权
- Swagger 接口文档生成
- Hutool等工具库

# 已实现

## 前端

- 管理页面与接口（用户、角色、数据）
- token自动检查与更新
- 全局异常处理
- 全局响应拦截器
- 全局请求拦截器
- 国际化设置
- openAPI

## 后端

- 数据库设计
- 通过注解进行参数校验
  - 分组校验
- 配置 swagger 接口文档
- 统一响应体
  - 自定义数据统一响应体
  - 自定义业务响应码
- 全局异常处理
  - 自定义异常类
  - 全局异常处理类
- 自定义注解
  - 拓展错误码和响应信息 @ExceptionCode
  - 绕过统一响应体注解 @NotResponseBody
- 配置 JWT + Redis
  - 使用 hutools 构建 JWTUtil
  - 自定义 Redis 工具类
- 通过注解管理接口权限（操作权限）
  - 权限注解 @Auth
  - 通过接口扫描自动添加权限至数据库
- 自定义 sql 拦截器（数据权限）
  - MyPaginationInterceptor
- spring security 登录认证
  - 自定义UserDetailService
  - 登录过滤器 LoginFilter
  - 鉴权过滤器 AuthFilter
  - 鉴权规则源（SecurityMetadataSource）
  - 自定义访问决策管理器（AccessDecisionManager）
  - 自定义授权错误处理器（AccessDeniedHandler）
  - 自定义认证异常处理类（AuthenticationEntryPoint）
  - 配置类
- 各业务功能模块

# 待更新

1. 个人中心
2. 用户管理模块中的用户详情（userProfile）
3. WebStock消息通知
4. 用户登录状态（在线、离线、锁定）心跳监控











