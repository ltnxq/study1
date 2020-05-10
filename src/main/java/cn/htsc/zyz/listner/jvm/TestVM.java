package cn.htsc.zyz.listner.jvm;

import java.util.ArrayList;
import java.util.List;

//-Xms20M -Xmx20M -Xmn10M -XX:+UseSerialGC -XX:+PrintGCDetails -verbose:gc
public class TestVM {
    private static final int _512KB = 512 * 1024;
    private static final int _7MB = 7*1024*1024;
    private static final int _8MB = 8*1024*1024;

    public static void main(String[] args) {
        List<byte[]> list = new ArrayList<>();
//
        list.add(new byte[_8MB]);
        list.add(new byte[_8MB]);
    }
}
