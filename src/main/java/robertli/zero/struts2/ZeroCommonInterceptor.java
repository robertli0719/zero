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
import robertli.zero.entity.User;
import robertli.zero.service.UserService;

/**
 * This is a struts2 interceptor. This one will be use for the package
 * robertli.zero.action..* <br>
 * This interceptor will be used after defaultStack, and we will use it to
 * inject Object into Action.
 *
 * @version 1.0 2016-09-20
 * @author Robert Li
 */
public class ZeroCommonInterceptor implements Interceptor {

    @Resource
    private UserService userService;

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

        ActionContext actionContext = ai.getInvocationContext();
        actionContext.put("domain", webConfiguration.getDomain());
        actionContext.put("image_action_url", webConfiguration.getImageActionUrl());
        actionContext.put("google_signin_client_id", webConfiguration.getGoogleSigninClientId());

        if (ai.getAction() instanceof UserAware) {
            UserAware userAware = (UserAware) ai.getAction();
            HttpServletRequest request = ServletActionContext.getRequest();
            String sessionId = request.getSession().getId();
            try {
                User user = userService.getCurrentUser(sessionId);
                userAware.setUser(user);
            } catch (RuntimeException re) {
                System.out.println(re);
            }
        }

        if (ai.getAction() instanceof SessionIdAware) {
            SessionIdAware action = (SessionIdAware) ai.getAction();
            HttpServletRequest request = ServletActionContext.getRequest();
            String sessionId = request.getSession().getId();
            action.setSessionId(sessionId);
        }
        return ai.invoke();
    }

}
