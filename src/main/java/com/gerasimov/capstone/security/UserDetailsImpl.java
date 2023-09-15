package com.gerasimov.capstone.security;

import com.gerasimov.capstone.domain.UserDto;
import com.gerasimov.capstone.entity.Role;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Slf4j
public class UserDetailsImpl implements UserDetails {
    @Getter
    private UserDto userDto;

    public UserDetailsImpl(UserDto userDto){
        this.userDto = userDto;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Role userRole = userDto.getRole();
        log.info("UserDetailsImpl. User role is " + userRole.getName() );
        GrantedAuthority authority = new SimpleGrantedAuthority(userRole.getName());
        return Collections.singleton(authority);
    }

    @Override
    public String getPassword() {
        return userDto.getPassword();
    }

    @Override
    public String getUsername() {
        return userDto.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
