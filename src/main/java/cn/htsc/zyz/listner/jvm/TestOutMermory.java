package cn.htsc.zyz.listner.jvm;

import java.util.ArrayList;
import java.util.List;

public class TestOutMermory {

    public static void main(String[] args) {
        int i = 0;
        try {
            List<String> list = new ArrayList<>();
            String a = "hello";
            while (true){
                list.add(a);
                a = a +a ;
                i++;
            }
        } catch (Throwable e) {
            System.out.println(i);
            e.printStackTrace();
        }

    }
}
