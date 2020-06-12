package com.test.rbac.rbac.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.test.rbac.rbac.dao.TokenDao;
import com.test.rbac.rbac.dto.*;
import com.test.rbac.rbac.entity.RoleMenuEntity;
import com.test.rbac.rbac.entity.TokenEntity;
import com.test.rbac.rbac.entity.UserEntity;
import com.test.rbac.rbac.entity.UserRoleEntity;
import com.test.rbac.rbac.service.*;
import com.test.rbac.common.dto.CommonReturn;
import com.test.rbac.common.service.impl.BaseServiceImpl;
import com.test.rbac.tools.my.MyUtils;
import com.test.rbac.tools.tokenUtils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author DNYY
 */
@Service
public class TokenServiceImpl extends BaseServiceImpl<TokenDao, TokenEntity, TokenDTO> implements TokenService {

    @Autowired
    UserRoleService userRoleService;

    @Autowired
    TokenService tokenService;

    @Autowired
    RoleService roleService;

    @Autowired
    RoleMenuService roleMenuService;

    @Autowired
    MenuService menuService;

    @Autowired
    UserService userService;

    QueryWrapper<RoleMenuEntity> roleMenuEntityQueryWrapper = new QueryWrapper<>();


    /**
     * 在添加方法前的钩子方法
     * @param dto
     */
    @Override
    public void beforeInsert(TokenDTO dto) {
        //添加创建时间与更新时间
        Date date = new Date();
        dto.setCreateDate(date);
        dto.setUpdateDate(date);
    }

    /**
     * 在编辑方法前的钩子方法
     * @param dto
     */
    @Override
    public void beforEedit(TokenDTO dto) {
        //添加更新时间
        Date date = new Date();
        dto.setUpdateDate(date);
    }

    /**
     * 创建token
     * @param userId
     * @return
     */
    @Override
    public Map<String,Object> createToken(Long userId){
        //token
        String token;

        //当前时间
        Date now = new Date();
        //过期时间
        Date expireTime = new Date(now.getTime() + 60 * 60 * 12 * 1000);

        //判断是否生成过Token
        QueryWrapper<TokenEntity> tokenQueryWrapper = new QueryWrapper<>();
        TokenEntity tokenEntity = this.getOne(tokenQueryWrapper.eq("user_id",userId));
        if(tokenEntity == null){
            token = TokenUtils.getRandomString(64);

            tokenEntity = new TokenEntity();
            tokenEntity.setUserId(userId);
            tokenEntity.setToken(token);
            tokenEntity.setExpireDate(expireTime);
            tokenEntity.setCreateDate(now);
            tokenEntity.setUpdateDate(now);

            //生成token
            this.save(tokenEntity);
        }else{
            token = TokenUtils.getRandomString(64);

            tokenEntity.setUpdateDate(now);
            tokenEntity.setExpireDate(expireTime);
            tokenEntity.setToken(token);

            //更新token
            updateById(tokenEntity);
        }

        Map<String,Object> map = new HashMap<>();
        map.put("token",token);
        map.put("expire",expireTime);

        return map;
    }

    /**
     * 查看token有没有过期
     * @param tokenEntity
     * @return
     */
    @Override
    public Boolean checkToken(TokenEntity tokenEntity) {
        if(tokenEntity.getExpireDate().getTime() < System.currentTimeMillis()){
            return false;
        }else{
            return true;
        }
    }

    /**
     * 通过用户的token来查看用户的信息
     * @param token
     * @return
     */
    @Override
    public CommonReturn see(String token) {
        CommonReturn result = new CommonReturn();
        UserDetailed user = new UserDetailed();
        QueryWrapper<TokenEntity> tokenQueryWrapper = new QueryWrapper<>();
        TokenEntity tokenEntity =  tokenService.getOne(tokenQueryWrapper.eq("token",token));
        //没有找到token
        if (tokenEntity==null){
            result.setAll(10001,null,"参数错误,请仔细检查您输入的参数!");
            return result;
        }
        //token过期
        if(!tokenService.checkToken(tokenEntity)){
            result.setAll(30003,null,"用户token过期请重新登陆!");
            return result;
        }
        //查询用户信息
        QueryWrapper<UserEntity> userQueryWrapper = new QueryWrapper<>();
        UserEntity userEntity = userService.getOne(userQueryWrapper.eq("id",tokenEntity.getUserId()));
        //没有找到对应用户
        if (userEntity==null) {
            result.setAll(10001,null,"参数错误,请仔细检查您输入的参数!");
            return result;
        }
        //查找用户角色信息
        QueryWrapper<UserRoleEntity> userRoleEntityQueryWrapper = new QueryWrapper<>();
        List<UserRoleEntity> userRoles = userRoleService.list(userRoleEntityQueryWrapper.eq("user_id",userEntity.getId()));
        //拿出userRole的id属性
        List<Long> ids = userRoles.stream().map(item -> item.getId()).collect(Collectors.toList());
        //拿到用户角色信息
        RoleDTO test = new RoleDTO();
        List<RoleDTO> roles = roleService.selectByIds(ids);
        //通过用户角色信息来获取用户权限
        ids = roles.stream().map(item -> item.getId()).collect(Collectors.toList());
        List<RoleMenuEntity> roleMenuEntities = new ArrayList<>();
        //获取用户角色中角色所有的权限id
//        for (Long id : ids ) {
//            QueryWrapper<RoleMenuEntity> roleMenuEntityQueryWrapper = new QueryWrapper<>();
//            List<RoleMenuEntity> roleMenus = roleMenuService.list(roleMenuEntityQueryWrapper.eq("role_id",id));
//            roleMenuEntities.addAll(roleMenus);
//        }
        //更换了上面的注释内容
        QueryWrapper<RoleMenuEntity> roleMenuEntityQueryWrapper = new QueryWrapper<>();
        List<RoleMenuEntity> roleMenus = roleMenuService.list(roleMenuEntityQueryWrapper.in("role_id",ids));
        roleMenuEntities.addAll(roleMenus);


        ids = roleMenuEntities.stream().map(item -> item.getMenuId()).collect(Collectors.toList());
        List<MenuDTO> menus = menuService.selectByIds(ids);



        //打包发出
        UserDTO userDTO = new UserDTO(userEntity.getId(),userEntity.getRealName());

        //加入token
        userDTO.setToken(token);

        //限制用户能看到的信息
        RoleDTO role = new RoleDTO();
        role.setId(2L);
        role.setName("123");
        roles = MyUtils.AllSet(roles,role);

        MenuDTO menu = new MenuDTO();
        menu.setPermissions("11");
        menu.setUrl("123");
        menu.setPid(1L);
        menu.setId(1L);
        menus = MyUtils.AllSet(menus,menu);

        user.setUserDTO(userDTO);
        user.setRoleDTOS(roles);
        user.setMenuDTOS(menus);


        result.setAll(20000,user,"查找成功");
        return result;
    }

}
