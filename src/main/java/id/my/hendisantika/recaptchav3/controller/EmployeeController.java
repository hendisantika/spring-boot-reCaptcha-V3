package id.my.hendisantika.recaptchav3.controller;

import id.my.hendisantika.recaptchav3.repository.EmployeeRepository;
import id.my.hendisantika.recaptchav3.service.ReCaptchaValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-reCaptcha-V3
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 17/06/24
 * Time: 13.15
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final ReCaptchaValidationService validator;

    private final EmployeeRepository employeeRepository;
}
