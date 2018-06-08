package com.duzhentong;

import com.util.GetMethod;
import com.util.GetUser;
import com.util.PostMethod;
import com.util.SendMail;
import java.util.*;

/**
 * Created with IDEA
 *
 * @author duzhentong
 * @Date 2018/5/7
 * @Time 19:14
 */
public class Login {

    private static final int STATUSCODE_200 = 200;
    private static final int STATUSCODE_302 = 302;

    public static void main(String[] args) {
        Map map = new HashMap();
        Map userMessage = GetUser.getUser();
        map.put("returnurl", "https%3a%2f%2fstackoverflow.com%2f");
        map.put("fkey", "a5d637b887e4eb32adf839777b12454c55ee8db4bb767c67f26903742cbaac59");
        map.put("ssrc", "head");
        map.put("email", userMessage.get("email"));
        map.put("password", userMessage.get("password"));
        Map cookieMap = PostMethod.doPost(map, "utf-8");
        int statusCode = GetMethod.doGet(cookieMap);
        System.out.println(statusCode);
        if (statusCode != STATUSCODE_200 && statusCode != STATUSCODE_302) {
            SendMail.sendMail();
        }
    }
}
