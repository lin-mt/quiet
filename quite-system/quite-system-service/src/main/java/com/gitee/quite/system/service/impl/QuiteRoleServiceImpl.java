package com.gitee.quite.system.service.impl;

import com.gitee.quite.common.service.exception.ServiceException;
import com.gitee.quite.common.service.util.Wus;
import com.gitee.quite.system.entity.QuiteRole;
import com.gitee.quite.system.repository.QuiteRoleRepository;
import com.gitee.quite.system.service.QuiteRoleService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.gitee.quite.system.entity.QQuiteRole.quiteRole;

/**
 * 角色 Service 实现类.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
public class QuiteRoleServiceImpl implements QuiteRoleService {
    
    private final QuiteRoleRepository roleRepository;
    
    private final JPAQueryFactory jpaQueryFactory;
    
    public QuiteRoleServiceImpl(QuiteRoleRepository roleRepository, JPAQueryFactory jpaQueryFactory) {
        this.roleRepository = roleRepository;
        this.jpaQueryFactory = jpaQueryFactory;
    }
    
    @Override
    public QuiteRole save(QuiteRole quiteRole) {
        checkRoleInfo(quiteRole);
        return roleRepository.save(quiteRole);
    }
    
    @Override
    public QuiteRole update(QuiteRole quiteRole) {
        checkRoleInfo(quiteRole);
        return roleRepository.saveAndFlush(quiteRole);
    }
    
    @Override
    public boolean delete(Long deleteId) {
        roleRepository.deleteById(deleteId);
        return true;
    }
    
    @Override
    public QueryResults<QuiteRole> page(QuiteRole params, Pageable page) {
        BooleanBuilder builder = new BooleanBuilder();
        Wus.NotNullEq(params.getId(), quiteRole.id, builder);
        Wus.NotNullEq(params.getParentId(), quiteRole.parentId, builder);
        Wus.NotBlankContains(params.getRoleName(), quiteRole.roleName, builder);
        Wus.NotBlankContains(params.getRoleCnName(), quiteRole.roleCnName, builder);
        Wus.NotBlankContains(params.getRemarks(), quiteRole.remarks, builder);
        return jpaQueryFactory.selectFrom(quiteRole).where(builder).offset(page.getOffset()).limit(page.getPageSize())
                .fetchResults();
    }
    
    private void checkRoleInfo(QuiteRole role) {
        QuiteRole quiteRole = roleRepository.getByRoleName(role.getRoleName());
        if (quiteRole != null && !quiteRole.getId().equals(role.getId())) {
            throw new ServiceException("role.name.exist", role.getRoleName());
        }
        if (role.getParentId() != null) {
            if (!roleRepository.existsById(role.getParentId())) {
                throw new ServiceException("role.parent.id.no.exist", role.getParentId());
            }
        }
    }
}
