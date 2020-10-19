package com.gitee.quite.system.service.impl;

import com.gitee.quite.common.service.util.Wus;
import com.gitee.quite.system.entity.QuitePermission;
import com.gitee.quite.system.repository.QuitePermissionRepository;
import com.gitee.quite.system.service.QuitePermissionService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.gitee.quite.system.entity.QQuitePermission.quitePermission;

/**
 * 权限 Service 实现类.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
public class QuitePermissionServiceImpl implements QuitePermissionService {
    
    private final JPAQueryFactory jpaQueryFactory;
    
    private final QuitePermissionRepository permissionRepository;
    
    public QuitePermissionServiceImpl(QuitePermissionRepository permissionRepository, JPAQueryFactory jpaQueryFactory) {
        this.permissionRepository = permissionRepository;
        this.jpaQueryFactory = jpaQueryFactory;
    }
    
    @Override
    public QuitePermission saveOrUpdate(QuitePermission permission) {
        return permissionRepository.save(permission);
    }
    
    @Override
    public boolean delete(Long deleteId) {
        permissionRepository.deleteById(deleteId);
        return true;
    }
    
    @Override
    public QueryResults<QuitePermission> page(QuitePermission params, Pageable page) {
        BooleanBuilder builder = new BooleanBuilder();
        Wus.NotNullEq(params.getId(), quitePermission.id, builder);
        Wus.NotBlankContains(params.getApplicationName(), quitePermission.applicationName, builder);
        Wus.NotBlankContains(params.getUrlPattern(), quitePermission.urlPattern, builder);
        Wus.NotBlankContains(params.getPreFilterValue(), quitePermission.preFilterValue, builder);
        Wus.NotBlankContains(params.getPreFilterFilterTarget(), quitePermission.preFilterFilterTarget, builder);
        Wus.NotBlankContains(params.getPreAuthorizeValue(), quitePermission.preAuthorizeValue, builder);
        Wus.NotBlankContains(params.getPostFilterValue(), quitePermission.postFilterValue, builder);
        Wus.NotBlankContains(params.getPostAuthorizeValue(), quitePermission.postAuthorizeValue, builder);
        return jpaQueryFactory.selectFrom(quitePermission).where(builder).offset(page.getOffset())
                .limit(page.getPageSize()).fetchResults();
    }
    
}
