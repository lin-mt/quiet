package com.gitee.quite.system.controller;

import com.gitee.quite.system.base.PostParam;
import com.gitee.quite.system.entity.QuiteUser;
import com.gitee.quite.system.enums.Whether;
import com.gitee.quite.system.result.Result;
import com.gitee.quite.system.service.QuiteUserService;
import com.gitee.quite.system.util.SpringSecurityUtils;
import com.querydsl.core.QueryResults;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户 Controller.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@RestController
@RequestMapping("/user")
public class QuiteUserController {
    
    private final QuiteUserService quiteUserService;
    
    public QuiteUserController(QuiteUserService quiteUserService) {
        this.quiteUserService = quiteUserService;
    }
    
    /**
     * 用户注册.
     *
     * @param postParam :save 用户信息
     * @return 注册后的用户信息
     */
    @PostMapping("/register")
    public Result<QuiteUser> register(@RequestBody QuiteUserPostParam postParam) {
        // TODO 可以根据租户的配置确定是否注册就直接启用该用户
        postParam.getSave().setEnabled(Whether.YES);
        return Result.success(quiteUserService.save(postParam.getSave()));
    }
    
    /**
     * 查询用户.
     *
     * @return 查询的用户信息
     */
    @PostMapping("/page")
    public Result<QueryResults<QuiteUser>> page(@RequestBody QuiteUserPostParam postParam) {
        return Result.success(quiteUserService.pageByEntity(postParam.getParam(), postParam.page()));
    }
    
    /**
     * 删除用户.
     *
     * @param postParam :deleteId 要删除的用户ID
     * @return 删除结果
     */
    @DeleteMapping("/delete")
    public Result<Object> delete(@RequestBody QuiteUserPostParam postParam) {
        if (quiteUserService.delete(postParam.getDeleteId())) {
            return Result.deleteSuccess();
        }
        return Result.deleteFailure();
    }
    
    /**
     * 更新用户.
     *
     * @param postParam :update 要更新的用户信息
     * @return 更新后的用户信息
     */
    @PutMapping("/update")
    public Result<QuiteUser> update(@RequestBody QuiteUserPostParam postParam) {
        return Result.success(quiteUserService.update(postParam.getUpdate()));
    }
    
    /**
     * 获取当前登陆人信息.
     *
     * @return 当前登陆人信息
     */
    @GetMapping("/currentUserInfo")
    public Result<QuiteUser> currentUserInfo() {
        return Result.success(SpringSecurityUtils.getCurrentUser());
    }
    
    static class QuiteUserPostParam extends PostParam<QuiteUser, QuiteUser> {
    
    }
}
