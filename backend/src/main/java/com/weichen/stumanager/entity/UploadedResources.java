package com.weichen.stumanager.entity;

public class UploadedResources {
    private Integer id;
    private String uploader;
    private String title;
    private String description;
    private String url;
    public int getId()
    {
        return id;
    }
    public void setId(Integer id)
    {
        this.id=id;
    }
    public String getUploader()
    {
        return uploader;
    }
    public void setUploader(String uploader)
    {
        this.uploader=uploader;
    }
    public String getTitle()
    {
        return title;
    }
    public void setTitle(String title)
    {
        this.title=title;
    }
    public String getDescription()
    {
        return description;
    }
    public void setDescription(String description)
    {
        this.description=description;
    }
    public String getUrl()
    {
        return url;
    }
    public void setUrl()
    {
        this.url=url;
    }
}
