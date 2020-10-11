package com.gitee.quite.system.controller;

import com.gitee.quite.system.base.PostParam;
import com.gitee.quite.system.entity.QuiteRolePermission;
import com.gitee.quite.system.result.Result;
import com.gitee.quite.system.service.QuiteRolePermissionService;
import com.gitee.quite.system.validation.group.curd.base.Create;
import com.gitee.quite.system.validation.group.curd.base.Update;
import com.gitee.quite.system.validation.group.curd.single.DeleteSingle;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 角色-权限 Controller.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@RestController
@RequestMapping("/rolePermission")
public class QuiteRolePermissionController {
    
    private final QuiteRolePermissionService rolePermissionService;
    
    public QuiteRolePermissionController(QuiteRolePermissionService rolePermissionService) {
        this.rolePermissionService = rolePermissionService;
    }
    
    /**
     * 新增角色-权限信息.
     *
     * @param postParam ：save 新增的角色-权限信息
     * @return 新增的角色信息
     */
    @PostMapping("/save")
    public Result<QuiteRolePermission> save(
            @RequestBody @Validated(Create.class) QuiteRolePermissionPostParam postParam) {
        return Result.success(rolePermissionService.saveOrUpdate(postParam.getSave()));
    }
    
    /**
     * 更新角色-权限信息.
     *
     * @param postParam ：update 更新的角色-权限信息
     * @return 更新的角色信息
     */
    @PostMapping("/update")
    public Result<QuiteRolePermission> update(
            @RequestBody @Validated(Update.class) QuiteRolePermissionPostParam postParam) {
        return Result.success(rolePermissionService.saveOrUpdate(postParam.getUpdate()));
    }
    
    /**
     * 删除角色-权限信息.
     *
     * @param postParam ：updateId 删除的角色-权限信息的ID
     * @return 删除结果
     */
    @DeleteMapping("/delete")
    public Result<QuiteRolePermission> delete(
            @RequestBody @Validated(DeleteSingle.class) QuiteRolePermissionPostParam postParam) {
        if (rolePermissionService.delete(postParam.getDeleteId())) {
            return Result.deleteSuccess();
        }
        return Result.deleteFailure();
    }
    
    static class QuiteRolePermissionPostParam extends PostParam<QuiteRolePermission, QuiteRolePermission> {
    
    }
    
}
