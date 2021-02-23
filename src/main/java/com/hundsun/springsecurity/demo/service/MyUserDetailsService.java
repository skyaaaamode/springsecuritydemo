package com.hundsun.springsecurity.demo.service;

import com.hundsun.springsecurity.demo.entity.User;
import com.hundsun.springsecurity.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

//TODO 为什么能够加载到这个数据源
@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 从数据库尝试读取该用户
        User user = userMapper.findByUserName(username);
        // 用户不存在，抛出异常
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        // 将数据库形式的roles解析为UserDetails的权限集
        // AuthorityUtils.commaSeparatedStringToAuthorityList是Spring Security
        //提供的用于将逗号隔开的权限集字符串切割成可用权限对象列表的方法
        // 当然也可以自己实现，如用分号来隔开等，参考generateAuthorities
        user.setAuthorities(AuthorityUtils.commaSeparatedStringToAuthorityList(user.getRoles()));
        return user;
    }
    private List<GrantedAuthority> generateAuthorities(String roles){
        ArrayList<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        String[] split = roles.split(",");
        if(split!=null&&split.length>0){
            for (String item:split){
                grantedAuthorities.add(new SimpleGrantedAuthority(item));
            }
        }
        return grantedAuthorities;
    }
}
