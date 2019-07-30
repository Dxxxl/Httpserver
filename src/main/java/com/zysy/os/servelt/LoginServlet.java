package com.zysy.os.servelt;

import com.zysy.httpserver.RequestObject;

import javax.servlet.Servlet;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.PrintWriter;

/**
 * @Title:LoginServlet
 * @Description:
 * @Auther:
 * @version:1.0
 * @create 2019/7/25 15:58 25

/**
 *处理登录业务的java程序,该java程序由Webapp开发，由web服务器开发人员负责调用
 */
public class LoginServlet implements Servlet {
    /**
     * 处理业务的核心类
     * @param request
     * @param response
     */
    public void service(ServletRequest request, ServletResponse response){

        System.out.println("测试，请稍等.....");
/*        如果这个服务是想把网页输出出来，那么就需要响应信息头，我们在HandlerRequest中添加
        响应信息*/
        // 获取响应流对象
        PrintWriter out=response.getWrite();
        out.print("<html>");
        out.print("<head>");
        out.print("<title>正在验证身份....</title>");
        out.print("<meta content='text/html;charset=utf-8'/>");
        out.print("</head>");
        out.print("<body>");
        //out.print("<center><font size ='35px' color='bule'>正在验证身份稍等</font></center>");
        out.print("<h1>正在响应信息请稍等</h1>");
        out.print("</body>");
        out.print("</html>");
    }

}
