package com.example.demo.service.imp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.demo.dao.datastruct.*;
import com.example.demo.dao.interf.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LoginUserDetailService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private UserRepository userr;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        log.info("用户的登录信息被加载了,当前请求用户名为："+userName);

        //通过userName获取用户信息
        com.example.demo.dao.datastruct.User userInfo = userr.getByName(userName);
        if(userInfo == null) {
            throw new UsernameNotFoundException("not found");
        }
        log.info("数据库中保存的用户信息" + userInfo.toString());

        //定义权限列表.
        List<GrantedAuthority> authorities = new ArrayList<>();
        List<ShopRole> roles=userr.getRoleByUserName(userName);
        for(int i=0;i<roles.size();i++)
        {
        	// 用户可以访问的资源名称（或者说用户所拥有的权限） 注意：必须"ROLE_"开头
            authorities.add(new SimpleGrantedAuthority("ROLE_"+roles.get(i).getName()));
        }
        
        //注意这里的user为security内置的用户类型，类型为org.springframework.security.core.userdetails.User
        User userDetails = new User(userInfo.getName(),passwordEncoder.encode(userInfo.getPassword()),authorities);
        log.info(userDetails.toString());
        return userDetails;
    }

}