/*
 * Copyright 2021. lin-mt@outlook.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gitee.quiet.system.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gitee.quiet.common.service.jpa.SelectBuilder;
import com.gitee.quiet.common.service.jpa.converter.SetStringConverter;
import com.gitee.quiet.common.service.jpa.entity.BaseEntity;
import com.querydsl.core.BooleanBuilder;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
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
@Entity
@Table(name = "quiet_client")
public class QuietClient extends BaseEntity implements ClientDetails {
    
    @Column(name = "client_id", length = 20, nullable = false, unique = true)
    @NotEmpty(message = "{client.clientId}{not.empty}")
    @Length(max = 20, message = "{client.clientId}{length.max.limit}")
    private String clientId;
    
    @Column(name = "client_name", length = 30, nullable = false)
    @NotEmpty(message = "{client.clientName}{not.empty}")
    @Length(max = 30, message = "{client.clientName}{length.max.limit}")
    private String clientName;
    
    @Column(name = "client_secret", length = 60, nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotEmpty(message = "{client.clientSecret}{not.empty}")
    @Length(max = 60, message = "{client.clientSecret}{length.max.limit}")
    private String clientSecret;
    
    @Column(name = "resource_ids", length = 60)
    @Convert(converter = SetStringConverter.class)
    private Set<String> resourceIds;
    
    @ColumnDefault("0")
    @Column(name = "secret_required", columnDefinition = "TINYINT(1)", nullable = false)
    @NotNull(message = "{client.secretRequired}{not.null}")
    private Boolean secretRequired;
    
    @ColumnDefault("0")
    @Column(name = "scoped", columnDefinition = "TINYINT(1)", nullable = false)
    @NotNull(message = "{client.scoped}{not.null}")
    private Boolean scoped;
    
    @Column(name = "scope")
    @Convert(converter = SetStringConverter.class)
    private Set<String> scope;
    
    @Column(name = "authorized_grant_types", length = 60)
    @Convert(converter = SetStringConverter.class)
    private Set<String> authorizedGrantTypes;
    
    @Column(name = "registered_redirect_uri", length = 60)
    @Convert(converter = SetStringConverter.class)
    private Set<String> registeredRedirectUri;
    
    @Column(name = "access_token_validity_seconds")
    @NotNull(message = "{client.accessTokenValiditySeconds}{not.null}")
    private Integer accessTokenValiditySeconds;
    
    @Column(name = "refresh_token_validity_seconds")
    @NotNull(message = "{client.refreshTokenValiditySeconds}{not.null}")
    private Integer refreshTokenValiditySeconds;
    
    @ColumnDefault("0")
    @Column(name = "auto_approve", columnDefinition = "TINYINT(1)", nullable = false)
    @NotNull(message = "{client.autoApprove}{not.null}")
    private Boolean autoApprove;
    
    @Column(name = "remark", length = 100)
    @Length(max = 100, message = "{client.remark}{length.max.limit}")
    private String remark;
    
    @Override
    public String getClientId() {
        return clientId;
    }
    
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
    
    @Override
    public String getClientSecret() {
        return clientSecret;
    }
    
    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }
    
    public String getClientName() {
        return clientName;
    }
    
    public void setClientName(String clientName) {
        this.clientName = clientName;
    }
    
    @Override
    public Set<String> getResourceIds() {
        return resourceIds;
    }
    
    public void setResourceIds(Set<String> resourceIds) {
        this.resourceIds = resourceIds;
    }
    
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
    
    public Boolean getSecretRequired() {
        return secretRequired;
    }
    
    public void setSecretRequired(Boolean secretRequired) {
        this.secretRequired = secretRequired;
    }
    
    /**
     * Whether this client is limited to a specific scope. If false, the scope of the authentication request will be
     * ignored.
     *
     * @return Whether this client is limited to a specific scope.
     */
    @Override
    @Transient
    public boolean isScoped() {
        return BooleanUtils.toBoolean(getScoped());
    }
    
    public Boolean getScoped() {
        return scoped;
    }
    
    public void setScoped(Boolean scoped) {
        this.scoped = scoped;
    }
    
    @Override
    public Set<String> getScope() {
        return scope;
    }
    
    public void setScope(Set<String> scope) {
        this.scope = scope;
    }
    
    @Override
    public Set<String> getAuthorizedGrantTypes() {
        return authorizedGrantTypes;
    }
    
    public void setAuthorizedGrantTypes(Set<String> authorizedGrantTypes) {
        this.authorizedGrantTypes = authorizedGrantTypes;
    }
    
    @Override
    public Set<String> getRegisteredRedirectUri() {
        return registeredRedirectUri;
    }
    
    public void setRegisteredRedirectUri(Set<String> registeredRedirectUri) {
        this.registeredRedirectUri = registeredRedirectUri;
    }
    
    /**
     * Returns the authorities that are granted to the OAuth client. Cannot return <code>null</code>. Note that these
     * are NOT the authorities that are granted to the user with an authorized access token. Instead, these authorities
     * are inherent to the client itself.
     *
     * @return the authorities (never <code>null</code>)
     */
    @Override
    @Transient
    public Collection<GrantedAuthority> getAuthorities() {
        return CollectionUtils.emptyCollection();
    }
    
    @Override
    public Integer getAccessTokenValiditySeconds() {
        return accessTokenValiditySeconds;
    }
    
    public void setAccessTokenValiditySeconds(Integer accessTokenValiditySeconds) {
        this.accessTokenValiditySeconds = accessTokenValiditySeconds;
    }
    
    @Override
    public Integer getRefreshTokenValiditySeconds() {
        return refreshTokenValiditySeconds;
    }
    
    public void setRefreshTokenValiditySeconds(Integer refreshTokenValiditySeconds) {
        this.refreshTokenValiditySeconds = refreshTokenValiditySeconds;
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
     * Additional information for this client, not needed by the vanilla OAuth protocol but might be useful, for
     * example, for storing descriptive information.
     *
     * @return a map of additional information
     */
    @Override
    @Transient
    @JsonIgnore
    public Map<String, Object> getAdditionalInformation() {
        return null;
    }
    
    public Boolean getAutoApprove() {
        return autoApprove;
    }
    
    public void setAutoApprove(Boolean autoApprove) {
        this.autoApprove = autoApprove;
    }
    
    public String getRemark() {
        return remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    @Override
    public String toString() {
        return new ToStringBuilder(this).append("clientId", clientId).append("resourceIds", resourceIds)
                .append("secretRequired", secretRequired).append("clientSecret", clientSecret).append("scoped", scoped)
                .append("scope", scope).append("authorizedGrantTypes", authorizedGrantTypes)
                .append("registeredRedirectUri", registeredRedirectUri)
                .append("accessTokenValiditySeconds", accessTokenValiditySeconds)
                .append("refreshTokenValiditySeconds", refreshTokenValiditySeconds).append("autoApprove", autoApprove)
                .toString();
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
        // @formatter:off
        return SelectBuilder.booleanBuilder()
                .notNullEq(getId(), quietClient.id)
                .notNullEq(getAutoApprove(), quietClient.autoApprove)
                .notNullEq(getScoped(), quietClient.scoped)
                .notNullEq(getSecretRequired(), quietClient.secretRequired)
                .notBlankContains(getClientId(), quietClient.clientId)
                .notBlankContains(getClientName(), quietClient.clientName)
                .notBlankContains(getRemark(), quietClient.remark)
                .getPredicate();
        // @formatter:on
    }
}
