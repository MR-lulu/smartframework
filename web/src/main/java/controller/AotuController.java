package controller;

import autoConfiguration.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by blue on 2018/5/22.
 */
@RestController
public class AotuController {
    @Autowired
    private HelloService helloService;

    @RequestMapping("auto/home")
    @ResponseBody
    public String auto(){
        System.out.println("<-->"+helloService.getMsg());
        return helloService.getMsg();
    }
}
