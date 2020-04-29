package cn.htsc.zyz.listner.thread.interrupt.mailBoxes;

import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;

@Slf4j(topic = "Test22.")
public class Test22 {
    public static void main(String[] args) {
        MessageQueue queue = new MessageQueue(2);
        //创建三个生产者线程
        for(int i=0;i<3;i++){
            int id = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    queue.put(new Message(id,"内容"+id));
                }
            },i+"线程").start();
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //创建一个消费者线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    Message take = queue.take();
                }
            }
        },"消费者线程").start();
    }
}
@Slf4j(topic = "MessageQueue.")
class MessageQueue{
    private static LinkedList<Message> list = new LinkedList<>();//初始化一个LinkedList
    private int capcity;//初始化一个容量大小

    public MessageQueue(int capcity) {
        this.capcity = capcity;
    }

    /**
     * 获取消息对象
     * @return
     */
    public Message take(){
        synchronized (list){//因为是多个线程对list进行读取，可以
          while (list.isEmpty()) {
              try {
                  log.debug("队列为空，无法消费");
                  list.wait();
              } catch (InterruptedException e) {
                  e.printStackTrace();
              }
          }
            Message message = list.removeFirst();
            log.debug("消费了:{}",message);
            list.notifyAll();
            return message ;//获取对象后，可以通知生产者进行消费
        }
    }

    /**
     * 存入消息对象
     */
    public void put(Message message){
      synchronized (list){
          while (list.size() == capcity){//如果集合已经达到容量,生产者等待
              try {
                  log.debug("队列已经满了，停止生产");
                  list.wait();
              } catch (InterruptedException e) {
                  e.printStackTrace();
              }
          }
          list.addLast(message);//向最后的队列加入消息对象
          log.debug("生产了:{}",message);
          list.notifyAll();
      }
    }
}

/**
 * 消息对象
 */
@Slf4j(topic = "Message.")
 final class Message{
  private int id;
  private Object value;

    public Message(int id, Object value) {
        this.id = id;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", value=" + value +
                '}';
    }
}