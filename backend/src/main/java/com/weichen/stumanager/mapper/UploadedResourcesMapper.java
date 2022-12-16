package com.weichen.stumanager.mapper;

import com.weichen.stumanager.entity.UploadedResources;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UploadedResourcesMapper {
    @Select("select * from uploadedResources")
    List<UploadedResources> getAllResources();
    @Insert("insert into uploadedResources(uploader,title,description,url)values(#{uploader},#{title},#{description},#{url})")
    void upload(@Param("uploader") String uploader, @Param("title") String title, @Param("description") String description,@Param("url") String url);
}
