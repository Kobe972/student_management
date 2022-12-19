package com.weichen.stumanager.mapper;

import com.weichen.stumanager.entity.CourseInfo;
import com.weichen.stumanager.entity.UploadedResources;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CourseInfoMapper {
    @Select("select * from courseInfo")
    List<CourseInfo> getAllInfo();
    @Insert("insert into courseinfo(uploader,title,description,coverURL,signUpURL)values(#{uploader},#{title},#{description},#{coverURL},#{signUpURL})")
    void upload(@Param("uploader") String uploader, @Param("title") String title, @Param("description") String description, @Param("coverURL") String coverURL, @Param("signUpURL") String signUpURL);
}
