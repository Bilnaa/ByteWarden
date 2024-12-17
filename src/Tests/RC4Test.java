package Tests;

import org.junit.Test;
import org.junit.Before;
import org.junit.Assert;
import Classes.RC4;

public class RC4Test {
    private RC4 rc4;
    private static final String KEY = "1234";
    private static final String CLEAR_TEXT = "Hello World";
    private static final String ENCRYPTED_TEXT = "01001101 00101100 00011101 11000011 10011100 11001100 01010000 01100010 01111101 01110111 01100100";

    @Before
    public void setUp() {
        rc4 = new RC4();
        rc4.key = KEY;
        rc4.init(KEY);
    }

    @Test
    public void testInit() {
        RC4 testRc4 = new RC4();
        testRc4.init(KEY);
        Assert.assertNotNull("Initialization should not produce a null array", testRc4);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInitWithNullKey() {
        rc4.init(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInitWithEmptyKey() {
        rc4.init("");
    }

    @Test
    public void testEncrypt() {
        String encrypted = rc4.encrypt(CLEAR_TEXT);
        Assert.assertEquals("The encrypted text does not match the expected result",
                ENCRYPTED_TEXT, encrypted);
    }

    @Test
    public void testEncryptEmptyString() {
        try {
            rc4.encrypt("");
            Assert.fail("An empty string should throw an exception");
        } catch (IllegalArgumentException e) {
            Assert.assertTrue(true);
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEncryptNull() {
        rc4.encrypt(null);
    }

    @Test
    public void testDecrypt() {
        String decrypted = rc4.decrypt(ENCRYPTED_TEXT);
        Assert.assertEquals("The decrypted text does not match the original text",
                CLEAR_TEXT, decrypted);
    }

    @Test
    public void testDecryptEmptyString() {
        try {
            rc4.decrypt("");
            Assert.fail("An empty string should throw an exception");
        } catch (IllegalArgumentException e) {
            Assert.assertTrue(true);
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDecryptNull() {
        rc4.decrypt(null);
    }

    @Test
    public void testFullCycle() {
        String encrypted = rc4.encrypt(CLEAR_TEXT);
        rc4.init(KEY); // Reinitialization needed for decryption
        String decrypted = rc4.decrypt(encrypted);
        Assert.assertEquals("The full encryption/decryption cycle failed",
                CLEAR_TEXT, decrypted);
    }

    @Test
    public void testDifferentKeys() {
        String encrypted1 = rc4.encrypt(CLEAR_TEXT);
        rc4.init("5678"); // Using a different key
        String encrypted2 = rc4.encrypt(CLEAR_TEXT);
        Assert.assertNotEquals("Different keys should produce different encryptions",
                encrypted1, encrypted2);
    }

    @Test
    public void testConsistentEncryption() {
        String encrypted1 = rc4.encrypt(CLEAR_TEXT);
        rc4.init(KEY);
        String encrypted2 = rc4.encrypt(CLEAR_TEXT);
        Assert.assertEquals("The same text with the same key should produce the same encryption",
                encrypted1, encrypted2);
    }

    @Test
    public void testLongText() {
        String longText = "This is a longer text that should also work properly with RC4 encryption!";
        String encrypted = rc4.encrypt(longText);
        rc4.init(KEY);
        String decrypted = rc4.decrypt(encrypted);
        Assert.assertEquals("Encryption/decryption of a long text failed",
                longText, decrypted);
    }

    @Test
    public void testSpecialCharacters() {
        String specialText = "Hello! @#$%^&*()_+ World";
        String encrypted = rc4.encrypt(specialText);
        rc4.init(KEY);
        String decrypted = rc4.decrypt(encrypted);
        Assert.assertEquals("Encryption/decryption with special characters failed",
                specialText, decrypted);
    }
}