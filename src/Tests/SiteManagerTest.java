package Classes;

import org.junit.*;
import java.io.File;
import java.util.*;

import static org.junit.Assert.*;

public class SiteManagerTest {

    private File tempFile;
    private SiteManager siteManager;

    @Before
    public void setUp() {
        tempFile = new File("test_db.json");
        Map<String, String> encryptionMap = Map.of("RotX", "3"); // Exemple de méthode de chiffrement
        siteManager = new SiteManager(tempFile, encryptionMap);
    }

    @After
    public void tearDown() {
        if (tempFile.exists()) {
            tempFile.delete();
        }
    }

    @Test
    public void testAddSite() {
        siteManager.addSite("example.com", "user123", "pass123");

        List<Map<String, String>> sites = siteManager.loadSites();
        assertEquals(1, sites.size());

        Map<String, String> site = sites.get(0);
        assertEquals("example.com", site.get("siteName"));
        assertEquals("user123", site.get("username"));
        assertEquals("pass123", site.get("password"));
    }

    @Test
    public void testModifySite() {
        siteManager.addSite("example.com", "user123", "pass123");
        siteManager.modifySite("example.com", "newUser", "newPass");

        List<Map<String, String>> sites = siteManager.loadSites();
        assertEquals(1, sites.size());

        Map<String, String> site = sites.get(0);
        assertEquals("example.com", site.get("siteName"));
        assertEquals("newUser", site.get("username"));
        assertEquals("newPass", site.get("password"));
    }

    @Test
    public void testDeleteSite() {
        siteManager.addSite("example.com", "user123", "pass123");
        siteManager.deleteSite("example.com");

        List<Map<String, String>> sites = siteManager.loadSites();
        assertTrue(sites.isEmpty());
    }

    @Test
    public void testLoadSites() {
        siteManager.addSite("example.com", "user123", "pass123");
        siteManager.addSite("test.com", "testUser", "testPass");

        List<Map<String, String>> sites = siteManager.loadSites();
        assertEquals(2, sites.size());

        Map<String, String> site1 = sites.get(0);
        Map<String, String> site2 = sites.get(1);

        assertEquals("example.com", site1.get("siteName"));
        assertEquals("user123", site1.get("username"));
        assertEquals("pass123", site1.get("password"));

        assertEquals("test.com", site2.get("siteName"));
        assertEquals("testUser", site2.get("username"));
        assertEquals("testPass", site2.get("password"));
    }

    @Test
    public void testDisplaySites() {
        siteManager.addSite("example.com", "user123", "pass123");
        siteManager.addSite("test.com", "testUser", "testPass");
    }
}
