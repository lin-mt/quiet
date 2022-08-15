package com.gitee.quiet.system;

import com.gitee.quiet.common.constant.service.RoleNames;
import com.gitee.quiet.service.enums.Gender;
import com.gitee.quiet.system.entity.QuietClient;
import com.gitee.quiet.system.entity.QuietRole;
import com.gitee.quiet.system.entity.QuietUser;
import com.gitee.quiet.system.entity.QuietUserRole;
import com.gitee.quiet.system.service.QuietClientService;
import com.gitee.quiet.system.service.QuietRoleService;
import com.gitee.quiet.system.service.QuietUserRoleService;
import com.gitee.quiet.system.service.QuietUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Set;

@SpringBootTest
class QuietSystemServiceApplicationTest {

  @Autowired private QuietClientService clientService;

  @Autowired private QuietUserService userService;

  @Autowired private QuietRoleService roleService;

  @Autowired private QuietUserRoleService userRoleService;

  @Test
  void createClient() {
    QuietClient client = new QuietClient();
    client.setClientId("quiet-doc");
    client.setClientSecret("quiet-doc");
    client.setClientName("QuietDoc");
    client.setAuthorizedGrantTypes(Set.of("password"));
    client.setScope(Set.of("all"));
    client.setAutoApprove(true);
    client.setScoped(true);
    client.setSecretRequired(true);
    client.setAccessTokenValiditySeconds(360000);
    client.setRefreshTokenValiditySeconds(720000);
    clientService.save(client);
  }

  @Test
  void createUser() {
    QuietUser user = new QuietUser();
    user.setUsername("admin");
    user.setSecretCode("quiet");
    user.setAccountExpired(false);
    user.setAccountLocked(false);
    user.setEnabled(true);
    user.setEmailAddress("lin-mt@outlook.com");
    user.setGender(Gender.MALE);
    user.setCredentialsExpired(false);
    user.setFullName("超级管理员");
    user.setPhoneNumber("17805930630");
    userService.save(user);
  }

  @Test
  void createRole() {
    QuietRole role = new QuietRole();
    role.setRoleCnName("SM");
    role.setRoleName(RoleNames.ScrumMaster);
    role.setParentId(234280405352449L);
    roleService.save(role);
  }

  @Test
  void userAddRole() {
    QuietUserRole userRole = new QuietUserRole();
    userRole.setRoleId(234279158030337L);
    userRole.setUserId(234275377860609L);
    userRoleService.addRoles(List.of(userRole));
  }
}
