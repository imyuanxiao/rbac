权限管理系统

基于 React + Spring Boot + Spring Security + JWT 的 权限管理系统。

# 项目简介

一个基于RBAC（Role-Based Access Control）模型的通用权限控制系统。管理员可以编辑角色、权限和用户信息，查看登录情况。用户可以使用无权限要求或权限较低的管理模块，更新个人资料。

**项目演示地址**

http://13.229.229.154:8000/ (实例失效，待更新)

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

# 项目部署（基于Ununtu）

服务器： AWS EC2

系统：Ununtu

## 启动实例

1. 选择Ubuntu作为操作系统

![img](https://cdn.nlark.com/yuque/0/2023/png/29364238/1691504197112-6bc9799e-8c43-4515-83aa-b685dd753d13.png)

![img](https://cdn.nlark.com/yuque/0/2023/png/29364238/1691504224909-4dd67b74-b9d8-488b-bc81-c13eeca6c76b.png)

2. 创建安全组

![img](https://cdn.nlark.com/yuque/0/2023/png/29364238/1691504263479-81d67d53-112d-4691-943c-129a4e0ff3fb.png)

## 修改用户名密码

1. EC2 Instance Connect

![img](https://cdn.nlark.com/yuque/0/2023/png/29364238/1691499258492-7ff8dc86-f2da-40f6-bce6-b894a6cb869e.png)

2. 启用root账户，设置密码

```java
sudo passwd root
```

![img](https://cdn.nlark.com/yuque/0/2023/png/29364238/1691499352592-59a94673-0897-4462-8679-6c3d966eaff4.png)

3.切换root账户

```java
su root
```

![img](https://cdn.nlark.com/yuque/0/2023/png/29364238/1691499396229-4ed42da8-7bb3-4d36-94b2-1db6b9228a90.png)

4. 修改ssh配置文件中 PasswordAuthentication 后面 No 为 Yes，允许密码登陆

```java
vim /etc/ssh/sshd_config
```

![img](https://cdn.nlark.com/yuque/0/2023/png/29364238/1691499018770-02c88191-481d-4662-ab62-b76f04048b6b.png)

5. 重启SSH

```java
sudo /sbin/service sshd restart
```

6. 为ubuntu设置密码

```java
passwd ubuntu
```

![img](https://cdn.nlark.com/yuque/0/2023/png/29364238/1691499560162-e832d98a-4034-4020-820e-017fca1bc7fa.png)

## 使用Finalshell连接

1. 获取host，如图

![img](https://cdn.nlark.com/yuque/0/2023/png/29364238/1691499099158-7baa493e-83da-47c0-9ae4-a49a1258d7bb.png)

2. 用户名密码为刚刚修改的ubuntu

![img](https://cdn.nlark.com/yuque/0/2023/png/29364238/1691499208930-9a7a9e0c-96d5-46eb-ab72-03e50be4e863.png)

![img](https://cdn.nlark.com/yuque/0/2023/png/29364238/1691499151507-e12ec49b-8624-401c-84a5-9bee61a0eedd.png)

![img](https://cdn.nlark.com/yuque/0/2023/png/29364238/1691499240990-fbadc622-43c8-4ed0-8eca-be6002d73530.png)

3. 更新软件包列表

```java
sudo apt update
```

![img](https://cdn.nlark.com/yuque/0/2023/png/29364238/1691499734451-e58ca971-cf1a-40af-8949-4b3b5ad22a01.png)

![img](https://cdn.nlark.com/yuque/0/2023/png/29364238/1691499724112-236f606b-b630-42b1-8b1b-de252e0199e3.png)

## 安装Redis

1. 命令行安装

```java
sudo apt install redis-server
```

2. 直接回车

![img](https://cdn.nlark.com/yuque/0/2023/png/29364238/1691499798679-327ed9e4-7d2c-4e55-915d-7b3bd9ef47e7.png)

3. 查看redis启动情况

```java
sudo systemctl status redis-server
```

![img](https://cdn.nlark.com/yuque/0/2023/png/29364238/1691499837469-14457432-fd03-464f-9fe2-4b88b87c238e.png)

4. 修改redis配置，通过 requirepass 设置密码

```java
sudo vim /etc/redis/redis.conf
```

bind 0.0.0.0 表示任何IP均可访问，按需设置

![img](https://cdn.nlark.com/yuque/0/2023/png/29364238/1691499993126-bb9aab63-a4d7-4209-8451-c93ebcbf95b9.png)

5. 重启redis服务

```java
sudo systemctl restart redis-server
```

6. 测试密码连接

```java
redis-cli -a yourpassword
```

![img](https://cdn.nlark.com/yuque/0/2023/png/29364238/1691500124097-a25bfcf1-aa46-47c5-9ee7-af73099f22c3.png)

7. 查看redis端口，默认为6379

```java
sudo lsof -i -n -P | grep redis
```

![img](https://cdn.nlark.com/yuque/0/2023/png/29364238/1691500854516-199f695d-f4fa-422f-a0de-cc453c689f8e.png)

8. 设置 aws ec2 安全组，对外开放 6379 端口（测试完如果不需要对外暴露，记得修改规则）

![img](https://cdn.nlark.com/yuque/0/2023/png/29364238/1691500416344-c82224bc-62d9-43b7-9cb3-b471e69780ec.png)

9. 用 My Redis 等工具测试连接，Host为服务器公网IP（测试完如果不需要对外暴露，记得修改规则）

![img](https://cdn.nlark.com/yuque/0/2023/png/29364238/1691500547521-b7b24c5a-8433-442e-954b-98f0dad56306.png)

![img](https://cdn.nlark.com/yuque/0/2023/png/29364238/1691500569634-0de808ec-7eb5-4bdd-b55a-60862833cb57.png)

## 安装Mysql

1. 命令行安装Mysql

```java
sudo apt install mysql-server
```

![img](https://cdn.nlark.com/yuque/0/2023/png/29364238/1691500737056-7508408a-0087-40a7-951d-699a33ed7b49.png)

2. 查看运行状态

```java
sudo systemctl status mysql
```

![img](https://cdn.nlark.com/yuque/0/2023/png/29364238/1691500780793-44b1d2a5-2c16-4cc5-b8e5-b76354262bae.png)

3. 默认是监听 3306 端口

```java
sudo lsof -i -n -P | grep mysql
```

![img](https://cdn.nlark.com/yuque/0/2023/png/29364238/1691500838951-0e59578b-610a-4508-b98f-40d795b39df5.png)

4. 设置跳过 mysql 密码登录

```java
sudo vim /etc/mysql/my.cnf
```

显示下面文件，需要修改 /etc/mysql/mysql.conf.d/mysqld.cnf

![img](https://cdn.nlark.com/yuque/0/2023/png/29364238/1691501055241-6ee434d9-0188-4d6c-8176-e3c5bf4efea8.png)

```java
sudo vim /etc/mysql/mysql.conf.d/mysqld.cnf
    
[mysqld]
skip-grant-tables #密码设置好后注释掉该设置
bind-address = 0.0.0.0 #外部访问测试好后注释掉该设置
```

如果需要外部访问数据库进行测试，需要设置 bind 为 0.0.0.0

![img](https://cdn.nlark.com/yuque/0/2023/png/29364238/1691501213595-24268908-4aa8-408b-8048-334b78d000d6.png)

5. 重启服务

```java
sudo systemctl restart mysql
```

6. 重设root密码，允许远程登录 root 账户

```java
mysql -u root -p

# 使用数据库
use mysql;

# 设置密码为空
UPDATE mysql.user SET authentication_string='' WHERE user='root' and host='localhost';

# 运行远程登录
UPDATE mysql.user SET host = '%' WHERE user = 'root';

# 修改root身份验证方式，并设置新密码
ALTER USER 'root'@'%' IDENTIFIED WITH mysql_native_password BY '123456';
FLUSH PRIVILEGES;
```

7. 设置安全组，对外开放3306端口（测试完如果不需要对外暴露，记得修改规则）

![img](https://cdn.nlark.com/yuque/0/2023/png/29364238/1691501426040-d23cf654-cf09-4eaa-8410-20b9a10b67d0.png)

8. 测试连接（测试完如果不需要对外暴露，记得修改规则）

![img](https://cdn.nlark.com/yuque/0/2023/png/29364238/1691502887397-19c5c1e1-db50-4997-85d9-2932c2a5bd02.png)

## 安装Java

1. 命令行安装

```java
sudo apt install openjdk-17-jdk
```

2. 查看版本

```java
java -version
```

![img](https://cdn.nlark.com/yuque/0/2023/png/29364238/1691503174094-af17f4aa-5ee4-493d-ad4e-5bacd9ae4bee.png)

## 部署后端

可自行按照之前配置的用户名密码等配置yml文件，并打成jar包

1. 通过 finalShell 上传jar包至服务器

![img](https://cdn.nlark.com/yuque/0/2023/png/29364238/1691503374370-59316c2d-587d-41fd-8d4b-29ae2e338787.png)

2. 命令行运行（后台运行，输出日志到目标文件）

```java
nohup java -jar your-jar-file.jar > yourJarLog.txt &
```

3. 查看运行情况

![img](https://cdn.nlark.com/yuque/0/2023/png/29364238/1691504074343-3324b188-482f-4b75-a090-c69c7e9a1bab.png)

4. 对外开放项目端口（测试完如果不需要对外暴露，记得修改规则）

![img](https://cdn.nlark.com/yuque/0/2023/png/29364238/1691504323624-d56b3f5d-30d9-4da2-a97f-d218a7b69324.png)

5. 测试连接（测试完如果不需要对外暴露，记得修改规则）

![img](https://cdn.nlark.com/yuque/0/2023/png/29364238/1691504100169-56d7263e-a910-4035-8241-d1ae80499e91.png)

## 部署前端

待更新

# 项目部署（基于Amazon Linux）

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





