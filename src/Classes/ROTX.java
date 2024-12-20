package Classes;

public class ROTX implements EncryptionAlgorithm {
    private int x; // The rotation amount for the ROTX algorithm

    public ROTX(int x) {
        this.x = x; // Initialize the rotation amount
    }

    @Override
    public void init(String key) {
        try {
            this.x = Integer.parseInt(key); // Parse the key as an integer
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Key must be an integer for ROTX"); // Throw an exception if the key is not a valid integer
        }
    }

    @Override
    public String encrypt(String data) {
        return encryptROT(data, x); // Encrypt the data using the ROTX algorithm
    }

    @Override
    public String decrypt(String data) {
        return decryptROT(data, x); // Decrypt the data using the ROTX algorithm
    }

    public static String encryptROT(String input, int x) {
        StringBuilder encryptedString = new StringBuilder(); // StringBuilder to build the encrypted string
        x = x % 26; // Ensure the rotation amount is within the range of 0-25
        if (x < 0) {
            x += 26; // Adjust for negative rotation amounts
        }
        for (char character : input.toCharArray()) { // Iterate over each character in the input string
            if (Character.isLetter(character)) { // Check if the character is a letter
                char base = Character.isLowerCase(character) ? 'a' : 'A'; // Determine the base character ('a' for lowercase, 'A' for uppercase)
                char encryptedChar = (char) ((character - base + x) % 26 + base); // Calculate the encrypted character
                encryptedString.append(encryptedChar); // Append the encrypted character to the result
            } else {
                encryptedString.append(character); // Append non-letter characters unchanged
            }
        }
        return encryptedString.toString(); // Return the encrypted string
    }

    public static String decryptROT(String input, int x) {
        return encryptROT(input, -x); // Decrypt by encrypting with the negative rotation amount
    }
}