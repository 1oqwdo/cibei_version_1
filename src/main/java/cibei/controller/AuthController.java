package cibei.controller;

import cibei.dto.RegisterRequest;
import cibei.dto.Result;
import cibei.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * POST /api/auth/send-code
     */
    @PostMapping("/send-code")
    public Result<Void> sendCode(@RequestBody java.util.Map<String, String> body) {
        String phone = body.get("phone");
        if (phone == null || phone.isBlank()) {
            return Result.fail(400, "手机号不能为空");
        }
        return authService.sendCode(phone);
    }

    /**
     * POST /api/auth/register
     */
    @PostMapping("/register")
    public Result<Void> register(@RequestBody RegisterRequest req) {
        if (req == null) {
            return Result.fail(400, "请求参数不能为空");
        }
        return authService.register(req);
    }
}
