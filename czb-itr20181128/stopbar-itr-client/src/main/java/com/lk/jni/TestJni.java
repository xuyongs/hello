package com.lk.jni;

import java.util.Arrays;

/**
 * @author linkai
 * @since 16/6/24
 */
public class TestJni {
    static {
//        System.load("C:\\Program Files (x86)\\EParkingManage\\x64\\EPAmountDLL.dll");
//        System.load("C:\\Program Files (x86)\\EParkingManage\\x64\\TBEParkingDLL.dll");
//        System.load("C:\\Program Files (x86)\\EParkingManage\\x64\\lk.dll");
        System.loadLibrary("lk");//载入动态链接库文件。（*.dll）
    }

    public static void main(String[] args) {
        if (args != null && args.length > 0) {
            System.out.println(Arrays.toString(args));
            // interfacetype 接口类型定义 1-金额计算 2-查询计费金额 3-车位分享预定
            if (args[0].equals("1")) {
                // platenumber 车牌号
                String json = "{\"interfacetype\":\"1\",\"platenumber\":\"沪A33223\"}";
                System.out.println(new TestJni().callService(json));
            } else if (args[0].equals("2")) {
                // platenumber 车牌号
                String json = "{" +
                        "\"interfacetype\":\"2\"" +
                        ",\"platenumber\":\"沪A33223\"" +
                        ",\"starttime\":\"沪A33223\"" +
                        ",\"endtime\":\"沪A33223\"" +
                        "}";
                System.out.println(new TestJni().callService(json));
            }
        }
        System.out.println(new TestJni().callService("111"));//调用native方法
    }

    public native String callService(String s);//声明native方法
}
