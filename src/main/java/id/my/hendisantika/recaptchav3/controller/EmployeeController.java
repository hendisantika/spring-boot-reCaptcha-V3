package id.my.hendisantika.recaptchav3.controller;

import id.my.hendisantika.recaptchav3.config.ReCaptchaConfig;
import id.my.hendisantika.recaptchav3.entity.Employee;
import id.my.hendisantika.recaptchav3.repository.EmployeeRepository;
import id.my.hendisantika.recaptchav3.service.ReCaptchaValidationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
@Slf4j
@Controller
@RequiredArgsConstructor
public class EmployeeController {

    private final ReCaptchaValidationService validator;
    private final EmployeeRepository employeeRepository;
    private final ReCaptchaConfig reCaptchaConfig;

    @GetMapping("/register")
    public String showRegister(Model model) {
        String siteKey = reCaptchaConfig.getSite().getKey();
        log.info("DEBUG: Site key loaded: {}", siteKey != null ? siteKey.substring(0, Math.min(10, siteKey.length())) + "..." : "NULL");
        model.addAttribute("employee", new Employee());
        model.addAttribute("recaptchaSiteKey", siteKey);
        return "register";
    }

    @PostMapping("/save")
    public String saveEmployee(@ModelAttribute("employee")
                               Employee employee,
                               @RequestParam(name = "recaptcha-token", required = false)
                               String captcha, Model model) {
        log.info("DEBUG: Received reCAPTCHA token: {}", captcha);
        
        if (captcha == null || captcha.trim().isEmpty()) {
            log.info("DEBUG: reCAPTCHA token is null or empty");
            model.addAttribute("message", "reCAPTCHA token is missing. Please try again.");
        } else if (validator.validateCaptcha(captcha)) {
            log.info("DEBUG: reCAPTCHA validation successful, saving employee: {}", employee.getName());
            employeeRepository.save(employee);
            model.addAttribute("employee", new Employee());
            model.addAttribute("message", "Employee added!!");
        } else {
            log.info("DEBUG: reCAPTCHA validation failed");
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

    @GetMapping("/edit/{id}")
    public String showEdit(@PathVariable Integer id, Model model) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
        model.addAttribute("employee", employee);
        model.addAttribute("recaptchaSiteKey", reCaptchaConfig.getSite().getKey());
        return "edit";
    }

    @PostMapping("/update")
    public String updateEmployee(@ModelAttribute("employee")
                                 Employee employee,
                                 @RequestParam(name = "recaptcha-token", required = false)
                                 String captcha, Model model) {
        log.info("DEBUG: Updating employee ID: {}, Received reCAPTCHA token: {}", employee.getId(), captcha);

        if (captcha == null || captcha.trim().isEmpty()) {
            log.info("DEBUG: reCAPTCHA token is null or empty");
            model.addAttribute("message", "reCAPTCHA token is missing. Please try again.");
        } else if (validator.validateCaptcha(captcha)) {
            log.info("DEBUG: reCAPTCHA validation successful, updating employee: {}", employee.getName());
            employeeRepository.save(employee);
            model.addAttribute("employee", employee);
            model.addAttribute("message", "Employee updated!!");
        } else {
            log.info("DEBUG: reCAPTCHA validation failed");
            model.addAttribute("message", "Please Verify Captcha");
        }
        model.addAttribute("recaptchaSiteKey", reCaptchaConfig.getSite().getKey());
        return "edit";
    }

    @GetMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable Integer id) {
        log.info("DEBUG: Deleting employee ID: {}", id);
        employeeRepository.deleteById(id);
        return "redirect:/";
    }
}
