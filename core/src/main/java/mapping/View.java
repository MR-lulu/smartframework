package mapping;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

/**
 * Created by blue on 2017/12/14.
 */
@Getter
@AllArgsConstructor
public class View {
    private String path;

    private Map<String,Object> model;

    public View(String path) {
        this.path = path;
    }

    public View addModel(String key,Object value){
        model.put(key,value);
        return this;
    }
}
