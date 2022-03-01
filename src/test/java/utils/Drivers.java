package utils;
// JAVA
import java.io.File;
import java.util.HashMap;
import java.util.Map;
// SELENIUM
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
// WEB DRIVER MANAGER
import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * <strong>This is a SINGLETON</strong> WebDriver Handler. Use "getDriver()" to initialize the WebDriver.
 */
public class Drivers {
    /**
     * This.class's Singleton Object. It will be == to null if not already initialized.
     */
    private static Drivers DRIVERS;
    /**
     * The WebDriver returned from "getDriver()"
     */
    private static WebDriver driver;

    private Drivers() {
        String browser = Props.getProp("browser");
        try {
            if (driver == null) {
                if (browser.contains("chrome")) driver = Chrome();
                if (browser.contains("firefox")) driver = Firefox();
                assert driver != null;
                driver.manage().window().maximize();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Used to initialize the Web Driver
     * @return A WebDriver. If one already exists then this will fetch it, else a new one will be created and then returned.
     */
    public static WebDriver getDriver() {
        if (DRIVERS == null) DRIVERS = new Drivers();
        return driver;
    }

    public static void quit() {
        driver.quit();
    }

    /**
     * CHROME driver setup
     * @return WebDriver Chrome edition
     */
    private static WebDriver Chrome() {
        /*
            TO SELECT BROWSER VERSION
            WebDriverManager.chromedriver().browserVersion("95").setup();
         */

        // SETUP WEB DRIVER
        WebDriverManager.chromedriver().setup();

        // CHROME DRIVER SETTINGS
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("download.default_directory", System.getProperty("user.dir"+ File.separator +"externalFiles"+ File.separator +"downloads"));

        ChromeOptions options = new ChromeOptions()
                .setHeadless(false)
                .setExperimentalOption("prefs", prefs);

        return new ChromeDriver(options);
    }

    /**
     * FIREFOX driver setup
     * @return WebDriver Firefox edition
     */
    private WebDriver Firefox() {
        WebDriverManager.firefoxdriver().setup();
        return new FirefoxDriver();
    }
}

