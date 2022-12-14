package com.weichen.stumanager.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.weichen.stumanager.entity.User;
import com.weichen.stumanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

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
    @GetMapping("listeningWordTest/getSheet")
    @ResponseBody
    public String getSheet(@RequestParam("sheet") int sheet) throws JsonProcessingException
    {
        String url = "http://127.0.0.1:7000/listeningWordTest/getSheet";
        String params = "?sheet=" + sheet;
        String fullUrl = url + params;
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(fullUrl, String.class);
        return response;
    }
    @GetMapping("/listeningWordTest/getChoices")
    @ResponseBody
    public String getChoices(@RequestParam("sheet") String sheet, @RequestParam("word") String word) {
        String url = "http://127.0.0.1:7000/listeningWordTest/getChoices";
        String params = "?sheet="+sheet+"&word="+word;
        String fullUrl = url + params;
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(fullUrl, String.class);
        return response;
    }
    @GetMapping("/listeningWordTest")
    public String listeningWordTest()
    {
        return "wordLiteralTest";
    }
}
