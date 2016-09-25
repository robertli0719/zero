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
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Scanner;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import robertli.zero.core.WebService;

/**
 * WebService can get or post information based on URL
 *
 * @version 1.0 2016-09-18
 * @author robert
 */
@Component("webService")
public class WebServiceImpl implements WebService {

    private String makeParamsString(Map<String, String> params) throws UnsupportedEncodingException {
        String str = "";
        int c = 0;
        for (String key : params.keySet()) {
            String value = params.get(key);
            value = URLEncoder.encode(value, "utf-8");
            str += (c++ == 0 ? "" : "&") + key + "=" + value;
        }
        return str;
    }

    private String reciveString(InputStream in) {
        String result = "";
        try (Scanner scan = new Scanner(in, "utf-8")) {
            while (scan.hasNextLine()) {
                result += scan.nextLine() + "\n";
            }
        }
        return result;
    }

    @Override
    public String get(String url, Map<String, String> params) throws IOException {
        String result = "";
        if (params != null) {
            String paramsString = makeParamsString(params);
            if (paramsString.isEmpty() == false) {
                url += "?" + paramsString;
            }
        }
        URL theUrl = new URL(url);
        URLConnection connection = theUrl.openConnection();
        connection.setDoOutput(false);
        connection.connect();
        InputStream in = connection.getInputStream();
        try (Scanner scan = new Scanner(in, "utf-8")) {
            while (scan.hasNextLine()) {
                result += scan.nextLine() + "\n";
            }
        }
        return result;
    }

    @Override
    public String get(String url) throws IOException {
        return get(url, null);
    }

    @Override
    public JSONObject getJSON(String url, Map<String, String> params) throws IOException {
        String strResult = get(url, params);
        return new JSONObject(strResult);
    }

    @Override
    public JSONObject getJSON(String url) throws IOException {
        String strResult = get(url);
        return new JSONObject(strResult);
    }

    @Override
    public String post(String url, Map<String, String> params) throws IOException {
        URL theUrl = new URL(url);
        URLConnection connection = theUrl.openConnection();
        connection.setDoOutput(true);
        OutputStream out = connection.getOutputStream();
        if (params != null) {
            String paramsString = makeParamsString(params);
            try (PrintWriter writer = new PrintWriter(out)) {
                writer.print(paramsString);
            }
        }
        connection.connect();
        InputStream in = connection.getInputStream();
        return reciveString(in);
    }

    @Override
    public String post(String url) throws IOException {
        return post(url, null);
    }

    @Override
    public JSONObject postJSON(String url, Map<String, String> params) throws IOException {
        String strResult = post(url, params);
        return new JSONObject(strResult);
    }

    @Override
    public JSONObject postJSON(String url) throws IOException {
        String strResult = post(url);
        return new JSONObject(strResult);
    }

}
