package Tests;

import org.junit.Test;
import static org.junit.Assert.*;
import Classes.ROTX;

public class ROTXTest {

    // Test de base avec une rotation classique
    @Test
    public void testRotationPositive() {
        String input = "abc";
        int shift = 3;
        String expected = "def"; // "abc" avec ROT(3) devient "def"
        String result = ROTX.encryptROT(input, shift);
        assertEquals("Le texte n'est pas correctement transformé", expected, result);
    }

    // Test avec une rotation nulle
    @Test
    public void testRotationZero() {
        String input = "abc";
        int shift = 0;
        String expected = "abc"; // ROT(0) ne modifie pas le texte
        String result = ROTX.encryptROT(input, shift);
        assertEquals("La rotation nulle n'a pas fonctionné comme prévu", expected, result);
    }

    // Test avec une rotation négative
    @Test
    public void testRotationNegative() {
        String input = "abc";
        int shift = -3;
        String expected = "xyz"; // "abc" avec ROT(-3) devient "xyz"
        String result = ROTX.encryptROT(input, shift);
        assertEquals("La rotation négative ne donne pas le bon résultat", expected, result);
    }

    // Test avec des caractères non alphabétiques (espaces et symboles)
    @Test
    public void testNonAlphabeticCharacters() {
        String input = "abc 123!";
        int shift = 3;
        String expected = "def 123!"; // Les caractères non alphabétiques ne doivent pas être affectés
        String result = ROTX.encryptROT(input, shift);
        assertEquals("Les caractères non alphabétiques ne sont pas correctement gérés", expected, result);
    }



    // Test sur un texte vide
    @Test
    public void testEmptyString() {
        String input = "";
        int shift = 5;
        String expected = ""; // La chaîne vide ne doit pas être modifiée
        String result = ROTX.encryptROT(input, shift);
        assertEquals("La chaîne vide n'a pas été gérée correctement", expected, result);
    }


}
