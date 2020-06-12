package com.test.rbac.rbac.controller;

import com.test.rbac.rbac.dto.UserDTO;
import com.test.rbac.tools.password.PasswordUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/test")
public class TestClass {
    @RequestMapping("/test")
    public String test(@RequestBody UserDTO userDTO){
        String password = PasswordUtils.encode(userDTO.getPassword());
        return password;
    }
}
