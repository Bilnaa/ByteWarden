package Classes;
import java.util.Scanner;

public class VigenereAlgo {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Ask user to type their phrase
        System.out.print("Enter a phrase to encrypt: ");
        String plainText = scanner.nextLine();

        // Ask user to type their key and validate it
        String key;
        while (true) {
            System.out.print("Enter your key (no numbers allowed): ");
            key = scanner.nextLine();
            if (isValidKey(key)) {
                break;
            } else {
                System.out.println("Invalid key. The key must not contain numbers. Please try again.");
            }
        }

        // Call encryption function and display the result
        String encryptedText = encrypt(plainText, key);
        System.out.println("Encrypted text: " + encryptedText);

        // Call decryption function and display the result
        String decryptedText = decrypt(encryptedText, key);
        System.out.println("Decrypted text: " + decryptedText);

        scanner.close();
    }

    // Validate that the key does not contain numbers
    private static boolean isValidKey(String key) {
        for (char c : key.toCharArray()) {
            if (Character.isDigit(c)) {
                return false; // Reject if the key contains any digit
            }
        }
        return true; // Key is valid if it contains no digits
    }

    // Encrypt the plaintext using the Vigenere cipher algorithm
    public static String encrypt(String plainText, String key) {
        StringBuilder encryptedText = new StringBuilder();

        // Clean the key to contain only alphabetical characters
        key = cleanKey(key);
        // Generate a full-length key that matches the length of the plaintext
        key = generateFullKey(plainText, key);

        for (int i = 0, keyIndex = 0; i < plainText.length(); i++) {
            char pi = plainText.charAt(i);

            // Only encrypt alphabetic characters
            if (Character.isLetter(pi)) {
                boolean isUpperCase = Character.isUpperCase(pi);
                char normalizedPi = Character.toLowerCase(pi);
                char ki = key.charAt(keyIndex++);

                // Encryption formula: Ci = (Pi + Ki) mod 26
                char ci = (char) (((normalizedPi - 'a' + ki - 'a') % 26) + 'a');
                encryptedText.append(isUpperCase ? Character.toUpperCase(ci) : ci);
            } else {
                // Keep non-alphabetic characters unchanged
                encryptedText.append(pi);
            }
        }

        return encryptedText.toString();
    }

    // Decrypt the ciphertext using the Vigenere cipher algorithm
    public static String decrypt(String encryptedText, String key) {
        StringBuilder decryptedText = new StringBuilder();

        // Clean the key to contain only alphabetical characters
        key = cleanKey(key);
        // Generate a full-length key that matches the length of the ciphertext
        key = generateFullKey(encryptedText, key);

        for (int i = 0, keyIndex = 0; i < encryptedText.length(); i++) {
            char ci = encryptedText.charAt(i);

            // Only decrypt alphabetic characters
            if (Character.isLetter(ci)) {
                boolean isUpperCase = Character.isUpperCase(ci);
                char normalizedCi = Character.toLowerCase(ci);
                char ki = key.charAt(keyIndex++);

                // Decryption formula: Pi = (Ci - Ki + 26) mod 26
                char pi = (char) (((normalizedCi - ki + 26) % 26) + 'a');
                decryptedText.append(isUpperCase ? Character.toUpperCase(pi) : pi);
            } else {
                // Keep non-alphabetic characters unchanged
                decryptedText.append(ci);
            }
        }

        return decryptedText.toString();
    }

    // Generate a full-length key matching the text length, ignoring non-alphabetic characters
    private static String generateFullKey(String text, String key) {
        StringBuilder fullKey = new StringBuilder();
        int keyLength = key.length();
        int keyIndex = 0;

        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) {
                fullKey.append(key.charAt(keyIndex % keyLength));
                keyIndex++;
            }
        }

        return fullKey.toString();
    }

    // Remove non-alphabetical characters from the key
    private static String cleanKey(String key) {
        StringBuilder cleanedKey = new StringBuilder();

        for (char c : key.toCharArray()) {
            if (Character.isLetter(c)) {
                cleanedKey.append(Character.toLowerCase(c));
            }
        }

        return cleanedKey.toString();
    }
}
