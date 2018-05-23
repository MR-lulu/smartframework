package exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


/**
 * Created by blue on 2018/5/23.
 */
@ControllerAdvice
public class ControllerException {
    private Logger logger = LoggerFactory.getLogger("MVC");

    @ExceptionHandler(Throwable.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Object commonExceptionHandler(Throwable throwable){
        logger.error("Exception Handler:{}",throwable);
        ExceptionInfo exceptionInfo = new ExceptionInfo();
        exceptionInfo.setMsg("未捕获的异常，请联系管理员");
        return exceptionInfo;
    }
}
