/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.core;

import java.io.IOException;
import java.util.Map;
import org.json.JSONObject;

/**
 * WebService can get or post information based on URL
 *
 * @version 1.0 2016-09-18
 * @author Robert Li
 */
public interface WebService {

    public String get(String url, Map<String, String> params) throws IOException;

    public String get(String url) throws IOException;

    public JSONObject getJSON(String url, Map<String, String> params) throws IOException;

    public JSONObject getJSON(String url) throws IOException;

    public String post(String url, Map<String, String> params) throws IOException;

    public String post(String url) throws IOException;

    public JSONObject postJSON(String url, Map<String, String> params) throws IOException;

    public JSONObject postJSON(String url) throws IOException;

}
