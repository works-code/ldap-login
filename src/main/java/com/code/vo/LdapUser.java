package com.code.vo;

import lombok.Data;
import org.springframework.security.ldap.userdetails.LdapUserDetailsImpl;

import java.util.List;

@Data
public class LdapUser extends LdapUserDetailsImpl {
    private String dn;
    private String cn;
    private String sn;
    private String uid;
    private List<String> groups;
    private List<String> roles;
}
