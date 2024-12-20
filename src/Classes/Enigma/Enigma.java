package Classes.Enigma;

import Classes.EncryptionAlgorithm;

public class Enigma implements EncryptionAlgorithm {
    private Rotors rotors = new Rotors(); // Rotors component of the Enigma machine
    private Reflector reflector = new Reflector(); // Reflector component of the Enigma machine
    private Plugboard plugboard = new Plugboard(); // Plugboard component of the Enigma machine

    public Enigma() {
        // Default constructor
    }

    public Enigma(Rotors rotors, Reflector reflector, Plugboard plugboard) {
        // Constructor with parameters to initialize the components
        this.rotors = rotors;
        this.reflector = reflector;
        this.plugboard = plugboard;
    }

    @Override
    public void init(String key) {
        // Initialize the Enigma machine with the given key
        // This could involve setting the initial positions of the rotors, etc.
    }

    @Override
    public String encrypt(String message) {
        StringBuilder encryptedMessage = new StringBuilder(); // StringBuilder to build the encrypted message
        for (char c : message.toCharArray()) { // Iterate through each character in the message
            if (Character.isLetter(c)) { // Process only letters
                c = Character.toUpperCase(c); // Convert character to uppercase
                c = plugboard.swap(c); // Swap character using the plugboard
                c = rotors.rotate(c); // Rotate character through the rotors
                c = reflector.reflect(c); // Reflect character using the reflector
                c = rotors.rotateBack(c); // Rotate character back through the rotors
                c = plugboard.swap(c); // Swap character again using the plugboard
                if (Character.isLowerCase(message.charAt(encryptedMessage.length()))) {
                    c = Character.toLowerCase(c); // Convert back to lowercase if original character was lowercase
                }
            }
            encryptedMessage.append(c); // Append the processed character to the encrypted message
        }
        return encryptedMessage.toString(); // Return the final encrypted message
    }

    @Override
    public String decrypt(String message) {
        rotors.resetToInitialPosition(); // Reset rotors to their initial position
        return encrypt(message); // Decrypt by re-encrypting the message
    }

    public Rotors getRotors() {
        return rotors; // Getter for rotors
    }

    public Reflector getReflector() {
        return reflector; // Getter for reflector
    }

    public Plugboard getPlugboard() {
        return plugboard; // Getter for plugboard
    }
}