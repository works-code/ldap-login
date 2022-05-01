package com.code.config;

import com.code.handler.LoginSuccessHandler;
import com.code.vo.LdapInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;
import org.springframework.security.ldap.userdetails.UserDetailsContextMapper;

@EnableWebSecurity
public class LdapWebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public LdapInfo ldapInfo;

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http.authorizeRequests().anyRequest().fullyAuthenticated()
                .and().formLogin().successHandler(new LoginSuccessHandler());
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception
    {
        auth.ldapAuthentication()
                .userDnPatterns(ldapInfo.getUsersearchPattern())
                .userSearchBase(ldapInfo.getBasedn())
                .userDetailsContextMapper(userDetailsContextMapper())
                .contextSource()
                .url(ldapInfo.getUrl()+"/"+ldapInfo.getBasedn())
                .and()
                .passwordCompare()
                .passwordEncoder(new LdapShaPasswordEncoder())
                .passwordAttribute("userPassword");
    }

    @Bean
    public UserDetailsContextMapper userDetailsContextMapper() {
        return new CustomLdapUserMapper();
    }

}
