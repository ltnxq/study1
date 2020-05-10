package cn.htsc.zyz.listner.jvm;

import java.util.ArrayList;
import java.util.List;

public class TestHeapUsage1 {
    public static void main(String[] args) throws Exception{
        List<Student> list = new ArrayList<>();
        for(int i = 0;i<200;i++){
            list.add(new Student());
        }
    Thread.sleep(1000000000L);
    }
}
class Student{
    private byte[] big = new byte[1024*1024];
}
