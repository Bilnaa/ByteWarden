package Classes;

public class RC4 {

    private int[] S = new int[256];
    private int[] T = new int[256];
    public String key = "";

    // Initialization of S and T arrays with the key
    public void init(String key) {
        if (key == null || key.isEmpty()) {
            throw new IllegalArgumentException("The key cannot be null or empty");
        }
        this.key = key;

        // Initialize S and T arrays
        for (int i = 0; i < 256; i++) {
            S[i] = i;
            T[i] = key.charAt(i % key.length());
        }

        // Initial permutation of S
        int j = 0;
        for (int i = 0; i < 256; i++) {
            j = (j + S[i] + T[i]) & 0xFF;
            swap(i, j);
        }
    }

    // Encrypt the plaintext
    public String encrypt(String clearText) {
        if (clearText == null || clearText.isEmpty()) {
            throw new IllegalArgumentException("The text to encrypt cannot be null or empty");
        }

        // Reinitialize to ensure a clean state
        init(this.key);

        StringBuilder encrypted = new StringBuilder();
        int i = 0, j = 0;

        // For each character in the plaintext
        for (int n = 0; n < clearText.length(); n++) {
            char c = clearText.charAt(n);
            // Increment i modulo 256
            i = (i + 1) & 0xFF;
            // Increment j modulo 256
            j = (j + S[i]) & 0xFF;
            // Swap S[i] and S[j]
            swap(i, j);
            // Calculate t
            int t = (S[i] + S[j]) & 0xFF;
            // Get the value of S[t]
            int k = S[t];
            // Encrypt the character
            int encryptedByte = c ^ k;

            // Convert to binary with zero padding
            encrypted.append(String.format("%8s",
                            Integer.toBinaryString(encryptedByte & 0xFF))
                    .replace(' ', '0'));

            // Add space except for the last character
            if (n < clearText.length() - 1) {
                encrypted.append(" ");
            }
        }

        return encrypted.toString();
    }

    // Decrypt the encrypted text
    public String decrypt(String encryptedText) {
        if (encryptedText == null || encryptedText.isEmpty()) {
            throw new IllegalArgumentException("The encrypted text cannot be null or empty");
        }

        // Reinitialize to ensure a clean state
        init(this.key);

        StringBuilder decrypted = new StringBuilder();
        String[] binaryStrings = encryptedText.split(" ");
        int i = 0, j = 0;

        // For each byte in the encrypted text
        for (String binaryString : binaryStrings) {
            char c = (char) Integer.parseInt(binaryString, 2);
            i = (i + 1) & 0xFF;
            j = (j + S[i]) & 0xFF;
            swap(i, j);
            int t = (S[i] + S[j]) & 0xFF;
            int k = S[t];
            decrypted.append((char) (c ^ k));
        }

        return decrypted.toString();
    }

    // Swap function to exchange two elements in the S array
    private void swap(int i, int j) {
        int temp = S[i];
        S[i] = S[j];
        S[j] = temp;
    }
}