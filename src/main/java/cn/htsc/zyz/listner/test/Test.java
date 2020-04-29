package cn.htsc.zyz.listner.test;

import org.springframework.util.CollectionUtils;

import java.util.*;

public class Test {
    public static void main(String[] args) {
        //创建attachVo对象
        AttachVo attachVo = new AttachVo("你好");
        attachVo.setDocId("123456");
        AttachVo attachVo1 = new AttachVo("你好");
        attachVo1.setDocId("1234568");
        List<AttachVo> attachVoList = new ArrayList<>();
        attachVoList.add(attachVo);
        attachVoList.add(attachVo1);
        List<AttachVo> result = dealSame(attachVoList);
        System.out.println(result);
    }

    private static  <T> List<T> dealSame(List<T> list){

        if(CollectionUtils.isEmpty(list)){
            return null;
        }
        //
        Set<T> set1 = new HashSet<>();
        T[] objects = (T[])list.toArray();
        Collections.addAll(set1,objects);
        //
        List<T> result = new ArrayList<>();
        Collections.addAll(result,(T[])set1.toArray());
        return result;

    }
}
