package controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by blue on 2018/5/14.
 */
@RestController
public class TestController {
    @RequestMapping("/exception")
    public String test(){
        System.out.println("haha");
        throw new RuntimeException("sss");
       // return "haha";
    }
}
