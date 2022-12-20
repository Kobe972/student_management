package com.weichen.stumanager.service;

import com.weichen.stumanager.entity.Role;
import com.weichen.stumanager.entity.User;
import com.weichen.stumanager.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class UserService implements UserDetailsService {

    @Resource
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 用户名必须是唯一的，不允许重复
        User user = userMapper.getUserByUserName(username);
        if(ObjectUtils.isEmpty(user)){
            throw new UsernameNotFoundException("根据用户名找不到该用户的信息！");
        }
        List<Role> roleList = userMapper.getUserRolesByUserId(user.getUserId());
        user.setRoles(roleList);
        return user;
    }
    public User getUserByName(String username)
    {
        return userMapper.getUserByUserName(username);
    }
    public boolean insertMember(String username, String password)
    {
        User user=getUserByName(username);
        if(user!=null) return false;
        BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
        userMapper.insertUserToAuthUser(username,bCryptPasswordEncoder.encode(password));
        user=getUserByName(username);
        userMapper.insertUserToAutoUserRole(user.getUserId(),"member");
        return true;
    }
    public boolean insertFaculty(String username, String password)
    {
        User user=getUserByName(username);
        if(user!=null) return false;
        BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
        userMapper.insertUserToAuthUser(username,bCryptPasswordEncoder.encode(password));
        user=getUserByName(username);
        userMapper.insertUserToAutoUserRole(user.getUserId(),"faculty");
        return true;
    }
}
