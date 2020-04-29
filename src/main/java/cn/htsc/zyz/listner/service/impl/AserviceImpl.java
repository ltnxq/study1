package cn.htsc.zyz.listner.service.impl;

import cn.htsc.zyz.listner.mapper.TestMapper;
import cn.htsc.zyz.listner.service.IAservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class AserviceImpl implements IAservice {
    @Autowired
    private TestMapper testMapper;

    @Override
    public void test1() {
        Integer count = testMapper.queryCount();
        System.out.println(count);
    }
}
