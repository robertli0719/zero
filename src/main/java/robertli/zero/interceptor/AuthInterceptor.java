/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.interceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import robertli.zero.core.ClientAccessTokenManager;

/**
 * This Interceptor is used to inject the access_token to request attributes
 * before invoking the rest controller.
 *
 * @version 2016-12-18 1.0
 * @author Robert Li
 */
public class AuthInterceptor implements HandlerInterceptor {

    @Resource
    private ClientAccessTokenManager clientAccessTokenManager;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        String accessToken = clientAccessTokenManager.getAccessToken(request);
        request.setAttribute("accessToken", accessToken);
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
