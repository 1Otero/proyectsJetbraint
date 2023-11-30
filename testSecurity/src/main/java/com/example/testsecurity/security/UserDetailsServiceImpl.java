package com.example.testsecurity.security;

import com.example.testsecurity.entitys.Role;
import com.example.testsecurity.entitys.RoleUser;
import com.example.testsecurity.entitys.User;
import com.example.testsecurity.repositorys.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
   @Autowired
   private UserRepository user;

    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        System.out.println(new BCryptPasswordEncoder().encode("ivan"));
        User user= this.user
                .findOneByMail(mail)
                .orElseThrow(() -> { return new UsernameNotFoundException("El usuario " + mail + " not found ");});
        System.out.println("user:   ");
        System.out.println(user.getUserName());
        System.out.println("_______________ user --");
        return new UserDetailsImpl(user);
    }

    public UserDetails loadUserByPassword(String password) throws UsernameNotFoundException{
        User u= new User();
        u.setName("Pele");
        u.setPassword("relol");
        u.setMail("pele@gmail.com");
        u.setListRoles(Arrays
                .asList(new RoleUser(1L,"maximo rol",
                        new Role(2L, "admin",null), null),
                        new RoleUser(2L,"puede comprar",
                                new Role(2L, "cli",null), null)));
        return new UserDetailsImpl(u);
    }

}
