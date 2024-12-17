package Classes;

import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;

public class MD5 {
    /**
     * Computes the MD5 hash of a string.
     * @param input The string to hash
     * @return The MD5 hash in hexadecimal (32 characters)
     * @throws IllegalArgumentException if input is null
     * @throws RuntimeException if an error occurs during hashing
     */
    public static String hash(String input) {
        if (input == null) {
            throw new IllegalArgumentException("Input cannot be null");
        }

        try {
            // Get an instance of MessageDigest configured for MD5
            // MessageDigest is a class that implements the hashing algorithm
            MessageDigest md = MessageDigest.getInstance("MD5");

            // Convert the input string to a byte array using UTF-8 encoding
            // then compute the MD5 hash of these bytes
            // The result is a 16-byte array (128 bits, standard MD5 size)
            byte[] messageDigest = md.digest(input.getBytes(StandardCharsets.UTF_8));

            // Create a StringBuilder to build the hexadecimal representation
            // It will be more efficient than a simple String for concatenations
            StringBuilder hexString = new StringBuilder();

            // For each byte of the MD5 hash
            for (byte b : messageDigest) {
                // String.format("%02x", b & 0xff) does several things:
                // 1. b & 0xff: converts the byte to an unsigned integer (0-255)
                //    because bytes in Java are signed (-128 to 127)
                // 2. %02x: formats the integer in hexadecimal with 2 positions
                //    - % indicates the start of the format
                //    - 0 indicates to pad with zeros
                //    - 2 indicates the minimum width
                //    - x indicates lowercase hexadecimal notation
                hexString.append(String.format("%02x", b & 0xff));
            }

            // Final conversion of the StringBuilder to a String
            // The result will be a 32-character hexadecimal string
            return hexString.toString();

        } catch (Exception e) {
            // In case of an error (very rare as MD5 is a standard algorithm)
            // we encapsulate the exception in a RuntimeException
            throw new RuntimeException("Error computing MD5 hash", e);
        }
    }
}