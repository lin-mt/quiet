package com.gitee.quite.controller;

import com.gitee.quite.base.PostParam;
import com.gitee.linmt.entity.Result;
import com.gitee.quite.entity.QuiteUserRole;
import com.gitee.quite.service.QuiteUserRoleService;
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
     * 用户新增角色/更新用户-角色信息.
     *
     * @param postParam :saveOrUpdate 用户-角色对应信息
     * @return 新增/更新结果
     */
    @PostMapping("/saveOrUpdate")
    public Result<QuiteUserRole> saveOrUpdate(@RequestBody QuiteUserRolePostParam postParam) {
        return Result.success(userRoleService.saveOrUpdate(postParam.getSaveOrUpdate()));
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
    
    static class QuiteUserRolePostParam extends PostParam<QuiteUserRole> {
    
    }
}
