package Tests;

import Classes.VigenereAlgo;
import org.junit.Test;
import static org.junit.Assert.*;

public class VigenereTest {

    private final VigenereAlgo vigenere = new VigenereAlgo();

    @Test
    public void testEncrypt_basic() {
        String plainText = "HELLO";
        String key = "KEY";
        String expectedCipherText = "RIJVS";
        assertEquals(expectedCipherText, vigenere.encrypt(plainText, key));
    }

    @Test
    public void testDecrypt_basic() {
        String cipherText = "RIJVS";
        String key = "KEY";
        String expectedPlainText = "HELLO";
        assertEquals(expectedPlainText, vigenere.decrypt(cipherText, key));
    }


    @Test
    public void testEncrypt_withLowercase() {
        String plainText = "hello";
        String key = "key";
        String expectedCipherText = "RIJVS";
        assertEquals(expectedCipherText, vigenere.encrypt(plainText.toUpperCase(), key.toUpperCase()));
    }

    @Test
    public void testDecrypt_withLowercase() {
        String cipherText = "rijvs";
        String key = "key";
        String expectedPlainText = "HELLO";
        assertEquals(expectedPlainText, vigenere.decrypt(cipherText.toUpperCase(), key.toUpperCase()));
    }

    @Test
    public void testEncrypt_emptyString() {
        String plainText = "";
        String key = "KEY";
        assertEquals("", vigenere.encrypt(plainText, key));
    }

    @Test
    public void testDecrypt_emptyString() {
        String cipherText = "";
        String key = "KEY";
        assertEquals("", vigenere.decrypt(cipherText, key));
    }

    @Test
    public void testGenerateFullKey() {
        String text = "TEST";
        String key = "LONGKEY";
        String expectedKey = "LONG"; // Doit être exactement de la longueur de "TEST"
        String result = VigenereAlgo.generateFullKey(text, key);

        assertEquals(expectedKey, result);
    }

    @Test
    public void testEncrypt_keyLongerThanText() {
        String plainText = "TEST";
        String key = "LONGKEY";
        String expectedCipherText = "ESFZ";
        String result = VigenereAlgo.encrypt(plainText, key);

        assertEquals(expectedCipherText, result);
    }


    @Test
    public void testDecrypt_keyLongerThanText() {
        String cipherText = "ESFZ";
        String key = "LONGKEY";
        String expectedPlainText = "TEST";
        assertEquals(expectedPlainText, vigenere.decrypt(cipherText, key));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEncrypt_nonAlphabeticKey() {
        String plainText = "HELLO";
        String key = "K3Y!"; // Non-alphabetic characters in key
        vigenere.encrypt(plainText, key);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDecrypt_nonAlphabeticKey() {
        String cipherText = "RIJVS";
        String key = "K3Y!"; // Non-alphabetic characters in key
        vigenere.decrypt(cipherText, key);
    }
}
