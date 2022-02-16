package com.javaschool.microecare.usermanagement.auth;

import com.javaschool.microecare.usermanagement.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class WebSecurityConfigurerImpl extends WebSecurityConfigurerAdapter {
    //Roles for TVPP users:
    // ADMIN: can manage entities and do customer care
    // EMPLOYEE: can do customer care, cannot manage entities
    @Autowired
    UserDetailsServiceImpl userDetailsService;

    private static final String leysanUserName = "leysan";
    private static final String leysanPassword = "leysan123";

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService) // db users
                .passwordEncoder(getEncoder());
        auth
                .inMemoryAuthentication() // hardcoded users
                .withUser(leysanUserName)
                .password(getEncoder().encode(leysanPassword))
                .roles("ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeRequests()
                //TODO: попроавить эндпоинты так, чтобы не зависело от порядка
                //TODO: перенести эндпоинты в конфиг

                //admin pages (only tariffs/all available for non-admin)
                .mvcMatchers("/tvpp/start").hasAnyRole("ADMIN", "EMPLOYEE")
                .mvcMatchers("/tvpp/tariffs/").hasAnyRole("ADMIN", "EMPLOYEE")
                .mvcMatchers("/tvpp/tariffs/**").hasRole("ADMIN")
                .mvcMatchers("/admin/tariffs/all").hasAnyRole("ADMIN", "USER")
                .mvcMatchers("/admin/**").hasRole("ADMIN")

                .anyRequest().authenticated()
                .and()
                .formLogin()
                .and()
                .httpBasic(); // (3)
    }



    @Bean
    public PasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }


}
