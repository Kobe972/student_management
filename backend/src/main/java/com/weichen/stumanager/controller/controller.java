package com.weichen.stumanager.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.weichen.stumanager.entity.CourseInfo;
import com.weichen.stumanager.entity.User;
import com.weichen.stumanager.service.CourseInfoService;
import com.weichen.stumanager.service.UploadedResourcesService;
import com.weichen.stumanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

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
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class controller {
    @Resource
    UserService userService = null;

    @Resource
    UploadedResourcesService uploadedResourcesService=null;

    @Resource
    CourseInfoService courseInfoService=null;

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
    @GetMapping("/sheetChoice")
    public String sheetChoice()
    {
        return "sheetChoice";
    }
    @GetMapping("/listeningWordTest")
    public ModelAndView listeningWordTest(@RequestParam("sheet") int sheet) {
        ModelAndView result;
        result = new ModelAndView("wordLiteralTest");
        result.addObject("sheet",sheet);
        return result;
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
    @PostMapping("/upload/courseInfo")
    @ResponseBody
    public String uploadCourseInfoPost(@RequestParam("file") MultipartFile file,@RequestParam("title") String title,@RequestParam("description") String description,@RequestParam("signUpURL") String signUpURL)
    {
        String basePath = "image" + File.separator + "courseCover" + File.separator;
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
            courseInfoService.upload(userName,title,description,"/image/courseCover/"+filename,signUpURL);
            return "success";
        } catch (IOException | NoSuchAlgorithmException e) {
            return e.toString();
        }
    }
    @GetMapping("/courseList")
    @ResponseBody
    public String getCourseList(@RequestParam("num") int num) {
        List<CourseInfo> courseInfos = courseInfoService.getAllInfo();
        List<CourseInfo> subList = courseInfos.subList(Math.max(0, courseInfos.size() - num), courseInfos.size());

        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(subList);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "";
        }
    }
    @GetMapping(value = "/image/courseCover/{coverURL}", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getCourseCover(@PathVariable String coverURL) throws IOException {
        String basePath = "image" + File.separator + "courseCover" + File.separator;
        File file = new File(basePath+coverURL);
        return Files.readAllBytes(file.toPath());
    }
    @GetMapping("uploadCourseInfo")
    public String uploadCourseInfoGet(){return "uploadCourseInfo";}
    @GetMapping("backstage")
    public String backstage(){return "backstage";}
    @GetMapping("delete/courseInfo")
    public String deleteCourseInfo(@RequestParam("id") String id, @RequestParam("coverURL") String coverURL)
    {
        try
        {
            courseInfoService.delete(id);
            String basePath = "image" + File.separator + "courseCover" + File.separator;
            File file = new File(basePath+coverURL);
            file.delete();
            return "index";
        } catch (Exception e)
        {
            return "index";
        }
    }
    @GetMapping("/getRole")
    @ResponseBody
    public Set<String> getRole(Authentication authentication) {
        if (authentication == null) {
            return Collections.emptySet();
        }
        return authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());
    }
    @GetMapping("/getUsername")
    @ResponseBody
    public String getUsername(Authentication authentication)
    {
        if(authentication==null) return "";
        String userName;
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        userName = userDetails.getUsername();
        return userName;
    }
}
