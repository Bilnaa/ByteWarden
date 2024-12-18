package Classes.Steganography;

public class Text extends Steganography {
    private String stegoText;

    @Override
    public void encode(String message) {
        // Check if the message is null or empty
        if (message == null || message.isEmpty()) {
            throw new IllegalArgumentException("Message cannot be empty");
        }
        
        StringBuilder binary = new StringBuilder();
        // Convert each character of the message to its binary representation
        for (char c : message.toCharArray()) {
            String binaryChar = String.format("%8s", Integer.toBinaryString(c))
                                    .replace(' ', '0'); // Ensure each binary string is 8 bits long
            binary.append(binaryChar);
        }

        StringBuilder result = new StringBuilder();
        // Split the content into words
        String[] words = this.getContent().split("\\s+");
        int binaryIndex = 0;

        // Embed the binary message into the text by adding extra spaces
        for (int i = 0; i < words.length - 1 && binaryIndex < binary.length(); i++) {
            result.append(words[i]);
            result.append(binary.charAt(binaryIndex) == '1' ? "  " : " "); // Add double space for '1' and single space for '0'
            binaryIndex++;
        }
        
        // Append the remaining words
        for (int i = binaryIndex; i < words.length; i++) {
            result.append(words[i]);
            if (i < words.length - 1) result.append(" ");
        }

        // Store the steganographed text
        this.stegoText = result.toString();
    }

    @Override
    public String decode() {
        // Check if there is any steganographed text
        if (this.stegoText == null) {
            throw new IllegalStateException("No steganographed text found");
        }

        StringBuilder binary = new StringBuilder();
        // Split the steganographed text by non-space characters
        String[] words = this.stegoText.split("\\S+");
        
        // Convert spaces back to binary representation
        for (String space : words) {
            if (!space.isEmpty()) {
                binary.append(space.length() > 1 ? "1" : "0"); // Double space is '1', single space is '0'
            }
        }

        StringBuilder message = new StringBuilder();
        // Convert binary string back to characters
        for (int i = 0; i < binary.length() - 7; i += 8) {
            String byteStr = binary.substring(i, i + 8);
            int charCode = Integer.parseInt(byteStr, 2);
            message.append((char) charCode);
        }

        return message.toString();
    }

    @Override
    public String getContent() {
        return super.getContent();
    }
}
