package org.fx.urils;

/**
 * @author ld
 * @name
 * @table
 * @remarks
 */
public class StaticToken {

    private static String token = "";

    public static String getToken() {
        System.out.println("读取:" + token);
        return token;
    }

    public static void setToken(String token) {
        System.out.println("更新" + token);
        StaticToken.token = token;
    }
}
