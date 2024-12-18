package Classes.Enigma;

public class Enigma {
    private Rotors rotors = new Rotors(); // Rotors component
    private Reflector reflector = new Reflector(); // Reflector component
    private Plugboard plugboard = new Plugboard(); // Plugboard component

    public Enigma() {
    }

    public Enigma(Rotors rotors, Reflector reflector, Plugboard plugboard) {
        this.rotors = rotors;
        this.reflector = reflector;
        this.plugboard = plugboard;
    }

    public Rotors getRotors() {
        return rotors; // Return the rotors component
    }

    public Reflector getReflector() {
        return reflector; // Return the reflector component
    }

    public Plugboard getPlugboard() {
        return plugboard; // Return the plugboard component
    }

    public String encrypt(String message) {
        StringBuilder encryptedMessage = new StringBuilder();
        for (char c : message.toCharArray()) {
            if (Character.isLetter(c)) {
                c = Character.toUpperCase(c); // Convert to uppercase
                c = plugboard.swap(c); // Swap using plugboard
                c = rotors.rotate(c); // Rotate through rotors
                c = reflector.reflect(c); // Reflect the character
                c = rotors.rotateBack(c); // Rotate back through rotors
                c = plugboard.swap(c); // Swap using plugboard again
                if (Character.isLowerCase(message.charAt(encryptedMessage.length()))) {
                    c = Character.toLowerCase(c); // Convert back to lowercase if needed
                }
            }
            encryptedMessage.append(c); // Append the encrypted character
        }
        return encryptedMessage.toString();
    }

    public String decrypt(String message) {
        rotors.resetToInitialPosition(); // Reset rotors to initial positions
        return encrypt(message); // Decrypt by re-encrypting
    }
}