package com.woodworkingexchange.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.session.HttpSessionEventPublisher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter
{
  @Autowired
  private UserDetailsService userDetailsService;
  
  @Bean
  public PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
  }
  
  @Autowired
  public void globalSecurity (AuthenticationManagerBuilder auth) throws Exception
  {
    auth.userDetailsService(userDetailsService)
      .passwordEncoder(passwordEncoder());
  }
  
  @Override
  protected void configure(HttpSecurity http) throws Exception
  {
    http
      .csrf().disable()
      .authorizeRequests()
        .antMatchers("/index").permitAll()
        .antMatchers("/login/**").permitAll()
        .antMatchers("/register").permitAll()
        .antMatchers("/**.html").permitAll()
        .antMatchers("/login/registerSuccess").permitAll()
        .antMatchers("/css/**").permitAll()
        .antMatchers("/javascript/**").permitAll()
        .antMatchers("/images/**").permitAll()
        .antMatchers("/contact").permitAll()
        .antMatchers("/listings/listing").permitAll()
        .antMatchers("/listings/viewListing").permitAll()
        .antMatchers("/search/keyword/**").permitAll()
        .antMatchers("/**").hasRole("USER")
        .antMatchers("/**/**").hasRole("USER")
        .anyRequest().authenticated()
        .and()
      .formLogin()
        .loginPage("/login")
        .permitAll()
        .and()
      .logout()
        .logoutSuccessUrl("/").permitAll()
        .and()
      .sessionManagement()
        .maximumSessions(1);
  }
  
  @Bean
  public HttpSessionEventPublisher httpSessionEventPublisher() {
      return new HttpSessionEventPublisher();
  }
  
}
