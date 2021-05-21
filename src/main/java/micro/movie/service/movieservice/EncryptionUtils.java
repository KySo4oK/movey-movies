package micro.movie.service.movieservice;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class EncryptionUtils {
    public String encrypt(String str) {
        try {
            byte[] utf8 = str.getBytes(StandardCharsets.UTF_8);
            Cipher ecipher = Cipher.getInstance("AES");
            SecretKey key = new SecretKeySpec(System.getenv("SECRET_KEY").getBytes(), "AES");
            ecipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] enc = ecipher.doFinal(utf8);
            return new String(Base64.getEncoder().encode(enc));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String decrypt(String str) {
        try {
            Cipher dcipher = Cipher.getInstance("AES");
            SecretKey key = new SecretKeySpec(System.getenv("SECRET_KEY").getBytes(), "AES");
            dcipher.init(Cipher.DECRYPT_MODE, key);
            byte[] dec = Base64.getDecoder().decode(str);
            byte[] utf8 = dcipher.doFinal(dec);
            return new String(utf8, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

