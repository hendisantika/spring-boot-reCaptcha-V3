package id.my.hendisantika.recaptchav3.service;

import id.my.hendisantika.recaptchav3.config.ReCaptchaConfig;
import id.my.hendisantika.recaptchav3.dto.ReCaptchResponseType;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class ReCaptchaValidationService {

    private final ReCaptchaConfig reCaptchaConfig;

    public boolean validateCaptcha(String captchaResponse) {
        RestTemplate restTemplate = new RestTemplate();

        MultiValueMap<String, String> requestMap = new LinkedMultiValueMap<>();
        requestMap.add("secret", reCaptchaConfig.getSecretKey());
        requestMap.add("response", captchaResponse);

        try {
            ReCaptchResponseType apiResponse = restTemplate.postForObject(reCaptchaConfig.getVerify().getUrl(), requestMap, ReCaptchResponseType.class);
            if (apiResponse == null) {
                return false;
            }

            // For reCAPTCHA v3, check both success flag and score
            // Score should be >= 0.5 for legitimate users
            return apiResponse.isSuccess() && apiResponse.getScore() != null && apiResponse.getScore() >= 0.5;
        } catch (Exception e) {
            // Log the error and return false for any validation failures
            return false;
        }
    }
}
