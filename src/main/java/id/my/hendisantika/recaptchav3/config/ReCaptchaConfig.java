package id.my.hendisantika.recaptchav3.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-reCaptcha-V3
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 01/09/25
 * Time: 10.30
 * To change this template use File | Settings | File Templates.
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "google.recaptcha")
public class ReCaptchaConfig {

    private Site site = new Site();
    private String secretKey;
    private Verify verify = new Verify();

    @Data
    public static class Site {
        private String key;
    }

    @Data
    public static class Verify {
        private String url;
    }
}