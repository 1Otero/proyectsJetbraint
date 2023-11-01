package com.example.testjwt.security;

import com.example.testjwt.entity.Client;
import com.example.testjwt.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class userDetailServiceImpl implements UserDetailsService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("mail enviado: ");
        System.out.println(email);
        System.out.println("passw: ");
        System.out.println(new BCryptPasswordEncoder().encode("ivan"));
        Client cli=  clientRepository.findOneByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("El usuario con email: " + email + " No existe. "));
        return new UserDetailsImpl(cli);
    }



}
