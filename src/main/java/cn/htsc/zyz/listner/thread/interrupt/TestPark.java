package cn.htsc.zyz.listner.thread.interrupt;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

@Slf4j(topic = "TestPark.")
public class TestPark {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            log.debug("start......");
            try {
                TimeUnit.SECONDS.sleep(2);
                log.debug("park....");
                LockSupport.park();//使线程进入wait状态
                log.debug("resume");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t1");
        t1.start();
        //
        try {
            TimeUnit.SECONDS.sleep(1);
            log.debug("unpark.......");
            LockSupport.unpark(t1);//唤醒t1线程
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
