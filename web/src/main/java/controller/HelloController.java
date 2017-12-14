package controller;

import annotation.Action;
import annotation.Controller;
import mapping.View;

/**
 * Created by blue on 2017/12/14.
 */
@Controller
public class HelloController {
    @Action("get:/hello")
    public View hello(){
        return new View("hello.jsp");
    }
}
