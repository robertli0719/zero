/*
 * The MIT License
 *
 * Copyright 2016 Robert Li.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package robertli.zero.struts2;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;
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

    @Override
    public void destroy() {
    }

    @Override
    public void init() {
    }

    @Override
    public String intercept(ActionInvocation ai) throws Exception {

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
