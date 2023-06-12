权限管理系统

基于 React + Spring Boot + Spring Security + JWT 的 权限管理系统。

# 项目简介

一个基于RBAC（Role-Based Access Control）模型的通用权限控制系统。管理员可以编辑角色、权限和用户信息，查看登录情况。用户可以使用无权限要求或权限较低的管理模块，更新个人资料。

**项目演示地址**

http://13.229.229.154:8000/

![项目截图](https://img1.imgtp.com/2023/06/12/2fS6fxzd.png)


该项目的旧版仓库：

前端：https://github.com/imyuanxiao/rbac-frontend-old

旧版前端项目未使用脚手架，使用 React 原生项目 + Ant Design 进行搭建。

后端：https://github.com/imyuanxiao/rbac-backend-old

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

- 管理页面与接口（用户、角色、数据、个人中心）
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

# 待实现

1. WebStock消息通知
2. 用户登录状态（在线、离线、锁定）心跳监控

# 项目部署

服务器： AWS EC2

系统：AWS Linux 2023

## 环境设置

### Redis

1. 查找软件

```
sudo yum search "redis"
```

2. 安装Redis6

```
sudo yum install redis6.x86_64
或者
udo dnf install -y redis6
```

3. 启动Redis6

```
sudo systemctl start redis6
sudo systemctl enable redis6
```

4. 修改配置文件，设置密码

```bash
sudo vim /etc/redis6/redis6.conf
```

```
# 设置密码
requirepass your_password

# 仅用于测试，可以不改，允许所有IP访问
bind 0.0.0.0
```

5. 重启服务器

```bash
sudo systemctl restart redis6
```

6. 查看运行进程和端口

```
ps aux | grep redis
sudo lsof -i -P -n | grep <查到的PID>
```

### Mysql

**参考文档：AWS linux 2023 安装mysql8**

[https://awswithatiq.com/how-to-install-mysql-on-amazon-linux-2023-fedora-version/#Prerequisites](https://awswithatiq.com/how-to-install-mysql-on-amazon-linux-2023-fedora-version/#Prerequisites)

1. 终端运行以下命令

```
sudo wget https://dev.mysql.com/get/mysql80-community-release-el9-1.noarch.rpm 
sudo ls -lrt
sudo dnf install mysql80-community-release-el9-1.noarch.rpm
dnf repolist enabled | grep "mysql.*-community.*"
sudo dnf install mysql-community-server
sudo systemctl start mysqld
sudo mysql -V
```

2. 进入mysql，设置root密码为空，运行远程登录

```sql
mysql -u root -p
use mysql;
# 设置密码为空
UPDATE mysql.user SET authentication_string='' WHERE user='root' and host='localhost';
# 运行远程登录
UPDATE mysql.user SET host = '%' WHERE user = 'root';
FLUSH PRIVILEGES;
```

如果进入mysql提示需要输入密码，先去设置，在my.cnf增加：skip-grant-tables

```
sudo vi /etc/my.cnf
```

3. 退出mysql，运行脚本设置root密码

```
sudo mysql_secure_installation
```

4. 查看运行进程和端口

```sql
ps aux | grep mysql
sudo lsof -i -P -n | grep <查到的PID>
```

### Java

参考文档：

[Amazon Linux EC2 上的先决条件 - Amazon Neptune](https://docs.aws.amazon.com/zh_cn/neptune/latest/userguide/iam-auth-connect-prerq.html)

安装java8

```
sudo yum install java-1.8.0-devel
```

### nginx

1. 安装nginx

```
sudo yum install nginx -y
```

2. 启动nginx

```
sudo service nginx start
```

## 部署后端

1. IDEA里使用maven的package命令将项目打包成jar包（注意在打包前配置端口、数据库密码等）。

2. jar包上传至服务器，运行

```
nohup java -jar your-jar-file.jar > yourJarLog.txt &
```

![image.png](https://cdn.nlark.com/yuque/0/2023/png/29364238/1686599743947-c8a01ec2-39e2-48da-88a2-0c5aaac93de9.png#averageHue=%23233e52&clientId=u852bb88a-1acd-4&from=paste&height=60&id=u1dbd297f&originHeight=90&originWidth=892&originalType=binary&ratio=1.5&rotation=0&showTitle=false&size=77404&status=done&style=none&taskId=u082477a1-1a73-43f3-8a61-2d936b9f3b8&title=&width=594.6666666666666)

上图的1455266即为项目进程查看项目端口，默认为8080

```
sudo lsof -i -P -n | grep 1455266
```

## 部署前端

1. 打包成dist文件
2. 上传至服务器
3. 配置nginx

```
server {
    listen 8000;
    # server_name your.domain.com;

    root /var/www/your-project;
    index index.html index.htm;

    location / {
        try_files $uri $uri/ /index.html;
    }

    location ^~ /assets/ {
        gzip_static on;
        expires max;
        add_header Cache-Control public;
    }
    
    # 此处是配置反向代理，将前端请求转发到后端
    location /api/ {
        proxy_pass http://localhost:8080/;
        proxy_set_header Host $host;
    }
    
    error_page 500 502 503 504 /500.html;
    client_max_body_size 20M;
    keepalive_timeout 10;
}
```

4. 确保nginx有该路径的访问权限

```
sudo chown -R nginx:nginx /var/www
sudo chmod -R 755 /var/www
```

5. 服务器开放8000端口访问

这样就可以通过IP地址+端口号进行前端页面的访问了





