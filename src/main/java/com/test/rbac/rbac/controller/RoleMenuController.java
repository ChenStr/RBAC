package com.test.rbac.rbac.controller;

import com.test.rbac.rbac.dto.RoleMenuDTO;
import com.test.rbac.rbac.dto.ToListDTO;
import com.test.rbac.rbac.service.RoleMenuService;
import com.test.rbac.rbac.service.RoleService;
import com.test.rbac.common.dto.CommonReturn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("roleMenu")
public class RoleMenuController {

    @Autowired
    RoleService roleService;

    @Autowired
    RoleMenuService roleMenuService;

    /**
     * 查询角色与菜单的信息(需要权限)
     * @param roleMenu
     * @return
     */
    @GetMapping("getRoleMenu")
    public CommonReturn getRoleMenu(@RequestBody RoleMenuDTO roleMenu){
        CommonReturn result = roleMenuService.getRoleMenu(roleMenu);
        return result;
    }

    /**
     * 添加角色与菜单的绑定关系(需要权限，还需要检查是否是管理员)
     * @param menuToList
     * @return
     */
    @PostMapping("addRoleMenu")
    public CommonReturn addRoleMenu(@RequestBody ToListDTO<Long,Long> menuToList){
        CommonReturn result = roleMenuService.addRoleMenu(menuToList);
        return result;
    }

    /**
     * 解除角色与菜单的绑定关系(需要权限，还需要检查是否是管理员)
     * @param menuToList
     * @return
     */
    @DeleteMapping("delRoleMenu")
    public CommonReturn delRoleMenu(@RequestBody ToListDTO<Long,Long> menuToList){
        CommonReturn result = roleMenuService.delRoleMenu(menuToList);
        return result;
    }

}
