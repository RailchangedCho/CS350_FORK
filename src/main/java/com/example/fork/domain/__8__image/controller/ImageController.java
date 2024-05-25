package com.example.fork.domain.__8__image.controller;

import com.example.fork.domain.__8__image.service.ImageService;
import com.example.fork.global.fileHandler.LocalStorageService.LocalStorageService;
import com.example.fork.global.fileHandler.LocalStorageService.StorageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.core.io.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Pattern;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/image")
@Validated
public class ImageController {

    private final LocalStorageService localStorageService;
    private final ImageService imageService;

    @Autowired
    public ImageController(LocalStorageService localStorageService,
                           ImageService imageService) {
        this.localStorageService = localStorageService;
        this.imageService = imageService;
    }

    @GetMapping("/files/{filename:.+}")
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = localStorageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @GetMapping("/{image_id}")
    public void servFile(@PathVariable String image_id,
                         HttpServletResponse response) throws IOException {

        String filename = imageService.getFileNameByImageId(image_id);
        File f = new File(localStorageService.getRootPath(), filename);

        response.setContentType("application/download");
        response.setContentLength((int)f.length());
        response.setHeader("Content-disposition", "attachment;filename=\"" + filename + "\"");

        OutputStream os = response.getOutputStream();
        FileInputStream fis = new FileInputStream(f);
        FileCopyUtils.copy(fis, os);
        fis.close();
        os.close();
    }

    @PostMapping("/{usage}/{usage_id}")
    public ResponseEntity<Map<String, Object>> handleFileUpload(@RequestParam("file") MultipartFile file,
                                                                @PathVariable @Pattern(regexp = "(facility|stamp|review)") String usage,
                                                                @PathVariable String usage_id) {
        Map<String, Object> responseBody = new HashMap<>();

        /* check path variable */
        // TODO : USAGE VALIDATION
//        if (usage.equals("user")) {
//            if (!dataValidator.isValidUserId(usageId)) {
//                /* invalid path */
//                responseBody.put("error_code", ErrorCode.RESOURCE_NOT_EXIST);
//                responseBody.put("reason", "There is no resource for the given path.");
//                return new ResponseEntity<>(responseBody, HttpStatus.NOT_FOUND);
//            }
//            return new ResponseEntity<>(null, HttpStatus.OK);
//        } else if (usage.equals("admin")) {
//            if (!dataValidator.isValidAdminId(usageId)) {
//                /* invalid path */
//                responseBody.put("error_code", ErrorCode.RESOURCE_NOT_EXIST);
//                responseBody.put("reason", "There is no resource for the given path.");
//                return new ResponseEntity<>(responseBody, HttpStatus.NOT_FOUND);
//            }
        //return new ResponseEntity<>(null, HttpStatus.OK);

        /* generate random file name */
        String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
        String generatedFileName = UUID.randomUUID() + "." + extension;

        /* upload */
        try {
            localStorageService.store(file, generatedFileName);
        } catch (StorageException e) {
            /* exception case : empty file, malicious file name, IOExceptions */
            //responseBody.put("error_code", ErrorCode.LOCAL_STORAGE_SAVE_ERROR);
            responseBody.put("reason", e.getMessage());
            return new ResponseEntity<>(responseBody, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        /* delete if previous one exists */
        String prevImageId = imageService.getPrevImageId(usage, usage_id);
        String prevImageName = imageService.getPrevImageName(usage, usage_id);

        /* delete */
        try {
            if (!prevImageName.equals("")) {
                localStorageService.delete(prevImageName);
            }
        } catch (Exception e) {
            /* exception case : empty file, malicious file name, IOExceptions */
            //responseBody.put("error_code", ErrorCode.LOCAL_STORAGE_DELETE_ERROR);
            responseBody.put("reason", e.getMessage());
            return new ResponseEntity<>(responseBody, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        /* delete from RDBMS */
        if (!prevImageId.equals("")) {
            imageService.deleteFK(usage, usage_id);
            Map<String, Object> deleteResultMap = imageService.deleteByImageId(prevImageId);
            if (!deleteResultMap.get("httpStatusCode").equals(HttpStatus.OK)) {
                /* delete fails */
                //responseBody.put("error_code", ErrorCode.DB_DELETE_ERROR);
                responseBody.put("reason", "Failed to delete previous data in RDBMS");
                return new ResponseEntity<>(responseBody, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        /* add image entity and update usage target */
        Map<String, Object> resultMap = imageService.addImage(usage, usage_id, generatedFileName);
        //return dataValidator.getMapResponseEntity(responseBody, resultMap);
        Map<String, Object> responseBody1 = new HashMap<>();
        responseBody1.put("success", true);
        responseBody1.put("error_code", 0);
        responseBody1.put("error_text", "no error");
        //responseBody1.put("item", null);

        return new ResponseEntity<>(responseBody1, HttpStatus.OK);
    }
}
