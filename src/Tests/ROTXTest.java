package Tests;

import org.junit.Test;
import static org.junit.Assert.*;
import Classes.ROTX;

public class ROTXTest {

    // Base test with classic rotation
    @Test
    public void testRotationPositive() {
        String input = "abc";
        int shift = 3;
        String expected = "def"; // "abc" with ROT(3) becomes "def"
        String result = ROTX.encryptROT(input, shift);
        assertEquals("Le texte n'est pas correctement transformé", expected, result);
    }

    // Test with a null function
    @Test
    public void testRotationZero() {
        String input = "abc";
        int shift = 0;
        String expected = "abc"; // ROT(0) don't modify the text
        String result = ROTX.encryptROT(input, shift);
        assertEquals("La rotation nulle n'a pas fonctionné comme prévu", expected, result);
    }

    // Test with negative rotation
    @Test
    public void testRotationNegative() {
        String input = "abc";
        int shift = -3;
        String expected = "xyz"; // "abc" with ROT(-3) becomes "xyz"
        String result = ROTX.encryptROT(input, shift);
        assertEquals("La rotation négative ne donne pas le bon résultat", expected, result);
    }

    // Test with non-alphabetical characters (spaces and symbols)
    @Test
    public void testNonAlphabeticCharacters() {
        String input = "abc 123!";
        int shift = 3;
        String expected = "def 123!"; // Non-alphabetical characters must not be affected
        String result = ROTX.encryptROT(input, shift);
        assertEquals("Les caractères non alphabétiques ne sont pas correctement gérés", expected, result);
    }

    // Test on an empty text
    @Test
    public void testEmptyString() {
        String input = "";
        int shift = 5;
        String expected = ""; // La chaîne vide ne doit pas être modifiée
        String result = ROTX.encryptROT(input, shift);
        assertEquals("La chaîne vide n'a pas été gérée correctement", expected, result);
    }

    // Test de décryptage avec une rotation positive
    @Test
    public void testDecryptionPositive() {
        String input = "def";
        int shift = 3;
        String expected = "abc"; // "def" avec ROT(-3) devient "abc"
        String result = ROTX.decryptROT(input, shift);
        assertEquals("Le texte n'a pas été correctement décrypté", expected, result);
    }

    // Test de décryptage avec une rotation négative
    @Test
    public void testDecryptionNegative() {
        String input = "xyz";
        int shift = -3;
        String expected = "abc"; // "xyz" avec ROT(3) devient "abc"
        String result = ROTX.decryptROT(input, shift);
        assertEquals("Le texte n'a pas été correctement décrypté", expected, result);
    }

    // Test de décryptage avec une rotation nulle
    @Test
    public void testDecryptionZero() {
        String input = "abc";
        int shift = 0;
        String expected = "abc"; // ROT(0) ne modifie pas le texte
        String result = ROTX.decryptROT(input, shift);
        assertEquals("La décryptation nulle n'a pas fonctionné comme prévu", expected, result);
    }

    // Test de décryptage avec des caractères non alphabétiques
    @Test
    public void testDecryptionNonAlphabeticCharacters() {
        String input = "def 123!";
        int shift = 3;
        String expected = "abc 123!"; // Les caractères non alphabétiques ne doivent pas être affectés
        String result = ROTX.decryptROT(input, shift);
        assertEquals("Les caractères non alphabétiques ne sont pas correctement décryptés", expected, result);
    }

    // Test de décryptage avec une chaîne vide
    @Test
    public void testDecryptionEmptyString() {
        String input = "";
        int shift = 5;
        String expected = ""; // La chaîne vide ne doit pas être modifiée
        String result = ROTX.decryptROT(input, shift);
        assertEquals("La chaîne vide n'a pas été décryptée correctement", expected, result);
    }
}
