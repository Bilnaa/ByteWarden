package Tests;

import Classes.Enigma.Enigma;
import Classes.Enigma.Plugboard;
import Classes.Enigma.Reflector;
import Classes.Enigma.Rotors;

import org.junit.Test;
import static org.junit.Assert.*;

public class EnigmaTest {

    @Test
    public void testPlugboardConnection() {
        Plugboard plugboard = new Plugboard();
        // Test basic connection
        plugboard.connect('A', 'B');
        assertEquals('B', plugboard.swap('A'));
        assertEquals('A', plugboard.swap('B'));
        assertEquals('C', plugboard.swap('C')); // Unconnected letter should return itself
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPlugboardDoubleConnection() {
        Plugboard plugboard = new Plugboard();
        plugboard.connect('A', 'B');
        plugboard.connect('A', 'C'); // Should throw exception
    }

    @Test
    public void testRotorStepping() {
        Rotors rotors = new Rotors();
        char[] initialPositions = {'A', 'A', 'A'};
        rotors.setPositions(initialPositions);
        
        // Test basic rotation
        char result = rotors.rotate('A');
        assertNotEquals('A', result);
        
        // Test multiple characters to ensure proper stepping
        String input = "HELLO";
        StringBuilder output = new StringBuilder();
        for (char c : input.toCharArray()) {
            output.append(rotors.rotate(c));
        }
        assertNotEquals(input, output.toString());
    }

    @Test
    public void testCompleteEnigmaEncryption() {
        // Create Enigma machine components
        Rotors rotors = new Rotors();
        Reflector reflector = new Reflector();
        Plugboard plugboard = new Plugboard();
        
        // Set up plugboard connections
        plugboard.connect('A', 'M');
        plugboard.connect('B', 'N');
        plugboard.connect('C', 'O');
        
        // Create Enigma machine
        Enigma enigma = new Enigma(rotors, reflector, plugboard);
        
        // Test encryption
        String message = "HELLO WORLD";
        String encrypted = enigma.encrypt(message);
        
        // Test that encryption changes the message
        assertNotEquals(message, encrypted);
        
        // Test that decryption returns the original message
        String decrypted = enigma.decrypt(encrypted);
        assertEquals(message, decrypted);
    }

    @Test
    public void testRotorPosition() {
        Rotors rotors = new Rotors();
        char[] positions = {'B', 'C', 'D'};
        rotors.setPositions(positions);
        
        // Encrypt same letter multiple times to test different rotor positions
        String input = "AAAAA";
        StringBuilder output = new StringBuilder();
        for (char c : input.toCharArray()) {
            output.append(rotors.rotate(c));
        }
        
        // Each 'A' should encrypt to a different letter due to rotor movement
        String result = output.toString();
        for (int i = 1; i < result.length(); i++) {
            assertNotEquals(result.charAt(i-1), result.charAt(i));
        }
    }

    @Test
    public void testReflector() {
        Reflector reflector = new Reflector();
        char input = 'A';
        char reflected = reflector.reflect(input);
        
        // Test that reflection is consistent
        assertEquals(reflected, reflector.reflect(input));
        
        // Test that reflection is reciprocal
        assertEquals(input, reflector.reflect(reflected));
    }

    @Test
    public void testLongMessage() {
        Enigma enigma = new Enigma();
        // Set initial rotor positions
        char[] initialPositions = {'A', 'A', 'A'};
        enigma.getRotors().setPositions(initialPositions);

        String longMessage = "THIS IS A VERY LONG MESSAGE THAT SHOULD TEST THE FULL FUNCTIONALITY " +
                "OF THE ENIGMA MACHINE INCLUDING MULTIPLE ROTOR ROTATIONS AND STEPPING";

        String encrypted = enigma.encrypt(longMessage);
        String decrypted = enigma.decrypt(encrypted);

        // Test that encryption changes the message
        assertNotEquals(longMessage, encrypted);

        // Test that decryption recovers the original message
        assertEquals(longMessage, decrypted);
    }

    @Test
    public void testNonAlphabeticCharacters() {
        Enigma enigma = new Enigma();
        String message = "HELLO, WORLD! 123";
        String encrypted = enigma.encrypt(message);
        String decrypted = enigma.decrypt(encrypted);
        
        // Test that non-alphabetic characters remain unchanged
        assertTrue(encrypted.contains(","));
        assertTrue(encrypted.contains("!"));
        assertTrue(encrypted.contains("123"));
        
        // Test that the original message is recovered
        assertEquals(message, decrypted);
    }

    @Test
    public void testCaseSensitivity() {
        Enigma enigma = new Enigma();
        String message = "Hello World";
        String encrypted = enigma.encrypt(message);
        String decrypted = enigma.decrypt(encrypted);
        
        // Test that the case of the original message is preserved
        assertEquals(message, decrypted);
    }
}