package controller;

import annotation.Action;
import annotation.Controller;
import config.ConfigHepler;
import lombok.extern.slf4j.Slf4j;
import mapping.HandlerMapping;
import mapping.Param;
import mapping.View;
import utils.MapperUtil;

/**
 * Created by blue on 2017/12/14.
 */
@Controller
@Slf4j
public class HelloController {
    @Action("get:/hello")
    public View hello(Param param){
        log.info("参数：{}",param);
        return new View("hello.jsp");
    }

    public static void main(String[] args) {
        MapperUtil util = new MapperUtil();
        IUser user = util.getMapper(IUser.class);
        user.getAll(12L,1,"bob");
    }
}
