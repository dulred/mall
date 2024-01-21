package com.example.ggb.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA256Util {

    public static String SHA256Encode(String input){
        try {
            // 创建一个 MessageDigest 实例并指定算法为 "SHA-256"
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // 将输入字符串按指定编码转换为字节数组
            byte[] inputBytes = input.getBytes(StandardCharsets.UTF_8);

            // 使用 update() 方法将字节数组添加到消息摘要
            digest.update(inputBytes);

            // 计算散列值并获取结果
            byte[] hashBytes = digest.digest();

            // 将散列值转换为十六进制字符串
            StringBuilder hexString = new StringBuilder();
            for (byte hashByte : hashBytes) {
                String hex = Integer.toHexString(0xff & hashByte);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            String sha256Hash = hexString.toString();
            return sha256Hash;

        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }
   
}
