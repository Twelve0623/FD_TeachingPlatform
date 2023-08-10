package com.teaching.common.helper;


import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * @Description：
 * @Author：sacher
 * @Create：2023/8/9 22:45
 **/
public class EncryptHelper {
    private static final String RSA = "RSA";
    private static final String SALT = "65i3vH3d%2BCIx85";
    private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    public static final String PUB = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDg3zTezT6W7PovTxENrXGlO4y5+rM5PfRnLGPIc8Tqlx+KQyyjr3xMktoZBmDvmlrlRKYdRAScjx+yHcRRmPjeA5AnmugT/uNIft7iX+gq2g7Ey0qPf6Jy8c85H12FkgXq8vFXqGxMKU4owChqu/7xqAtBEwFX7s2f3WzorOGyvwIDAQAB";

    public static final String PRI = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAODfNN7NPpbs+i9PEQ2tcaU7jLn6szk99GcsY8hzxOqXH4pDLKOvfEyS2hkGYO+aWuVEph1EBJyPH7IdxFGY+N4DkCea6BP+40h+3uJf6CraDsTLSo9/onLxzzkfXYWSBery8VeobEwpTijAKGq7/vGoC0ETAVfuzZ/dbOis4bK/AgMBAAECgYA3mXitDfpHvtgtElOZGiVbQVoOMdpWcOcZC3Swf09U4YC+4tUKs7z8AeNDBLy7AWMwBMU9vy+Uth6UH6AGvNfrNiIhV/96x08jh0C/nL/WYJ8wHjlFZIb35eR3hmpkprUpiS8J3V2L1VghLCM4fVHuytMJEU1lAdUWBHaJUBp1YQJBAPlP/WcXas8FCQ0YIw6tWDEBpYH5DCntjQ39K6nRRg6JP0WvX+Cn+BSL9cx9qaOAuiXMxi1WUbsBo4mXgcx0SLMCQQDm52KZ7IyEE2me1Foenq8C87TYThtJ4jOa86ZH0/0EDwZS0U66seuDGKwXTWgL4Q0HvqPHS8EETgq2F4nICbvFAkAVUYW1nwb/FMslV38H7kKwThe8+XLU0v9QxulI6CA5dfr3Fnv4VeScvSsThVtQI93HBa+ciNkMSKOXTGFI/liJAkA6K0RdvofPpgxPpPNnD1bLiZWfgF3DiSay/HqBUdhFs3tGVdBdnADWr9p6rFdv1qwvrUkZJe5kJTzxjpB5Qmp5AkAfs0V7b4CEwIDfDbB4tvFmns8rW32tms5r+S1d5C8NY4WS+Ly3E+36Eege/xWw/deeeTugMIk/5BMtrmRDUlH9";


    private static final MessageDigest SHA1;
    private static final MessageDigest MD5;

    static {
        try {
            SHA1 = MessageDigest.getInstance("sha1");
            MD5 = MessageDigest.getInstance("MD5");
        } catch (Exception e){
            throw new RuntimeException("no md5 algorithm error...", e);
        }
    }
    public static byte[] decode64Bytes(String val){
        val = StringHelper.defaultString(val).replaceAll("\\s+", "");
        if(StringHelper.isBlank(val)){
            return new byte[0];
        }
        try {
            return Base64.getDecoder().decode(BytesHelper.utf8Bytes(val));
        }catch (Exception e){
            throw new RuntimeException("decode base64 error...", e);
        }
    }

    /**
     * 构建RSA密钥对
     *
     * @return /
     * @throws NoSuchAlgorithmException /
     */
    public static RsaKeyPair generateKeyPair()  {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(1024);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
            RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();
            String publicKeyString = encode64(rsaPublicKey.getEncoded());
            String privateKeyString = encode64(rsaPrivateKey.getEncoded());
            return new RsaKeyPair(publicKeyString, privateKeyString);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("generateKeyPair is error",e);
        }
    }

    /**
     * RSA密钥对对象
     */
    public static class RsaKeyPair {

        private final String publicKey;
        private final String privateKey;

        public RsaKeyPair(String publicKey, String privateKey) {
            this.publicKey = publicKey;
            this.privateKey = privateKey;
        }

        public String getPublicKey() {
            return publicKey;
        }

        public String getPrivateKey() {
            return privateKey;
        }

    }
    public static String decryptByPrivateKey(String privateKeyText, String text) {
        try {
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec5 = new PKCS8EncodedKeySpec(decode64Bytes(privateKeyText));
            KeyFactory keyFactory = KeyFactory.getInstance(RSA);
            PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec5);
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] result = cipher.doFinal(decode64Bytes(text));
            return new String(result);
        } catch (Exception e) {
            throw new RuntimeException("the decryptByPrivateKey is error",e);
        }
    }

    public static String encode64(byte [] bytes){
        if(null == bytes){
            return StringHelper.EMPTY;
        }
        return BytesHelper.string(Base64.getEncoder().encode(bytes));
    }


    /**
     * 公钥加密
     *
     * @param publicKeyText 公钥
     * @param text 待加密的文本
     * @return /
     */
    public static String encryptByPublicKey(String publicKeyText, String text){
        try {
            X509EncodedKeySpec x509EncodedKeySpec2 = new X509EncodedKeySpec(decode64Bytes(publicKeyText));
            KeyFactory keyFactory = KeyFactory.getInstance(RSA);
            PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec2);
            Cipher cipher = Cipher.getInstance(RSA);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] result = cipher.doFinal(text.getBytes());
            return encode64(result);
        } catch (Exception e) {
            throw new RuntimeException("the encryptByPublicKey is error",e);
        }
    }

    /** 字节数组转 16 进制字符串 **/
    public static String toHex(byte[] bytes) {
        char[] rs = new char[bytes.length * 2];
        for (int i = 0; i < rs.length; i = i + 2) {
            byte b = bytes[i / 2];
            rs[i] = HEX_DIGITS[(b >>> 0x4) & 0xf];
            rs[i + 1] = HEX_DIGITS[b & 0xf];
        }
        return new String(rs);
    }

    /** SHA1 混淆加密 */
    public static String cryptogram(String val) {
        if(StringHelper.isBlank(val)){
            return val;
        }
        synchronized (SHA1) {
            return toHex(SHA1.digest(BytesHelper.utf8Bytes(val + SALT)));
        }
    }
    public static String md5(String val) {
        if(StringHelper.isBlank(val)){
            return val;
        }
        return md5(BytesHelper.utf8Bytes(val));
    }
    /** MD5 加密 **/
    public static String md5(byte[] bytes) {
        if(null == bytes || bytes.length < 1){
            return StringHelper.EMPTY;
        }
        synchronized(MD5) {
            return toHex(MD5.digest(bytes));
        }
    }
}
