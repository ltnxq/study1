package spring;

import cn.htsc.zyz.listner.aop.TargetInterface;
import cn.htsc.zyz.listner.service.IAservice;
import cn.htsc.zyz.listner.spring.User;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "../applicationContext.xml")
public class SpringTest {

    @Autowired
    private User user;
    @Autowired
    private TargetInterface target;
    @Autowired
    private IAservice aservice;

    @Test
    public void testUser(){
        user.sayHello();
    }

    @Test
    public void testAop(){
       target.method();
       target.method1();
    }

    @Test
    public void testMybatis(){
        aservice.test1();
    }
}
