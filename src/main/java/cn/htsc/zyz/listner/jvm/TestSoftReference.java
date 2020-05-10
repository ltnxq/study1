package cn.htsc.zyz.listner.jvm;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;
//-Xmx8m--设置JVM的堆内存的大小
public class TestSoftReference {
    private static final int _4MB= 1024*1024*4;
    public static void main(String[] args) {
        //创建一个软引用的集合
        List<SoftReference<byte[]>> list = new ArrayList<>();
        //创建一个引用队列
        ReferenceQueue<byte[]> queue = new ReferenceQueue<>();
        for(int i=0;i<5;i++){
            //关联了引用队列，当byte[]被回收的时候，会将软引用加入到队列中
            //弱引用本身也会占用一点的内存空间
            SoftReference<byte[]> reference = new SoftReference<>(new byte[_4MB],queue);
            System.out.println(reference.get());
            list.add(reference);
            System.out.println(list.size());
        }
        //取出队列的元素,并且从list的集合去除
        Reference<? extends byte[]> poll = queue.poll();
        while (poll !=null){
            list.remove(poll);
            poll = queue.poll();
        }
        System.out.println("==============================");
        for (SoftReference<byte[]> reference : list) {
            System.out.println(reference.get());
        }
    }
}
