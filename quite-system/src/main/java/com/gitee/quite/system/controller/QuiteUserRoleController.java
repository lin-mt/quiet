package com.gitee.quite.system.controller;

import com.gitee.quite.system.base.PostParam;
import com.gitee.quite.system.entity.QuiteUserRole;
import com.gitee.quite.system.result.Result;
import com.gitee.quite.system.service.QuiteUserRoleService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户-角色 Controller.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@RestController
@RequestMapping("/userRole")
public class QuiteUserRoleController {
    
    private final QuiteUserRoleService userRoleService;
    
    public QuiteUserRoleController(QuiteUserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }
    
    /**
     * 新增用户-角色信息.
     *
     * @param postParam :save 用户-角色对应信息
     * @return 新增结果
     */
    @PostMapping("/save")
    public Result<QuiteUserRole> save(@RequestBody QuiteUserRolePostParam postParam) {
        return Result.success(userRoleService.saveOrUpdate(postParam.getSave()));
    }
    
    /**
     * 更新用户-角色信息.
     *
     * @param postParam :update 用户-角色对应信息
     * @return 更新结果
     */
    @PostMapping("/update")
    public Result<QuiteUserRole> saveOrUpdate(@RequestBody QuiteUserRolePostParam postParam) {
        return Result.success(userRoleService.saveOrUpdate(postParam.getUpdate()));
    }
    
    /**
     * 批量删除用户-角色对应关系.
     *
     * @param postParam :deleteIds 要删除的ID集合
     * @return 删除结果
     */
    @DeleteMapping("/delete")
    public Result<Object> delete(@RequestBody QuiteUserRolePostParam postParam) {
        if (userRoleService.delete(postParam.getDeleteIds())) {
            return Result.deleteSuccess();
        }
        return Result.deleteFailure();
    }
    
    static class QuiteUserRolePostParam extends PostParam<QuiteUserRole, QuiteUserRole> {
    
    }
}
