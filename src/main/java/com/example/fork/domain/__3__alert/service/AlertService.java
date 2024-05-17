package com.example.fork.domain.__3__alert.service;

import java.util.Map;

public interface AlertService {

    Boolean pushAlert(String userId, Map<String, Object> alertContent);
}
