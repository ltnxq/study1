package cn.htsc.zyz.listner.web;

import cn.htsc.zyz.listner.service.IAservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RequestMapping("/text")
@RestController
public class TestController {
    @Autowired
    private IAservice aservice;


  @RequestMapping("/quick")
    public String quick(){
      aservice.test1();
      return "";
  }
}
