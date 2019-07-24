package com.zysy.httpserver.util;



import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Title:DateUtil
 * @Description:
 * @Auther:日期工具类
 * @version:1.0
 * @create 2019/7/24 16:45 24
 *
 */
/**
 * 日期工具类
 */

public class DateUtil {
    private  static SimpleDateFormat dataFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
    private DateUtil(){}
    /**
     * 获取当前系统时间
     * 类型“yyyy-mm-dd HH:mm:ss SSS”
     */

    public static String getCurrentTime(){
        return dataFormat.format(new Date());

    }

}
