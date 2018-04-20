package com.duzhentong;

import com.util.GetUser;
import com.util.SendMail;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.http.client.params.ClientPNames;

import java.io.IOException;

/**
 * Created with IDEA
 *
 * @author duzhentong
 * @Date 2018/4/15
 * @Time 14:51
 */
public class Login {
    public static void main(String[] args) {
        HttpClient httpClient = new HttpClient();
        String url = "https://stackoverflow.com/users/login";
        String[] user = GetUser.getUser();
        PostMethod postMethod = new PostMethod(url);
        httpClient.getParams().setParameter(ClientPNames.COOKIE_POLICY,
                CookiePolicy.BROWSER_COMPATIBILITY);
        NameValuePair[] date = {
                new NameValuePair("returnurl", "https%3a%2f%2fstackoverflow.com%2f"),
                new NameValuePair("fkey", "a5d637b887e4eb32adf839777b12454c55ee8db4bb767c67f26903742cbaac59"),
                new NameValuePair("ssrc", "head"),
                new NameValuePair("email", user[0]),
                new NameValuePair("password", user[1])};
        postMethod.setRequestBody(date);
        int statusCode = 0;
        try {
            statusCode = httpClient.executeMethod(postMethod);
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (statusCode != 200 && statusCode != 302) {
//                SendMail.sendMail();
            }
        }
        System.out.println(statusCode);

    }

}
