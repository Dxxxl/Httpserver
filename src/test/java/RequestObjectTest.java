import com.zysy.httpserver.RequestObject;

/**
 * @Title:RequestObjectTest
 * @Description:
 * @Auther:
 * @version:1.0
 * @create 2019/7/29 17:20 29
 */
public class RequestObjectTest {
    @org.junit.Test
    public void getparameter() {
        RequestObject request1=new RequestObject("/oa/save");
        System.out.println(request1.getParameter("username"));
        RequestObject request2=new RequestObject(("/oa/save?"));
        System.out.println(request2.getParameter("username"));
        RequestObject request3=new RequestObject(("/oa/save?username="));
        System.out.println(request3.getParameter("username"));
        RequestObject request4=new RequestObject(("/oa/save?username=zhangsan&interest=sport&interest=music"));
        System.out.println(request4.getParameter("username"));
        System.out.println(request4.getParameter("interest"));

    }

    @org.junit.Test
    public void getParameterValues() {
        RequestObject request=new RequestObject("/oa/save?username=zhangsan&interest=sport&interest=music");
        String[] interests=request.getParameterValues("interest");
        for (String interest:interests){
            System.out.println(interest);
        }
    }
}
