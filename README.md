权限管理系统

基于 React + Spring Boot + Spring Security + JWT 的 权限管理系统。管理员可以编辑角色、权限和用户信息，查看用户登录情况，用户可以查看数据和修改个人信息。



## 数据库设计



## 自定义userDetailService



## swagger

[Swagger UI](http://localhost:8080/swagger-ui/index.html)



## 登录

登录

- 账号密码登录
- 手机号验证码登录（新账号自动注册）

注册

- 账号密码注册
- 手机号验证码登录注册（未查询到则默认注册新账号）



# 注解扫描自动添加权限

```java
@Component
public class ApplicationStartup implements ApplicationRunner {
    @Autowired
    private RequestMappingInfoHandlerMapping requestMappingInfoHandlerMapping;
    @Autowired
    private PermissionService permissionService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 扫描并获取所有需要权限处理的接口资源(该方法逻辑写在下面)
        List<Permission> list = getAuthOnMethod();
        
        // 先删除所有操作权限类型的权限资源，待会再新增资源，以实现全量更新（注意哦，数据库中不要设置外键，否则会删除失败）
        permissionService.deletePermissionByType(0);
        permissionService.deletePermissionByType(1);

        // 如果权限资源为空，就不用走后续数据插入步骤
        if (!CollectionUtil.isEmpty(list)) {
            // 将资源数据批量添加到数据库
            permissionService.insertPermissions(list);
        }

    }

    /**
     * 扫描并返回所有需要权限处理的接口资源
     */
    private List<Permission> getAuthOnMethod() {
        // 接下来要添加到数据库的资源
        List<Permission> list = new LinkedList<>();
        // 拿到所有接口信息，并开始遍历
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingInfoHandlerMapping.getHandlerMethods();
        handlerMethods.forEach((info, handlerMethod) -> {
            // 拿到类(模块)上的权限注解
            Auth moduleAuth = handlerMethod.getBeanType().getAnnotation(Auth.class);
            // 拿到接口方法上的权限注解
            Auth methodAuth = handlerMethod.getMethod().getAnnotation(Auth.class);

            // 先添加类上的注解
            if (moduleAuth == null) {
                return;
            }
            Permission permissionOnClass = new Permission()
                    .setType(0).setPermissionName(moduleAuth.name()).setId(moduleAuth.id());
            if(!list.contains(permissionOnClass)){
                list.add(permissionOnClass);
            }

            // 再添加方法上的注解
            if (methodAuth == null) {
                return;
            }
            // 拿到该接口方法的请求方式(GET、POST等)
            Set<RequestMethod> methods = info.getMethodsCondition().getMethods();
            // 如果一个接口方法标记了多个请求方式，权限id是无法识别的，不进行处理
            if (methods.size() != 1) {
                return;
            }
            // 将请求方式和路径用`:`拼接起来，以区分接口。比如：GET:/user/{id}、POST:/user/{id}
            String url = methods.toArray()[0] + ":" + info.getPatternsCondition().getPatterns().toArray()[0];
            // 将权限名、资源路径、资源类型组装成资源对象，并添加集合中
            Permission permission = new Permission();
            permission.setType(1)
                    .setPath(url)
                    .setPermissionName(methodAuth.name())
                    .setId(moduleAuth.id() + methodAuth.id());
            list.add(permission);
        });
        return list;
    }
}
```



# 数据权限控制

通过分页拦截器MyPaginationInterceptor实现



# 分组校验

在校验规则里指定校验分组，例如 "groups = Update.class"

```java
@Data
public class UserAddRequest {

    @NotNull(message = "UserID is required.", groups = Update.class)
    private Long id;

    @NotBlank(message = "Account is required.", groups = CreateUser.class)
    @Length(min = 4, max = 20, message = "Account must be between 4-20 characters in length.")
    @Email(message = "Invalid account.")
    private String account;

    private List<Long> roleIds;

    public interface Update {}

    public interface CreateUser{}

}
```

使用@Validated注解指定校验分组，从而只会对分组内规则进行校验

```java
public class UserController {

	@PutMapping("/update")
    public String updateUser(@RequestBody @Validated(UserAddRequest.Update.class) UserAddRequest param) {
        userService.update(param);
        return ACTION_SUCCESSFUL;
    }
}
```



# 前端

通过Ant Design Pro脚手架初始化项目

[开始使用 - Ant Design Pro](https://pro.ant.design/zh-CN/docs/getting-started/#初始化)



## ProTable组件

[ProTable - 高级表格 - ProComponents (ant.design)](https://procomponents.ant.design/components/table)



## Token更新





# 待优化

## 状态监听

使用心跳机制或者WebStock监听用户在线、离线状态
