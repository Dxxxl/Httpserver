package com.zysy.httpserver;

/**
 * @Title:ResponseObject
 * @Description:
 * @Auther:
 * @version:1.0
 * @create 2019/7/26 15:58 26
 */

import javax.servlet.ServletResponse;
import java.io.PrintWriter;
import java.security.PublicKey;

/**
 * 负责封装响应参数对象
 * author：xuli
 */

public class ResponseObject implements ServletResponse {

    private PrintWriter out;
    //将传过来的参数out的值传给新建的out
    @Override
    public void setWrite(PrintWriter out) {
        this.out=out;
    }

    public PrintWriter getWrite(){
        return  out;
    }

}

