/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.struts2;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;
import robertli.zero.core.WebConfiguration;
import robertli.zero.entity.Admin;
import robertli.zero.service.AdminService;

/**
 * This is a struts2 interceptor. This one will be use for the package
 * robertli.zero.action.admin.* <br>
 *
 * This interceptor will be used after AdminPermissionInterceptor and
 * defaultStack, and we will use it to inject object into actions.<br>
 *
 * We must use this interceptor after defaultStack to avoid the defaultStrack
 * replaces the object.
 *
 * @see AdminPermissionInterceptor
 * @version 1.0 2016-09-25
 * @author Robert Li
 */
public class AdminInjectionInterceptor implements Interceptor {

    @Resource
    private AdminService adminService;

    @Resource
    private WebConfiguration webConfiguration;

    @Override
    public void destroy() {
    }

    @Override
    public void init() {
    }

    @Override
    public String intercept(ActionInvocation ai) throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        String sessionId = request.getSession().getId();

        if (ai.getAction() instanceof SessionIdAware) {
            SessionIdAware action = (SessionIdAware) ai.getAction();
            action.setSessionId(sessionId);
        }

        ActionContext actionContext = ai.getInvocationContext();
        Admin admin = adminService.getCurrentAdmin(sessionId);
        actionContext.put("admin", admin);

        actionContext.put("domain", webConfiguration.getDomain());
        actionContext.put("image_action_url", webConfiguration.getImageActionUrl());
        actionContext.put("google_signin_client_id", webConfiguration.getGoogleSigninClientId());
        return ai.invoke();
    }

}
