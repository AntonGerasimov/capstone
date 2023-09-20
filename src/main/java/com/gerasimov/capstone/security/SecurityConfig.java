package com.gerasimov.capstone.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.filter.HiddenHttpMethodFilter;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String ROLE_ADMIN = "admin";
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
        return new HiddenHttpMethodFilter();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/users").hasRole(ROLE_ADMIN)
                    .antMatchers("/users/edit/**").hasRole(ROLE_ADMIN)
                    .antMatchers("/users/delete/**").hasRole(ROLE_ADMIN)
                    .antMatchers("/checkout").authenticated()
//                    .antMatchers("/menu/**").permitAll() // Publicly accessible URLs
//                    .antMatchers("/**").permitAll()
//                    .anyRequest().authenticated() // All other URLs require authentication
                    .anyRequest().permitAll()
                    .and()
                .formLogin() // Enable form-based authentication
                    .loginPage("/login") // Custom login page URL
                    .loginProcessingUrl("/login")
                    .permitAll() // Allow access to the login page
                    .and()
                //                    .disable()
                .logout() // Enable logout
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/")
                    .invalidateHttpSession(true)
                    .permitAll() // Allow access to the logout page
                    .and()
                .csrf().disable().cors();

    }

}
