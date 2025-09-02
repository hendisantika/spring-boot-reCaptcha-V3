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
        // Handle null or empty response
        if (captchaResponse == null || captchaResponse.trim().isEmpty()) {
            System.out.println("DEBUG: Empty or null captcha response");
            return false;
        }
        
        RestTemplate restTemplate = new RestTemplate();

        MultiValueMap<String, String> requestMap = new LinkedMultiValueMap<>();
        requestMap.add("secret", reCaptchaConfig.getSecretKey());
        requestMap.add("response", captchaResponse);

        try {
            System.out.println("DEBUG: Validating reCAPTCHA with Google...");
            ReCaptchResponseType apiResponse = restTemplate.postForObject(reCaptchaConfig.getVerify().getUrl(), requestMap, ReCaptchResponseType.class);

            if (apiResponse == null) {
                System.out.println("DEBUG: No response from Google reCAPTCHA API");
                return false;
            }

            System.out.println("DEBUG: reCAPTCHA Response - Success: " + apiResponse.isSuccess()
                    + ", Score: " + apiResponse.getScore()
                    + ", Action: " + apiResponse.getAction());

            // For reCAPTCHA v3, check success flag first
            if (!apiResponse.isSuccess()) {
                System.out.println("DEBUG: reCAPTCHA validation failed - success=false");
                if (apiResponse.getErrorCodes() != null) {
                    System.out.println("DEBUG: Error codes: " + String.join(", ", apiResponse.getErrorCodes()));
                }
                return false;
            }

            // Check score - lower threshold for better user experience
            // Score 0.3 is more lenient than 0.5
            if (apiResponse.getScore() != null && apiResponse.getScore() >= 0.3) {
                System.out.println("DEBUG: reCAPTCHA validation passed with score: " + apiResponse.getScore());
                return true;
            } else {
                System.out.println("DEBUG: reCAPTCHA score too low: " + apiResponse.getScore());
                return false;
            }
            
        } catch (Exception e) {
            System.out.println("DEBUG: Exception during reCAPTCHA validation: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
