package com.example.fork.domain.__8__image.service.impl;

import com.example.fork.domain.__8__image.service.ImageService;
import com.example.fork.global.data.dao.ImageDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class ImageServiceImpl implements ImageService {

    private ImageDao imageDao;

    @Autowired
    public ImageServiceImpl(ImageDao imageDao) {
        this.imageDao = imageDao;
    }

    @Override
    public Map<String, Object> deleteByImageId(String imageId) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            imageDao.deleteByImageId(imageId);
        } catch (Exception e) {
            /* DB delete error */
            // resultMap.put("errorCode", ErrorCode.DB_DELETE_ERROR);
            resultMap.put("reason", e.getMessage());
            resultMap.put("httpStatusCode", HttpStatus.INTERNAL_SERVER_ERROR);
            return resultMap;
        }
        /* no error */
        Map<String, Object> item = new HashMap<>();
        item.put("deleted image id", imageId);
        resultMap.put("item", item);
        //resultMap.put("errorCode", ErrorCode.NO_ERROR);
        resultMap.put("httpStatusCode", HttpStatus.OK);
        return resultMap;
    }

    @Override
    public Map<String, Object> addImage(String usage, String usageId, String fileName) {
        Map<String, Object> resultMap = new HashMap<>();
        String generatedId = UUID.randomUUID().toString();
        try {
            imageDao.addImage(usage, usageId, generatedId, fileName);
        } catch (Exception e) {
            /* DB delete error */
            //resultMap.put("errorCode", ErrorCode.DB_DELETE_ERROR);
            resultMap.put("reason", e.getMessage());
            resultMap.put("httpStatusCode", HttpStatus.INTERNAL_SERVER_ERROR);
            return resultMap;
        }
        /* no error */
        Map<String, Object> item = new HashMap<>();
        item.put("generated image id", generatedId);
        resultMap.put("item", item);
        //resultMap.put("errorCode", ErrorCode.NO_ERROR);
        resultMap.put("httpStatusCode", HttpStatus.OK);
        return resultMap;
    }

    @Override
    public String getPrevImageName(String usage, String usageId) {
        try {
            return imageDao.getPrevImageName(usage, usageId);
        } catch (Exception e) {
            /* DB access error */
            return "";
        }
    }

    @Override
    public String getPrevImageId(String usage, String usageId) {
        try {
            return imageDao.getPrevImageId(usage, usageId);
        } catch (Exception e) {
            /* DB access error */
            return "";
        }
    }

    @Override
    public String getFileNameByImageId(String imageId) {
        return imageDao.getFileNameByImageId(imageId);
    }

    @Override
    public void deleteFK(String usage, String usageId) {
        imageDao.deleteFK(usage, usageId);
    }
}
