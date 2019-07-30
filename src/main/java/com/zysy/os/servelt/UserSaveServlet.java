package com.zysy.os.servelt;

import javax.servlet.Servlet;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.PrintWriter;

/**
 * @Title:UserSaveServlet
 * @Description:
 * @Auther:
 * @version:1.0
 * @create 2019/7/29 17:58 29
 */

public class UserSaveServlet implements Servlet {
    @Override
    public void service(ServletRequest request, ServletResponse response){
        String username=request.getParameterValue("username");
        String school=request.getParameterValue("school");
        String sex=request.getParameterValue("sex");
        String[] department=request.getParameterValues("department");
        StringBuilder departments=new StringBuilder();
        for (String departmentvalue:department){
            departments.append(departmentvalue).append(" ");

        }
        //连接数据库执行保存操作

        //将响应结果响应到浏览器上。
        PrintWriter out=response.getWrite();
        out.print("用户名：" + username + "<br>");
        out.print("学校：" + school + "<br>");
        out.print("性别：" + sex + "<br>");
        out.print("部门：" + departments + "<br>");


    }

}
