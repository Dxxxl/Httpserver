package com.zysy.httpserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Title:BootStrap
 * @Description:
 * @Auther:xuli
 * @version:1.0
 * @create 2019/7/24 16:27 24
 */
public class BootStrap {
    /**
     * 主程序
     * @param  args
     */
    public static void main(String[] args) {
        //程序入口
        start();

    }
    /**
     * 主程序入口
     */
    public static void start() {
        //创建服务器套接字，并且绑定端口号：8080
        ServerSocket serverSocket=null;
        Socket clientSocket=null;
        BufferedReader br=null;
        try {
            System.out.println("获取系统端口号");
            int port = ServerParser.getPort();
            System.out.println("端口号:"+port);
            serverSocket = new ServerSocket(port);
            //开始监听网络，此时程序处于等待状态，等待客户端的信息
             clientSocket= serverSocket.accept();
             br=new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String temp=null;
            while ((temp =br.readLine())!=null){
                System.out.println(temp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            //关闭资源
            if (br!=null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (clientSocket!=null){
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (serverSocket!=null){
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        }

    }
}
