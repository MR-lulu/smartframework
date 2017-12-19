package controller;

import annotation.Action;
import annotation.Controller;
import config.ConfigHepler;
import mapping.HandlerMapping;
import mapping.Param;
import mapping.View;

/**
 * Created by blue on 2017/12/14.
 */
@Controller
public class HelloController {
    @Action("get:/hello")
    public View hello(Param param){
        return new View("hello.jsp");
    }
}
