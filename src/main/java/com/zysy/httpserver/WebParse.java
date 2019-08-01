package com.zysy.httpserver;

/**
 * @Title:WebParse
 * @Description:
 * @Auther:
 * @version:1.0
 * @create 2019/7/25 19:30 25
 */

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.SAXException;

import javax.swing.text.html.parser.Parser;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 解析每个WebApp的web.xml文件
 * @auther xuli
 */

public class WebParse {
    public static Map<String,Map<String,String>>servletMaps = new HashMap<String, Map<String, String>>();
    /**
     * 解析每个WebApp的web.xml文件
     * @param webAppNames 所有WebApp名字
     * @throws DocumentException
     */
    public static void parser(String[] webAppNames) throws DocumentException{
        for (String webAppName:webAppNames){
            Map<String ,String> servletMap= parser(webAppName);
            servletMaps.put(webAppName,servletMap);
        }
    }
    private static Map<String,String>parser(String webAppName)throws DocumentException{
        //获取web.xml路径
        String webPath=webAppName+"/WEB-INF/web.xml";
        //获取解析器
        SAXReader saxReader=new SAXReader();
        //将web.xml读取到Document对象中
        Document document=saxReader.read(new File(webPath));
        //获取web-app下的servlet节点
        List<Element> servletNodes= document.selectNodes("/web-app/servlet");
        //获取sevlet-name和servlet-class值存放到servletInfoMap集合中
        Map<String,String>servletInfoMap=new HashMap<String, String>();
//      遍历servletNodes获取servlet-name和servlet-class值
        for(Element servletNode:servletNodes){
            Element servletNameElt= (Element) servletNode.selectSingleNode("servlet-name");
            //如果下面出问题 则是上面servletNode空
            String servletName=servletNameElt.getStringValue();
            //获取servlet-class节点元素对象
            Element servletClassElt= (Element) servletNode.selectSingleNode("servlet-class");
            //获取servletClassElt节点元素对象值
            String servletClassName=servletClassElt.getStringValue();
            //将servletName和servletClassName分别当作key和value存放到servletInfoMap集合中
            servletInfoMap.put(servletName,servletClassName);
        }
           // 获取web-app下的servlet-mapping节点 web-app->servlet-mapping
            List<Element> servletMappingNodes=document.selectNodes("/web-app/servlet-mapping");
           //获取servlet-name和uri-pattern的值存放在servletMappingInfoMap中
            Map<String,String> servletMappingInfoMap=new HashMap<String, String>();

            //遍历servletMappingNodes获取servlet-name和url-pattern
        for (Element servletMappingNode:servletMappingNodes){
            Element servletNameElt= (Element) servletMappingNode.selectSingleNode("servlet-name");
            String servletName=servletNameElt.getStringValue();
            //获取url-pattern节点元素对象
            Element servletPathElt= (Element) servletMappingNode.selectSingleNode("url-pattern");
            //获取urlpatternElt节点元素的对象值
            String urlPattren=servletPathElt.getStringValue();
            servletMappingInfoMap.put(servletName,urlPattren);
        }
           //获取servletInfoMap或者servletMappingInfoMap任何一个key值的集合
            Set<String> servletNames=servletInfoMap.keySet();
//创建一个servletMap集合，将servletMappingInfoMap的value和servletInfoMap的value分别当作key和value分别存在servletMap集合中
            Map<String,String> servletMap=new HashMap<String, String>();
            //遍历servletNames，将servletPath和servletClassName存放在servletMap中
            for (String servletName:servletNames){
                //获取servletMappingInfoMap集合中的value：urlPattern
                String urlPattern=servletMappingInfoMap.get(servletName);
                //获取servletInfoMap集合中的value：servletClass 完整类名
                String servletClassName=servletInfoMap.get(servletName);
                //将urlPattern和servletClassName分别当作key和value值存在servletMap集合中
                servletMap.put(urlPattern,servletClassName);
            }
            return servletMap;

    }

}
