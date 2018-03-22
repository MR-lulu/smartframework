package mapping;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

/**
 * Created by blue on 2017/12/14.
 */
@AllArgsConstructor
@Getter
public class Param {
    private Map<String,Object> paramMap;

    public long getLong(String name){
        return (Long) paramMap.get(name);
    }

    public String getString(String name){
        return (String) paramMap.get(name);
    }
}
