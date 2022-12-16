package com.weichen.stumanager.service;

import com.weichen.stumanager.entity.UploadedResources;
import com.weichen.stumanager.mapper.UploadedResourcesMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UploadedResourcesService {
    @Resource
    private UploadedResourcesMapper uploadedResourcesMapper;

    public List<UploadedResources> getAllResources()
    {
        return uploadedResourcesMapper.getAllResources();
    }
    public void upload(String uploader, String title, String description, String url)
    {
        uploadedResourcesMapper.upload(uploader,title,description,url);
    }
}
