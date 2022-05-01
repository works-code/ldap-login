package com.code.config;

import com.code.vo.LdapInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import java.util.Hashtable;

@Slf4j
@Configuration
public class WebConfig {

    @Autowired
    public LdapInfo ldapInfo;

    @Bean
    LdapContext ldapContext(){
        LdapContext ctx = null;
        try{
            Hashtable<String, String> environment = new Hashtable<>();
            environment.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
            environment.put(Context.PROVIDER_URL, ldapInfo.getUrl());
            environment.put(Context.SECURITY_AUTHENTICATION, "simple");
            environment.put(Context.SECURITY_PRINCIPAL, ldapInfo.getManagerdn());
            environment.put(Context.SECURITY_CREDENTIALS, ldapInfo.getManagerpwd());
            ctx = new InitialLdapContext(environment, null);
            log.info("[INFO] ldapContext Connected!");
        }catch (NamingException e){
            log.error("[ERROR] Create LdapContext Exception {}", e);
        }
        return ctx;
    }
}
