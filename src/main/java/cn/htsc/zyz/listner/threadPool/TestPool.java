package cn.htsc.zyz.listner.threadPool;


import lombok.extern.slf4j.Slf4j;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j(topic = "c.TestPool")
public class TestPool {

    public static void main(String[] args) {
        TreadPool treadPool = new TreadPool(1, 1000, TimeUnit.MICROSECONDS, 1,((queue, task) -> {
            //死等
           // queue.put(task);
            //超时等待
            //queue.offer(task,1500,TimeUnit.MILLISECONDS);
            //调用者放弃任务执行
            //log.debug("放弃任务执行{}",task);
            //调用者抛出异常
           // throw  new RuntimeException("任务执行失败" +task);
            //调用者自己执行
            task.run();
        }));
        for(int i=0;i<4;i++){
            int j = i;
            treadPool.execue(() ->{
                try {
                    Thread.sleep(500L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.debug("{}",j);
            });
        }
    }
}
    @FunctionalInterface
    interface RejectPolicy<T>{
      void reject(BlockingQueue<T> queue,T task);
    }

    @Slf4j(topic = "c.TreadPool")
    class TreadPool{
        //任务队列
        private BlockingQueue<Runnable> taskQueue;
        //线程集合
        private HashSet<Worker> workers = new HashSet();
        //核心线程数
        private int coreSize;
        //获取任务的超时时间
        private long timeout;
        //
        private TimeUnit timeUnit;

        private RejectPolicy<Runnable> rejectPolicy;

        public TreadPool(int coreSize, long timeout, TimeUnit timeUnit,int capacity,RejectPolicy<Runnable> rejectPolicy) {
            this.coreSize = coreSize;
            this.timeout = timeout;
            this.timeUnit = timeUnit;
            this.taskQueue = new BlockingQueue<Runnable>(capacity);
            this.rejectPolicy = rejectPolicy;
        }
        //执行任务方法
        public void execue(Runnable task){
            synchronized (workers){
                //当任务数量没有超过核心数，直接交给woker对象执行
                //当任务数量超过核心数，加入阻塞队列
                if(workers.size()<coreSize){
                   Worker worker = new Worker(task);
                   log.debug("新增woker{},{}",worker,task);
                   workers.add(worker);
                    worker.start();
                }else {
                    //存入等待队列的策略
                   // taskQueue.put(task);
                    //1、死等
                    //2、带超时时间等待
                    //3、让调用者放弃执行
                    //4、让调用者抛出异常
                    //5、让调用者自己执行任务
                    taskQueue.tryPut(rejectPolicy,task);
                }
            }
        }

        class Worker extends Thread{
           private Runnable task;
           public Worker(Runnable task) {
                this.task = task;
            }
            @Override
            public void run(){
                //1、如果传递过来的task不为空，直接执行
                //2、如果task为空，直接去任务队列里获取
//                while (task != null || (task = taskQueue.take()) != null){
                while (task != null || (task = taskQueue.pool(timeout,timeUnit)) != null){
                    try{
                        log.debug("正在执行{}",task);
                      task.run();
                    }catch (Exception e){

                    }
                    finally {
                      task = null;
                    }
                }
                //退出循环,线程队列释放线程
                synchronized (workers){
                    log.debug("work被移除{}",this);
                    workers.remove(this);
                }
            }

        }
        //执行任务

    }

    @Slf4j(topic = "c.BlockingQueue")
    class BlockingQueue<T> {
        //创建一个链表,双向链表
        private Deque<T> queue = new ArrayDeque<>();
        //定义一个锁对象
        private ReentrantLock lock = new ReentrantLock();
        //生产者条件变量
        private Condition fullWaitSet = lock.newCondition();
        //消费者条件变量
        private Condition emptyWaitSet = lock.newCondition();
        //容量
        private int capcity;//容量大小

        public BlockingQueue(int capcity) {
            this.capcity = capcity;
        }

        //待超时的阻塞获取
        public T pool(long timeout, TimeUnit unit){
            lock.lock();
            try {
                long nanos = unit.toNanos(timeout);
                while (queue.isEmpty()){
                    try {
                        if(nanos<=0)
                            return null;
                        nanos = emptyWaitSet.awaitNanos(nanos);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                T t = queue.removeFirst();
                fullWaitSet.signalAll();
                return t;
            }finally {
                lock.unlock();
            }
        }
        //阻塞获取
        public T take(){
            lock.lock();
            try {
                while (queue.isEmpty()){

                    try {
                        emptyWaitSet.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                T t = queue.removeFirst();
                fullWaitSet.signalAll();
                return t;
            }finally {
                lock.unlock();
            }
        }
        //阻塞添加
        public void put(T t){
            lock.lock();
            try {
                while (queue.size()>=capcity){
                    try {
                        log.debug("等待加入队列{}",t);
                        fullWaitSet.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //队列添加元素
                queue.addLast(t);
                emptyWaitSet.signalAll();
            }finally {
                lock.unlock();
            }
        }
        //待超时的添加
        public boolean offer(T task,long timeout,TimeUnit unit){
            lock.lock();
            try {
                long nanos = unit.toNanos(timeout);
                while (queue.size()>=capcity){
                    try {
                        log.debug("等待加入队列{}",task);
                        if(nanos <=0){
                            return false;
                        }
                         nanos = fullWaitSet.awaitNanos(nanos);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //队列添加元素
                queue.addLast(task);
                log.debug("等待队列加入:{}",task);
                emptyWaitSet.signalAll();
                return true;
            }finally {
                lock.unlock();
            }
        }
        //队列大小
        public int size(){
            lock.unlock();
            try {
                return queue.size();
            }finally {
                lock.unlock();
            }
        }
        //含有拒绝模式的添加
        public void tryPut(RejectPolicy<T> rejectPolicy, T task) {
            lock.lock();
            try {
                //判断队列是否已经满了
                if(queue.size() == capcity){
                    rejectPolicy.reject(this,task);
                }else {//没有满
                    queue.addLast(task);
                    log.debug("加入任务队列 {}",task);
                    emptyWaitSet.signalAll();
                }
            }finally {
                lock.unlock();
            }
        }
    }


