package com.teaching.platform.dto.request;

import com.teaching.common.core.IRequest;
import com.teaching.common.exception.ServiceException;
import com.teaching.common.helper.EncryptHelper;
import com.teaching.platform.kern.CodeMSG;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Objects;

/**
 * @Description：
 * @Author：sacher
 * @Create：2023/10/6 21:16
 **/
public class UpdatePasswordRequest implements IRequest {
    @Schema(title = "原密码（加密）")
    public String originalPassword;

    @Schema(title = "新密码（加密）")
    public String password;

    @Schema(title = "确认新密码（加密）")
    public String passwordAgain;


    @Override
    public void verify() {
        this.originalPassword = EncryptHelper.decryptByPrivateKey(EncryptHelper.PRI, this.originalPassword);
        this.password = EncryptHelper.decryptByPrivateKey(EncryptHelper.PRI, this.password);
        this.passwordAgain = EncryptHelper.decryptByPrivateKey(EncryptHelper.PRI, this.passwordAgain);
        if(!Objects.equals(this.password,this.passwordAgain)){
            throw new ServiceException(CodeMSG.PASSWORD_AGAIN_ERROR);
        }
    }
}
