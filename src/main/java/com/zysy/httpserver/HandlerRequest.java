package com.zysy.httpserver;

/**
 * @Title:HandlerRequest
 * @Description:n
 * @Auther:
 * @version:1.0
 * @create 2019/7/24 19:48 24
 */

import jdk.management.cmm.SystemResourcePressureMXBean;

import java.io.*;
import java.net.Socket;

/**
 * 处理客户端请求,runnnable接口，重写run方法
 * version:1.0
 */
public class HandlerRequest implements Runnable {
    public  Socket clientSocket;
    public HandlerRequest(Socket clientSocket){
        this.clientSocket=clientSocket;
    }
    @Override
    public void run(){
        BufferedReader br=null;
        PrintWriter out=null;
        //处理客户端请求
        try {
             br=new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            //打印线程名称
            System.out.println("httpserver thread "+Thread.currentThread().getName());
            //获取响应流对象
            out = new PrintWriter(clientSocket.getOutputStream());
            //获取请求协议的请求行
            String requestLine = br.readLine();//获取请求行 GET /URI
            //获取URI 请求方式->请求方式 URI->请求协议版本号；
            String requestURI=requestLine.split(" ")[1];//"GET" "/index.html" "HTTP1.0"
            //判断用户请求是否为一个静态页面。以.html .htm结尾的文件叫做html页面
            if (requestURI.endsWith(".html") || requestURI.endsWith(".htm")){
                //处理静态页面的方法。 找到了页面 怎么了响应回去？用输出流
                responseStaticpPage(requestURI,out);
            }
            if (requestURI.endsWith(".jpg")|| requestURI.endsWith(".jepg")){
                responseStaticpPicuter(requestURI,out);
            }
            //强制刷新
            out.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (br !=null)
            {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (clientSocket != null){
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
/**
 * 处理图片
 * @param requestURI 请求uri
 * @param out 响应流对象
 */
    private void responseStaticpPicuter(String requestURI, PrintWriter out) {
        System.out.println("处理图片");

    }

    /**
 * 处理静态页面
 * @param requestURI 请求URI
 * @param out 响应流对象
 */
    public void responseStaticpPage(String requestURI, PrintWriter out) {
        //静态页面的路径 requestURI= /hello/index.html
        String htmlPath=requestURI.substring(1);
        BufferedReader br=null;
        try {
            //读取页面
           br=new BufferedReader(new FileReader(htmlPath));
           //stringBuffer 线程是安全的 stringbulider 线程不安全 但效率高
           StringBuilder html=new StringBuilder();
           //拼接响应信息
            html.append("HTTP/1.1 200 OK\n")    ;
            html.append("Content-Type:text/html;charset=utf-8\n\n");
            String temp=null;
           while ((temp=br.readLine())!= null){
               //.append  每行读取的数据给了html的字符串了
               html.append(temp);
           }
           //输出html
            out.print(html);
        } catch (FileNotFoundException e) {
            //404找不到资源
            StringBuilder html=new StringBuilder();
            html.append("HTTP/1.1 404 NotFound\n");
            html.append("Content-Type:text/html;charset=utf-8\n\n");
            html.append("<html>");
            html.append("<head>");
            html.append("<title>404-错误</title>");
            html.append("<meta content='text/html;charset=utf-8'/>");
            html.append("</head>");
            html.append("<body>");
            html.append("<h1>404-Not Found</h1>");
            html.append("</body>");
            html.append("</html>");
            out.print(html);
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
