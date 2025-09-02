package id.my.hendisantika.recaptchav3.controller;

import id.my.hendisantika.recaptchav3.config.ReCaptchaConfig;
import id.my.hendisantika.recaptchav3.entity.Employee;
import id.my.hendisantika.recaptchav3.repository.EmployeeRepository;
import id.my.hendisantika.recaptchav3.service.ReCaptchaValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
@RequestMapping("/")
@RequiredArgsConstructor
public class EmployeeController {

    private final ReCaptchaValidationService validator;
    private final EmployeeRepository employeeRepository;
    private final ReCaptchaConfig reCaptchaConfig;

    @GetMapping("/register")
    public String showRegister(Model model) {
        model.addAttribute("employee", new Employee());
        model.addAttribute("recaptchaSiteKey", reCaptchaConfig.getSite().getKey());
        return "register";
    }

    @PostMapping("/save")
    public String saveEmployee(@ModelAttribute("employee")
                               Employee employee,
                               @RequestParam(name = "recaptcha-token", required = false)
                               String captcha, Model model) {
        if (captcha == null || captcha.trim().isEmpty()) {
            model.addAttribute("message", "reCAPTCHA token is missing. Please try again.");
        } else if (validator.validateCaptcha(captcha)) {
            employeeRepository.save(employee);
            model.addAttribute("employee", new Employee());
            model.addAttribute("message", "Employee added!!");
        } else {
            model.addAttribute("message", "Please Verify Captcha");
        }
        model.addAttribute("recaptchaSiteKey", reCaptchaConfig.getSite().getKey());
        return "register";
    }

    @GetMapping
    public String getAllEmployees(Model model) {
        model.addAttribute("list", employeeRepository.findAll());
        return "list";
    }
}
