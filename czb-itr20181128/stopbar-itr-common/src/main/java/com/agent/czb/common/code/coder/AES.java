package com.agent.czb.common.code.coder;

import com.agent.czb.common.code.Coder;
import com.agent.czb.common.code.KeyGen;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.SecureRandom;


/**
 * 基于AES的编码/解码器
 */
public class AES implements Coder {

    public String getAlgorithm() {
        return "AES";
    }


    public String encode(String data, String key) {
        try {
            String algorithm = getAlgorithm();
            KeyGenerator keygen = KeyGenerator.getInstance(algorithm);
            keygen.init(128, new SecureRandom(key.getBytes()));
            SecretKey secretKey = keygen.generateKey();

            Cipher c = Cipher.getInstance(algorithm);
            c.init(Cipher.ENCRYPT_MODE, secretKey);

            byte[] result = c.doFinal(data.getBytes());
            return new String(Base64.encodeBase64(result));
        } catch (Exception ex) {
            return data;
        }
    }


    public String decode(String data, String key) {
        try {
            byte[] result = Base64.decodeBase64(data.getBytes());

            String algorithm = getAlgorithm();
            KeyGenerator keygen = KeyGenerator.getInstance(algorithm);
            keygen.init(128, new SecureRandom(key.getBytes()));
            SecretKey secretKey = keygen.generateKey();

            Cipher c = Cipher.getInstance(algorithm);
            c.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(c.doFinal(result));
        } catch (Exception ex) {
            return data;
        }
    }


    public String createKey() {
        return KeyGen.getKey(8);
    }
}
