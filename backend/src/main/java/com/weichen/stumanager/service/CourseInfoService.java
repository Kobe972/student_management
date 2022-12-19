package com.weichen.stumanager.service;

import com.weichen.stumanager.entity.CourseInfo;
import com.weichen.stumanager.entity.UploadedResources;
import com.weichen.stumanager.mapper.CourseInfoMapper;
import com.weichen.stumanager.mapper.UploadedResourcesMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CourseInfoService {
    @Resource
    private CourseInfoMapper courseInfoMapper;

    public List<CourseInfo> getAllInfo()
    {
        return courseInfoMapper.getAllInfo();
    }
    public void upload(String uploader, String title, String description, String coverURL, String signUpURL)
    {
        courseInfoMapper.upload(uploader,title,description,coverURL,signUpURL);
    }
    public void delete(String id)
    {
        courseInfoMapper.delete(id);
    }
}
