package javax.servlet;

/**
 * @Title:ServletRequest
 * @Description:
 * @Auther:
 * @version:1.0
 * @create 2019/7/29 17:35 29
 */
public interface ServletRequest {
    /**
     * 通过参数的名字获取的参数值
     * @param name
     * @return
     */
    String getParameter(String name);

    String getParameterValue(String key);

    /**
     * 通过参数的名字获取的参数值
     * @param name
     * @return
     */
    String[] getParameterValues(String name);
}
