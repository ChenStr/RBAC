package com.test.rbac.rbac.controller;

import com.test.rbac.rbac.dto.OneListDTO;
import com.test.rbac.rbac.dto.RoleDTO;
import com.test.rbac.rbac.service.RoleService;
import com.test.rbac.common.dto.CommonReturn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 角色管理
 * @author DNYY
 */
@RestController
@RequestMapping("role")
public class RoleController {

    @Autowired
    RoleService roleService;

    /**
     * 添加角色(需要验证权限，需要管理员验证)
     * @param role
     * @return
     */
    @PostMapping("/addRole")
    public CommonReturn addRole(@RequestBody RoleDTO role){

        CommonReturn result = roleService.saveRole(role);
        return result;
    }

    /**
     * 修改角色(需要权限验证，需要管理员验证)
     * @param role
     * @return
     */
    @PutMapping("/editRole")
    public CommonReturn editRole(@RequestBody RoleDTO role){

        CommonReturn result = roleService.editRole(role);
        return result;
    }

    /**
     * 根据给定的条件来查找角色(需要验证权限)
     * @param role
     * @return
     */
    @GetMapping("/getRole")
    public CommonReturn getRole(@RequestBody RoleDTO role){

        CommonReturn result = roleService.getRole(role);
        return result;
    }

    /**
     * 根据给定的条件来查找角色(需要验证权限)
     * @param date
     * @return
     */
    @GetMapping("/getAll")
    public CommonReturn getAll(@RequestBody RoleDTO date){

        CommonReturn result = roleService.getAllRole(date);
        return result;
    }

    /**
     * 删除角色，同时解除所有与角色绑定的用户关系(需要权限验证，需要管理员验证)
     * @param ids
     * @return
     */
    @DeleteMapping("delRole")
    public CommonReturn delRole(@RequestBody OneListDTO<Long> ids){
        CommonReturn result = roleService.delRole(ids);
        return result;
    }
}
