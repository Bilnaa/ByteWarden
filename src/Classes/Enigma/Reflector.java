package Classes.Enigma;

public class Reflector {
    private final String reflector = "YRUHQSLDPXNGOKMIEBFZCWVJAT";

    public Reflector() {
    }

    public String getReflector() {
        return reflector;
    }

    public char reflect(char c) {
        return reflector.charAt(c - 'A');
    }
}
