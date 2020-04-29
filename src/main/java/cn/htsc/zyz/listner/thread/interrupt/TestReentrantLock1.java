package cn.htsc.zyz.listner.thread.interrupt;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.Thread.sleep;

@Slf4j(topic = "TestReentrantLock1.")
public class TestReentrantLock1 {
    static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            //设置锁的状态为可打断的锁
            log.debug("启动。。。。。");
            try {
                lock.lockInterruptibly();
            } catch (InterruptedException e) {
                log.debug("线程被打断");
                e.printStackTrace();
                return;
            }
            try {
                log.debug("获得了锁");
            } finally {
                lock.unlock();
            }
        }, "t1");
        lock.lock();
        t1.start();
        log.debug("获得了锁");
        //执行打断
        try {
            TimeUnit.SECONDS.sleep(1);
            log.debug("执行打断");
            t1.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally{
           lock.unlock();
        }
    }
}
