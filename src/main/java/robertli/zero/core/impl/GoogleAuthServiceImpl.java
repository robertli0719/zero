/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.core.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import robertli.zero.core.GoogleAuthService;
import robertli.zero.core.WebService;

/**
 * This service is for using Google sign-in for web site.<br>
 * reference:<br>
 * https://developers.google.com/identity/sign-in/web/backend-auth
 * <br>
 *
 *
 * @version 1.0 2016-09-20
 * @author Robert Li
 */
@Component("googleAuthService")
public class GoogleAuthServiceImpl implements GoogleAuthService {

    @Resource
    private WebService webService;

    private final String URL_HEAD = "https://www.googleapis.com/oauth2/v3/tokeninfo?id_token=";

    @Override
    public Map<String, String> getInfo(String token) throws IOException {
        String url = URL_HEAD + token;
        JSONObject json = webService.getJSON(url);

        Map<String, String> resultMap = new HashMap<>();
        for (String key : json.keySet()) {
            String val = json.getString(key);
            resultMap.put(key, val);
        }
        return resultMap;
    }

}
