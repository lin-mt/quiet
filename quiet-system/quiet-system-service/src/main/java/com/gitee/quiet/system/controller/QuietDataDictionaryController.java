package com.gitee.quiet.system.controller;

import com.gitee.quiet.common.base.result.Result;
import com.gitee.quiet.common.validation.group.curd.Create;
import com.gitee.quiet.common.validation.group.curd.Update;
import com.gitee.quiet.common.validation.group.curd.single.DeleteSingle;
import com.gitee.quiet.system.entity.QuietDataDictionary;
import com.gitee.quiet.system.params.QuietDataDictionaryParam;
import com.gitee.quiet.system.service.QuietDataDictionaryService;
import com.querydsl.core.QueryResults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 数据字典Controller.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@RestController
@RequestMapping("/dataDictionary")
public class QuietDataDictionaryController {
    
    private final QuietDataDictionaryService dataDictionaryService;
    
    public QuietDataDictionaryController(QuietDataDictionaryService dataDictionaryService) {
        this.dataDictionaryService = dataDictionaryService;
    }
    
    /**
     * 根据数据字典类型查询该类型的数据字典的树形结构提供选项
     *
     * @return 数据字典树形结构
     */
    @PostMapping("/treeByTypeForSelect")
    public Result<List<QuietDataDictionary>> treeByTypeForSelect(@RequestBody QuietDataDictionaryParam postParam) {
        return Result.success(dataDictionaryService.treeByTypeForSelect(postParam.getType()));
    }
    
    /**
     * 根据数据字典类型返回该类型的树形结构
     *
     * @return 数据字典树形结构
     */
    @PostMapping("/treeByType")
    public Result<List<QuietDataDictionary>> treeByType(@RequestBody QuietDataDictionaryParam postParam) {
        return Result.success(dataDictionaryService.treeByType(postParam.getType()));
    }
    
    /**
     * 分页查询数据字典.
     *
     * @return 查询的所有信息
     */
    @PostMapping("/page")
    public Result<QueryResults<QuietDataDictionary>> page(@RequestBody QuietDataDictionaryParam postParam) {
        return Result.success(dataDictionaryService.page(postParam.getParams(), postParam.page()));
    }
    
    /**
     * 新增数据字典.
     *
     * @param postParam :save 新增的数据字典信息
     * @return 新增后的数据字典信息
     */
    @PostMapping("/save")
    public Result<QuietDataDictionary> save(@RequestBody @Validated(Create.class) QuietDataDictionaryParam postParam) {
        return Result.createSuccess(dataDictionaryService.save(postParam.getSave()));
    }
    
    /**
     * 删除数据字典.
     *
     * @param postParam :deleteId 删除的数据字典ID
     * @return Result
     */
    @PostMapping("/delete")
    @PreAuthorize(value = "hasRole('Admin')")
    public Result<Object> delete(@RequestBody @Validated(DeleteSingle.class) QuietDataDictionaryParam postParam) {
        dataDictionaryService.delete(postParam.getDeleteId());
        return Result.deleteSuccess();
    }
    
    /**
     * 更新数据字典.
     *
     * @param postParam :update 更新的数据字典信息
     * @return 新增后的数据字典信息
     */
    @PostMapping("/update")
    public Result<QuietDataDictionary> update(
            @RequestBody @Validated(Update.class) QuietDataDictionaryParam postParam) {
        return Result.updateSuccess(dataDictionaryService.update(postParam.getUpdate()));
    }
}
