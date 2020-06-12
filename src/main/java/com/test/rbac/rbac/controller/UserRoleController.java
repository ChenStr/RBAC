package com.test.rbac.rbac.controller;

import com.test.rbac.rbac.dto.ToListDTO;
import com.test.rbac.rbac.dto.UserRoleDTO;
import com.test.rbac.rbac.service.UserRoleService;
import com.test.rbac.common.dto.CommonReturn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("userRole")
public class UserRoleController {

    @Autowired
    UserRoleService userRoleService;

    /**
     * 查询用户与角色的信息(需要权限)
     * @param userRoleDTO
     * @return
     */
    @GetMapping("getUserRole")
    public CommonReturn getUserRole(@RequestBody UserRoleDTO userRoleDTO){
        CommonReturn result = userRoleService.getUserRole(userRoleDTO);
        return result;
    }

    /**
     * 添加用户与角色的绑定关系(需要权限，还需要检查是否是管理员)
     * @param userToList
     * @return
     */
    @PostMapping("addUserRole")
    public CommonReturn addUserRole(@RequestBody ToListDTO<Long,Long> userToList){
        CommonReturn result = userRoleService.addUserRole(userToList);
        return result;
    }

    /**
     * 接触用户与角色的绑定关系(需要权限，还需要检查是否是管理员)
     * @param userTolist
     * @return
     */
    @DeleteMapping("delUserRole")
    public CommonReturn delUserRole(@RequestBody ToListDTO<Long,Long> userTolist){
        CommonReturn result = userRoleService.delUserRole(userTolist);
        return result;
    }
}
