package cn.htsc.zyz.listner.thread.interrupt;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

@Slf4j(topic = "TestReentrantLock.")
public class TestReentrantLock {
    static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        method1();
    }

    private static void method1() {
        lock.lock();
        try {
            log.debug("执行method1....");
            method2();
        } finally {
            lock.unlock();//释放锁
        }
    }

    private static void method2() {
        lock.lock();
        try {
            log.debug("执行method2....");
            method3();
        } finally {
            lock.lock();
        }
    }

    private static void method3() {
        lock.lock();
        try {
            log.debug("执行method3.。。。");
        } finally {
            lock.lock();
        }
    }
}
