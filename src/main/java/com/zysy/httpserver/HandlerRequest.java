package com.zysy.httpserver;

/**
 * @Title:HandlerRequest
 * @Description:n
 * @Auther:
 * @version:1.0
 * @create 2019/7/24 19:48 24ddddd
 */



import javax.servlet.Servlet;
import java.io.*;
import java.net.Socket;
import java.util.Map;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;


/**
 * 处理客户端请求,runnnable接口，重写run方法
 * version:1.0
 */
public class HandlerRequest implements Runnable {
    public  Socket clientSocket;
    public HandlerRequest(Socket clientSocket){
        this.clientSocket=clientSocket;
    }
    private  static Logger logger=Logger.getLogger(HandlerRequest.class);

    @Override
    public void run(){

      //  BasicConfigurator.configure();
       logger.debug("assddczx");
        BufferedReader br=null;
        PrintWriter out=null;
        //处理客户端请求
        try {
           /* Properties properties;
            properties.entrySet()*/
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
            }else{
               //不是一个静态页面类，说明是一个动态资源：java程序
                //requestURI：/oa/login？usernname=xuli&password=111
                //requestURI：/oa/login 无参数
                String servletPath=requestURI;
                System.out.println("servletPath= " +servletPath);
                //判断serveletPath是否包含？号来确定是否有参数
                if (servletPath.contains("?")) {
                    servletPath= servletPath.split("[?]")[0];//取出/oa/login是个正则表达 ？需要转义 [？] \\？两种方法
                    System.out.println("servletPathURI= " +servletPath);
                }
                //找到路径，调用LoginServlet类的service方法
/*                if ("/oa/login".equals(servletPath)){
                    LoginServlet loginServlet=new LoginServlet();
                    loginServlet.service();
                }*/
                //  获取应用名称：oa在uri里 /oa/login
                String webAppName = servletPath.split("/")[1];
                //获取servletMaps集合中的value->servletMap ->key:urlPattern value:servletClassName
                //WebParse.servletMaps里的形式是Map<string Map<string,string>>
                Map<String,String>servletMap = WebParse.servletMaps.get(webAppName);
                //获取servlet的请求路径:/login
                String urlPattern = servletPath.substring(webAppName.length()+1);
                //获取servlet完整的类名
                String servletClassName = servletMap.get(urlPattern);
                //判断该servelt业务处理类类
                if (servletClassName != null){
                    //获取封装响应参数对象
                    ResponseObject responseObject = new ResponseObject();
                    responseObject.setWrite(out);
                    //获取封装请求参数对象
                    RequestObject requestObject = new RequestObject(requestURI);
                    out.print("HTTP/1.1 200 OK\n");
                    out.print("Content-Type:text/html;charset=utf-8\n\n");
                    //创建servlet对象之前，先从缓存池中查找是否存在
                    //1.如果有，拿来直接使用
                    //2.如果没有 创建servlet对象，放到缓存池中
                    Servlet servlet = ServletCache.get(urlPattern);
                    if (servlet == null){
                        //t通过反射机制创建该业务处理类
                        Class c=Class.forName(servletClassName);
                        Object obj=c.newInstance();
                        //这个时候，servlet业务处理类里的方法？
                         servlet = (Servlet) obj;
                         //将创建好的Servlet对象放在缓存池中 urlpattern=/user/save
                        ServletCache.put(urlPattern,servlet);
                    }
                    System.out.println("servlet:"+servlet);
                    servlet.service(requestObject,responseObject);
                }else{
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

                }

            }
            //强制刷新
            out.flush();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {


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
