package com.example.fork.domain.__3__alert.service.impl;

import com.example.fork.domain.__3__alert.service.AlertService;
import com.example.fork.global.data.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.LinkedHashMap;
import java.util.HashMap;

@Service
public class AlertServiceImpl implements AlertService {

    private final UserDao userDao;

    @Value("${firebase.api-key}")
    String apiKey;

    @Autowired
    public AlertServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public Boolean pushAlert(String userId, Map<String, Object> alertContent) {

        try {
            // TBD: get device token from database
            String deviceToken = userDao.getUser(userId).getDeviceId();
            if (deviceToken == null) {
                return false;
            }
            Map<String, Object> pushAlertContents = new LinkedHashMap<>();
            pushAlertContents.put("to", deviceToken);
            Map<String, Object> notification = new HashMap<>();
            notification.put("body", alertContent.get("body"));
            notification.put("title", alertContent.get("title"));
            pushAlertContents.put("notification", notification);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "key=" + apiKey);
            HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(pushAlertContents, headers);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Map> responseBody = restTemplate.exchange("https://fcm.googleapis.com/fcm/send", HttpMethod.POST, requestEntity, Map.class);
            Integer successFlag = (Integer) responseBody.getBody().get("success");
//            if (successFlag != 1) {
//                return false;
//            } else {
//                try {
//                    Boolean pushed = alertDao.pushUserAlert(userId, alertContent);
//                    return pushed;
//                } catch (RuntimeException e) {
//                    throw e;
//                }
//            }
        } catch (RuntimeException e) {
            throw e;
        }

        return true;
    }
}
