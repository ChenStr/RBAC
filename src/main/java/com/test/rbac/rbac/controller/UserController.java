package com.test.rbac.rbac.controller;

import com.test.rbac.rbac.dto.UserDTO;
import com.test.rbac.rbac.service.RoleService;
import com.test.rbac.rbac.service.TokenService;
import com.test.rbac.rbac.service.UserService;
import com.test.rbac.common.dto.CommonReturn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户管理
 * @author DNYY
 */
@RestController
@RequestMapping("user/")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private RoleService roleService;

    /**
     * 用户注册
     * @param user
     * @return
     */
    @PostMapping("register")
    public CommonReturn register(@RequestBody UserDTO user){

        CommonReturn result = userService.register(user);
        return result;
    }

    /**
     * 用户登陆
     * @param user
     * @return
     */
    @PostMapping("login")
    public CommonReturn login(@RequestBody UserDTO user){

        CommonReturn result = userService.login(user);
        return result;
    }

    /**
     * 用户修改信息方法，需要进行权限的认证
     * @param user
     * @return
     */
    @PutMapping("editUser")
    public CommonReturn editUser(@RequestBody UserDTO user){

        CommonReturn result = userService.editUser(user);
        return result;
    }


    /**
     * 查看用户信息
     * @param user
     * @return
     */
    @GetMapping("getUser")
    public CommonReturn getUser(@RequestBody UserDTO user){
        CommonReturn result = null;
        return result;
    }

    /**
     * 用户路由里的测试方法
     * @return
     */
    @GetMapping("test")
    public CommonReturn addtest(String token){
        CommonReturn result = new CommonReturn();

//        CommonReturn result = tokenService.see(token);
//        System.out.println("this is a function");
        return result;
    }
}
