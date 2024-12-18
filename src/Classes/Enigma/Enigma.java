package Classes.Enigma;

public class Enigma {
    private Rotors rotors = new Rotors();
    private Reflector reflector = new Reflector();
    private Plugboard plugboard = new Plugboard();

    public Enigma() {
    }

    public Enigma(Rotors rotors, Reflector reflector, Plugboard plugboard) {
        this.rotors = rotors;
        this.reflector = reflector;
        this.plugboard = plugboard;
    }

    public Rotors getRotors() {
        return rotors;
    }

    public Reflector getReflector() {
        return reflector;
    }

    public Plugboard getPlugboard() {
        return plugboard;
    }

    public String encrypt(String message) {
        StringBuilder encryptedMessage = new StringBuilder();
        for (char c : message.toCharArray()) {
            if (Character.isLetter(c)) {
                c = Character.toUpperCase(c);
                c = plugboard.swap(c);
                c = rotors.rotate(c);
                c = reflector.reflect(c);
                c = rotors.rotateBack(c);
                c = plugboard.swap(c);
                if (Character.isLowerCase(message.charAt(encryptedMessage.length()))) {
                    c = Character.toLowerCase(c);
                }
            }
            encryptedMessage.append(c);
        }
        return encryptedMessage.toString();
    }

    public String decrypt(String message) {
        rotors.resetToInitialPosition();
        return encrypt(message);
    }
}