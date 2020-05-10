package cn.htsc.zyz.listner.jvm;

public class TestStack {
    public static void main(String[] args) {
       method1();
    }
    public static void method1(){
        method2(1,2);
    }
    public static int method2(int a,int b){
        int c= a + b;
        return c;
    }
}
