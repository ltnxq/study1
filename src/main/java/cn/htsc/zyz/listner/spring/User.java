package cn.htsc.zyz.listner.spring;


import org.springframework.stereotype.Component;

@Component
public class User {
    private String  username;//姓名

    private String age;//姓名

    public User() {
    }

    public User(String username, String age) {
        this.username = username;
        this.age = age;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void sayHello(){
        System.out.println("hello.....");
    }
}
