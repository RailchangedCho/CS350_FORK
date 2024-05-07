package com.example.fork.domain.__3__alert.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Pattern;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/alert")
public class AlertController {

    @ResponseBody
    @PostMapping("/device")
    public ResponseEntity<Map<String, Object>> saveDeviceId(@RequestHeader Map<String, String> requestHeader,
                                                            @RequestParam("code") String code) {

        /*
        String JwtTokenString = requestHeader.get("authorization");
        String requestUserId = authProvider.getUserInfoByAccessToken(JwtTokenString).get("id");
        Integer userAuthType = authProvider.getUserAuthType(requestUserId);

        EndUserDto endUserDto = endUserDao.getEndUserDetail(requestUserId);
        endUserDto.setDevice_id(code);
        endUserDao.editEndUser(endUserDto);

         */

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("success", true);
        responseBody.put("error_code", 0);
        responseBody.put("error_text", "no error");
        //responseBody.put("item", item);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @ResponseBody
    @PostMapping("")
    public ResponseEntity<Map<String, Object>> sendAlert(@RequestHeader Map<String, String> requestHeader,
                                                         @RequestBody Map<String, Object> requestBody) {

        /*
        System.out.println(requestBody);
        System.out.println(" *** 알림 수신 및 처리 완료 *** ");

        // Case 1. 주문 상태 최신화
        if (requestBody.get("type").toString().equals("orderStatus")) {

            Map<String, Object> bodyMap = (Map<String, Object>) requestBody.get("body");

            String orderId = bodyMap.get("orderId").toString();
            OrderDto orderDto = orderDao.getOrderDetail(orderId);
            String userId = orderDto.getEndUser();

            if (bodyMap.get("status_code").equals(0)) {
                orderDto.setStatus_code(OrderStatus.READY);
            }

            else if (bodyMap.get("status_code").equals(1)) {
                orderDto.setStatus_code(OrderStatus.RUNNING);
            }

            else if (bodyMap.get("status_code").equals(2)) {
                orderDto.setStatus_code(OrderStatus.DONE);
                Map<String, Object> alertBody = new HashMap<>();
                alertBody.put("orderId", orderId);
                alertBody.put("content", "주문하신 음료의 제작이 완료되었습니다.");
                Map<String, Object> alertContent = new HashMap<>();
                alertContent.put("title", "주문 제작 완료");
                alertContent.put("body", alertBody);

                alertService.pushAlert(userId, alertContent);
            }

            else {
                orderDto.setStatus_code(OrderStatus.CANCEL);
            }

            orderDao.editOrder(orderDto);
        }

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("success", true);
        responseBody.put("error_code", 0);
        responseBody.put("error_text", "no error");
        //responseBody.put("item", null);
        */
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
