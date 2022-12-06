package com.weichen.stumanager.controller;

import com.weichen.stumanager.entity.User;
import com.weichen.stumanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class controller {
    @Autowired
    UserService userService = null;
    @RequestMapping("/")
    public String index()
    {
        return "index";
    }
    @PostMapping("/register")
    public String addMemberUser(@RequestParam("userName") String userName,@RequestParam("password") String password)
    {
        userService.insertMember(userName,password);
        return "redirect:/login";
    }
    @GetMapping("/register")
    public String memberRegister()
    {
        return "memberRegister";
    }
}
