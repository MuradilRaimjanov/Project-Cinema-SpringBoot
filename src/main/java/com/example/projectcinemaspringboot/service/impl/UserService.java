package com.example.projectcinemaspringboot.service.impl;

import com.example.projectcinemaspringboot.model.Role;
import com.example.projectcinemaspringboot.model.User;
import com.example.projectcinemaspringboot.repository.RoleRepository;
import com.example.projectcinemaspringboot.repository.UserRepository;
import com.example.projectcinemaspringboot.service.ServiceLayer;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserService implements ServiceLayer<User> {

    @Autowired
    UserRepository userRepository;

    @Autowired
    JavaMailSender emailSender;
    final RoleRepository roleRepository;

    final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JavaMailSender emailSender) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailSender = emailSender;
    }


    @Override
    public void save(User user) {
        Role role = null;
        if (user.getName().equals("admin")) {
            role = roleRepository.findByName("ADMIN");
            if (role == null){
                role = new Role("ADMIN");
            }
        } else {
            role = roleRepository.findByName("USER");
            if (role == null) {
                role = new Role("USER");
            }
        }
//        List<Role> roles = new ArrayList<>();
//        Role role = new Role();
//        if (user.getName().equals("admin")) {
//            role.setName("ADMIN");
//        } else {
//            role.setName("USER");
//        }
        user.setRoles(new ArrayList<>(Collections.singletonList(role)));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public User findById(Long id) {
        return null;
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public User update(Long id, User user) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public User findByName(String name) {
        return null;
    }

    public void sendMessage(String email) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setFrom("gadgetarium_application@gmail.com");
        mailMessage.setSubject("Order information!");
        String massage = "This message from my Spring Boot application";
        mailMessage.setText(massage);
        emailSender.send((mailMessage));
    }
}
