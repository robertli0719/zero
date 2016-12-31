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
 *
 * @author Robert Li
 */
public class AdminEditPermissionInterceptor implements HandlerInterceptor {

    @Resource
    private AuthService authService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        final String method = request.getMethod();
        if (method.equals("GET")) {
            return true;
        }

        String accessToken = (String) request.getAttribute("accessToken");
        if (accessToken == null) {
            throw new RestException("UNAUTHORIZED", "you should login", "AdminEditPermissionInterceptor: can't get access_token", HttpStatus.UNAUTHORIZED);
        }
        UserProfileDto userProfile = authService.getUserProfile(accessToken);
        if (userProfile == null) {
            throw new RestException("UNAUTHORIZED", "you should login again", "AdminEditPermissionInterceptor: access_token don't work", HttpStatus.UNAUTHORIZED);
        }

        String userTypeName = userProfile.getUserTypeName();
        if (UserService.USER_TYPE_ADMIN.equals(userTypeName) == false) {
            throw new RestException("NO_ADMIN_PERMISSION", "you need admin permission", "AdminPermissionInterceptor: this userType is not admin", HttpStatus.FORBIDDEN);
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest hsr, HttpServletResponse hsr1, Object o, ModelAndView mav) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest hsr, HttpServletResponse hsr1, Object o, Exception excptn) throws Exception {
    }
}
