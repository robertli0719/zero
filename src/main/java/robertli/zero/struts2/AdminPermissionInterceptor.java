/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.struts2;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

/**
 * This is a struts2 interceptor. This one will be use for the package
 * robertli.zero.action.admin.* <br>
 *
 * This interceptor will be used before defaultStack, and we will use it for
 * redirect to login page
 *
 * @version 1.0 2016-09-24
 * @author Robert Li
 */
public class AdminPermissionInterceptor implements Interceptor {

    @Override
    public void destroy() {
    }

    @Override
    public void init() {
    }

    @Override
    public String intercept(ActionInvocation ai) throws Exception {
        return ai.invoke();
    }

}
