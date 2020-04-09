package com.linktop.cloud.entranceguardcore.util;

/**
 * @创建人 baojielei
 * @创建时间 2020/3/19
 * @描述
 */

/**
 * session工具类
 *@Author zhk
 *@Date 2018-1-16
 **/


import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @ClassName SessionUtil
 * @Description 对于Session中的操作，封装起来，因为键值对中的键是String类型，不便于控制，在这里可以隐藏细节。
 * @Author 过道
 * @Date 2018/12/24 21:00
 * @Version 1.0
 */
public class SessionUtil {
    // 获取一个session对象
    private static HttpSession session = null;

    /**
     * user在session中的名字，也就是键值对的键。
     */
    private static final String USER_NAME_IN_SESSION = "result";
    /**
     * shop在session中的名字，也就是键值对的键。
     */
    private static final String SHOP_NAME_IN_SESSION = "shop";

    /**
     * 获取session 的方法
     *
     * @return HttpSession
     */
    public static HttpSession getSession() {
        if(session==null){
            try {
                session = getRequest().getSession();
            } catch (Exception e) {
            }
        }
        //HttpSession session = null;
        return session;
    }

    /**
     * 获取一个request对象的方法
     *
     * @return HttpServletRequest
     */
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return (HttpServletRequest) attrs.getRequest();
    }

    /**
     * 从session中获取到当前用户
     *
     * @return
     */
    public static Result getUserFromSession(String name) {
        return (Result) session.getAttribute(name);
    }

    /**
     * 更新Session中的user
     *
     * @return
     */
    public static Result updateUserInSession(String name,Result result) {
        // 直接放入user，顶替掉原来session中的user
        return putUserIntoSession(name,result);
    }

    /**
     * 将user放入Session对象中
     *
     * @param result 用户
     * @return
//     */
    public static Result putUserIntoSession(String name,Result result) {
        session.setAttribute(name, result);
        return getUserFromSession(name);
    }


    /**
     * 从session中移除User对象
     *
     * @return
     */
//    public static Result removeUserFromSession() {
//        Result user = getUserFromSession();
//        session.removeAttribute(USER_NAME_IN_SESSION);
//        // 移出商店,如果有的话
//        if (getShopFromSession() != null) {
//            removeShopFromSession();
//        }
//        return result;
//    }







}

