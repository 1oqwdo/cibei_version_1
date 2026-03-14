package cibei.dto;

import lombok.Data;

/**
 * 注册请求参数
 */
@Data
public class RegisterRequest {

    /** 手机号，必填 */
    private String phone;

    /** 短信验证码，必填 */
    private String code;

    /** 密码，必填，6-20位 */
    private String password;

    /** 确认密码，必填 */
    private String confirmPassword;

    /** 是否同意协议，必须为 true */
    private Boolean agreed;
}
