package com.test.rbac.rbac.controller;

import com.test.rbac.rbac.dto.MenuDTO;
import com.test.rbac.rbac.dto.OneListDTO;
import com.test.rbac.rbac.dto.RoleDTO;
import com.test.rbac.rbac.service.MenuService;
import com.test.rbac.common.dto.CommonReturn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 菜单管理
 * @author DNYY
 */
@RestController
@RequestMapping("menu")
public class MenuController {

    @Autowired
    MenuService menuService;

    /**
     * 根据给定的条件来查找菜单(需要验证权限)
     * @param menu
     * @return
     */
    @GetMapping("/getMenu")
    public CommonReturn getMenu(@RequestBody MenuDTO menu){

        CommonReturn result = menuService.getMenu(menu);
        return result;
    }

    /**
     * 添加菜单(需要验证权限，需要管理员验证)
     * @param menu
     * @return
     */
    @PostMapping("/addMenu")
    public CommonReturn addMenu(@RequestBody MenuDTO menu){

        CommonReturn result = menuService.saveMenu(menu);
        return result;
    }

    /**
     * 修改菜单(需要权限验证，需要管理员权限验证)
     * @param menu
     * @return
     */
    @PutMapping("/editMenu")
    public CommonReturn editMenu(@RequestBody MenuDTO menu){
        CommonReturn result = menuService.editMenu(menu);
        return result;
    }

    /**
     * 删除菜单(需要验证权限，需要管理员验证)
     * @param ids
     * @return
     */
    @DeleteMapping("/delMenu")
    public CommonReturn delMenu(@RequestBody OneListDTO<Long> ids){
        CommonReturn result = menuService.delMenu(ids);
        return result;
    }

    /**
     * 根据给定的条件来查找菜单(需要验证权限)
     * @param menu
     * @return
     */
    @GetMapping("/getAll")
    public CommonReturn getAll(@RequestBody MenuDTO menu){

        CommonReturn result = menuService.getAllMenu(menu);
        return result;
    }
}
