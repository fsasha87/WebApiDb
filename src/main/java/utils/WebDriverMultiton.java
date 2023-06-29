package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.concurrent.TimeUnit;

public class WebDriverMultiton {
    private static final Logger LOG = Logger.getLogger(String.valueOf(WebDriverMultiton.class));
    private static ThreadLocal<WebDriver> webDriverThreadLocal = new ThreadLocal<>();
    private WebDriverMultiton(){
    }

    public static WebDriver getDriver() {
        if (webDriverThreadLocal.get() != null){
            return webDriverThreadLocal.get();
        }
        WebDriver instance;
        WebDriverManager.chromedriver().setup();
//        instance = new ChromeDriver();
        ChromeOptions options = new ChromeOptions();
            options.addArguments( /*"--headless",*/"--disable-gpu", "--lang=en", "--ignore-certificate-errors", "--silent", "--no-sandbox", "--disable-dev-shm-usage");
//        options.addArguments( "--headless", "--disable-gpu", "--lang=en", "--ignore-certificate-errors", "--silent", "--no-sandbox", "--disable-dev-shm-usage");
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});//not to show "Chrome is being controlled by automated test-software"
        instance = new ChromeDriver(options);
        instance.manage().window().maximize();
        instance.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        webDriverThreadLocal.set(instance);
        LOG.info("WebDriver is created");
        return webDriverThreadLocal.get();
    }

    public static void close() {
        try {
            if (webDriverThreadLocal != null) {
                webDriverThreadLocal.get().quit();
                LOG.info("WebDriver is closed");
            }
        }catch (Exception e){
            System.err.println("ERROR: Cannot close the driver");
        }finally {
            webDriverThreadLocal.remove();
        }
    }
}
