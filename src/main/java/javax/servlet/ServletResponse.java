package javax.servlet;

/**
 * @Title:ServletResponse
 * @Description:
 * @Auther:
 * @version:1.0
 * @create 2019/7/26 16:21 26
 */
import java.io.PrintWriter;


/**
 * 负责响应的接口类型
 */

public interface ServletResponse {

    void  setWrite(PrintWriter out);
 /**
  * 获取响应流
 */
    PrintWriter getWrite();
}
