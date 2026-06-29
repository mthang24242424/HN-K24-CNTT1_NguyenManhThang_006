package exception;

import exception.controller.UserController;
import exception.dto.UserRequest;
import exception.dto.ErrorResponse;
import exception.entity.User;
import exception.handler.GlobalExceptionHandler;
import exception.service.UserRegistrationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * ExceptionSimulator simulates Spring Web MVC's request dispatching
 * and its Global Handler Exception Resolver (AOP exception interceptor).
 */
public class ExceptionSimulator {

    private final UserController controller;
    private final GlobalExceptionHandler globalExceptionHandler;

    public ExceptionSimulator() {
        // Spring DI container setup
        UserRegistrationService service = new UserRegistrationService();
        this.controller = new UserController(service);
        this.globalExceptionHandler = new GlobalExceptionHandler();
    }

    /**
     * Simulates Spring's DispatcherServlet dispatching logic,
     * including global interceptors and RestControllerAdvice exception resolving.
     */
    public void simulatePostRequest(UserRequest request) {
        System.out.println(">>> Incoming HTTP POST /api/v1/users/register");
        System.out.println("Payload: { username: \"" + request.getUsername() + "\", age: " + request.getAge() + " }");
        
        ResponseEntity<?> responseEntity;
        try {
            // Call the controller endpoint
            responseEntity = controller.register(request);
        } catch (Exception ex) {
            // Simulation of Spring MVC's AOP Interceptor catching exception globally
            System.out.println("[Spring MVC DispatcherServlet] Intercepted Exception: " + ex.getClass().getName());
            
            // Check if our Advice handler can resolve it
            if (ex instanceof IllegalArgumentException) {
                responseEntity = globalExceptionHandler.handleIllegalArgument((IllegalArgumentException) ex);
            } else {
                // Unhandled fallback to 500 (representing old crash behavior)
                System.out.println("[Spring MVC DispatcherServlet] Fallback to 500 Internal Server Error");
                responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR, "HTML White-label Error Page: " + ex.getMessage());
            }
        }

        // Output formatting to prove REST requirements
        System.out.println("HTTP Response Status: " + responseEntity.getStatus().value() + " " + responseEntity.getStatus().getReasonPhrase());
        Object body = responseEntity.getBody();
        if (body instanceof ErrorResponse) {
            ErrorResponse error = (ErrorResponse) body;
            System.out.println("Response Body (JSON): { \"error\": \"" + error.getError() + "\", \"message\": \"" + error.getMessage() + "\" }");
        } else if (body instanceof User) {
            User user = (User) body;
            System.out.println("Response Body (JSON): { \"username\": \"" + user.getUsername() + "\", \"age\": " + user.getAge() + " }");
        } else {
            System.out.println("Response Body (Raw): " + body);
        }
        System.out.println("--------------------------------------------------------------------------------");
    }

    public static void main(String[] args) {
        ExceptionSimulator simulator = new ExceptionSimulator();

        System.out.println("=== Spring Web MVC AOP Exception Handling Simulator ===\n");

        // Case 1: Valid user registration
        UserRequest validRequest = new UserRequest("john_doe", 20);
        simulator.simulatePostRequest(validRequest);

        // Case 2: Validation failure (Age < 18)
        UserRequest tooYoungRequest = new UserRequest("alex_young", 15);
        simulator.simulatePostRequest(tooYoungRequest);

        // Case 3: Validation failure (Username empty)
        UserRequest emptyUsernameRequest = new UserRequest("", 25);
        simulator.simulatePostRequest(emptyUsernameRequest);
    }
}
