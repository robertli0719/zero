/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.struts2;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;

/**
 * This is a struts2 interceptor. This firewall can protect our system from too
 * many request from same IP to occupy the resource of the system.
 *
 * This interceptor will be used before defaultStack and any other interceptor,
 * so that it can response to user directly without spend more cost when attack
 * happened.
 *
 * @version 1.0 2016-09-28
 * @author Robert Li
 */
public class FireWallInterceptor implements Interceptor {

    private static final FireWallRecode minRecode = new FireWallRecode(60 * 1000);
    private static final FireWallRecode dayRecode = new FireWallRecode(24 * 60 * 60 * 1000);

    @Override
    public void destroy() {
    }

    @Override
    public void init() {
    }

    @Override
    public String intercept(ActionInvocation ai) throws Exception {
        HttpServletRequest req = ServletActionContext.getRequest();
        String ip = req.getRemoteAddr();

        int dayNumber = dayRecode.checkIp(ip);
        int minNumber = minRecode.checkIp(ip);
        if (dayNumber > 2000) {
            return "firewall_out";
        } else if (minNumber > 100) {
            return "firewall_out";
        }

        String result = ai.invoke();
        return result;
    }

}

class FireWallRecode {

    private long lastTime;
    private final long timeRange;
    private Map<String, Integer> map;

    public FireWallRecode(long timeRange) {
        lastTime = getNow();
        map = new ConcurrentHashMap();
        this.timeRange = timeRange;
    }

    public synchronized int checkIp(String ip) {
        long now = getNow();
        if (now - lastTime > timeRange) {
            map = new ConcurrentHashMap();
            lastTime = now;
        }

        if (map.containsKey(ip)) {
            int num = map.get(ip);
            map.put(ip, num + 1);
        } else {
            map.put(ip, 1);
        }
        return map.get(ip);
    }

    private long getNow() {
        return (new Date()).getTime();
    }

}
