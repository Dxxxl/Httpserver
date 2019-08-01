package com.zysy.httpserver;

import sun.text.normalizer.ReplaceableString;

import javax.servlet.ServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @Title:RequestObject
 * @Description:
 * @Auther:
 * @version:1.0
 * @create 2019/7/29 16:06 29
 */
public class RequestObject implements ServletRequest {
    //定义一个map集合存放"usename"->"zhangsan","sex"->"m","interest"="sport music food"
    private Map<String,String[]>parameterMap=new HashMap<String, String[]>();
    public RequestObject(String requestURI){
        //这里的requestURIAndData可能是:
        // /oa/save
        // /oa/save?username='zhangsan'&school='123'
        // /oa/save?username='zhangsan'&school='123'&sex='man'
        // /oa/save?username=&school=qinghua&sex="man"&department=data
        if (requestURI.contains("?")){
            String[] uriAndData=requestURI.split("[?]");
            if (uriAndData.length>1){
                //获取参数
                String data=uriAndData[1];
                //判断是否存在多个参数
                if (data.contains("&")){
                    //存在多个参数
                    String[] nameAndValues=data.split("&");
                    for (String nameAndValue:nameAndValues){
                        //根据 "="将参数划分
                        String[] nameAndValueArr=nameAndValue.split("=");
                        //判断key值是否在parameterMap集合中存在
                        //1.如果存在，说明该参数为多选框
                        //2，如果不存在，说明是普通的标签
                        parameterMap.containsKey(nameAndValueArr[0]);
                        if (parameterMap.containsKey(nameAndValueArr[0])){
                            //将之前多选框里的值取出来
                            String[] values=parameterMap.get(nameAndValueArr[0]);
                            //定义一个新的数组，新数组的长度永远比values数组长度大1
                            String[] newValues=new  String[values.length+1];
                            System.arraycopy(values,0,newValues,0,values.length);
                            if (nameAndValueArr.length>1){
                                newValues[newValues.length-1]=nameAndValueArr[1];
                            }else{
                                newValues[newValues.length-1]="";
                            }
                            parameterMap.put(nameAndValueArr[0],newValues);
                        }else{
                            //判断参数是否有值
                            if (nameAndValueArr.length>1){
                                parameterMap.put(nameAndValueArr[0],new String[]{nameAndValueArr[1]});
                            }else{
                                parameterMap.put(nameAndValueArr[0],new String[]{""});
                            }
                        }
                    }
                }else{
                    //只有一个参数
                    String[] strs=data.split("=");
                    if (strs.length>1){
                        parameterMap.put(strs[0],new String[]{strs[1]});
                    }else{
                        parameterMap.put(strs[0],new String[]{""});
                    }
                }
            }
        }

    }



    @Override
    public String getParameterValue(String key) {
        String[] value=parameterMap.get(key);
        return (value != null && value.length!=0)?value[0]:null;
    }

    @Override
    public String[] getParameterValues(String key) {
        return  parameterMap.get(key);
    }


}

