package cn.htsc.zyz.listner.thread.interrupt.mailBoxes;


import lombok.extern.slf4j.Slf4j;

import java.util.Hashtable;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j(topic = "Test21.")
public class Test21 {

    public static void main(String[] args) {
        for(int i=0;i<3;i++){
           new People().start();
        }
        //
        try {
            TimeUnit.SECONDS.sleep(1);
            //获取所有的信箱ID
            Set<Integer> ids = Factory.getIds();
            for(Integer id:ids){
                //每个信箱需要一个邮递员进行
                new PostMan(id,"内容"+id).start();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
@Slf4j(topic = "People.")
class People extends Thread{
    @Override
    public void run() {
        GuardedObject gurdenObject = Factory.createGurdenObject();//创建一个信箱对象
        log.debug("开始收信:id{}",gurdenObject.getId());
        Object mail = gurdenObject.get(5000);
        log.debug("收信完成:id{},收信内容是{}",gurdenObject.getId(),mail);
    }
}
@Slf4j(topic = "PostMan.")
class PostMan extends Thread{
    private Integer id;//邮箱的编号
    private String mail;//邮箱内容
    public PostMan(Integer id,String mail){
        this.id = id;
        this.mail = mail;
    }
    @Override
    public void run() {
        GuardedObject guardedObject = Factory.getGuardedObject(id);//获取信箱
        log.debug("开始送信:id{},内容{}",id,mail);
        guardedObject.complete(mail);
    }
}



//创建一个工厂类
class Factory{
    private static   Map<Integer, GuardedObject> map = new Hashtable<>();
    private static  int id =1;
    //创建一个生成id的方法
    private static synchronized int  generateId(){
        return id++;
    }
    //创建一个生成对象的方法
    public static GuardedObject createGurdenObject(){
        GuardedObject guardenObject = new GuardedObject(generateId());
        map.put(guardenObject.getId(),guardenObject);
        return guardenObject;
    }
    //获取所有的id
    public static Set<Integer> getIds(){
        return map.keySet();
    }
    //
    public static GuardedObject getGuardedObject(Integer id){
            return map.remove(id);
    }
}





//创建一个GuardenObject类
class GuardedObject{
    private Integer id;

    public GuardedObject(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

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