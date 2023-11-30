package com.example.jwtspring.security;

import com.example.jwtspring.modelos.Client;
import com.example.jwtspring.repositorys.clientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private clientRepository user;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
     System.out.println(new BCryptPasswordEncoder().encode("good"));
     Client cli= user.findOneByCorreo(username)
                    .orElseThrow(()-> {return new UsernameNotFoundException("this username not found in database ");});
     System.out.println(cli.getName());
     return new UserDetailsImpl(cli);
    }
}
