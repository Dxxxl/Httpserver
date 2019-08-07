package com.zysy.httpserver;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.dom4j.DocumentException;


import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;


/**
 * @Title:BootStrap
 * @Description:
 * @Auther:xuli
 * @version:1.0
 * @create 2019/7/24 16:27 24
 */
public class BootStrap {
    private  static Logger logger=Logger.getLogger(BootStrap.class);

    /**
     * 主程序
     * @param  args
     */
    public static void main(String[] args) {
        //程序入口
       // logger.error("ssssss");
        start();
    }

    /**
     * 主程序入口
     */
    public static void start() {


        //创建服务器套接字，并且绑定端口号：8080
        BasicConfigurator.configure();
        logger.error("ssssss");
       // PropertyConfigurator.configure("log4j.properties");
        ServerSocket serverSocket=null;
        Socket clientSocket=null;
        BufferedReader br=null;
        try {
            String[] webAppNames={"oa"};
            //在初始化的时候解析web.xml配置文件
            WebParse.parser(webAppNames);
            //定义了一个获取端口号的
            int port = ServerParser.getPort();
            System.out.println("获取系统端口号:"+port);

            serverSocket = new ServerSocket(port);
            //创建线程池
            ThreadPoolExecutor pool=new ThreadPoolExecutor(10,20,10, TimeUnit.DAYS,new ArrayBlockingQueue<Runnable>(10));

            //ExecutorService pool= Executors.newFixedThreadPool(10);
            while (true) {
                //开始监听网络，此时程序处于等待状态，等待客户端的信息
                clientSocket= serverSocket.accept();
/*                br=new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String temp=null;
                while ((temp =br.readLine())!=null){
                    System.out.println(temp);
                }*/
             //   new Thread(new HandlerRequest(clientSocket)).start();  //开启一个线程
                pool.execute(new HandlerRequest(clientSocket));



            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } finally {
            //关闭资源
/*            if (br!=null) {
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
            }*/
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
