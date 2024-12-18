package Classes.Steganography;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Image extends Steganography {
    private BufferedImage image;
    private String imagePath;
    
    public Image(String imagePath) {
        this.imagePath = imagePath;
        try {
            this.image = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void encode(String message) {
        if (image == null || message == null) {
            throw new IllegalArgumentException("Image or message is null");
        }

        // Add message length at the beginning
        message = message.length() + ":" + message;
        
        // Convert message to binary
        byte[] messageBytes = message.getBytes();
        int messageLength = messageBytes.length;
        
        // Check if image can hold the message
        if (messageLength * 8 > image.getWidth() * image.getHeight()) {
            throw new IllegalArgumentException("Message too large for this image");
        }

        int messageIndex = 0;
        int bitIndex = 0;

        // Iterate through image pixels
        for (int y = 0; y < image.getHeight() && messageIndex < messageLength; y++) {
            for (int x = 0; x < image.getWidth() && messageIndex < messageLength; x++) {
                int pixel = image.getRGB(x, y);
                
                if (bitIndex == 8) {
                    messageIndex++;
                    bitIndex = 0;
                }
                
                if (messageIndex < messageLength) {
                    // Get current byte from message
                    byte currentByte = messageBytes[messageIndex];
                    
                    // Get current bit from byte
                    int messageBit = (currentByte >> (7 - bitIndex)) & 1;
                    
                    // Modify blue channel
                    int blue = pixel & 0xff;
                    blue = (blue & 0xfe) | messageBit;
                    
                    // Update pixel
                    pixel = (pixel & 0xffffff00) | blue;
                    image.setRGB(x, y, pixel);
                    
                    bitIndex++;
                }
            }
        }
        
        // Save encoded image
        try {
            String outputPath = imagePath.substring(0, imagePath.lastIndexOf('.')) + "_encoded.png";
            ImageIO.write(image, "png", new File(outputPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String decode() {
        if (image == null) {
            throw new IllegalArgumentException("No image loaded");
        }

        StringBuilder binary = new StringBuilder();
        StringBuilder message = new StringBuilder();
        int count = 0;
        
        // First, extract the message length
        StringBuilder lengthStr = new StringBuilder();
        boolean foundDelimiter = false;
        
        outer:
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int pixel = image.getRGB(x, y);
                int blue = pixel & 0xff;
                binary.append(blue & 1);
                
                if (binary.length() == 8) {
                    int ascii = Integer.parseInt(binary.toString(), 2);
                    char character = (char) ascii;
                    binary.setLength(0);
                    
                    if (!foundDelimiter) {
                        if (character == ':') {
                            foundDelimiter = true;
                            count = Integer.parseInt(lengthStr.toString());
                        } else {
                            lengthStr.append(character);
                        }
                    } else {
                        message.append(character);
                        if (message.length() == count) {
                            break outer;
                        }
                    }
                }
            }
        }
        
        return message.toString();
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }
}