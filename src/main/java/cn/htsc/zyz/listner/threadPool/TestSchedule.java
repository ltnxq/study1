package cn.htsc.zyz.listner.threadPool;

import lombok.extern.slf4j.Slf4j;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j(topic = "c.TestSchedule")
public class TestSchedule {
    public static void main(String[] args) {
        LocalDateTime now = LocalDateTime.now();//获取当前时间
        log.info("当前时间{}",now);
        //改成周四18:00
        LocalDateTime localTime = now.withHour(10).withMinute(42).withSecond(0).withNano(0).with(DayOfWeek.SUNDAY);
        //如果当前的时间大于周四时间
        //延迟到下一个周四执行任务
         if(now.compareTo(localTime) >0){
             localTime = localTime.plusWeeks(1);
         }
         log.info("localTime{}",localTime);
         //设置延时时间
        long initialDelay = Duration.between(now,localTime).toMillis();//转换为毫秒值
        long period = 1000;
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(1);
        pool.scheduleAtFixedRate(() ->{
            log.debug("running......");
        },initialDelay,period, TimeUnit.MILLISECONDS);
    }
}
