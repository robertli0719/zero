/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.core.impl;

import org.json.JSONObject;
import org.springframework.stereotype.Component;
import robertli.zero.core.JsonService;

@Component("jsonService")
public class JsonServiceImpl implements JsonService {

    @Override
    public JSONObject createSuccessResult() {
        JSONObject json = new JSONObject();
        json.put("result", "success");
        return json;
    }

    @Override
    public JSONObject createFailResult(String errorString) {
        JSONObject json = new JSONObject();
        json.put("result", "fail");
        json.put("errorString", errorString);
        return json;
    }

}
