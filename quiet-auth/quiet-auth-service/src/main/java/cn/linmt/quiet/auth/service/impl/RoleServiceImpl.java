package cn.linmt.quiet.auth.service.impl;

import cn.linmt.quiet.auth.entity.Role;
import cn.linmt.quiet.auth.repository.RoleRepository;
import cn.linmt.quiet.auth.service.RoleService;
import java.util.*;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService, RoleHierarchy {

  private final RoleRepository repository;

  @Override
  public Collection<? extends GrantedAuthority> getReachableGrantedAuthorities(
      Collection<? extends GrantedAuthority> authorities) {
    if (CollectionUtils.isEmpty(authorities)) {
      return new ArrayList<>();
    }
    Set<String> roleNames =
        authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet());
    Set<Role> roles = repository.findByAuthorityIn(roleNames);
    return getReachableRoles(roles);
  }

  private Set<Role> getReachableRoles(Set<Role> roles) {
    if (CollectionUtils.isEmpty(roles)) {
      return new HashSet<>();
    }
    Set<Role> reachableRoles = new HashSet<>(roles);
    for (Role role : roles) {
      Set<Role> children = role.getChildren();
      reachableRoles.addAll(getReachableRoles(children));
    }
    return reachableRoles;
  }
}
