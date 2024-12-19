package Classes.Steganography;

public class Text extends Steganography {
    private String stegoText;
    private String content;

    @Override
    public void encode(String message) {
        validateMessage(message);
        validateContent();
        
        String binary = convertToBinary(message);
        this.stegoText = embedMessage(binary);
    }

    private void validateMessage(String message) {
        if (message == null || message.isEmpty()) {
            throw new IllegalArgumentException("Message cannot be empty");
        }
    }

    private void validateContent() {
        if (content == null || content.trim().isEmpty()) {
            throw new IllegalStateException("Content cannot be empty");
        }
    }

    private String convertToBinary(String message) {
        StringBuilder binary = new StringBuilder();
        for (char c : message.toCharArray()) {
            String binaryChar = String.format("%8s", Integer.toBinaryString(c))
                                    .replace(' ', '0');
            binary.append(binaryChar);
        }
        return binary.toString();
    }

    private String embedMessage(String binary) {
        String[] words = content.split("\\s+");
        int requiredSpaces = binary.length();
        
        if (words.length - 1 < requiredSpaces) {
            throw new IllegalStateException(
                "Content is too short to encode the message. Need at least " + 
                (requiredSpaces + 1) + " words, but got " + words.length);
        }

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < words.length; i++) {
            result.append(words[i]);
            
            if (i < binary.length()) {
                // Add either two spaces (for 1) or one space (for 0)
                result.append(binary.charAt(i) == '1' ? "  " : " ");
            } else if (i < words.length - 1) {
                // Add normal space for remaining words
                result.append(" ");
            }
        }
        return result.toString();
    }

    @Override
    public String decode() {
        if (stegoText == null) {
            throw new IllegalStateException("No encoded message found");
        }
    
        StringBuilder binary = new StringBuilder();
        String[] parts = stegoText.split("\\S+");
        
        // Skip first empty part if exists
        for (int i = 1; i < parts.length; i++) {
            if (!parts[i].isEmpty()) {
                binary.append(parts[i].length() > 1 ? "1" : "0");
            }
        }
    
        // Convert binary back to text
        StringBuilder message = new StringBuilder();
        String binaryStr = binary.toString();
        
        for (int i = 0; i + 7 < binaryStr.length(); i += 8) {
            String byteStr = binaryStr.substring(i, i + 8);
            int charCode = Integer.parseInt(byteStr, 2);
            message.append((char) charCode);
        }
    
        return message.toString().trim(); // Add trim() here
    }
    
    @Override
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}