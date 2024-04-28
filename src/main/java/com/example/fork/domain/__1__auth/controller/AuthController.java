package com.example.fork.domain.__1__auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.constraints.Pattern;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    //@Autowired
    //public AuthController() {
    //}

    @PostMapping("")
    //@RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<Map<String, Object>> userRegister(@RequestHeader Map<String, String> requestHeader,
                                                            @RequestParam(required = false, defaultValue = "distance") @Pattern(regexp = "^(distance)$") String field,
                                                            @RequestParam(required = false, defaultValue = "asc") @Pattern(regexp = "^(asc|desc)$") String sort,
                                                            @RequestParam(required = false, defaultValue = "0") Float latitude,
                                                            @RequestParam(required = false, defaultValue = "0") Float longitude) {

        // Processing ResponseEntity
        Map<String, Object> item = new HashMap<>();
        //item.put("BranchList", branchDtoList);
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("success", true);
        responseBody.put("error_code", 0);
        responseBody.put("error_text", "no error");
        responseBody.put("item", item);

        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }
}
