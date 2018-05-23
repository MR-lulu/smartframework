package exception;

import lombok.Data;

/**
 * Created by blue on 2018/5/23.
 */
@Data
public class ExceptionInfo<T> {
    private String msg;
    private long timestamp;
    private T data;

    public ExceptionInfo(){
        timestamp = System.currentTimeMillis();
    }
}
