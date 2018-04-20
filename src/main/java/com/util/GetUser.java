package com.util;

import com.duzhentong.Login;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created with IDEA
 *
 * @author duzhentong
 * @Date 2018/4/15
 * @Time 18:54
 */
public class GetUser {
    public static String[] getUser() {
        String[] user = new String[2];
        Properties properties = new Properties();
        try {
            InputStream inputStream = Login.class.getClassLoader()
                    .getResourceAsStream("user.properties");
            properties.load(inputStream);
            user[0] = properties.getProperty("email").trim();
            user[1] = properties.getProperty("password").trim();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return user;
    }
}
