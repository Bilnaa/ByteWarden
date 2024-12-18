package Classes.Enigma;

public class Rotors {
    private final Rotor[] rotors; // Array of Rotor objects
    private char[] initialPositions; // Initial positions of the rotors

    private static class Rotor {
        private final String wiring; // Wiring configuration of the rotor
        private int position; // Current position of the rotor
        private final int notchPosition; // Position of the notch

        public Rotor(String wiring, int notchPosition) {
            this.wiring = wiring;
            this.position = 0;
            this.notchPosition = notchPosition;
        }

        public void rotate() {
            position = (position + 1) % 26; // Rotate the rotor by one position
        }

        public boolean isAtNotch() {
            return position == notchPosition; // Check if the rotor is at the notch position
        }

        public char encrypt(char c) {
            int inputPos = (Character.toUpperCase(c) - 'A' + position) % 26; // Calculate input position
            char output = wiring.charAt(inputPos); // Get the output character from the wiring
            return (char)((output - 'A' - position + 26) % 26 + 'A'); // Return the encrypted character
        }

        public char encryptBack(char c) {
            int inputPos = (Character.toUpperCase(c) - 'A' + position) % 26; // Calculate input position
            int outputPos = wiring.indexOf((char)(inputPos + 'A')); // Get the output position from the wiring
            return (char)((outputPos - position + 26) % 26 + 'A'); // Return the decrypted character
        }
    }

    public Rotors() {
        rotors = new Rotor[3]; // Initialize the array of rotors
        rotors[0] = new Rotor("EKMFLGDQVZNTOWYHXUSPAIBRCJ", 16); // Rotor I
        rotors[1] = new Rotor("AJDKSIRUXBLHWTMCQGZNPYFVOE", 4); // Rotor II
        rotors[2] = new Rotor("BDFHJLCPRTXVZNYEIWGAKMUSQO", 21); // Rotor III
    }

    private void step() {
        boolean middleAtNotch = rotors[1].isAtNotch(); // Check if the middle rotor is at the notch

        if (middleAtNotch) {
            rotors[0].rotate(); // Rotate the left rotor
            rotors[1].rotate(); // Rotate the middle rotor
        }

        if (rotors[2].isAtNotch()) {
            rotors[1].rotate(); // Rotate the middle rotor if the right rotor is at the notch
        }

        rotors[2].rotate(); // Always rotate the right rotor
    }

    public char rotate(char c) {
        step(); // Step the rotors
        char result = c;
        for (int i = rotors.length - 1; i >= 0; i--) {
            result = rotors[i].encrypt(result); // Encrypt the character through the rotors
        }
        return result;
    }

    public char rotateBack(char c) {
        char result = c;
        for (int i = 0; i < rotors.length; i++) {
            result = rotors[i].encryptBack(result); // Decrypt the character through the rotors
        }
        return result;
    }

    public void setPositions(char[] positions) {
        if (positions.length != 3) {
            throw new IllegalArgumentException("Must provide exactly 3 positions"); // Validate input
        }
        this.initialPositions = positions.clone(); // Store initial positions
        for (int i = 0; i < 3; i++) {
            rotors[i].position = Character.toUpperCase(positions[i]) - 'A'; // Set rotor positions
        }
    }

    public void resetToInitialPosition() {
        if (initialPositions != null) {
            for (int i = 0; i < 3; i++) {
                rotors[i].position = Character.toUpperCase(initialPositions[i]) - 'A'; // Reset to initial positions
            }
        } else {
            for (int i = 0; i < 3; i++) {
                rotors[i].position = 0; // Reset to position 0 if no initial positions are set
            }
        }
    }
}