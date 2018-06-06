package com.duzhentong;

import com.util.GetUser;
import com.util.SendMail;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;

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
            if (statusCode == 302) {
                /**
                 * 使用浏览器如果返回302状态码，需要重定向，浏览器会自动完成这个操作，即使在控制台上看到的也是302，
                 * 这时候其实网页已经跳转了，但是使用HttpClients就不一样了，即使返回302，他不会完成自动跳转的步骤，
                 * 需要手动编写代码完成
                 */

                //重定项的网址信息在返回的头信息里
                Header header = postMethod.getResponseHeader("location");
                //重定向地址
                String location = header.getValue();
                HttpClientBuilder builder = HttpClients.custom()
                        .disableAutomaticRetries() //关闭自动处理重定向
                        .setRedirectStrategy(new LaxRedirectStrategy());//利用LaxRedirectStrategy处理post重定向问题

                CloseableHttpClient client = builder.build();

                HttpPost httpPost = new HttpPost(location);

                CloseableHttpResponse response = client.execute(httpPost);

                statusCode = response.getStatusLine().getStatusCode();
                System.out.println("statusCode=" + statusCode);
                if (statusCode != 200) {
                    SendMail.sendMail();
                }
            } else {
                SendMail.sendMail();
            }
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
