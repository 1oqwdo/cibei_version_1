package cibei.service;

import cibei.dto.RegisterRequest;
import cibei.dto.Result;

public interface AuthService {

    /** 发送短信验证码 */
    Result<Void> sendCode(String phone);

    /** 用户注册 */
    Result<Void> register(RegisterRequest req);
}
