package com.test.rbac.rbac.controller;


import com.test.rbac.rbac.dto.MenuDTO;
import com.test.rbac.rbac.dto.UserDetailed;
import com.test.rbac.rbac.service.MenuService;
import com.test.rbac.rbac.service.TokenService;
import com.test.rbac.common.dto.CommonReturn;
import com.test.rbac.tools.my.MyUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;


@Aspect
@Component
@Configuration
public class AopTestClass {

    @Autowired
    MenuService menuService;

    @Autowired
    TokenService tokenService;

    /**
     * 切点签名,直接空着就行了
     */
    @Pointcut("execution(public * com.test.rbac.rbac.controller.*.edit*(..)) || execution( public * com.test.rbac.rbac.controller.*.add*(..)) || execution( public * com.test.rbac.rbac.controller.*.get*(..)) || execution( public * com.test.rbac.rbac.controller.*.del*(..))")
    public void print() {
//        System.out.println("this is a print");
    }

//    @Before("print()")
//    public void before(JoinPoint joinPoint) {
//        System.out.println("this is a before");
//    }


//    @After("print()")
//    public void after() {
//        System.out.println("this is a after");
//    }

//    /**
//     * 返回通知 before()->function()->after()->getAfterReturn()
//     * @param joinPoint
//     * @param result 执行方法的返回值
//     */
//    @AfterReturning(pointcut = "print()", returning = "result")
//    public void getAfterReturn(JoinPoint joinPoint,CommonReturn result) {
//        result = null;
//        System.out.println("this is a AfterReturning()");
//    }

//    /**
//     * 返回异常通知
//     * @param joinPoint
//     * @param e
//     */
//    @AfterThrowing(value="print()",throwing="e")
//    public void AfterThrowing(JoinPoint joinPoint, Exception e){
//        System.out.println("this is a AfterThrowing");
//    }

    /**
     * @Around：环绕通知 Around->after
     */
    @Around(value = "print()")
    public Object Around(ProceedingJoinPoint joinPoint) throws Throwable {
        CommonReturn result = new CommonReturn();
        //获取你访问的地址
        String url = joinPoint.getSignature().toString();
        int i = url.indexOf(".",35);
        int j = url.indexOf("(");
        if(i!=-1 && j!=-1){
            url = url.substring(i+1,j);
        }
        //获取传入参数
        Object[] Args = joinPoint.getArgs();
        //获取token用户
        String token = null;

        //通过用户的token来获取用户的角色与权限信息
        try{
            token = (String) MyUtils.getFieldValueByFieldName("token",Args[0]);
        }catch (Exception e){
            result.setAll(10001,null,"参数错误");
        }
        result = tokenService.see(token);
        try{
            if(result==null || result.data==null){
                return result;
            }
        }catch (Exception e){
            return result;
        }
        UserDetailed userD = (UserDetailed)result.getData();
        List<MenuDTO> userMenus = userD.getMenuDTOS();
        //查找用户菜单权限里是否有给定的权限
        for( MenuDTO userMenu : userMenus ){
            if(userMenu.getUrl().equals(url)){
                //进入本体方法，proceed()执行方法
                return joinPoint.proceed();
            }
//            System.out.println(userMenu.getUrl().equals(url) + "----" + userMenu.getUrl() + "----" + url);
        }
        result.setAll(30004,null,"用户没有权限");
        return result;
    }
}
