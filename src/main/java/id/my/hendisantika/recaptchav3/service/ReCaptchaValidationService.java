package id.my.hendisantika.recaptchav3.service;

import id.my.hendisantika.recaptchav3.config.ReCaptchaConfig;
import id.my.hendisantika.recaptchav3.dto.ReCaptchResponseType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@Service
@RequiredArgsConstructor
public class ReCaptchaValidationService {

    private final ReCaptchaConfig reCaptchaConfig;

    public boolean validateCaptcha(String captchaResponse) {
        // Handle null or empty response
        if (captchaResponse == null || captchaResponse.trim().isEmpty()) {
            log.info("DEBUG: Empty or null captcha response");
            return false;
        }

        // Debug: Print the secret key being used (first 10 chars only for security)
        String secretKey = reCaptchaConfig.getSecretKey();
        log.info("DEBUG: Secret key loaded: {}", secretKey != null ? secretKey.substring(0, Math.min(10, secretKey.length())) + "..." : "NULL");
        log.info("DEBUG: Verify URL: {}", reCaptchaConfig.getVerify().getUrl());

        RestTemplate restTemplate = new RestTemplate();

        MultiValueMap<String, String> requestMap = new LinkedMultiValueMap<>();
        requestMap.add("secret", secretKey);
        requestMap.add("response", captchaResponse);

        try {
            log.info("DEBUG: Validating reCAPTCHA with Google...");
            ReCaptchResponseType apiResponse = restTemplate.postForObject(reCaptchaConfig.getVerify().getUrl(), requestMap, ReCaptchResponseType.class);

            if (apiResponse == null) {
                log.info("DEBUG: No response from Google reCAPTCHA API");
                return false;
            }

            log.info("DEBUG: reCAPTCHA Response - Success: {}, Score: {}, Action: {}", apiResponse.isSuccess(), apiResponse.getScore(), apiResponse.getAction());

            // For reCAPTCHA v3, check success flag first
            if (!apiResponse.isSuccess()) {
                log.info("DEBUG: reCAPTCHA validation failed - success=false");
                if (apiResponse.getErrorCodes() != null) {
                    log.info("DEBUG: Error codes: {}", String.join(", ", apiResponse.getErrorCodes()));
                }
                return false;
            }

            // Check score - lower threshold for better user experience
            // Score 0.3 is more lenient than 0.5
            if (apiResponse.getScore() != null && apiResponse.getScore() >= 0.3) {
                log.info("DEBUG: reCAPTCHA validation passed with score: {}", apiResponse.getScore());
                return true;
            } else {
                log.info("DEBUG: reCAPTCHA score too low: " + apiResponse.getScore());
                return false;
            }
            
        } catch (Exception e) {
            log.info("DEBUG: Exception during reCAPTCHA validation: {}", e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
