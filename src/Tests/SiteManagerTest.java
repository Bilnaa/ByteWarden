package Tests;

import Classes.SiteManager;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class SiteManagerTest {
    private SiteManager siteManager;
    private File testFile;

    @Before
    public void setUp() {
        testFile = new File("src/Tests/assets/test_sites.json");
        siteManager = new SiteManager(testFile);

        if (testFile.exists()) {
            testFile.delete();
        }
    }

    @Test
    public void testAddSite() {
        siteManager.addSite("example.com", "user1", "password1");
        List<Map<String, String>> sites = siteManager.loadSites();

        assertEquals(1, sites.size());
        assertEquals("example.com", sites.get(0).get("siteName"));
        assertEquals("user1", sites.get(0).get("username"));
        assertEquals("password1", sites.get(0).get("password"));
    }

    @Test
    public void testModifySite() {
        siteManager.modifySite("example.com", "newUser", "newPassword");

        List<Map<String, String>> sites = siteManager.loadSites();

        System.out.println("Sites after modification: " + sites);

        assertEquals(1, sites.size());
        assertEquals("newUser", sites.get(0).get("username"));
        assertEquals("newPassword", sites.get(0).get("password"));
    }


    @Test
    public void testDeleteSite() {
        siteManager.addSite("example.com", "user1", "password1");
        siteManager.deleteSite("example.com");

        List<Map<String, String>> sites = siteManager.loadSites();

        assertTrue(sites.isEmpty());
    }
}
