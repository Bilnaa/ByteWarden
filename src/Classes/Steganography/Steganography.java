package Classes.Steganography;

import java.io.IOException;

public class Steganography {
    protected String content;

    public void encode(String message) throws IOException {
    }

    public String decode() throws IOException {
        return null;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        if (content == null) {
            throw new IllegalArgumentException("Content cannot be null");
        }
        this.content = content;
    }
}