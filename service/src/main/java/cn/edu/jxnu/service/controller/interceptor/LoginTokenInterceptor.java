package cn.edu.jxnu.service.controller.interceptor;


import cn.edu.jxnu.service.entity.LoginToken;
import cn.edu.jxnu.service.entity.User;
import cn.edu.jxnu.service.service.UserService;
import cn.edu.jxnu.service.util.CookieUtil;
import cn.edu.jxnu.service.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Component
public class LoginTokenInterceptor implements HandlerInterceptor {

    @Autowired
    private UserService userService;

    @Autowired
    private HostHolder hostHolder;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 从cookie中获取凭证
        String token = CookieUtil.getValue(request, "token");

        if (token != null) {
            LoginToken loginToken = userService.findLoginToken(token);
            // 检查凭证是否有效
            if (loginToken != null && loginToken.getStatus() == 1 && loginToken.getExpired().after(new Date())) {
                // 根据凭证查询用户
                User user = userService.findUserById(loginToken.getUserId());
                // 本次请求中持有用户
                hostHolder.setUser(user);
//                 构建用户认证的结果,并存入SecurityContext,以便Secutiry进行授权
                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        user, user.getPassword(), userService.getAuthorities(user.getId()));
                SecurityContextHolder.setContext(new SecurityContextImpl(authentication));
            }
        }

        return true;
    }

//    @Override
//    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//        User user = hostHolder.getUser();
//        if (user != null && modelAndView != null) {
//            modelAndView.addObject("loginUser", user);
//        }
//    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        hostHolder.clear();
//        SecurityContextHolder.clearContext();
    }
}
