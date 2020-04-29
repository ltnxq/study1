package cn.htsc.zyz.listner.aop;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Component;

@Component
public class Target implements TargetInterface {
    public void method() {
        System.out.println("Targer running ......");
    }

    public void method1() {
        System.out.println("method1 running ......");
    }
}
