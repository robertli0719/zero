/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.interceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import robertli.zero.controller.RestException;
import robertli.zero.dto.user.UserProfileDto;
import robertli.zero.service.AuthService;
import robertli.zero.service.UserService;

/**
 * @version 1.0 2016-12-30
 * @author Robert Li
 */
public class AdminReadPermissionInterceptor implements HandlerInterceptor {

    @Resource
    private AuthService authService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        final String method = request.getMethod();
        if (method.equals("GET") == false) {
            return true;
        }

        String accessToken = (String) request.getAttribute("accessToken");
        if (accessToken == null) {
            throw new RestException("UNAUTHORIZED", "you should login", "AdminReadPermissionInterceptor: can't get access_token", HttpStatus.UNAUTHORIZED);
        }
        UserProfileDto userProfile = authService.getUserProfile(accessToken);
        if (userProfile == null) {
            throw new RestException("UNAUTHORIZED", "you should login again", "AdminReadPermissionInterceptor: access_token don't work", HttpStatus.UNAUTHORIZED);
        }

        String userTypeName = userProfile.getUserTypeName();
        if (UserService.USER_TYPE_ADMIN.equals(userTypeName) == false) {
            throw new RestException("NO_ADMIN_PERMISSION", "you need admin permission", "AdminReadPermissionInterceptor: this userType is not admin", HttpStatus.FORBIDDEN);
        }

        System.out.println("AdminPermissionInterceptor: " + accessToken);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView mav) throws Exception {
//        System.out.println("AuthInterceptor postHandle");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object o, Exception excptn) throws Exception {
//        System.out.println("AuthInterceptor afterCompletion");
    }

}
