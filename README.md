# Quiet

## 编码规约

1. 各个 Service 除了注入自身的 Repository 以外，只能注入其他 Service，不能直接注入其他的 Repository：
   > 如`QuietUserService`只能注入`QuietUserRepository`和`QuietRoleService`，不能注入`QuietRoleRepository`