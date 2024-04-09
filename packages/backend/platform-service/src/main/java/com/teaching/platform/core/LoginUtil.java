
package com.teaching.platform.core;

import com.teaching.common.core.RequestContextThreadHolder;
import com.teaching.common.helper.EncryptHelper;
import com.teaching.common.helper.StringHelper;

public class LoginUtil {


    public static String adminPwd(String password){
        return EncryptHelper.cryptogram(EncryptHelper.md5(password));
    }


    public static Long getUserId(){
        String uid = RequestContextThreadHolder.get().getSession().uid;
        return Long.parseLong(StringHelper.defaultIfBlank(uid,"0L"));
    }
}
