/*
 * Copyright (C) 2022  lin-mt<lin-mt@outlook.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.gitee.quiet.system.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gitee.quiet.jpa.entity.base.BaseEntity;
import com.gitee.quiet.jpa.utils.SelectBuilder;
import com.querydsl.core.BooleanBuilder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.gitee.quiet.system.entity.QQuietClient.quietClient;

/**
 * Quiet 客户端.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@Setter
@Entity
@ToString
@Table(name = "quiet_client")
@SuppressWarnings("deprecation")
public class QuietClient extends BaseEntity implements ClientDetails {

  @NotBlank
  @Length(max = 20)
  @Column(name = "client_id", length = 20, nullable = false, unique = true)
  private String clientId;

  @NotBlank
  @Length(max = 30)
  @Column(name = "client_name", length = 30, nullable = false)
  private String clientName;

  @NotBlank
  @Length(max = 60)
  @Column(name = "client_secret", length = 60, nullable = false)
  private String clientSecret;

  @Column(name = "resource_ids", length = 60)
  private Set<String> resourceIds;

  @NotNull
  @ColumnDefault("0")
  @Column(name = "secret_required", columnDefinition = "TINYINT(1)")
  private Boolean secretRequired;

  @NotNull
  @ColumnDefault("0")
  @Column(name = "scoped", columnDefinition = "TINYINT(1)")
  private Boolean scoped;

  @Column(name = "scope")
  private Set<String> scope;

  @Column(name = "authorized_grant_types", length = 60)
  private Set<String> authorizedGrantTypes;

  @Column(name = "registered_redirect_uri", length = 60)
  private Set<String> registeredRedirectUri;

  @Min(0)
  @NotNull
  @Column(name = "access_token_validity_seconds")
  private Integer accessTokenValiditySeconds;

  @Min(0)
  @NotNull
  @Column(name = "refresh_token_validity_seconds")
  private Integer refreshTokenValiditySeconds;

  @NotNull
  @ColumnDefault("0")
  @Column(name = "auto_approve", columnDefinition = "TINYINT(1)", nullable = false)
  private Boolean autoApprove;

  @Length(max = 100)
  @Column(name = "remark", length = 100)
  private String remark;

  /**
   * Whether a secret is required to authenticate this client.
   *
   * @return Whether a secret is required to authenticate this client.
   */
  @Override
  @Transient
  public boolean isSecretRequired() {
    return BooleanUtils.toBoolean(getSecretRequired());
  }

  /**
   * Whether this client is limited to a specific scope. If false, the scope of the authentication
   * request will be ignored.
   *
   * @return Whether this client is limited to a specific scope.
   */
  @Override
  @Transient
  public boolean isScoped() {
    return BooleanUtils.toBoolean(getScoped());
  }

  /**
   * Returns the authorities that are granted to the OAuth client. Cannot return <code>null</code>.
   * Note that these are NOT the authorities that are granted to the user with an authorized access
   * token. Instead, these authorities are inherent to the client itself.
   *
   * @return the authorities (never <code>null</code>)
   */
  @Override
  @Transient
  public Collection<GrantedAuthority> getAuthorities() {
    return CollectionUtils.emptyCollection();
  }

  /**
   * Test whether client needs user approval for a particular scope.
   *
   * @param scope the scope to consider
   * @return true if this client does not need user approval
   */
  @Override
  @Transient
  public boolean isAutoApprove(String scope) {
    return true;
  }

  /**
   * Additional information for this client, not needed by the vanilla OAuth protocol but might be
   * useful, for example, for storing descriptive information.
   *
   * @return a map of additional information
   */
  @Override
  @Transient
  @JsonIgnore
  public Map<String, Object> getAdditionalInformation() {
    return null;
  }

  /**
   * 添加客户端支持的认证类型
   *
   * @param authorizedGrantType 要添加的认证类型
   */
  public void addAuthorizedGrantType(String authorizedGrantType) {
    if (getAuthorizedGrantTypes() == null) {
      setAuthorizedGrantTypes(new HashSet<>());
    }
    getAuthorizedGrantTypes().add(authorizedGrantType);
  }

  /**
   * 删除客户端支持的认证类型
   *
   * @param authorizedGrantType 要删除的认证类型
   */
  public void removeAuthorizedGrantType(String authorizedGrantType) {
    if (CollectionUtils.isNotEmpty(getAuthorizedGrantTypes())) {
      getAuthorizedGrantTypes().remove(authorizedGrantType);
    }
  }

  /**
   * 添加客户端的授权范围
   *
   * @param scope 要添加的授权范围
   */
  public void addScope(String scope) {
    if (getScope() == null) {
      setScope(new HashSet<>());
    }
    getScope().add(scope);
  }

  /**
   * 删除客户端的授权范围
   *
   * @param scope 要删除的授权范围
   */
  public void removeScope(String scope) {
    if (CollectionUtils.isNotEmpty(getScope())) {
      getScope().remove(scope);
    }
  }

  @Nullable
  @Override
  public BooleanBuilder booleanBuilder() {
    return SelectBuilder.booleanBuilder()
        .isIdEq(getId(), quietClient.id)
        .notNullEq(getAutoApprove(), quietClient.autoApprove)
        .notNullEq(getScoped(), quietClient.scoped)
        .notNullEq(getSecretRequired(), quietClient.secretRequired)
        .notBlankContains(getClientId(), quietClient.clientId)
        .notBlankContains(getClientName(), quietClient.clientName)
        .notBlankContains(getRemark(), quietClient.remark)
        .getPredicate();
  }
}
