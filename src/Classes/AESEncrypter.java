package Classes;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * Class used to encrypt and decrypt string of text using AES method.
 * @remarks A secret key will be needed to instantiate one encrypter.
 */
public class AESEncrypter {

    // Fields
    /**
     * The one secret key required by the AES algorithm.
     * This secret key is based on a user inputted string.
     */
    private SecretKeySpec secretKey;

    // Constructor
    public AESEncrypter(String secretKey) {
        try
        {
            byte[] key;
            // Convert the key string received into an array of byte.
            key = secretKey.getBytes("UTF-8");

            // Get the object required to produce a hash
            MessageDigest hashProvider = MessageDigest.getInstance("SHA-256");
            // store the hash of the secretKey instead of the key directly
            key = hashProvider.digest(key);
            // create the encrypted secretKey for an AES based encrypter
            // based on the hash of the string provided by user.
            this.secretKey = new SecretKeySpec(key, "AES");
        }
        catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    // Functions
    /**
     * Encrypt a plain text using the AES algorithm.
     * @param plainText The text to encrypt.
     * @return The encrypted text.
     */
    public String encrypt(String plainText) {
        try
        {
            // Get the required object to encrypt plain text into the AES encryption format.
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            // Set the cipher in encryption mode
            cipher.init(Cipher.ENCRYPT_MODE, this.secretKey);

            // Get a byte array from the plainText String.
            // Encryption and hashing algorithm work with byte arrays
            // UTF-8 is a character encoding standard used for electronic communication.
            byte[] plainTextBytes = plainText.getBytes("UTF-8");

            // Cipher doFinal tell the encrypter to complete its task.
            byte[] cipheredTextBytes = cipher.doFinal(plainTextBytes);

            // Convert back the byte array to a string to be displayed after the completion of the algorithm.
            // Base64 is a group of binary-to-text encoding schemes that transforms binary data into a sequence of printable characters.
            return Base64.getEncoder().encodeToString(cipheredTextBytes);
        }
        catch (Exception e) {
            // throw an exception if an error occur
            // ex : the instance requested is not defined.
            throw new RuntimeException(e);
        }

    }

    /** Decrypt a text which has been encrypted using the AES algorithm and the same secretKey.
     * @param cipherText The text to decrypt.
     * @return A plain text string.
     */
    public String decrypt(String cipherText) {
        try
        {
            // Get the required object to decrypt text encrypted using the AES encryption format.
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            // set the cypher in decryption mode.
            cipher.init(Cipher.DECRYPT_MODE, this.secretKey);

            // Get a byteArray from the encrypted string.
            byte[] cipherTextBytes = Base64.getDecoder().decode(cipherText);

            // Cipher doFinal tell the encrypter to complete its task.
            return new String(cipher.doFinal(cipherTextBytes),"UTF-8");
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
