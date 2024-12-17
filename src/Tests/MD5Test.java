package Tests;

import org.junit.Test;
import org.junit.Assert;
import Classes.MD5;

public class MD5Test {

    @Test
    public void testEmptyString() {
        String result = MD5.hash("");
        Assert.assertEquals("d41d8cd98f00b204e9800998ecf8427e", result);
    }

    @Test
    public void testSimpleString() {
        String result = MD5.hash("test");
        Assert.assertEquals("098f6bcd4621d373cade4e832627b4f6", result);
    }

    @Test
    public void testLongString() {
        String result = MD5.hash("The quick brown fox jumps over the lazy dog");
        Assert.assertEquals("9e107d9d372bb6826bd81d3542a419d6", result);
    }

    @Test
    public void testSpecialCharacters() {
        String result = MD5.hash("!@#$%^&*()_+");
        Assert.assertEquals("04dde9f462255fe14b5160bbf2acffe8", result);
    }

    @Test
    public void testUnicodeCharacters() {
        String result = MD5.hash("héllo wörld");
        Assert.assertEquals("ed0c22cc110ede12327851863c078138", result);
    }

    @Test
    public void testConsistency() {
        String input = "test123";
        String firstHash = MD5.hash(input);
        String secondHash = MD5.hash(input);
        Assert.assertEquals("Le même input devrait produire le même hash", firstHash, secondHash);
    }

    @Test(expected = RuntimeException.class)
    public void testNullInput() {
        MD5.hash(null);
    }

    @Test
    public void testCaseSensitivity() {
        String lowerCase = MD5.hash("hello");
        String upperCase = MD5.hash("HELLO");
        Assert.assertNotEquals("Les hashs de différentes casses devraient être différents",
                lowerCase, upperCase);
    }

    @Test
    public void testHashLength() {
        String result = MD5.hash("test");
        Assert.assertEquals("La longueur du hash MD5 devrait être de 32 caractères",
                32, result.length());
    }

    @Test
    public void testWhitespaceHandling() {
        String withSpaces = MD5.hash("test test");
        String withoutSpaces = MD5.hash("testtest");
        Assert.assertNotEquals("Les espaces devraient affecter le hash",
                withSpaces, withoutSpaces);
    }
}