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
        Assert.assertNotNull("L'initialisation ne devrait pas produire un tableau nul", testRc4);
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
        Assert.assertEquals("Le texte chiffré ne correspond pas au résultat attendu",
                ENCRYPTED_TEXT, encrypted);
    }

    @Test
    public void testEncryptEmptyString() {
        try {
            rc4.encrypt("");
            Assert.fail("Une chaîne vide devrait lever une exception");
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
        Assert.assertEquals("Le texte déchiffré ne correspond pas au texte original",
                CLEAR_TEXT, decrypted);
    }

    @Test
    public void testDecryptEmptyString() {
        try {
            rc4.decrypt("");
            Assert.fail("Une chaîne vide devrait lever une exception");
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
        rc4.init(KEY); // Réinitialisation nécessaire pour le déchiffrement
        String decrypted = rc4.decrypt(encrypted);
        Assert.assertEquals("Le cycle complet de chiffrement/déchiffrement a échoué",
                CLEAR_TEXT, decrypted);
    }

    @Test
    public void testDifferentKeys() {
        String encrypted1 = rc4.encrypt(CLEAR_TEXT);
        rc4.init("5678"); // Utilisation d'une clé différente
        String encrypted2 = rc4.encrypt(CLEAR_TEXT);
        Assert.assertNotEquals("Des clés différentes devraient produire des chiffrements différents",
                encrypted1, encrypted2);
    }

    @Test
    public void testConsistentEncryption() {
        String encrypted1 = rc4.encrypt(CLEAR_TEXT);
        rc4.init(KEY);
        String encrypted2 = rc4.encrypt(CLEAR_TEXT);
        Assert.assertEquals("Le même texte avec la même clé devrait produire le même chiffrement",
                encrypted1, encrypted2);
    }

    @Test
    public void testLongText() {
        String longText = "This is a longer text that should also work properly with RC4 encryption!";
        String encrypted = rc4.encrypt(longText);
        rc4.init(KEY);
        String decrypted = rc4.decrypt(encrypted);
        Assert.assertEquals("Le chiffrement/déchiffrement d'un texte long a échoué",
                longText, decrypted);
    }

    @Test
    public void testSpecialCharacters() {
        String specialText = "Hello! @#$%^&*()_+ World";
        String encrypted = rc4.encrypt(specialText);
        rc4.init(KEY);
        String decrypted = rc4.decrypt(encrypted);
        Assert.assertEquals("Le chiffrement/déchiffrement avec caractères spéciaux a échoué",
                specialText, decrypted);
    }
}