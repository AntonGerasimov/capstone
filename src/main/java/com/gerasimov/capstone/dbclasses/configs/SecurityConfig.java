package com.gerasimov.capstone.dbclasses.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/users").hasRole("admin")
                    .antMatchers("/**").permitAll() // Publicly accessible URLs
                    .anyRequest().authenticated() // All other URLs require authentication
                    .and()
                .formLogin() // Enable form-based authentication
                    .loginPage("/login") // Custom login page URL
                    .loginProcessingUrl("/login")
                    .defaultSuccessUrl("/users/success")
                    .permitAll() // Allow access to the login page
                    .and()
                .logout() // Enable logout
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/")
                    .invalidateHttpSession(true)
                    .permitAll(); // Allow access to the logout page
    }

}
