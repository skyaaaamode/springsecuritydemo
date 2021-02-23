package com.hundsun.springsecurity.demo.configration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;

/**
 * @author zhouzf32074
 * @Classname WebSecurityConfig
 * @Description TODO
 * @Date 2021/2/22 14:30
 */

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;


    //简单处理模模板
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests().anyRequest().authenticated().and()
//        .formLogin()
//        .loginPage("/myLogin.html")
//                //指定处理的路径
//                .loginProcessingUrl("/login")
//                //指定处理逻辑
//                .successHandler(new AuthenticationSuccessHandler() {
//                    @Override
//                    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
//                        httpServletResponse.setContentType("application/json;charset=UTF-8");
//                        httpServletResponse.getWriter().write("恭喜登录");
//                    }
//                })
//                .failureHandler(new AuthenticationFailureHandler() {
//                    @Override
//                    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
//                        httpServletResponse.setContentType("application/json;charset=UTF-8");
//                        httpServletResponse.setStatus(401);
//                        httpServletResponse.getWriter().write("登陆失败了");
//                    }
//                })
//                .permitAll().and().csrf().disable();
//
//    }


    /**
     * antMatchers（）是一个采用ANT模式的URL匹配器。ANT模式使用？匹配任意单个字符，使用*
     * 匹配0或任意数量的字符，使用**匹配0或者更多的目录。antMatchers（"/admin/api/**"）相当于匹配
     * 了/admin/api/下的所有API。此处我们指定当其必须为ADMIN角色时才能访问，/user/api/与之同
     * 理。/app/api/下的API会调用permitAll（）公开其权限
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/admin/api/**").hasRole("ADMIN")
                .antMatchers("/user/api/**").hasRole("USER")
                .antMatchers("/app/api/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().permitAll();
    }


    /**
     * 1.验证用户的数据源的配置方式
     *    1）@Bean注解
     *    2）实现userDetailService
     * @return
     */
//    @Override
//    @Bean
//    public UserDetailsService userDetailsService() {
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//        manager.createUser(User.withUsername("user").password("123").roles("USER").build());
//        manager.createUser(User.withUsername("admin").password("123").roles("USER", "ADMIN").build());
//        return manager;
//    }
//
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }


    /**
     * 2.配置数据库数据源
     */

//    @Override
//    @Bean
//    public UserDetailsService userDetailsService() {
//        JdbcUserDetailsManager manager = new JdbcUserDetailsManager();
//        manager.setDataSource(dataSource);
//        if (!manager.userExists("user")) {//因为本质是执行sql语句，如果重启，不判断值是否存在会报错
//            manager.createUser(User.withUsername("user").password("123").roles("USER").build());
//        }
//        if (!manager.userExists("admin")) {
//            //本质上是调用sql方法，如果要更高对应的sql脚本，可以调用自带set方法
//            manager.createUser(User.withUsername("admin").password("123").roles("USER", "ADMIN").build());
//        }
//        return manager;
//    }







}
