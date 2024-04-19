package com.hwann.marketmate.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import org.springframework.stereotype.Component;

@Component
public class CryptoUtil {

    private static final String AES = "AES";
    private final SecretKeySpec secretKeySpec;

    public CryptoUtil() {
        // 키 초기화 로직
        String key = "oingisprettyintheworld1234567890";
        byte[] decodedKey = Base64.getDecoder().decode(key);
        this.secretKeySpec = new SecretKeySpec(decodedKey, 0, decodedKey.length, AES);
    }

    public String encrypt(String data) throws Exception {
        Cipher cipher = Cipher.getInstance(AES);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        byte[] encrypted = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encrypted);
    }

    public String decrypt(String encryptedData) throws Exception {
        Cipher cipher = Cipher.getInstance(AES);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        byte[] original = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
        return new String(original);
    }
}
