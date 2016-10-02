/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.core;

import org.json.JSONObject;

/**
 * This service is using for the struts Actions which return JSON result.
 *
 * @version 1.0 2016-10-01
 * @author Robert Li
 */
public interface JsonService {

    public JSONObject createSuccessResult();

    JSONObject createFailResult(String errorString);
}
