package Tests;

import Classes.Steganography.Text;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;


public class SteganographyTextTest {

    @Test
    public void testEncodeDecode() {
        Text text = new Text();
        text.setContent("This is a simple test content for steganography.");
        
        String message = "Hello";
        text.encode(message);
        String decodedMessage = text.decode();
        
        assertEquals(message, decodedMessage);
    }

    @Test
    public void testEncodeEmptyMessage() {
        Text text = new Text();
        text.setContent("This is a simple test content for steganography.");
        
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            text.encode("");
        });
        
        assertEquals("Message cannot be empty", exception.getMessage());
    }

    @Test
    public void testDecodeWithoutEncoding() {
        Text text = new Text();
        
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            text.decode();
        });
        
        assertEquals("No steganographed text found", exception.getMessage());
    }
}
