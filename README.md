# Quiet

## 编码规约

1. 各个 `Service` 除了注入自身的 Repository 以外，只能注入其他 `Service`，不能直接注入其他的 `Repository`（后续可以优化增加中间层，代替 `Service` 之间的依赖）
   > `QuietUserService`只能注入`QuietUserRepository`和`QuietRoleService`，不能注入`QuietRoleRepository`
2. 如果要实现根据某个条件查询某些信息的功能，那么方法需要写在条件对应的 Service 里面（Controller同理）
   > 实现功能：根据用户ID查询角色信息，那么条件为`用户ID`，所以该方法需要写在`QuietUserService`
3. 代码中尽量不要出现编辑器的警告，如果实在无法避免，加上注解，并尽量注明原因
4. 接口方法、`Controller`方法必须有完整的注释，返回的数据结构如果有泛型，必须添加泛型，如果返回的数据与泛型无关，则使用`java.lang.Object`
5. 方法内的注释，使用`//+空格+注释`
6. 如果出现循环依赖，则根据控制台的输出顺序，从第二个开始依次添加注解`@Lazy`，直至循环依赖解决，各个 Service 相互调用的方法中不能出现死循环调用
7. 根据 ID 批量查询数据的时候，入参只能是`Collection<Long>`或者`Set<Long>`
8. 中间表不提供`Controller`，如`用户-角色`表不提供`QuietUserRoleController`，如有需求操作该表数据，则根据需求的条件，将功能写在条件对应的`Controller`，并在该`Controller`
   注入中间表的`Service`，使用该`Service`处理数据
   > 实现功能：根据用户ID删除用户的某个角色，则该功能的接口写在`QuietUserController`，使用`QuietUserRoleService`删除数据
9. 类`com.gitee.quiet.common.service.base.Param` 及其子类，不能作为`Service`方法的形参
10. `Controller`的所有请求方法全部为`POST`
11. 所有 `Service` 方法的参数尽量加上注解，例如不能为空的加上 `@NotNull`
12. 所有缓存全部添加在 `Service` 实现类，在对应的 `Service` 实现类必须有一个缓存 key 前缀为 CACHE_INFO：`${应用名称}:${模块名称}:${业务名称（去掉前缀的表名）}`
13. ID 生成器的 worker-id 为服务ID，data-center-id 为服务数量 ID，生成规则为：worker-id * 10 + data-center-id（所以服务数量）