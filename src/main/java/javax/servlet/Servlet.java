package javax.servlet;

import com.zysy.httpserver.RequestObject;

/**
 * @Title:Servlet
 * @Description:
 * @Auther:
 * @version:1.0
 * @create 2019/7/26 13:38 26
 */

/**
 * 处理请求的核心方法
 */
public interface Servlet{
    /**
     * 处理请求的核心方法
     */
     void service(ServletRequest request, ServletResponse response);

}