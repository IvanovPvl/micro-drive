package io.microdrive.accounts.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    private static final String[] WHITE_LIST = new String[]{
            "/create",
            "/drivers",
            "/.well-known/jwks.json"
    };

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(WHITE_LIST).permitAll()
                .anyRequest().authenticated();
    }

}
