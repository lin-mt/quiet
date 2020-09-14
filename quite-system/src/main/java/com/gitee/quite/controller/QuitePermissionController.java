package com.gitee.quite.controller;

import com.gitee.quite.base.PostParam;
import com.gitee.linmt.entity.Result;
import com.gitee.quite.entity.QuitePermission;
import com.gitee.linmt.exception.ServiceException;
import com.gitee.quite.service.QuitePermissionService;
import org.apache.commons.lang3.StringUtils;
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
     * 新增或更新权限配置.
     *
     * @param postParam :saveOrUpdate 新增或更新的权限配置信息
     * @return 新增或更新的权限信息
     */
    @PostMapping("/saveOrUpdate")
    public Result<QuitePermission> save(@RequestBody QuitePermissionPostParam postParam) {
        return Result.success(permissionService.saveOrUpdate(postParam.getSaveOrUpdate()));
    }
    
    /**
     * 删除权限配置.
     *
     * @param postParam :deleteId 要删除的权限配置信息的ID
     * @return 删除的权限信息
     */
    @DeleteMapping("/delete")
    public Result<Object> delete(@RequestBody QuitePermissionPostParam postParam) {
        if (permissionService.delete(postParam.getDeleteId())) {
            return Result.deleteSuccess();
        }
        return Result.deleteFailure();
    }
    
    static class QuitePermissionPostParam extends PostParam<QuitePermission> {
        
        @Override
        public void checkProperties(QuitePermission entity) {
            if (StringUtils.isBlank(entity.getUrlPattern())) {
                throw new ServiceException("E0301");
            }
            if (StringUtils.isNotBlank(entity.getPostAuthorizeValue())) {
                return;
            }
            if (StringUtils.isNotBlank(entity.getPostFilterValue())) {
                return;
            }
            if (StringUtils.isNotBlank(entity.getPreAuthorizeValue())) {
                return;
            }
            if (StringUtils.isNotBlank(entity.getPreFilterValue())) {
                return;
            }
            throw new ServiceException("E0302");
        }
    }
}
