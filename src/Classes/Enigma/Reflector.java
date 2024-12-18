package Classes.Enigma;

public class Reflector {
    private final String reflector = "YRUHQSLDPXNGOKMIEBFZCWVJAT"; // Reflector wiring

    public Reflector() {
    }

    public String getReflector() {
        return reflector; // Return the reflector wiring
    }

    public char reflect(char c) {
        return reflector.charAt(c - 'A'); // Reflect the character
    }
}
