package sevlet;



import cn.htsc.zyz.listner.session.Person;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Serializable;
import java.util.Enumeration;

public class Aservlet implements Servlet, Serializable {
    public void init(ServletConfig servletConfig) throws ServletException {
        System.out.println("init。。。。");
        System.out.println(servletConfig.getInitParameter("p1"));
        System.out.println(servletConfig.getInitParameter("p2"));
        System.out.println("servletName" + servletConfig.getServletName());
        Enumeration<String> initParameterNames = servletConfig.getInitParameterNames();
        //迭代遍历
        while (initParameterNames.hasMoreElements())
            System.out.println(initParameterNames.nextElement());
    }

    /**
     * 获取servlet的配置文件
     * @return
     */
    public ServletConfig getServletConfig() {
        System.out.println("config.....");
        return null;
    }

    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        System.out.println("service.....");
        //将request进行强转
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        //获取session域
        HttpSession session = request.getSession();
        session.setAttribute("person",new Person("张三",23));
    }

    public String getServletInfo() {
        System.out.println("getServletInfo");
        return "我是一个快乐的sevlet";
    }

    public void destroy() {
        System.out.println("destroy");
    }
}
