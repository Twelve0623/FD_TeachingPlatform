package com.teaching.platform.service;

import com.teaching.common.core.BaseService;
import com.teaching.common.helper.RedisHelper;
import com.teaching.common.helper.SnowIdHelper;
import com.teaching.platform.core.LoginCodeEnum;
import com.teaching.platform.core.LoginCodeProperties;
import com.teaching.platform.dto.response.CaptchaResponse;
import com.teaching.platform.kern.IConstant;
import com.wf.captcha.base.Captcha;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;


/**
 * @Description：
 * @Author：sacher
 * @Create：2023/5/4 17:35
 **/
@Service
public class LoginService extends BaseService {

    @Resource
    private RedisHelper redisHelper;

    @Resource
    private LoginCodeProperties loginCodeProperties;


    public CaptchaResponse getCode() {
        CaptchaResponse captchaResponse = new CaptchaResponse();
        // 获取运算的结果
        Captcha captcha = loginCodeProperties.getCaptcha();
        String uuid = SnowIdHelper.uuid();
        //当验证码类型为 arithmetic时且长度 >= 2 时，captcha.text()的结果有几率为浮点型
        String captchaValue = captcha.text();
        if (captcha.getCharType() - 1 == LoginCodeEnum.arithmetic.ordinal() && captchaValue.contains(".")) {
            captchaValue = captchaValue.split("\\.")[0];
        }
        // 保存
        redisHelper.set(IConstant.Redis.REDIS_LOGIN_CODE + uuid, captchaValue, loginCodeProperties.loginCode.expiration);
        // 验证码信息
        captchaResponse.img = captcha.toBase64();
        captchaResponse.uuid = uuid;
        return captchaResponse;
    }
}
