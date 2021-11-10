package com.example.demo.config;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.example.demo.service.imp.LoginUserDetailService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@EnableWebSecurity
public class HttpSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
    private ObjectMapper objectMapper;
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/",
                			"/home",
                			"/hello",
                			"/javascript/**",
                			"/css/**",
                			"/register",
                			"/validation",
                			"/user/*/payConfirmed",
                			"/goodsDetail/*",
                			"/img/goodsImg/*").permitAll()
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .permitAll()
                .and()
            .logout()
            .permitAll()
            .logoutSuccessUrl("/logout_success");
//            .logoutSuccessHandler((request,response,authentication) -> {
//                Map<String,Object> map = new HashMap<String,Object>();
//                map.put("code",200);
//                map.put("message","退出成功");
//                map.put("data",authentication);
//                response.setContentType("application/json;charset=utf-8");
//                PrintWriter out = response.getWriter();
//                out.write(objectMapper.writeValueAsString(map));
//                out.flush();
//                out.close();
//            	response.setContentType("text/html; charset=utf-8");
//            })
        
        http.csrf().disable();
    }
    
    private void onLoginSuccess()
    {
    	
    }
    
    @Autowired
    private LoginUserDetailService loginUserDetailService;

    /**
     * 强散列哈希加密实现
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
    	// 从数据库读取的用户进行身份认证
        auth.userDetailsService(loginUserDetailService).passwordEncoder(passwordEncoder());
    }
}