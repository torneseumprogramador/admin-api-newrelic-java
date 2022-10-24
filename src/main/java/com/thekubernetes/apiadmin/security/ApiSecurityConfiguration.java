package com.thekubernetes.apiadmin.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class ApiSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private ApiEntryPoint entryPoint;

    public void configure(HttpSecurity httpSec) throws Exception {
        httpSec.csrf().disable()
                .exceptionHandling().authenticationEntryPoint(entryPoint)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/").permitAll()
                .antMatchers(HttpMethod.POST, "/create").permitAll()
                .antMatchers(HttpMethod.POST, "/login").permitAll()
                .antMatchers(HttpMethod.POST, "/token/valid").permitAll()
                .anyRequest().authenticated().and().cors();

        httpSec.addFilterBefore(new ApiFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
