package com.weichen.stumanager.mapper;

import com.weichen.stumanager.entity.Role;
import com.weichen.stumanager.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    User getUserByUserName(String userName);
    List<Role> getUserRolesByUserId(Integer userId);
}
