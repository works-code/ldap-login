package com.code.config;

import com.code.vo.LdapInfo;
import com.code.vo.LdapUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.ldap.userdetails.LdapUserDetailsImpl;
import org.springframework.security.ldap.userdetails.LdapUserDetailsMapper;
import org.springframework.util.StringUtils;
import javax.naming.NamingEnumeration;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.LdapContext;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
@Configuration
public class CustomLdapUserMapper extends LdapUserDetailsMapper {

    @Autowired
    public LdapContext ldapContext;

    @Autowired
    public LdapInfo ldapInfo;

    @Override
    public LdapUser mapUserFromContext(DirContextOperations ctx, String username, Collection<? extends GrantedAuthority> authorities) {
        LdapUserDetailsImpl details = (LdapUserDetailsImpl) super.mapUserFromContext(ctx, username, authorities);
        LdapUser customLdapUser = new LdapUser(){{
            setCn(ctx.getStringAttribute("cn"));
            setDn(details.getDn());
            setSn(ctx.getStringAttribute("sn"));
            setUid(details.getUsername());
        }};
        try{
            // 유저가 속한 그룹 검색
            SearchControls controls = new SearchControls();
            controls.setReturningAttributes(new String[] {"cn","ou"});
            controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
            String filter = String.format(ldapInfo.getGroupsearchPattern(), details.getUsername());
            NamingEnumeration<?> answer = ldapContext.search(ldapInfo.getBasedn(), filter, controls);

            List<String> groups = new ArrayList<>();
            while(answer.hasMore()) {
                SearchResult searchResult = (SearchResult) answer.next();
                Attributes attributes = searchResult.getAttributes();
                groups.add(StringUtils.isEmpty(attributes.get("ou")) ? "" : attributes.get("cn").toString().split(":")[1]);
            }
            customLdapUser.setGroups(groups);
        }catch (Exception e){
            log.error("[ERROR] {}", e);
        }
        return customLdapUser;
    }
}
