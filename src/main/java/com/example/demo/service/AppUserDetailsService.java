package com.example.demo.service;

import com.example.demo.dao.UserRepo;
import com.example.demo.model.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class AppUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepo repoUsr;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        AppUser user = repoUsr.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        UserDetails usr = User.withUsername(user.getUsername()).password(user.getPassword()).authorities(user.getRole()).build();
        return usr;

    }
}
