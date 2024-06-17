package id.my.hendisantika.recaptchav3.service;

import id.my.hendisantika.recaptchav3.dto.ReCaptchResponseType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

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

    public boolean validateCaptcha(String captchaResponse) {
        RestTemplate restTemplate = new RestTemplate();

        MultiValueMap<String, String> requestMap = new LinkedMultiValueMap<>();
        requestMap.add("secret", RECAPTCHA_SECRET);
        requestMap.add("response", captchaResponse);

        ReCaptchResponseType apiResponse = restTemplate.postForObject(GOOGLE_RECAPTCHA_ENDPOINT, requestMap, ReCaptchResponseType.class);
        if (apiResponse == null) {
            return false;
        }

        return Boolean.TRUE.equals(apiResponse.isSuccess());
    }
}
