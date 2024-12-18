package Classes.Enigma;

import java.util.HashMap;
import java.util.Map;

public class Plugboard {
    // Map to store the letter pairs for swapping
    private Map<Character, Character> connections;

    public Plugboard() {
        connections = new HashMap<>();
    }

    /**
     * Connects two letters in the plugboard
     * @param a First letter to connect
     * @param b Second letter to connect
     * @throws IllegalArgumentException if letters are already connected
     */
    public void connect(char a, char b) {
        // Convert to uppercase for consistency
        a = Character.toUpperCase(a);
        b = Character.toUpperCase(b);
        
        // Check if letters are already connected
        if (connections.containsKey(a) || connections.containsKey(b)) {
            throw new IllegalArgumentException("Letters are already connected");
        }
        
        // Create bidirectional connection
        connections.put(a, b);
        connections.put(b, a);
    }

    /**
     * Swaps a letter according to plugboard connections
     * @param c Letter to swap
     * @return Swapped letter if connection exists, original letter otherwise
     */
    public char swap(char c) {
        c = Character.toUpperCase(c);
        return connections.getOrDefault(c, c);
    }
}