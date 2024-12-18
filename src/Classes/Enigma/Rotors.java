package Classes.Enigma;

public class Rotors {
    private final Rotor[] rotors;
    private char[] initialPositions;

    private static class Rotor {
        private final String wiring;
        private int position;
        private final int notchPosition;

        public Rotor(String wiring, int notchPosition) {
            this.wiring = wiring;
            this.position = 0;
            this.notchPosition = notchPosition;
        }

        public void rotate() {
            position = (position + 1) % 26;
        }

        public boolean isAtNotch() {
            return position == notchPosition;
        }

        public char encrypt(char c) {
            int inputPos = (Character.toUpperCase(c) - 'A' + position) % 26;
            char output = wiring.charAt(inputPos);
            return (char)((output - 'A' - position + 26) % 26 + 'A');
        }

        public char encryptBack(char c) {
            int inputPos = (Character.toUpperCase(c) - 'A' + position) % 26;
            int outputPos = wiring.indexOf((char)(inputPos + 'A'));
            return (char)((outputPos - position + 26) % 26 + 'A');
        }
    }

    public Rotors() {
        rotors = new Rotor[3];
        rotors[0] = new Rotor("EKMFLGDQVZNTOWYHXUSPAIBRCJ", 16);
        rotors[1] = new Rotor("AJDKSIRUXBLHWTMCQGZNPYFVOE", 4);
        rotors[2] = new Rotor("BDFHJLCPRTXVZNYEIWGAKMUSQO", 21);
    }

    private void step() {
        boolean middleAtNotch = rotors[1].isAtNotch();

        if (middleAtNotch) {
            rotors[0].rotate();
            rotors[1].rotate();
        }

        if (rotors[2].isAtNotch()) {
            rotors[1].rotate();
        }

        rotors[2].rotate();
    }

    public char rotate(char c) {
        step();
        char result = c;
        for (int i = rotors.length - 1; i >= 0; i--) {
            result = rotors[i].encrypt(result);
        }
        return result;
    }

    public char rotateBack(char c) {
        char result = c;
        for (int i = 0; i < rotors.length; i++) {
            result = rotors[i].encryptBack(result);
        }
        return result;
    }

    public void setPositions(char[] positions) {
        if (positions.length != 3) {
            throw new IllegalArgumentException("Must provide exactly 3 positions");
        }
        this.initialPositions = positions.clone();
        for (int i = 0; i < 3; i++) {
            rotors[i].position = Character.toUpperCase(positions[i]) - 'A';
        }
    }

    public void resetToInitialPosition() {
        if (initialPositions != null) {
            for (int i = 0; i < 3; i++) {
                rotors[i].position = Character.toUpperCase(initialPositions[i]) - 'A';
            }
        } else {
            for (int i = 0; i < 3; i++) {
                rotors[i].position = 0;
            }
        }
    }
}