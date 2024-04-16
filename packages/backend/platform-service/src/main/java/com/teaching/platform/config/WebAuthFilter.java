package com.teaching.platform.config;

import com.teaching.common.core.RequestContextSession;
import com.teaching.common.helper.JwtHelper;
import com.teaching.common.helper.StringHelper;
import com.teaching.common.web.IWebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


/**
 * @Description：
 * @Author：sacher
 * @Create：2023/5/6 23:13
 **/
public class WebAuthFilter implements IWebFilter {
    @Override
    public boolean authentication(RequestContextSession session, HttpServletRequest request, HttpServletResponse response) {
        if (!StringHelper.isBlank(session.signature)) {
            //用户ID
            session.uid = JwtHelper.getUid(session.signature.replaceAll("Bearer ",""));
            //拓展字段 暂无
//            session.ext =
        }
        return true;
    }
}
