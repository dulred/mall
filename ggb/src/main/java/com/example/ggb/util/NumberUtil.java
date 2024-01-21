package com.example.ggb.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 13
 * @qq交流群 796794009
 * @email 2449207463@qq.com
 * @link https://github.com/newbee-ltd
 */
public class NumberUtil {

    private NumberUtil() {
    }


    /**
     * 判断是否为11位电话号码
     *
     * @param phone
     * @return
     */
    public static boolean isPhone(String phone) {
        Pattern pattern = Pattern.compile("^((13[0-9])|(14[5,7])|(15[^4,\\D])|(17[0-8])|(18[0-9]))\\d{8}$");
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }
    
// 原生字符串操作写法
    // public static boolean isPhone2(String phone) {
    //     int len = phone.length();
    //     if (len != 11) {
    //         return false;
    //     }
    
    //     if (!(phone.startsWith("13") || phone.startsWith("14") || phone.startsWith("15")
    //             || phone.startsWith("17") || phone.startsWith("18"))) {
    //         return false;
    //     }
    
    //     for (int i = 0; i < len; i++) {
    //         char c = phone.charAt(i);
    //         if (c < '0' || c > '9') {
    //             return false;
    //         }
    //     }
    
    //     return true;
    // }

    /**
     * 生成指定长度的随机数
     *
     * @param length
     * @return
     */
    public static int genRandomNum(int length) {
        int num = 1;
        double random = Math.random();
        if (random < 0.1) {
            random = random + 0.1;
        }
        for (int i = 0; i < length; i++) {
            num = num * 10;
        }
        return (int) ((random * num));
    }

    /**
     * 生成订单流水号
     *
     * @return
     */
    public static String genOrderNo() {
        StringBuffer buffer = new StringBuffer(String.valueOf(System.currentTimeMillis()));
        int num = genRandomNum(4);
        buffer.append(num);
        return buffer.toString();
    }
}
