package exception.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import exception.dto.UserRequest;
import exception.entity.User;
import exception.service.UserRegistrationService;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserRegistrationService userService;

    @Autowired
    public UserController(UserRegistrationService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody UserRequest request) {
        User newUser = userService.registerUser(request);
        return ResponseEntity.ok(newUser);
    }
}
