package com.czy.mycppdemo3;

import java.io.UnsupportedEncodingException;

public class AppCodeTest {
    public static void main(String[] args) {
        String name = "ab好cd";
        System.out.println((int)'好');
        System.out.println(name.length());
        try {
            byte[] names = name.getBytes("gb2312");
            // 要求：当截取到 文字的时候，不能拆开，否则啥也不是
            // 输出： ab? ，  如何解决
            System.out.println(new String(names, 0, 3));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
