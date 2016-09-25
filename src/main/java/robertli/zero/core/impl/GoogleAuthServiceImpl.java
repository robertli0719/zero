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
 * This service is for using Google sign-in for web site.<br />
 * reference:<br/>
 * https://developers.google.com/identity/sign-in/web/backend-auth
 * <br/>
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
