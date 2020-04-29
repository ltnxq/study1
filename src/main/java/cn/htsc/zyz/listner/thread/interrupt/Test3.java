package cn.htsc.zyz.listner.thread.interrupt;

import java.util.concurrent.TimeUnit;

public class Test3 {
    public static void main(String[] args) throws InterruptedException {

        TwoPhaseTermination twoPhaseTermination = new TwoPhaseTermination();
        twoPhaseTermination.start();
        TimeUnit.SECONDS.sleep(3);
        twoPhaseTermination.stop();

    }


}
 class TwoPhaseTermination{
    //创建一个线程
    private Thread monitor;
    //定义一个启动线程的方法
    public void start(){
        monitor = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    if(monitor.isInterrupted()){//如果打断
                        System.out.println("释放一些必要的资源");
                        break;
                    }
                    try {
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        monitor.interrupt();//重新设置打断的标记
                    }

                }

            }
        });
        monitor.start();//启动线程
    }
    //关闭线程的方法
    public void stop(){
        monitor.interrupt();//设置打断状态
    }
}