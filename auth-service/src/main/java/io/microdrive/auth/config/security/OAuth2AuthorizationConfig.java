package io.microdrive.auth.config.security;

import lombok.val;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;

import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
public class OAuth2AuthorizationConfig extends AuthorizationServerConfigurerAdapter {

    private final TokenStore tokenStore;
    private final TokenEnhancer tokenEnhancer;
    private final UserDetailsService userDetailsService;
    private final JwtAccessTokenConverter tokenConverter;
    private final AuthenticationManager authenticationManagerBean;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("browser")
                .secret("{noop}secret")
                .authorizedGrantTypes("refresh_token", "password")
                .scopes("ui");
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        val tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(this.tokenEnhancer, this.tokenConverter));

        endpoints.tokenStore(this.tokenStore)
                .accessTokenConverter(this.tokenConverter)
                .tokenEnhancer(this.tokenEnhancer)
                .authenticationManager(authenticationManagerBean)
                .userDetailsService(userDetailsService);
    }
}
