package Tests;

import Classes.Steganography.Image;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class SteganographyImageTest {

    private static final String IMAGE_PATH = "src/Tests/assets/image.png";
    private static final String IMAGE_PATH_SMALLEST_PNG = "src/Tests/assets/world_smallest.png";

    @Test
    public void testEncodeDecode() throws IOException {
        Image image = new Image(IMAGE_PATH);
        String message = "Hello";
        image.encode(message);
        String decodedMessage = image.decode();
        assertEquals(message, decodedMessage);
    }

    @Test
    public void testEncodeMessageTooLarge() {
        Image image = new Image(IMAGE_PATH_SMALLEST_PNG);
        String largeMessage = "This message is too large for the image to hold.";
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            image.encode(largeMessage);
        });
        
        assertEquals("Message too large for this image", exception.getMessage());
    }

    @Test
    public void testDecodeWithoutImage() {
        Exception exception = assertThrows(NullPointerException.class, () -> {
            new Image(null);
        });
        assertEquals(null, exception.getMessage());
    }
}
