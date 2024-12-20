package Tests;

import Classes.PolybSquareEncrypter;
import Classes.PolybSquareLayout;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PolybSquareEncryterTest
{
    private PolybSquareEncrypter horizontalLayoutPolybSquareEncrypter;

    // Horizontal layout

    // Encryption
    @Before
    public void setUp() {
        this.horizontalLayoutPolybSquareEncrypter = new PolybSquareEncrypter(PolybSquareLayout.HORIZONTAL);
    }

    @Test
    public void testPolybSquareEncryterEncryption() {
        String plainText = ("hello");
        String expectedEncryptedText = "3251232353";
        String encryptionResult = this.horizontalLayoutPolybSquareEncrypter.encrypt(plainText);

        // Validation
        assertEquals(expectedEncryptedText, encryptionResult);
    }

    @Test
    public void testPolybSquareEncryterEncryptionEmptyString() {
        String plainText = ("");
        String expectedEncryptedText = "";
        String encryptionResult = this.horizontalLayoutPolybSquareEncrypter.encrypt(plainText);

        // Validation
        assertEquals(expectedEncryptedText, encryptionResult);
    }

    @Test
    public void testPolybSquareEncryterEncryptionW() {
        String plainText = ("w");
        String expectedEncryptedText = "2525";
        String encryptionResult = this.horizontalLayoutPolybSquareEncrypter.encrypt(plainText);

        // Validation
        assertEquals(expectedEncryptedText, encryptionResult);
    }

    // Decryption
    @Test
    public void testPolybSquareEncryterDecryption() {
        String encryptedText = ("3251232353");
        String expectedPlainText = "hello";
        String decryptionResult = this.horizontalLayoutPolybSquareEncrypter.decrypt(encryptedText);

        // Validation
        assertEquals(expectedPlainText, decryptionResult);
    }

    @Test
    public void testPolybSquareEncryterDecryptionEmptyString() {
        String encryptedText = ("");
        String expectedPlainText = "";
        String decryptionResult = this.horizontalLayoutPolybSquareEncrypter.decrypt(encryptedText);

        // Validation
        assertEquals(expectedPlainText, decryptionResult);
    }

    @Test
    public void testPolybSquareEncryterDecryptionW() {
        String encryptedText = ("2525");
        String expectedPlainText = "vv";
        String decryptionResult = this.horizontalLayoutPolybSquareEncrypter.decrypt(encryptedText);

        // Validation
        assertEquals(expectedPlainText, decryptionResult);
    }

    // TODO Vertical Layout
    // TODO ClockWise spiral layout
    // TODO CounterClockWise spiral layout
}
