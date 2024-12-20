package Tests;

import Classes.MD5;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

public class MD5Test {

    private MD5 md5;
    
    @Before
    public void setUp() {
        md5 = new MD5();
    }
    @Test
    public void testEmptyString() {
        
        String result = md5.calculateHash("");
        Assert.assertEquals("d41d8cd98f00b204e9800998ecf8427e", result);
    }

    @Test
    public void testSimpleString() {
        String result = md5.calculateHash("test");
        Assert.assertEquals("098f6bcd4621d373cade4e832627b4f6", result);
    }

    @Test
    public void testLongString() {
        String result = md5.calculateHash("The quick brown fox jumps over the lazy dog");
        Assert.assertEquals("9e107d9d372bb6826bd81d3542a419d6", result);
    }

    @Test
    public void testSpecialCharacters() {
        String result = md5.calculateHash("!@#$%^&*()_+");
        Assert.assertEquals("04dde9f462255fe14b5160bbf2acffe8", result);
    }

    @Test
    public void testUnicodeCharacters() {
        String result = md5.calculateHash("héllo wörld");
        Assert.assertEquals("ed0c22cc110ede12327851863c078138", result);
    }

    @Test
    public void testConsistency() {
        String input = "test123";
        String firstHash = md5.calculateHash(input);
        String secondHash = md5.calculateHash(input);
        Assert.assertEquals("The same input should produce the same hash", firstHash, secondHash);
    }

    @Test(expected = RuntimeException.class)
    public void testNullInput() {
        md5.calculateHash(null);
    }

    @Test
    public void testCaseSensitivity() {
        String lowerCase = md5.calculateHash("hello");
        String upperCase = md5.calculateHash("HELLO");
        Assert.assertNotEquals("Hashes of different cases should be different",
                lowerCase, upperCase);
    }

    @Test
    public void testHashLength() {
        String result = md5.calculateHash("test");
        Assert.assertEquals("The length of the md5 hash should be 32 characters",
                32, result.length());
    }

    @Test
    public void testWhitespaceHandling() {
        String withSpaces = md5.calculateHash("test test");
        String withoutSpaces = md5.calculateHash("testtest");
        Assert.assertNotEquals("Spaces should affect the hash",
                withSpaces, withoutSpaces);
    }
}