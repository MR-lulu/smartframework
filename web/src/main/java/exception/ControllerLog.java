package exception;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by blue on 2018/5/23.
 */
@Aspect
@Component
public class ControllerLog {
    private Logger log = LoggerFactory.getLogger("LOG");

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void wrapperController(){}

    @Around("wrapperController() && args(object,..) ")
    public Object  doAround(ProceedingJoinPoint pj,Object object) throws Throwable {

        log.info("Excute Method: [{}] - Method Params: [{}]", pj.getSignature(), JSON.toJSONString(object));
        Object result = pj.proceed();
        return result;
    }
}
