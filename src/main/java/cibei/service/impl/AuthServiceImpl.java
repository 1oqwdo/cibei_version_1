package cibei.service.impl;

import cibei.dto.RegisterRequest;
import cibei.dto.Result;
import cibei.entity.User;
import cibei.mapper.UserMapper;
import cibei.service.AuthService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 注册/登录业务实现
 * 验证码使用内存 Map 模拟，生产环境替换为 Redis + 短信服务
 */
@Service
public class AuthServiceImpl implements AuthService {

    private final UserMapper userMapper;

    // 模拟验证码存储：phone -> code（生产环境用 Redis + TTL）
    private static final Map<String, String> CODE_STORE = new ConcurrentHashMap<>();

    public AuthServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public Result<Void> sendCode(String phone) {
        if (phone == null || !phone.matches("^1[3-9]\\d{9}$")) {
            return Result.fail(400, "手机号格式不正确");
        }
        // 生产环境：调用短信服务商 API 发送真实验证码
        String code = String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
        CODE_STORE.put(phone, code);
        System.out.println("[DEV] 验证码：" + code); // 开发期打印，上线删除
        return Result.ok(null);
    }

    @Override
    public Result<Void> register(RegisterRequest req) {
        // 1. 参数校验
        if (req.getPhone() == null || req.getPhone().isBlank()) {
            return Result.fail(400, "手机号不能为空");
        }
        if (!req.getPhone().matches("^1[3-9]\\d{9}$")) {
            return Result.fail(400, "手机号格式不正确");
        }
        if (req.getCode() == null || req.getCode().isBlank()) {
            return Result.fail(400, "验证码不能为空");
        }
        if (req.getPassword() == null || req.getPassword().isBlank()) {
            return Result.fail(400, "密码不能为空");
        }
        if (req.getPassword().length() < 6 || req.getPassword().length() > 20) {
            return Result.fail(400, "密码长度须在6-20位之间");
        }
        if (!req.getPassword().equals(req.getConfirmPassword())) {
            return Result.fail(400, "两次密码输入不一致");
        }
        if (req.getAgreed() == null || !req.getAgreed()) {
            return Result.fail(400, "请阅读并同意用户协议和隐私政策");
        }

        // 2. 验证码校验
        String storedCode = CODE_STORE.get(req.getPhone());
        if (storedCode == null || !storedCode.equals(req.getCode())) {
            return Result.fail(400, "验证码错误或已过期");
        }

        // 3. 手机号唯一性校验
        if (userMapper.selectByPhone(req.getPhone()) != null) {
            return Result.fail(409, "该手机号已注册");
        }

        // 4. 密码加密（生产环境使用 BCrypt，此处用简单 hash 占位）
        String encryptedPassword = simpleHash(req.getPassword());

        // 5. 写入用户表
        User user = new User();
        user.setId(UUID.randomUUID().toString().replace("-", ""));
        user.setPhone(req.getPhone());
        user.setPassword(encryptedPassword);
        user.setNickname("匿名用户");
        user.setCreatedAt(new Date());
        userMapper.insert(user);

        // 6. 验证码用完即删
        CODE_STORE.remove(req.getPhone());

        return Result.ok(null);
    }

    /**
     * 占位加密，生产环境替换为 BCryptPasswordEncoder
     */
    private String simpleHash(String password) {
        return Integer.toHexString(password.hashCode());
    }
}
