package com.example.fork.global.data.dao;

public interface ImageDao {

    String getPrevImageName(String usage, String usageId);
    String getPrevImageId(String usage, String usageId);
    String getFileNameByImageId(String imageId);
    void deleteByImageId(String imageId);
    void addImage(String usage, String usageId, String generatedImageId, String fileName);
    void deleteFK(String usage, String usageId);
}
