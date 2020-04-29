package cn.htsc.zyz.listner.thread.interrupt;


import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j(topic = "Test20." )
public class Test20 {
    public static void main(String[] args) {
        //创建一个对象
        GuardedObject guard = new GuardedObject();
        //新建一个线程获取结果
        new Thread(()->{
          //执行等待结果
            log.debug("等待结果");
            Object o = guard.get(3000);
            log.debug("产生结果对象为{}",o);
        },"t1").start();


        //新建一个产生结果的线程
        new Thread(()->{
            log.debug("执行产生结果");
            try {
                Thread.sleep(2000);
                guard.complete(null);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"t2").start();
    }

}

class GuardedObject{
    private Object response;//返回的结果
    //定义一个获取结果的方法
    //定义一个最大的等待时间
    public Object get(long timeout){
       long startTime = System.currentTimeMillis();//获取当前的时间
        long passtime = 0;
        synchronized (this){
            while (Objects.isNull(response)){
                if(passtime>=timeout)
                    break;//如果等待得时间大于设置得时间，直接退出
                try {
                    this.wait(timeout-passtime );//让线程一直等下去
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //求得经历得时间
                 passtime = System.currentTimeMillis()-startTime;
            }
            return response;
        }
    }
    //产生结果
    public void complete(Object response){
        synchronized (this){
            this.response = response;
            this.notifyAll();//唤醒所有的线程
        }
    }
}