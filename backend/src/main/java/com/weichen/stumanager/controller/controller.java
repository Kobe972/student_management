package com.weichen.stumanager.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.weichen.stumanager.entity.User;
import com.weichen.stumanager.service.UploadedResourcesService;
import com.weichen.stumanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class controller {
    @Resource
    UserService userService = null;

    @Resource
    UploadedResourcesService uploadedResourcesService=null;

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/register")
    public String addMemberUser(@RequestParam("userName") String userName, @RequestParam("password") String password) {
        userService.insertMember(userName, password);
        return "redirect:/login";
    }

    @GetMapping("/register")
    public String memberRegister() {
        return "memberRegister";
    }

    @GetMapping("listeningWordTest/getSheet")
    @ResponseBody
    public String getSheet(@RequestParam("sheet") int sheet) throws JsonProcessingException {
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
        String params = "?sheet=" + sheet + "&word=" + word;
        String fullUrl = url + params;
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(fullUrl, String.class);
        return response;
    }

    @GetMapping("/listeningWordTest")
    public String listeningWordTest() {
        return "wordLiteralTest";
    }

    @GetMapping("/uploadResources")
    public String uploadResourcesGet() {
        return "uploadResources";
    }

    @PostMapping("/upload/resources")
    @ResponseBody
    public String uploadResourcesPost(@RequestParam("file") MultipartFile file,@RequestParam("title") String title,@RequestParam("description") String description)
    {
        String basePath = "uploadedFiles" + File.separator + "resources" + File.separator;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName;
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        userName = userDetails.getUsername();  // Assuming that the user's ID is stored in the "username" field of the UserDetails object
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSS");
        String currentTime = now.format(formatter);
        try {
            Files.createDirectories(Paths.get(basePath));
            byte[] bytes = file.getBytes();
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] hash = messageDigest.digest((userName+title+description+file.getOriginalFilename()).getBytes(StandardCharsets.UTF_8));
            String filename = String.format("%032x", new BigInteger(1, hash))+currentTime+file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            Path path = Paths.get(basePath+filename);
            Files.write(path, bytes);
            uploadedResourcesService.upload(userName,title,description,"/getUploadedResources/"+filename);
            return "success";
        } catch (IOException | NoSuchAlgorithmException e) {
            return e.toString();
        }
    }
}
