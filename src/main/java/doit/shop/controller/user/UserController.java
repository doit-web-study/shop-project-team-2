package doit.shop.controller.user;

import doit.shop.controller.user.domain.UserRepository;
import doit.shop.controller.user.dto.UserInfoResponse;
import doit.shop.controller.user.dto.UserLoginRequest;
import doit.shop.controller.user.dto.UserLoginResponse;
import doit.shop.controller.user.dto.UserSignUpRequest;
import doit.shop.controller.user.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController implements UserControllerDocs {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/validate")
    public void checkDuplicateId(@RequestParam String id) {
        userService.checkDuplicatedId(id);
    }

    @PostMapping("/signup")
    public void signUp(@RequestBody UserSignUpRequest userSignUpRequest) {
        userService.signUp(userSignUpRequest);
    }

    @PostMapping("/login")
    public UserLoginResponse login(@RequestBody UserLoginRequest userLoginRequest) {
        return userService.login(userLoginRequest);
    }

    @GetMapping("/{userId}")
    public UserInfoResponse getUserInfo(@PathVariable Long userId) {
        return userService.getUserInfo(userId);
    }
}
