package com.zysy.os.servelt;

import com.mysql.cj.protocol.Resultset;
import com.zysy.httpserver.GetDBConnection;

import javax.servlet.Servlet;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Statement;

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
           Connection conn;
            Statement sql;
            Resultset rs;
            conn= GetDBConnection.connectoinDB("test","root","xuli");
            if (conn==null)
            {
                System.out.println("连接数据库失败");
            }else{
                System.out.println("连接数据库成功");
        }

        //将响应结果响应到浏览器上。
        PrintWriter out=response.getWrite();
        out.print("用户名：" + username + "<br>");
        out.print("学校：" + school + "<br>");
        out.print("性别：" + sex + "<br>");
        out.print("部门：" + departments + "<br>");


    }

}
