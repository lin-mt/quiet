package com.gitee.quite.system.controller;

import com.gitee.quite.system.base.PostParam;
import com.gitee.linmt.entity.Result;
import com.gitee.quite.system.entity.QuiteRolePermission;
import com.gitee.linmt.exception.ServiceException;
import com.gitee.quite.system.service.QuiteRolePermissionService;
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
     * 新增或更新角色-权限信息.
     *
     * @param postParam ：saveOrUpdate 新增或更新的角色-权限信息
     * @return 新增或更新的角色信息
     */
    @PostMapping("/saveOrUpdate")
    public Result<QuiteRolePermission> saveOrUpdate(@RequestBody QuiteRolePermissionPostParam postParam) {
        return Result.success(rolePermissionService.saveOrUpdate(postParam.getSaveOrUpdate()));
    }
    
    /**
     * 删除角色-权限信息.
     *
     * @param postParam ：updateId 删除的角色-权限信息的ID
     * @return 删除结果
     */
    @DeleteMapping("/delete")
    public Result<QuiteRolePermission> delete(@RequestBody QuiteRolePermissionPostParam postParam) {
        if (rolePermissionService.delete(postParam.getDeleteId())) {
            return Result.deleteSuccess();
        }
        return Result.deleteFailure();
    }
    
    static class QuiteRolePermissionPostParam extends PostParam<QuiteRolePermission> {
        
        @Override
        public void checkProperties(QuiteRolePermission entity) {
            if (entity.getRoleId() == null || entity.getPermissionId() == null) {
                throw new ServiceException("E0401");
            }
        }
    }
    
}
