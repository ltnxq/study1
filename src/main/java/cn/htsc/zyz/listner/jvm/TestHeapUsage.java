package cn.htsc.zyz.listner.jvm;

public class TestHeapUsage {
    public static void main(String[] args) throws Exception {
        System.out.println("1....");
        Thread.sleep(30000);
        byte[] array = new byte[1024 * 1024 * 10];//分配10M的内存空间
        System.out.println("2....");
        Thread.sleep(20000);
        array = null;
        System.gc();
        System.out.println("3....");
        Thread.sleep(10000);
    }
}
