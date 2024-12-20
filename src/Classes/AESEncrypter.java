package Classes;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

public class AESEncrypter {

    // Fields
    private SecretKeySpec secretKey;

    // Constructor
    public AESEncrypter(String secretKey) {

        try
        {
            byte[] key;
            key = secretKey.getBytes("UTF-8");

            MessageDigest hashProvider = MessageDigest.getInstance("SHA-1");
            key = hashProvider.digest(key);
            key = Arrays.copyOf(key, 16);
            this.secretKey = new SecretKeySpec(key, "AES");
        }
        catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        // this.secretKey = secretKey;
    }

    // Functions
    public String encrypt(String plainText) {
        try
        {
            //Cipher cipher = Cipher.getInstance("AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, this.secretKey);
            //byte[] encrypted = cipher.doFinal(plainText.getBytes("UTF-8"));
            return Base64.getEncoder().encodeToString(cipher.doFinal(plainText.getBytes("UTF-8")));
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
