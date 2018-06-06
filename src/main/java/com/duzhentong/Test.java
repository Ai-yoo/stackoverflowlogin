package com.duzhentong;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.DefaultHttpParams;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.*;

/**
 * Created with IDEA
 *
 * @author duzhentong
 * @Date 2018/5/7
 * @Time 19:14
 */
public class Test {
    public static Map doPost(Map<String, String> map, String charset) {
        Map<String, String> cookieMap = new HashMap<String, String>();
        CloseableHttpClient httpClient = null;
        HttpPost httpPost = null;
        String result = null;
        try {
            CookieStore cookieStore = new BasicCookieStore();
            httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
            httpPost = new HttpPost("https://stackoverflow.com/users/login");
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> elem = (Map.Entry<String, String>) iterator.next();
                list.add(new BasicNameValuePair(elem.getKey(), elem.getValue()));
            }
            if (list.size() > 0) {
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, charset);
                httpPost.setEntity(entity);
            }
            httpClient.execute(httpPost);
            List<Cookie> cookies = cookieStore.getCookies();
            System.out.println(cookies);
            for (int i = 0; i < cookies.size(); i++) {
                if (cookies.get(i).getName().equals("acct")) {
                    cookieMap.put("acct", cookies.get(i).getValue());
                    System.out.println(cookies.get(i).getValue());
                }
                if (cookies.get(i).getName().equals("prov")) {
                    cookieMap.put("prov", cookies.get(i).getValue());
                    System.out.println(cookies.get(i).getValue());
                }
                if (cookies.get(i).getName().equals("uauth")) {
                    cookieMap.put("uauth", cookies.get(i).getValue());
                    System.out.println(cookies.get(i).getValue());
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return cookieMap;
    }

    public static int doGet(Map cookieMap) {
        HttpClient httpClient = new HttpClient();
        int statusCode = 0;
        GetMethod getMethod = new GetMethod("https://stackoverflow.com/");
        //更改cookie策略
        DefaultHttpParams.getDefaultParams().setParameter("http.protocol.cookie-policy", CookiePolicy.BROWSER_COMPATIBILITY);
        // 每次访问需授权的网址时需带上前面的 cookie 作为通行证
        getMethod.setRequestHeader("acct", (String) cookieMap.get("acct"));
        getMethod.setRequestHeader("prov", (String) cookieMap.get("prov"));
        getMethod.setRequestHeader("uauth", (String) cookieMap.get("uauth"));
        try {
            statusCode=httpClient.executeMethod(getMethod);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return statusCode;
    }

    public static void main(String[] args) {
        Map map = new HashMap();
        map.put("returnurl", "https%3a%2f%2fstackoverflow.com%2f");
        map.put("fkey", "a5d637b887e4eb32adf839777b12454c55ee8db4bb767c67f26903742cbaac59");
        map.put(" ", "head");
        map.put("email", "duzhentong123@foxmail.com");
        map.put("password", "350058841250sb");
        Map cookieMap = doPost(map, "utf-8");
        int statusCode = doGet(cookieMap);
        System.out.println(statusCode);

    }
}
