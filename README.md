# Spring Boot reCAPTCHA V3 Integration

A Spring Boot application demonstrating the integration of Google reCAPTCHA V3 for secure form submissions with employee
registration functionality.

## Features

- **Google reCAPTCHA V3 Integration**: Seamless bot protection without user interaction
- **Employee Management**: Add and view employee records with validation
- **Responsive UI**: Modern Bootstrap-based interface with smooth animations
- **MySQL Database**: Persistent data storage with JPA/Hibernate
- **Docker Compose**: Easy database setup and management
- **Comprehensive Logging**: Debug information for reCAPTCHA validation

## Tech Stack

- **Backend**: Spring Boot 3.x, Spring Data JPA, Thymeleaf
- **Database**: MySQL 9.4.0
- **Frontend**: Bootstrap 5.3.0, Font Awesome 6.0.0, JavaScript
- **Security**: Google reCAPTCHA V3
- **Build Tool**: Maven
- **Containerization**: Docker Compose

## Prerequisites

- Java 17 or higher
- Maven 3.6+
- Docker and Docker Compose
- Google reCAPTCHA V3 Site Key and Secret Key

## Setup Instructions

### 1. Get reCAPTCHA Keys

1. Visit [Google reCAPTCHA Admin Console](https://www.google.com/recaptcha/admin)
2. Create a new site with reCAPTCHA v3
3. Note down your Site Key and Secret Key

### 2. Configure Application

Update `src/main/resources/application.properties`:

```properties
# Google reCAPTCHA v3 Configuration
google.recaptcha.site.key=YOUR_SITE_KEY_HERE
google.recaptcha.secret.key=YOUR_SECRET_KEY_HERE
google.recaptcha.verify.url=https://www.google.com/recaptcha/api/siteverify
```

### 3. Start Database

```bash
docker-compose up -d
```

### 4. Run Application

```bash
./mvnw spring-boot:run
```

The application will be available at `http://localhost:8081`

## Application Structure

```
src/
├── main/
│   ├── java/id/my/hendisantika/recaptchav3/
│   │   ├── config/
│   │   │   └── ReCaptchaConfig.java          # reCAPTCHA configuration
│   │   ├── controller/
│   │   │   └── EmployeeController.java       # Web controller
│   │   ├── dto/
│   │   │   └── ReCaptchResponseType.java     # reCAPTCHA response DTO
│   │   ├── entity/
│   │   │   └── Employee.java                 # Employee entity
│   │   ├── repository/
│   │   │   └── EmployeeRepository.java       # Data repository
│   │   ├── service/
│   │   │   └── ReCaptchaValidationService.java # reCAPTCHA validation
│   │   └── RecaptchaV3Application.java       # Main application
│   └── resources/
│       ├── templates/
│       │   ├── register.html                 # Registration form
│       │   └── list.html                     # Employee list view
│       └── application.properties            # Configuration
```

## API Endpoints

| Method | Endpoint    | Description                             |
|--------|-------------|-----------------------------------------|
| GET    | `/register` | Show employee registration form         |
| POST   | `/save`     | Save employee with reCAPTCHA validation |
| GET    | `/`         | List all employees                      |

## reCAPTCHA V3 Implementation

### Frontend (JavaScript)

- Automatically generates reCAPTCHA token on form submission
- Comprehensive validation and error handling
- Debug logging for troubleshooting

### Backend (Java)

- Validates reCAPTCHA token with Google API
- Configurable score threshold (currently 0.3)
- Detailed logging for debugging
- Graceful error handling

## Database Configuration

The application uses MySQL with the following default configuration:

```properties
spring.datasource.url=jdbc:mysql://localhost:3308/recaptcha_db
spring.datasource.username=yu71
spring.datasource.password=53cret
```

Database schema is auto-created via Hibernate DDL.

## Testing

### Manual Testing Scripts

The project includes test scripts:

- `test_employee_add.sh` - Test employee addition
- `test_recaptcha_verification.sh` - Test reCAPTCHA verification
- `final_verification.sh` - Complete verification

### Running Tests

```bash
chmod +x test_employee_add.sh
./test_employee_add.sh
```

## Troubleshooting

### Common Issues

1. **reCAPTCHA Token is null**
    - Check browser console for JavaScript errors
    - Verify Site Key is correctly configured
    - Ensure reCAPTCHA script is loaded

2. **reCAPTCHA Validation Failed**
    - Verify Secret Key is correct
    - Check network connectivity to Google API
    - Review server logs for detailed error messages

3. **Database Connection Issues**
    - Ensure Docker containers are running
    - Check database credentials in application.properties
    - Verify port 3308 is not in use by other applications

### Debug Logging

The application includes comprehensive debug logging:

```
DEBUG: Received reCAPTCHA token: [token]
DEBUG: Validating reCAPTCHA with Google...
DEBUG: reCAPTCHA Response - Success: true, Score: 0.9, Action: submit
```

## Security Considerations

- reCAPTCHA V3 uses behavioral analysis instead of challenges
- Score threshold is configurable (lower = more lenient)
- All form submissions are validated server-side
- Database credentials should be externalized for production

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## License

This project is open source and available under the [MIT License](LICENSE).

## Author

**Hendi Santika**

- Email: hendisantika@gmail.com
- Telegram: @hendisantika34
- GitHub: [hendisantika](https://github.com/hendisantika)

---

*Built with ❤️ using Spring Boot and Google reCAPTCHA V3*