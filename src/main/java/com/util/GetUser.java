package com.util;

import com.duzhentong.Login;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created with IDEA
 *
 * @author duzhentong
 * @Date 2018/4/15
 * @Time 18:54
 */
public class GetUser {
    public static Map<String,String> getUser() {
        Map<String, String> userMemages = new HashMap<String, String>();
        Properties properties = new Properties();
        try {
            InputStream inputStream = Login.class.getClassLoader()
                    .getResourceAsStream("user.properties");
            properties.load(inputStream);
            String email = properties.getProperty("email").trim();
            String password = properties.getProperty("password").trim();
            userMemages.put("email", email);
            userMemages.put("password", password);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userMemages;
    }
}
