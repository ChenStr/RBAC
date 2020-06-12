package com.test.rbac.rbac.service;

import com.test.rbac.rbac.dto.OneListDTO;
import com.test.rbac.rbac.dto.RoleDTO;
import com.test.rbac.rbac.entity.RoleEntity;
import com.test.rbac.common.dto.CommonReturn;
import com.test.rbac.common.service.BaseService;


/**
 * Role的Service层
 * @author DNYY
 */
public interface RoleService extends BaseService<RoleEntity, RoleDTO> {

    /**
     * 添加角色方法
     * @param roleDTO
     * @return
     */
    CommonReturn saveRole(RoleDTO roleDTO);

    /**
     * 编辑角色方法
     * @param roleDTO
     * @return
     */
    CommonReturn editRole(RoleDTO roleDTO);

    /**
     * 根据给定的条件来查找角色的方法
     * @param roleDTO
     * @return
     */
    CommonReturn getRole(RoleDTO roleDTO);

    /**
     * 获取全部的角色 分页
     * @param date
     * @return
     */
    CommonReturn getAllRole(RoleDTO date);

    CommonReturn delRole(OneListDTO<Long> ids);
}
