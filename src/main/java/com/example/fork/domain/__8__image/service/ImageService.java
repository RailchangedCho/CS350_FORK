package com.example.fork.domain.__8__image.service;

import java.util.Map;

public interface ImageService {

    Map<String, Object> deleteByImageId(String imageId);
    Map<String, Object> addImage(String usage, String usageId, String fileName);
    String getPrevImageName(String usage, String usageId);
    String getPrevImageId(String usage, String usageId);
    String getFileNameByImageId(String imageId);
    void deleteFK(String usage, String usageId);
}
