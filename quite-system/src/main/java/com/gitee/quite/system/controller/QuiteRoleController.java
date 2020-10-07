package com.gitee.quite.system.controller;

import com.gitee.quite.system.base.PostParam;
import com.gitee.quite.system.entity.QuiteRole;
import com.gitee.quite.system.result.Result;
import com.gitee.quite.system.service.QuiteRoleService;
import com.gitee.quite.system.validation.group.curd.base.Create;
import com.gitee.quite.system.validation.group.curd.base.Update;
import com.gitee.quite.system.validation.group.curd.single.DeleteSingle;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 角色 Controller.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@RestController
@RequestMapping("/role")
public class QuiteRoleController {
    
    private final QuiteRoleService roleService;
    
    public QuiteRoleController(QuiteRoleService roleService) {
        this.roleService = roleService;
    }
    
    /**
     * 新增角色.
     *
     * @param postParam :save 新增的角色信息
     * @return 新增后的角色信息
     */
    @PostMapping("/save")
    public Result<QuiteRole> save(@RequestBody @Validated(Create.class) QuiteRolePostParam postParam) {
        return Result.success(roleService.save(postParam.getSave()));
    }
    
    /**
     * 删除角色.
     *
     * @param postParam :deleteId 删除的角色ID
     * @return Result
     */
    @DeleteMapping("/delete")
    public Result<Object> delete(@RequestBody @Validated(DeleteSingle.class) QuiteRolePostParam postParam) {
        if (roleService.delete(postParam.getDeleteId())) {
            return Result.deleteSuccess();
        }
        return Result.deleteFailure();
    }
    
    /**
     * 更新角色.
     *
     * @param postParam :update 更新的角色信息
     * @return 新增后的角色信息
     */
    @PutMapping("/update")
    public Result<QuiteRole> update(@RequestBody @Validated(Update.class) QuiteRolePostParam postParam) {
        return Result.success(roleService.update(postParam.getUpdate()));
    }
    
    static class QuiteRolePostParam extends PostParam<QuiteRole, QuiteRole> {
    
    }
}
