package com.zysy.httpserver;

/**
 * @Title:ServletCache
 * @Description:
 * @Auther:
 * @version:1.0
 * @create 2019/7/30 10:32 30
 */

import javax.servlet.Servlet;
import java.util.HashMap;
import java.util.Map;

/**
 * servlet对象缓存池
 * author：xuli
 */

public class ServletCache {
    private static Map<String,Servlet> servletMap=new HashMap<String, Servlet>();
    public static void put(String urlPattern, Servlet servlet){
        servletMap.put(urlPattern,servlet);
    }
    public static  Servlet get(String urlPattern){
        return servletMap.get(urlPattern);
    }
}
