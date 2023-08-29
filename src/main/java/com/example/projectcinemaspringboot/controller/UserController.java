package com.example.projectcinemaspringboot.controller;

import com.example.projectcinemaspringboot.model.User;
import com.example.projectcinemaspringboot.service.impl.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UserController {

    UserService userService;

    @GetMapping("/save")
    public String save(Model model) {
        model.addAttribute("user", new User());
        return "/user/save";
    }

    @PostMapping("/save_user")
    public String saveUser(@ModelAttribute User user) {
        userService.sendMessage(user.getEmail());
        userService.save(user);
        return "redirect:/cinema/find_all";
    }
}
