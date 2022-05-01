package com.code.controller;

import com.code.vo.LdapUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
public class TestController {

    @GetMapping("/")
    public String index(HttpServletRequest request) {
        LdapUser ldapInfo = (LdapUser) request.getSession().getAttribute("ldapUser");
        return "Hello World "+ ldapInfo.getSn()+ldapInfo.getCn() +" !! [groups] "+ StringUtils.join(ldapInfo.getGroups(),',');
    }
}
