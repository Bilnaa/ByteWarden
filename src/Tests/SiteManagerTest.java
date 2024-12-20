package Tests;

import Classes.SiteManager;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class SiteManagerTest {
    private SiteManager siteManager;
    private File testDbFile;

    @Before
    public void setUp() throws IOException {
        // Create a new temporary file for each test
        testDbFile = File.createTempFile("src/Tests/assets/test_sites", ".json");
        siteManager = new SiteManager(testDbFile);

        // Configure test mode with Enigma parameters
        char[] rotorPositions = {'A', 'A', 'A'};
        String[][] plugboardConnections = {{"A", "B"}, {"C", "D"}};
        siteManager.configureTestMode(true, "Enigma", rotorPositions, new Object(), plugboardConnections);
    }

    @Test
    public void testAddSite() {
        // Clear any existing sites
        List<Map<String, String>> sites = siteManager.getSites();
        for (Map<String, String> site : sites) {
            siteManager.deleteSite(site.get("siteName"));
        }

        siteManager.addSite("TestSite", "testUser", "testPass");

        sites = siteManager.getSites();
        assertEquals(1, sites.size());
        assertEquals("TestSite", sites.get(0).get("siteName"));
        assertEquals("testUser", sites.get(0).get("username"));
        assertNotNull(sites.get(0).get("password"));
    }

    @Test
    public void testModifySite() {
        // Clear any existing sites
        List<Map<String, String>> sites = siteManager.getSites();
        for (Map<String, String> site : sites) {
            siteManager.deleteSite(site.get("siteName"));
        }

        siteManager.addSite("TestSite", "testUser", "testPass");
        siteManager.modifySite("TestSite", "newUser", "newPass");

        sites = siteManager.getSites();
        assertEquals(1, sites.size());
        assertEquals("newUser", sites.get(0).get("username"));
    }

    @Test
    public void testDeleteSite() {
        // Clear any existing sites
        List<Map<String, String>> sites = siteManager.getSites();
        for (Map<String, String> site : sites) {
            siteManager.deleteSite(site.get("siteName"));
        }

        siteManager.addSite("TestSite", "testUser", "testPass");
        siteManager.deleteSite("TestSite");

        sites = siteManager.getSites();
        assertTrue(sites.isEmpty());
    }

    @Test
    public void testGetSites() {
        // Clear any existing sites
        List<Map<String, String>> sites = siteManager.getSites();
        for (Map<String, String> site : sites) {
            siteManager.deleteSite(site.get("siteName"));
        }

        siteManager.addSite("Site1", "user1", "pass1");
        siteManager.addSite("Site2", "user2", "pass2");

        sites = siteManager.getSites();
        assertEquals(2, sites.size());
    }

    @Test
    public void testSaveSites() {
        // Clear any existing sites
        List<Map<String, String>> sites = siteManager.getSites();
        for (Map<String, String> site : sites) {
            siteManager.deleteSite(site.get("siteName"));
        }

        siteManager.addSite("TestSite", "testUser", "testPass");
        siteManager.saveSites();

        // Create a new SiteManager instance to read from the same file
        SiteManager newManager = new SiteManager(testDbFile);
        newManager.configureTestMode(true, "Enigma",
                new char[]{'A', 'A', 'A'}, new Object(),
                new String[][]{{"A", "B"}, {"C", "D"}});

        sites = newManager.getSites();
        assertEquals(1, sites.size());
        assertEquals("TestSite", sites.get(0).get("siteName"));
    }

    @Test
    public void testEncryptSites() {
        // Clear any existing sites
        List<Map<String, String>> sites = siteManager.getSites();
        for (Map<String, String> site : sites) {
            siteManager.deleteSite(site.get("siteName"));
        }

        siteManager.addSite("TestSite", "testUser", "testPass");
        String originalPassword = siteManager.getSites().get(0).get("password");
        siteManager.encryptSites("Enigma");

        sites = siteManager.getSites();
        assertNotEquals(originalPassword, sites.get(0).get("password"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEncryptSitesWithUnsupportedAlgorithm() {
        siteManager.encryptSites("UnsupportedAlgorithm");
    }

    @Test
    public void testSetEncryptionAlgorithm() {
        // Clear any existing sites
        List<Map<String, String>> sites = siteManager.getSites();
        for (Map<String, String> site : sites) {
            siteManager.deleteSite(site.get("siteName"));
        }

        siteManager.setEncryptionAlgorithm("Enigma");
        siteManager.addSite("TestSite", "testUser", "testPass");

        sites = siteManager.getSites();
        assertNotNull(sites.get(0).get("password"));
    }
}