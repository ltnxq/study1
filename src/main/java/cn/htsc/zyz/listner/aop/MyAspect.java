package cn.htsc.zyz.listner.aop;

import org.apache.ibatis.session.SqlSessionFactory;

public class MyAspect {
    //前置增强方法
    public void before(){
        System.out.println("前置代码增强。。。。。。");
    }
    //后置增强方法
    public void after(){
        System.out.println("后置代码增强。。。。。。。");
    }
}
