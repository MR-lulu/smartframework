package autoConfiguration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by blue on 2018/5/22.
 */
@ConfigurationProperties(prefix = "hello")
@PropertySource("classpath:/hello.properties")
public class HelloProperties {
    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
