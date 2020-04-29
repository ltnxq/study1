package cn.htsc.zyz.listner.thread.interrupt;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j(topic = "TestReentrantLock2.")
public class TestReentrantLock2 {
    static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                //尝试获取锁
                try {
                    if (!lock.tryLock(1,TimeUnit.SECONDS)) {
                        log.debug("没有获取到锁");
                        return;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //获取到锁
                try {
                    log.debug("获取到锁");
                } finally {
                    lock.unlock();//释放锁
                }
            }
        }, "t1");
        //主线程获取锁
        try {
            log.debug("获得锁");
            lock.lock();
            //启动t1线程
            t1.start();
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            log.debug("释放锁");
            lock.unlock();
        }
    }
}
