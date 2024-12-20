package Tests;

import Classes.AESEncrypter;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class AESEncrypterTest {

    private AESEncrypter encrypter;

    @Before
    public void setUp() {
        this.encrypter = new AESEncrypter("donotpromote");
    }

    // Encryption
    @Test
    public void testAESEncryterEncryption() {
        String plainText = ("hello");
        String expectedEncryptedText = "awZqgC5dfdW4WIaxW0aivQ==";
        String encryptionResult = this.encrypter.encrypt(plainText);

        // Validation
        assertEquals(expectedEncryptedText, encryptionResult);
    }

    @Test
    public void testAESEncryterEncryptionEmptyString() {
        String plainText = ("");
        String expectedEncryptedText = "OBSlF6+LdyFe0QFQ0OUjMw==";
        String encryptionResult = this.encrypter.encrypt(plainText);

        // Validation
        assertEquals(expectedEncryptedText, encryptionResult);
    }

    // Decryption
    @Test
    public void testAESEncryterDecryption() {
        String encryptedText = ("awZqgC5dfdW4WIaxW0aivQ==");
        String expectedPlainText = "hello";
        String decryptionResult = this.encrypter.decrypt(encryptedText);

        // Validation
        assertEquals(expectedPlainText, decryptionResult);
    }

    @Test
    public void testAESEncryterDecryptionEmptyString() {
        String encryptedText = ("OBSlF6+LdyFe0QFQ0OUjMw==");
        String expectedPlainText = "";
        String decryptionResult = this.encrypter.decrypt(encryptedText);

        // Validation
        assertEquals(expectedPlainText, decryptionResult);
    }
}
