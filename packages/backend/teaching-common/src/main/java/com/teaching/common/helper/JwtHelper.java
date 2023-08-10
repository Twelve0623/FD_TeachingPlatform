package com.teaching.common.helper;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.teaching.common.core.CommonCode;
import com.teaching.common.exception.ServiceException;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description：
 * @Author：sacher
 * @Create：2023/8/9 22:45
 **/
public class JwtHelper {

    /** token秘钥，请勿泄露，请勿随便修改 backups:Vn18BG0VqEp2WDBO */
    public static final String SECRET = "Vn18BG0VqEp2WDBO";
    /** token 默认过期时间: 30天 */
    public static final long CALENDAR_INTERVAL = 30 * 24 * 60 * 60 * 1000L;

    /** 签名字段， tid, uid, ext, expireTime **/
    public static String signatureJWT(final String tid, final String uid, final String ext, long expire) {
        long time = DateHelper.time();

        // header Map
        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HMAC256");
        map.put("typ", "JWT");

        return JWT.create().withHeader(map)
                .withClaim("tid", tid)
                .withClaim("uid", uid)
                .withClaim("ext", ext)
                .withIssuedAt(DateHelper.now())
                .withExpiresAt(new Date(time + expire))
                .sign(Algorithm.HMAC256(SECRET));
    }

    public static Map<String, Claim> verifyJWT(String signature) {
        DecodedJWT decodeJwt;
        try {
            decodeJwt = JWT.require(Algorithm.HMAC256(SECRET)).build().verify(signature);
        } catch (Exception e) {
            throw new ServiceException(CommonCode.ILLEGAL_TOKEN);
        }
        return decodeJwt.getClaims();
    }

    public static String getTid(String signature) {
        Map<String, Claim> claims = verifyJWT(signature);
        return claims.get("tid").asString();
    }

    public static String getUid(String signature) {
        Map<String, Claim> claims = verifyJWT(signature);
        return claims.get("uid").asString();
    }

    public static String getExt(String signature) {
        Map<String, Claim> claims = verifyJWT(signature);
        return claims.get("ext").asString();
    }

}
