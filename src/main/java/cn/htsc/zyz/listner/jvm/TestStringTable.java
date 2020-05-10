package cn.htsc.zyz.listner.jvm;

public class TestStringTable {
    public static void main(String[] args) {
//        String s1 = "a";
//        String s2 = "b";
//        String s3 = "ab";
//        String s4 = s1 + s2;
//        System.out.println(s3 == s4);
//        String s5 = "a" + "b";
//        System.out.println(s5 == s3);
        test1();
    }
    private static void test1(){
        String s3 = "ab";
        String s = new String("a") + new String("b");
        String s1 = s.intern();
        System.out.println(s1 == "ab");
        System.out.println(s == "ab");
    }
}
