package com.gitee.quite.system.controller;

import com.gitee.quite.common.validation.group.curd.Create;
import com.gitee.quite.common.validation.group.curd.Update;
import com.gitee.quite.common.validation.group.curd.single.DeleteSingle;
import com.gitee.quite.system.base.PostParam;
import com.gitee.quite.system.entity.QuitePermission;
import com.gitee.quite.system.result.Result;
import com.gitee.quite.system.service.QuitePermissionService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 权限 Controller.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@RestController
@RequestMapping("/permission")
public class QuitePermissionController {
    
    private final QuitePermissionService permissionService;
    
    public QuitePermissionController(QuitePermissionService permissionService) {
        this.permissionService = permissionService;
    }
    
    /**
     * 新增权限配置.
     *
     * @param postParam :save 新增的权限配置信息
     * @return 新增的权限信息
     */
    @PostMapping("/save")
    public Result<QuitePermission> save(@RequestBody @Validated(Create.class) QuitePermissionPostParam postParam) {
        return Result.success(permissionService.saveOrUpdate(postParam.getSave()));
    }
    
    /**
     * 更新权限配置.
     *
     * @param postParam :update 更新的权限配置信息
     * @return 更新的权限信息
     */
    @PostMapping("/update")
    public Result<QuitePermission> Update(@RequestBody @Validated(Update.class) QuitePermissionPostParam postParam) {
        return Result.success(permissionService.saveOrUpdate(postParam.getUpdate()));
    }
    
    /**
     * 删除权限配置.
     *
     * @param postParam :deleteId 要删除的权限配置信息的ID
     * @return 删除结果
     */
    @DeleteMapping("/delete")
    public Result<Object> delete(@RequestBody @Validated(DeleteSingle.class) QuitePermissionPostParam postParam) {
        if (permissionService.delete(postParam.getDeleteId())) {
            return Result.deleteSuccess();
        }
        return Result.deleteFailure();
    }
    
    static class QuitePermissionPostParam extends PostParam<QuitePermission, QuitePermission> {
    
    }
}
