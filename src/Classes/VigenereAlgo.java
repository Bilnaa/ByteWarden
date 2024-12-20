package Classes;


public class VigenereAlgo {


        String plainText = "TEST";

        String key = "LONGKEY";
        // Call encryption function and display the result
        String encryptedText = encrypt(plainText, key);


        // Call decryption function and display the result
        String decryptedText = decrypt(encryptedText, key);


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
        if (!isValidKey(key)) {
            throw new IllegalArgumentException("Key must contain only alphabetic characters.");
        }

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
                char normalizedPi = Character.toLowerCase(pi); // the calculations are based on lowerCase alphabet
                char ki = key.charAt(keyIndex++);


                // encrypt formula
                // normalizedPi - a : converts the character to an index between 0 and 25 (ex : normalizedPi = 'c' (ASCII 99), then 'c' - 'a' = 99-97 = 2)
                // ki - 'a' similarly normalizes the key character to the same 0–25 range.

                // normalizedPi - 'a' and ki - 'a' are added together to combine the plaintext and key in the normalized alphabet range.

                // % 26 ensures the result wraps around if it exceeds 25 (e.g., for 'z').
                // Example: If normalizedPi = 'z' (25) and ki = 'y' (24), then:
                // (25 + 24) % 26 = 49 % 26 = 23 (which corresponds to 'x').


                // + 'a' converts the normalized result (0–25) back into a valid ASCII character (alphabetical range).
                // Example: If the result is 2, adding 'a' (97) gives 99, which corresponds to 'c' in ASCII.
               // (char) : Casts the result back to a character
                char ci = (char) (((normalizedPi - 'a' + ki - 'a') % 26) + 'a');
                // + 'a' converts the normalized result (0 - 25) back into a valid ASCII character (97-122)
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
        if (!isValidKey(key)) {
            throw new IllegalArgumentException("Key must contain only alphabetic characters.");
        }

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
    public static String generateFullKey(String text, String key) {
        StringBuilder fullKey = new StringBuilder();
        int keyLength = key.length();
        int keyIndex = 0;

        // go threw the text length
        for (int i = 0; i < text.length(); i++) {
            char currentChar = text.charAt(i);
            if (Character.isLetter(currentChar)) {
                // if keyLength > KeyIndex : troncate the keyLength to keyIndex
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
