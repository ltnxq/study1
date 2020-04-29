package cn.htsc.zyz.listner.thread.interrupt;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j(topic = "test2.")
public class Test2 {
    public static void main(String[] args) throws InterruptedException {
        log.info("主方法执行");
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    if(Thread.currentThread().isInterrupted()){
                        //如果这个线程被打断，任务退出
                        break;
                    }
                }
            }
        },"t1");
        t1.start();//启动线程
        t1.join();
        //主线线程
        TimeUnit.SECONDS.sleep(1);
        t1.interrupt();//打断线程

    }


}
