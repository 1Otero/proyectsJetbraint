package com.example.ventas.utils.security;

import com.example.ventas.modelos.Client;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Arrays;

import java.util.Collection;
@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {
    private Client client;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority("CLI"), new SimpleGrantedAuthority("ADMIN"));
    }

    @Override
    public String getPassword() {
        return client.getPass();
    }

    @Override
    public String getUsername() {
        return client.getCorreo();
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
