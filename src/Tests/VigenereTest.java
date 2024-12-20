package Tests;

import Classes.VigenereAlgo;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class VigenereTest {

    private VigenereAlgo vigenere;

    @Before
    public void setUp() {
        vigenere = new VigenereAlgo();
    }

    @Test
    public void testEncrypt_basic() {
        String plainText = "HELLO";
        String key = "KEY";
        vigenere.setKey(key);
        String expectedCipherText = "RIJVS";
        assertEquals(expectedCipherText, vigenere.encrypt(plainText));
    }

    @Test
    public void testDecrypt_basic() {
        String cipherText = "RIJVS";
        String key = "KEY";
        vigenere.setKey(key);
        String expectedPlainText = "HELLO";
        assertEquals(expectedPlainText, vigenere.decrypt(cipherText));
    }

    @Test
    public void testEncrypt_withLowercase() {
        String plainText = "hello";
        String key = "key";
        vigenere.setKey(key.toUpperCase());
        String expectedCipherText = "RIJVS";
        assertEquals(expectedCipherText, vigenere.encrypt(plainText.toUpperCase()));
    }

    @Test
    public void testDecrypt_withLowercase() {
        String cipherText = "rijvs";
        String key = "key";
        vigenere.setKey(key.toUpperCase());
        String expectedPlainText = "HELLO";
        assertEquals(expectedPlainText, vigenere.decrypt(cipherText.toUpperCase()));
    }

    @Test
    public void testEncrypt_emptyString() {
        String plainText = "";
        String key = "KEY";
        vigenere.setKey(key);
        assertEquals("", vigenere.encrypt(plainText));
    }

    @Test
    public void testDecrypt_emptyString() {
        String cipherText = "";
        String key = "KEY";
        vigenere.setKey(key);
        assertEquals("", vigenere.decrypt(cipherText));
    }

    @Test
    public void testGenerateFullKey() {
        String text = "TEST";
        String key = "LONGKEY";
        String expectedKey = "LONG"; // Must be exactly the length of "TEST"
        String result = VigenereAlgo.generateFullKey(text, key);

        assertEquals(expectedKey, result);
    }

    @Test
    public void testEncrypt_keyLongerThanText() {
        String plainText = "TEST";
        String key = "LONGKEY";
        vigenere.setKey(key);
        String expectedCipherText = "ESFZ";
        String result = vigenere.encrypt(plainText);

        assertEquals(expectedCipherText, result);
    }

    @Test
    public void testDecrypt_keyLongerThanText() {
        String cipherText = "ESFZ";
        String key = "LONGKEY";
        vigenere.setKey(key);
        String expectedPlainText = "TEST";
        assertEquals(expectedPlainText, vigenere.decrypt(cipherText));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEncrypt_nonAlphabeticKey() {
        String plainText = "HELLO";
        String key = "K3Y!"; // Non-alphabetic characters in key
        vigenere.setKey(key);
        vigenere.encrypt(plainText);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDecrypt_nonAlphabeticKey() {
        String cipherText = "RIJVS";
        String key = "K3Y!"; // Non-alphabetic characters in key
        vigenere.setKey(key);
        vigenere.decrypt(cipherText);
    }
}