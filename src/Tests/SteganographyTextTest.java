package Tests;

import Classes.Steganography.Text;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class SteganographyTextTest {
    private Text steganography;
    private static final String LOREM_IPSUM = String.join(" ",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus porttitor augue dignissim mauris tincidunt efficitur. Nunc gravida ante vel iaculis interdum. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. " +
                    "Ut a iaculis dolor. Sed sed aliquet justo, sit amet malesuada " +
                    "ex. Fusce ullamcorper vehicula tristique. Maecenas sit amet nulla viverra, " +
                    "consectetur erat ut, porta libero. Sed lorem mauris, hendrerit in aliquam rhoncus, " +
                    "imperdiet sit amet dolor. Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
                    "Suspendisse vel ultrices ipsum. Nam risus velit, sollicitudin eget sodales eget, " +
                    "convallis non dolor. Suspendisse in mollis diam. Suspendisse vulputate posuere maximus. " +
                    "Fusce dignissim elit leo, vitae placerat ex semper et. Mauris fermentum ligula at felis semper viverra." +
                    " Duis a neque in turpis molestie semper. Duis arcu felis, porta vitae mauris sit amet, facilisis faucibus mauris. Phasellus viverra massa felis, non iaculis neque sagittis at. Nunc nisl lacus, lobortis in malesuada id, pharetra at justo. Sed non turpis ac erat iaculis tincidunt. Proin scelerisque pulvinar nulla non pretium. Donec eget neque a sem placerat tincidunt. Fusce ante ipsum, rhoncus pretium sapien ut, pellentesque bibendum nulla. Phasellus ac accumsan magna. Suspendisse egestas, eros ac feugiat maximus, nibh lorem scelerisque tellus, sed iaculis nulla velit at augue. Curabitur sit amet convallis sapien.Cras cursus ante quis eros tincidunt, et convallis elit faucibus. Cras eget laoreet lacus. Donec dui massa, sagittis eget ligula sed, laoreet suscipit nulla. Suspendisse eget posuere mauris, non faucibus justo. Integer dignissim consectetur ultrices. Suspendisse luctus, enim quis condimentum congue, nisl tortor dapibus nibh, id gravida nulla enim eu felis. Duis rutrum est sed bibendum efficitur. Ut elit elit, ornare eget neque et, vulputate vulputate nunc. Mauris consequat rhoncus risus. Sed imperdiet orci sollicitudin, convallis ligula in, gravida mi. Mauris pharetra lorem vitae sagittis condimentum. Aliquam erat volutpat. Donec cursus quis sem sit amet fringilla. Sed in ante justo. Vivamus tristique diam sit amet porta lobortis. Sed purus odio, egestas vel risus eu, laoreet tempus enim. Nullam vel bibendum nisi, eget dapibus felis. Interdum et malesuada fames ac ante ipsum primis in faucibus. Fusce mattis sit amet nunc sed maximus. Mauris tincidunt non sapien eu eleifend. Nam eget felis a quam pulvinar sodales. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Pellentesque pharetra volutpat tortor, et bibendum libero mattis idSed in varius felis, vitae pellentesque leo. In hac habitasse platea dictumst. Phasellus in ullamcorper quam. Etiam a ultricies ex. Sed nec nisl in ante semper mollis vel vel mi. Quisque non turpis neque. Donec est eros, efficitur ut lectus sed, viverra pulvinar lectus. Fusce a porta leo. Curabitur vitae erat congue, semper nisl quis, cursus leo. Pellentesque blandit ac nunc ac pretiumNulla sit amet vulputate nunc. Donec et turpis eu mauris tincidunt vulputate et nec dui. Vestibulum quis fermentum arcu. Nulla luctus facilisis turpis, et rutrum ligula fermentum sit amet. Aliquam vitae ornare justo. Nullam placerat sed nibh ornare sodales. Sed nec posuere nunc. Aenean blandit gravida dapibus. Cras fermentum justo ac vestibulum vestibulum. Interdum et malesuada fames ac ante ipsum primis in faucibus. Sed egestas orci ut mi scelerisque ornare. Morbi ex turpis, tristique eget est id, luctus tempor magna. In quam dui, dapibus nec viverra quis, rutrum eget urna. Curabitur iaculis dui non odio laoreet, ac bibendum orci feugiatDonec quis nisi ante. Ut a dolor arcu. Morbi mattis nulla a ligula congue feugiat. Nullam iaculis risus non metus blandit, viverra pellentesque tellus ullamcorper. Sed odio neque, sodales mollis libero in, tincidunt vulputate nulla. Duis non nunc venenatis, posuere lorem in, vulputate odio. Mauris venenatis efficitur libero. Nullam lectus turpis, tempor.");

    @Before
    public void setUp() {
        steganography = new Text();
    }

    @Test
    public void testSetAndGetContent() {
        String content = "This is a test content";
        steganography.setContent(content);
        assertEquals(content, steganography.getContent());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEncodeWithNullMessage() {
        steganography.setContent("Some content");
        steganography.encode(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEncodeWithEmptyMessage() {
        steganography.setContent("Some content");
        steganography.encode("");
    }

    @Test
    public void testEncodeAndDecode() {
        String message = "Hello";
        steganography.setContent(LOREM_IPSUM);
        steganography.encode(message);
        String decoded = steganography.decode();
        assertEquals(message, decoded);
    }

    @Test(expected = IllegalStateException.class)
    public void testDecodeWithoutEncoding() {
        steganography.decode();
    }

    @Test
    public void testEncodeLongMessage() {
        String message = "This is a longer secret message that needs to be hidden";
        steganography.setContent(LOREM_IPSUM);
        steganography.encode(message);
        String decoded = steganography.decode();
        assertEquals(message, decoded);
    }

    @Test
    public void testEncodeSpecialCharacters() {
        String message = "Test@#$%";
        steganography.setContent(LOREM_IPSUM);
        steganography.encode(message);
        String decoded = steganography.decode();
        assertEquals(message, decoded);
    }

    @Test
    public void testEncodeWithMinimalContent() {
        String message = "Hi";
        // Using just enough Lorem Ipsum words for "Hi" (needs 16 bits + 1 word)
        String minimalContent = "Lorem ipsum dolor sit amet consectetur adipiscing elit " +
                              "sed do eiusmod tempor incididunt ut labore et dolore magna";
        steganography.setContent(minimalContent);
        steganography.encode(message);
        String decoded = steganography.decode();
        assertEquals(message, decoded);
    }
}