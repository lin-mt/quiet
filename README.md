# Quiet

## TODO

1. 使用 Google style 格式化代码
2. 重新设计代码结构，解决循环依赖问题
3. 使用Spring Authorization Server鉴权

## 编码规约

1. 不能出现循环依赖，如需要多个`Service`方法实现功能的，需要抽取到`Manager`层
2. 代码中尽量不要出现编辑器的警告，如果实在无法避免，加上注解抑制警告，并尽量注明原因
3. 接口方法、`Controller`
   方法必须有完整的注释，返回的数据结构如果有泛型，必须添加泛型，如果返回的数据与泛型无关，则使用`java.lang.Object`
4. 方法内的注释，使用`//+空格+注释`
5. 根据 ID 批量查询数据的时候，入参只能是`Collection<Long>`或者`Set<Long>`
6. 中间表不提供`Controller`，如`用户-角色`表不提供`QuietUserRoleController`
   ，如有需求操作该表数据，则根据需求的条件，将功能写在条件对应的`Controller`，并在该`Controller`
   注入中间表的`Service`，使用该`Service`处理数据
   > 实现功能：根据用户ID删除用户的某个角色，则该功能的接口写在`QuietUserController`，使用`QuietUserRoleService`删除数据
7. 类`com.gitee.quiet.common.service.base.Param` 及其子类，不能作为`Service`方法的形参
8. 所有 `Service` 方法的参数尽量加上注解，例如不能为空的加上 `@NotNull`
9. 所有缓存全部添加在 `Service` 实现类，在对应的 `Service` 实现类必须有一个缓存 key 前缀为
   CACHE_INFO：`${应用名称}:${模块名称}:${业务名称（去掉前缀的表名）}:${缓存用途}`
10. ID 生成器的 worker-id 为服务ID，data-center-id 为服务数量 ID，生成规则为：worker-id * 10 + data-center-id（服务数量）