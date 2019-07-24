package com.zysy.httpserver;

/**
 * @Title:ServerParser
 * @Description:
 * @Auther:
 * @version:1.0
 * @create 2019/7/24 17:50 24
 */

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 解析server.xml配置文件
 */

public class ServerParser {
    /**
     * 获取服务器的端口号
     * return int port
     */

    public static int getPort(){
           int port=8080;
        try {
            //创建解析器
            SAXReader saxReader=new SAXReader();
            //通过解析器read方法，将配置文件读取到内存之中，生成一个Document[org.dom4j]对象
            Document document= saxReader.read("conf/server.xml");
            Element connectorElt= (Element) document.selectSingleNode("//connector");
            //获取port属性
            port = Integer.parseInt(connectorElt.attributeValue("port"));
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return port;

    }
}
