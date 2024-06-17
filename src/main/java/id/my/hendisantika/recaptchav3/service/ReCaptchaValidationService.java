package id.my.hendisantika.recaptchav3.service;

import org.springframework.stereotype.Service;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-reCaptcha-V3
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 17/06/24
 * Time: 13.14
 * To change this template use File | Settings | File Templates.
 */
@Service
public class ReCaptchaValidationService {

    private static final String GOOGLE_RECAPTCHA_ENDPOINT = "https://www.google.com/recaptcha/api/siteverify";
    private final String RECAPTCHA_SECRET = "Your Google reCaptcha secret key";

}
