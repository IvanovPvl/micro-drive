package io.microdrive.auth.domain;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

import java.util.*;
import java.io.IOException;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.stream.Collectors;

@Data
@Slf4j
@Entity
public class OauthClientDetails implements ClientDetails {

    @Id
    @Column(name = "client_id")
    private String clientId;

    @Column(name = "resource_ids")
    private String resourceIds;

    @Column(name = "client_secret")
    private String clientSecret;

    @Column
    private String scope;

    @Column(name = "authorized_grant_types")
    private String authorizedGrantTypes;

    @Column(name = "redirect_uri")
    private String registeredRedirectUri;

    @Column
    private String authorities;

    @Column(name = "access_token_validity")
    private int accessTokenValidity;

    @Column(name = "refresh_token_validity")
    private int refreshTokenValidity;

    @Column(name = "additional_information")
    private String additionalInformation;

    @Column(name = "auto_approve")
    private String autoApprove;

    public Set<String> getScope() {
        return StringUtils.commaDelimitedListToSet(scope);
    }

    public void setScope(Set<String> scope) {
        this.scope = StringUtils.collectionToCommaDelimitedString(scope);
    }

    public Set<String> getAuthorizedGrantTypes() {
        return StringUtils.commaDelimitedListToSet(authorizedGrantTypes);
    }

    public void setAuthorizedGrantTypes(Set<String> authorizedGrantTypes) {
        this.authorizedGrantTypes = StringUtils.collectionToCommaDelimitedString(authorizedGrantTypes);
    }

    @Override
    public Set<String> getResourceIds() {
        if (StringUtils.isEmpty(resourceIds)) {
            return new HashSet<>();
        }
        return StringUtils.commaDelimitedListToSet(resourceIds);
    }

    public void setResourceIds(Set<String> resourceIds) {
        this.resourceIds = StringUtils.collectionToCommaDelimitedString(resourceIds);
    }

    @Override
    public boolean isSecretRequired() {
        return !StringUtils.isEmpty(clientSecret);
    }

    @Override
    public boolean isScoped() {
        return getScope().size() > 0;
    }

    @Override
    public Set<String> getRegisteredRedirectUri() {
        return StringUtils.commaDelimitedListToSet(registeredRedirectUri);
    }

    public void setRegisteredRedirectUri(Set<String> registeredRedirectUris) {
        registeredRedirectUri = StringUtils.collectionToCommaDelimitedString(registeredRedirectUris);
    }

    private GrantedAuthority grantedAuthorityCreator(String s) {
        return () -> s;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return StringUtils.commaDelimitedListToSet(authorities)
                .stream()
                .map(this::grantedAuthorityCreator)
                .collect(Collectors.toSet());
    }

    public void setAuthorities(Set<GrantedAuthority> authorities) {
        Set<String> authTitles = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        this.authorities = StringUtils.collectionToCommaDelimitedString(authTitles);
    }

    @Override
    public Integer getAccessTokenValiditySeconds() {
        return accessTokenValidity;
    }

    public void setAccessTokenValiditySeconds(Integer seconds) {
        accessTokenValidity = seconds;
    }

    @Override
    public Integer getRefreshTokenValiditySeconds() {
        return refreshTokenValidity;
    }

    public void setRefreshTokenValiditySeconds(Integer seconds) {
        refreshTokenValidity = seconds;
    }

    @Override
    public boolean isAutoApprove(String scope) {
        return false;
    }

    @Override
    public Map<String, Object> getAdditionalInformation() {
        try {
            return new ObjectMapper().readValue(additionalInformation,
                    TypeFactory.defaultInstance().constructMapType(HashMap.class, String.class, Object.class));
        } catch (IOException ex) {
            log.error(ex.getMessage());
        }

        return new HashMap<>();
    }

    public void setAdditionalInformation(Map<String, Object> info) {
        try {
            additionalInformation = new ObjectMapper().writeValueAsString(info);
        } catch (IOException ex) {
            additionalInformation = "";
            log.error(ex.getMessage());
        }
    }

    public Set<String> getAutoApprove() {
        return StringUtils.commaDelimitedListToSet(autoApprove);
    }

    public void setAutoApprove(Set<String> autoApprove) {
        this.autoApprove = StringUtils.collectionToCommaDelimitedString(autoApprove);
    }
}
