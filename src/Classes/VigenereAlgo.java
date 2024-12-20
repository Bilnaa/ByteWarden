package Classes;

public class VigenereAlgo implements EncryptionAlgorithm {
    private String key;

    @Override
    public void init(String key) {
        if (!isValidKey(key)) { // Check if the key is valid
            throw new IllegalArgumentException("Key must contain only alphabetic characters.");
        }
        this.key = key; // Initialize the key
    }

    public void setKey(String key) {
        if (!isValidKey(key)) { // Check if the key is valid
            throw new IllegalArgumentException("Key must contain only alphabetic characters.");
        }
        this.key = key; // Set the key
    }

    @Override
    public String encrypt(String plainText) {
        return encryptVigenere(plainText, key); // Encrypt the plain text using Vigenere cipher
    }

    @Override
    public String decrypt(String encryptedText) {
        return decryptVigenere(encryptedText, key); // Decrypt the encrypted text using Vigenere cipher
    }

    private static boolean isValidKey(String key) {
        for (char c : key.toCharArray()) {
            if (Character.isDigit(c)) { // Check if the key contains any digits
                return false;
            }
        }
        return true; // Key is valid if it contains only alphabetic characters
    }

    private static String encryptVigenere(String plainText, String key) {
        StringBuilder encryptedText = new StringBuilder();
        key = cleanKey(key); // Clean the key
        key = generateFullKey(plainText, key); // Generate the full key

        for (int i = 0, keyIndex = 0; i < plainText.length(); i++) {
            char pi = plainText.charAt(i);
            if (Character.isLetter(pi)) { // Check if the character is a letter
                boolean isUpperCase = Character.isUpperCase(pi);
                char normalizedPi = Character.toLowerCase(pi);
                char ki = key.charAt(keyIndex++);

                char ci = (char) (((normalizedPi - 'a' + ki - 'a') % 26) + 'a'); // Encrypt the character
                encryptedText.append(isUpperCase ? Character.toUpperCase(ci) : ci); // Append the encrypted character
            } else {
                encryptedText.append(pi); // Append non-letter characters as is
            }
        }
        return encryptedText.toString(); // Return the encrypted text
    }

    private static String decryptVigenere(String encryptedText, String key) {
        StringBuilder decryptedText = new StringBuilder();
        key = cleanKey(key); // Clean the key
        key = generateFullKey(encryptedText, key); // Generate the full key

        for (int i = 0, keyIndex = 0; i < encryptedText.length(); i++) {
            char ci = encryptedText.charAt(i);
            if (Character.isLetter(ci)) { // Check if the character is a letter
                boolean isUpperCase = Character.isUpperCase(ci);
                char normalizedCi = Character.toLowerCase(ci);
                char ki = key.charAt(keyIndex++);

                char pi = (char) (((normalizedCi - ki + 26) % 26) + 'a'); // Decrypt the character
                decryptedText.append(isUpperCase ? Character.toUpperCase(pi) : pi); // Append the decrypted character
            } else {
                decryptedText.append(ci); // Append non-letter characters as is
            }
        }
        return decryptedText.toString(); // Return the decrypted text
    }

    public static String generateFullKey(String text, String key) {
        StringBuilder fullKey = new StringBuilder();
        int keyLength = key.length();
        int keyIndex = 0;

        for (int i = 0; i < text.length(); i++) {
            char currentChar = text.charAt(i);
            if (Character.isLetter(currentChar)) { // Check if the character is a letter
                fullKey.append(key.charAt(keyIndex % keyLength)); // Append the corresponding key character
                keyIndex++;
            }
        }
        return fullKey.toString(); // Return the full key
    }

    private static String cleanKey(String key) {
        StringBuilder cleanedKey = new StringBuilder();
        for (char c : key.toCharArray()) {
            if (Character.isLetter(c)) { // Check if the character is a letter
                cleanedKey.append(Character.toLowerCase(c)); // Append the lowercase letter
            }
        }
        return cleanedKey.toString(); // Return the cleaned key
    }
}