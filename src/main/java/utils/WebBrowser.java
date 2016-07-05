package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.annotations.*;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class WebBrowser extends ReportManager {

    public String browserName;
    private WebDriver driver;
    private static ThreadLocal<WebDriver> threadLocalDriver = new ThreadLocal<WebDriver>();
    private static ThreadLocal<String> threadLocalBrovser = new ThreadLocal<String>();
    File pathToDriver = new File("./src/main/resources/drivers/chromedriver");

    @Parameters({"browser", "device", "OSv"})
    @BeforeTest(alwaysRun = true)
    public void initBrowser(@Optional(value = "chrome") String browser, @Optional(value = "") String device,
                            @Optional(value = "") String OSv) {
        if (System.getProperty("browser") != null) {
            if (System.getProperty("browser").equals("<Default>")) {
                browser = "chrome";
            } else {
                browser = System.getProperty("browser");
            }
        }

        browserName = browser;
        if (browser.equals("firefox")) {
            driver = new FirefoxDriver();
            System.out.printf("[INFO] firefox is started");
        } else if (browser.equals("chrome")) {
            System.setProperty("webdriver.chrome.driver", pathToDriver.getAbsolutePath());
            driver = new ChromeDriver();
            System.out.println("[INFO] chrome is started");
        } else if (browser.equals("safari")) {
            driver = new SafariDriver();
            System.out.println("[INFO] safari is started");
        }
        threadLocalDriver.set(driver);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        threadLocalBrovser.set(browserName);
    }

    public static WebDriver Driver() {
        return threadLocalDriver.get();
    }

    public static String getCurrentBrovserName() {
        return threadLocalBrovser.get();
    }

    @AfterTest(alwaysRun = true)
    public void closeWebBrowser() {
        if (driver != null) {
            driver.quit();
            threadLocalDriver.remove();
            System.out.println("[INFO] browser closed");
        }
    }

    @AfterSuite(alwaysRun = true)
    public void flushReporter() {
        closeReporter();
    }

}
