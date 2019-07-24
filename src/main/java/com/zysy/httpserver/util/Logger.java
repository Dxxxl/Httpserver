package com.zysy.httpserver.util;

import javafx.scene.input.DataFormat;


import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Title:Logger
 * @Description:
 * @Auther:xuli
 * @version:1.0
 * @create 2019/7/24 16:31 24
 * 这是个日志
 */
public class Logger {
    //工具类的方法往往是静态的，直接通过类名去调用，不需要去创建对象
    //工具类的构造方法往往也是私有的，但不是必须的。
    private Logger(){
    }
    public static void log(String msg){
/*        //定义时间格式  年月日时分秒
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
        //获取当前系统时间
        Date nowTime = new Date();
        //将时间变为一个字符串
        String nowTimeStr =dateFormat.format(nowTime);*/
        System.out.println("[INFO] "+DateUtil.getCurrentTime()+" "+msg);

    }
}
