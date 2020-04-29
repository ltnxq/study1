package cn.htsc.zyz.listner;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 实现一个监听器
 * 必须在web-xml中注册该监听器
 */
public class MyServletContextListner implements ServletContextListener {

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.out.println("创建了servlet的对象");
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        System.out.println("销毁了servlet对象");
    }
}
