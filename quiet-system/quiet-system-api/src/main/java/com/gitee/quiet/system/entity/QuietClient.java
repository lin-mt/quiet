/*
 * Copyright 2021 lin-mt@outlook.com
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
import com.gitee.quiet.common.service.base.BaseEntity;
import com.gitee.quiet.common.service.converter.SetStringConverter;
import com.gitee.quiet.common.service.enums.Whether;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * Quiet 客户端.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Entity
@Table(name = "quiet_client")
public class QuietClient extends BaseEntity implements ClientDetails {
    
    @Column(name = "client_id")
    private String clientId;
    
    @Column(name = "resource_ids")
    @Convert(converter = SetStringConverter.class)
    private Set<String> resourceIds;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "secret_required")
    private Whether secretRequired;
    
    @Column(name = "client_secret")
    private String clientSecret;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "scoped")
    private Whether scoped;
    
    @Column(name = "scope")
    @Convert(converter = SetStringConverter.class)
    private Set<String> scope;
    
    @Column(name = "authorized_grant_types")
    @Convert(converter = SetStringConverter.class)
    private Set<String> authorizedGrantTypes;
    
    @Column(name = "registered_redirect_uri")
    @Convert(converter = SetStringConverter.class)
    private Set<String> registeredRedirectUri;
    
    @Column(name = "access_token_validity_seconds")
    private Integer accessTokenValiditySeconds;
    
    @Column(name = "refresh_token_validity_seconds")
    private Integer refreshTokenValiditySeconds;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "auto_approve")
    private Whether autoApprove;
    
    @Override
    public String getClientId() {
        return clientId;
    }
    
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
    
    @Override
    public Set<String> getResourceIds() {
        return resourceIds;
    }
    
    /**
     * Whether a secret is required to authenticate this client.
     *
     * @return Whether a secret is required to authenticate this client.
     */
    @Override
    @Transient
    @JsonIgnore
    public boolean isSecretRequired() {
        return Whether.YES.equals(getSecretRequired());
    }
    
    public void setResourceIds(Set<String> resourceIds) {
        this.resourceIds = resourceIds;
    }
    
    public Whether getSecretRequired() {
        return secretRequired;
    }
    
    public void setSecretRequired(Whether secretRequired) {
        this.secretRequired = secretRequired;
    }
    
    @Override
    public String getClientSecret() {
        return clientSecret;
    }
    
    /**
     * Whether this client is limited to a specific scope. If false, the scope of the authentication request will be
     * ignored.
     *
     * @return Whether this client is limited to a specific scope.
     */
    @Override
    @Transient
    @JsonIgnore
    public boolean isScoped() {
        return Whether.YES.equals(getScoped());
    }
    
    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }
    
    public Whether getScoped() {
        return scoped;
    }
    
    public void setScoped(Whether scoped) {
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
    
    /**
     * Returns the authorities that are granted to the OAuth client. Cannot return <code>null</code>. Note that these
     * are NOT the authorities that are granted to the user with an authorized access token. Instead, these authorities
     * are inherent to the client itself.
     *
     * @return the authorities (never <code>null</code>)
     */
    @Override
    @Transient
    @JsonIgnore
    public Collection<GrantedAuthority> getAuthorities() {
        return CollectionUtils.emptyCollection();
    }
    
    public void setRegisteredRedirectUri(Set<String> registeredRedirectUri) {
        this.registeredRedirectUri = registeredRedirectUri;
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
    
    /**
     * Test whether client needs user approval for a particular scope.
     *
     * @param scope the scope to consider
     * @return true if this client does not need user approval
     */
    @Override
    @Transient
    @JsonIgnore
    public boolean isAutoApprove(String scope) {
        LoggerFactory.getLogger(QuietClient.class).info(scope);
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
    
    public void setRefreshTokenValiditySeconds(Integer refreshTokenValiditySeconds) {
        this.refreshTokenValiditySeconds = refreshTokenValiditySeconds;
    }
    
    public Whether getAutoApprove() {
        return autoApprove;
    }
    
    public void setAutoApprove(Whether autoApprove) {
        this.autoApprove = autoApprove;
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
    
}
