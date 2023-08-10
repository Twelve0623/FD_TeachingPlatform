
package com.teaching.platform.core;

import com.teaching.common.helper.EncryptHelper;
import com.teaching.common.helper.SpringHelper;

public class LoginUtil {


    public static String adminPwd(String password){
        return EncryptHelper.cryptogram(EncryptHelper.md5(password));
    }

    public static void main(String[] args) {
        System.out.println(adminPwd("19971106"));
    }
}
