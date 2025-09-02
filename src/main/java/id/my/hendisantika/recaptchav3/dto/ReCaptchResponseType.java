package id.my.hendisantika.recaptchav3.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-reCaptcha-V3
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 17/06/24
 * Time: 13.13
 * To change this template use File | Settings | File Templates.
 */
@Getter
@Setter
public class ReCaptchResponseType {
    private boolean success;
    private String challenge_ts;
    private String hostname;
    private Double score; // reCAPTCHA v3 score (0.0 to 1.0)
    private String action; // reCAPTCHA v3 action
    private String[] errorCodes; // Error codes if validation fails
}
