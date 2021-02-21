package com.gitee.quiet.common.service.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gitee.quiet.common.service.base.QuietUserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * ResourceAuthentication 和 ResourceServer之间的用户信息转换.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public class QuietUserAuthenticationConverter extends DefaultUserAuthenticationConverter {
    
    public static final String QUIET_USER_DETAILS = "quiet_user_details";
    
    private final ObjectMapper objectMapper;
    
    private Collection<? extends GrantedAuthority> defaultAuthorities;
    
    public QuietUserAuthenticationConverter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
    
    /**
     * Default value for authorities if an Authentication is being created and the input has no data for authorities.
     * Note that unless this property is set, the default Authentication created by {@link #extractAuthentication(Map)}
     * will be unauthenticated.
     *
     * @param defaultAuthorities the defaultAuthorities to set. Default null.
     */
    @Override
    public void setDefaultAuthorities(String[] defaultAuthorities) {
        this.defaultAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList(StringUtils.arrayToCommaDelimitedString(defaultAuthorities));
    }
    
    @Override
    public Map<String, ?> convertUserAuthentication(Authentication authentication) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put(USERNAME, authentication.getName());
        if (authentication.getAuthorities() != null && !authentication.getAuthorities().isEmpty()) {
            response.put(AUTHORITIES, AuthorityUtils.authorityListToSet(authentication.getAuthorities()));
        }
        response.put(QUIET_USER_DETAILS, authentication.getPrincipal());
        return response;
    }
    
    @Override
    public Authentication extractAuthentication(Map<String, ?> map) {
        if (map.containsKey(QUIET_USER_DETAILS)) {
            QuietUserDetails principal = objectMapper.convertValue(map.get(QUIET_USER_DETAILS), QuietUserDetails.class);
            Collection<? extends GrantedAuthority> authorities = getAuthorities(map);
            return new UsernamePasswordAuthenticationToken(principal, "N/A", authorities);
        }
        return super.extractAuthentication(map);
    }
    
    private Collection<? extends GrantedAuthority> getAuthorities(Map<String, ?> map) {
        if (!map.containsKey(AUTHORITIES)) {
            return defaultAuthorities;
        }
        Object authorities = map.get(AUTHORITIES);
        if (authorities instanceof String) {
            return AuthorityUtils.commaSeparatedStringToAuthorityList((String) authorities);
        }
        if (authorities instanceof Collection) {
            return AuthorityUtils.commaSeparatedStringToAuthorityList(
                    StringUtils.collectionToCommaDelimitedString((Collection<?>) authorities));
        }
        throw new IllegalArgumentException("Authorities must be either a String or a Collection");
    }
}
