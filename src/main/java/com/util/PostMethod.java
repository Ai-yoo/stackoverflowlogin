package com.util;

import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.util.*;

/**
 * Created with IDEA
 *
 * @author duzhentong
 * @Date 2018/6/8
 * @Time 15:59
 */
public class PostMethod {
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
                String cookieName = cookies.get(i).getName();
                String cookieValue = cookies.get(i).getValue();
                if ("acct".equals(cookieName)) {
                    cookieMap.put("acct", cookieValue);
                    System.out.println(cookieValue);
                }
                if ("prov".equals(cookieName)) {
                    cookieMap.put("prov", cookieValue);
                    System.out.println(cookieValue);
                }
                if ("uauth".equals(cookieName)) {
                    cookieMap.put("uauth", cookieValue);
                    System.out.println(cookieValue);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return cookieMap;
    }
}
