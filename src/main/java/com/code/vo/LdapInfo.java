package com.code.vo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Data
@Configuration
@ConfigurationProperties(prefix = "ldap")
@PropertySource(value = "classpath:ldap.properties")
public class LdapInfo {
    private String url;
    private String basedn;
    private String managerdn;
    private String managerpwd;
    private String usersearchPattern;
    private String groupsearchPattern;
}
