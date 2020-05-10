package cn.htsc.zyz.listner.jvm;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.TestStackOverFlower")
public class TestStackOverFlower {
    private static int count;

    public static void main(String[] args) {
        try {
            method1();
        } catch (Exception e) {

            e.printStackTrace();
        }finally {
            log.debug("次数{}",count);
        }
    }
    private static void method1(){
        count++;
        method1();
    }
}
