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
