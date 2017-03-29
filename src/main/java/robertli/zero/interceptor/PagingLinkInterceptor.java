/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.core.MethodParameter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import robertli.zero.dto.PagingModal;

/**
 *
 * @version 1.0 2017-03-27
 * @author Robert Li
 */
public class PagingLinkInterceptor implements HandlerInterceptor {

    private boolean hasPagingModal(Object o) {
        if (o instanceof HandlerMethod == false) {
            return false;
        }
        final HandlerMethod method = (HandlerMethod) o;
        final MethodParameter[] mpArray = method.getMethodParameters();
        if (mpArray == null || mpArray.length == 0) {
            return false;
        }
        for (MethodParameter p : mpArray) {
            final String pagingModalClassName = PagingModal.class.getName();
            final String parameterClassName = p.getGenericParameterType().getTypeName();
            if (pagingModalClassName.equals(parameterClassName)) {
                return true;
            }
        }
        return false;
    }

    private int initLimit(HttpServletRequest request) {
        try {
            final String limitParam = request.getParameter("limit");
            final int limit = Integer.parseInt(limitParam);
            if (limit > PagingModal.MAX_LIMIT) {
                return PagingModal.MAX_LIMIT;
            }
            return limit;
        } catch (NumberFormatException e) {
            return PagingModal.DEFAULT_LIMIT;
        }
    }

    private int initOffset(HttpServletRequest request) {
        try {
            final String offsetParam = request.getParameter("offset");
            return Integer.parseInt(offsetParam);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        if (hasPagingModal(o) == false) {
            return true;
        }
        final int limit = initLimit(request);
        final int offset = initOffset(request);
        PagingModal pagingModal = new PagingModal(request, response, limit, offset);
        request.setAttribute("pagingModal", pagingModal);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView mav) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest hsr, HttpServletResponse hsr1, Object o, Exception excptn) throws Exception {
    }

}
