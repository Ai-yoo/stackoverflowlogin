package com.util;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.params.DefaultHttpParams;

import java.io.IOException;
import java.util.Map;

/**
 * Created with IDEA
 *
 * @author duzhentong
 * @Date 2018/6/8
 * @Time 15:59
 */
public class GetMethod {
    public static int doGet(Map cookieMap) {
        HttpClient httpClient = new HttpClient();
        int statusCode = 0;
        org.apache.commons.httpclient.methods.GetMethod getMethod = new org.apache.commons.httpclient.methods.GetMethod("https://stackoverflow.com/");
        //更改cookie策略
        DefaultHttpParams.getDefaultParams().setParameter("http.protocol.cookie-policy", CookiePolicy.BROWSER_COMPATIBILITY);
        // 每次访问需授权的网址时需带上前面的 cookie 作为通行证
        getMethod.setRequestHeader("acct", (String) cookieMap.get("acct"));
        getMethod.setRequestHeader("prov", (String) cookieMap.get("prov"));
        getMethod.setRequestHeader("uauth", (String) cookieMap.get("uauth"));
        try {
            statusCode = httpClient.executeMethod(getMethod);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return statusCode;
    }
}
