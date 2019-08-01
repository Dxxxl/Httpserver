package com.zysy.httpserver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @Title:GetDBConnection
 * @Description:
 * @Auther:
 * @version:1.0
 * @create 2019/7/30 19:35 30
 */
public class GetDBConnection {
    public static Connection connectoinDB(String DBName, String id, String p) {
        Connection con=null;
        String uri="jdbc:mysql://localhost:3306/"+DBName+"?serverTimezone=GMT";
        try { Class.forName("com.mysql.cj.jdbc.Driver");//加载JDBC-MySQL驱动
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        try {
            con= DriverManager.getConnection(uri,id,p);//连接代码
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return con;
    }

}
