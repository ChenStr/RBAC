package com.test.rbac.rbac.dao;

import com.test.rbac.rbac.entity.RoleMenuEntity;
import com.test.rbac.common.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统用户角色表dao层
 * @author DNYY
 */
@Mapper
public interface RoleMenuDao extends BaseDao<RoleMenuEntity> {
    
}
