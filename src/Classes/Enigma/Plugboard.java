package Classes.Enigma;

import java.util.HashMap;
import java.util.Map;

public class Plugboard {
    private Map<Character, Character> connections; // Map to store the letter pairs for swapping

    public Plugboard() {
        connections = new HashMap<>(); // Initialize the connections map
    }

    /**
     * Connects two letters in the plugboard
     * @param a First letter to connect
     * @param b Second letter to connect
     * @throws IllegalArgumentException if letters are already connected
     */
    public void connect(char a, char b) {
        a = Character.toUpperCase(a); // Convert to uppercase for consistency
        b = Character.toUpperCase(b);
        
        if (connections.containsKey(a) || connections.containsKey(b)) {
            throw new IllegalArgumentException("Letters are already connected"); // Check if letters are already connected
        }
        
        connections.put(a, b); // Create bidirectional connection
        connections.put(b, a);
    }

    /**
     * Swaps a letter according to plugboard connections
     * @param c Letter to swap
     * @return Swapped letter if connection exists, original letter otherwise
     */
    public char swap(char c) {
        c = Character.toUpperCase(c); // Convert to uppercase for consistency
        return connections.getOrDefault(c, c); // Swap the letter if connection exists
    }
}