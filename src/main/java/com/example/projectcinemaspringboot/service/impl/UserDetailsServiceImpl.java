package com.example.projectcinemaspringboot.service.impl;

import com.example.projectcinemaspringboot.model.User;
import com.example.projectcinemaspringboot.repository.UserRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level =  AccessLevel.PRIVATE)
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByName(username);
            if (user == null){
                throw  new UsernameNotFoundException(
                        String.format("User with name=%s not found!!!", username));
            }
        return user;
    }
}