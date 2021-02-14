package com.gitee.quiet.system.controller;

import com.gitee.quiet.common.base.result.Result;
import com.gitee.quiet.common.service.enums.Operation;
import com.gitee.quiet.common.validation.group.curd.Create;
import com.gitee.quiet.common.validation.group.curd.Update;
import com.gitee.quiet.common.validation.group.curd.single.DeleteSingle;
import com.gitee.quiet.system.entity.QuietClient;
import com.gitee.quiet.system.params.QuietClientParam;
import com.gitee.quiet.system.service.QuietClientService;
import com.querydsl.core.QueryResults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 客户端Controller.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@RestController
@RequestMapping("/client")
@PreAuthorize(value = "hasRole('SystemAdmin')")
public class QuietClientController {
    
    private final QuietClientService clientService;
    
    public QuietClientController(QuietClientService clientService) {
        this.clientService = clientService;
    }
    
    /**
     * 分页查询客户端.
     *
     * @return 查询所有信息
     */
    @PostMapping("/page")
    public Result<QueryResults<QuietClient>> page(@RequestBody QuietClientParam postParam) {
        return Result.success(clientService.page(postParam.getParams(), postParam.page()));
    }
    
    /**
     * 新增客户端.
     *
     * @param postParam :save 新增的客户端信息
     * @return 新增后的客户端信息
     */
    @PostMapping("/save")
    public Result<QuietClient> save(@RequestBody @Validated(Create.class) QuietClientParam postParam) {
        return Result.createSuccess(clientService.save(postParam.getSave()));
    }
    
    /**
     * 删除客户端.
     *
     * @param postParam :deleteId 删除的客户端ID
     * @return Result
     */
    @PostMapping("/delete")
    public Result<Object> delete(@RequestBody @Validated(DeleteSingle.class) QuietClientParam postParam) {
        clientService.deleteClient(postParam.getDeleteId());
        return Result.deleteSuccess();
    }
    
    /**
     * 更新客户端.
     *
     * @param postParam :update 更新的客户端信息
     * @return 新增后的客户端信息
     */
    @PostMapping("/update")
    public Result<QuietClient> update(@RequestBody @Validated(Update.class) QuietClientParam postParam) {
        return Result.updateSuccess(clientService.update(postParam.getUpdate()));
    }
    
    @PostMapping("/removeClientScope")
    public Result<Object> removeClientScope(@RequestBody QuietClientParam postParam) {
        clientService.changeClientScope(postParam.getId(), postParam.getClientScope(), Operation.DELETE);
        return Result.deleteSuccess();
    }
    
    @PostMapping("/removeClientAuthorizedGrantType")
    public Result<Object> removeClientAuthorizedGrantType(@RequestBody QuietClientParam postParam) {
        clientService.changeClientAuthorizedGrantType(postParam.getId(), postParam.getClientAuthorizedGrantType(),
                Operation.DELETE);
        return Result.deleteSuccess();
    }
    
}
