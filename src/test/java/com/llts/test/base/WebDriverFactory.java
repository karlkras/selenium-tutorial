package com.llts.test.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.*;
import java.time.Duration;
import java.util.Collections;

public class WebDriverFactory {
    private static final WebDriverFactory instance = new WebDriverFactory();

    private WebDriverFactory() {
    }

    public static WebDriverFactory getInstance() {
        return instance;
    }

    private static final ThreadLocal<WebDriver> threadedDriver = new ThreadLocal<>();
    private static final ThreadLocal<BrowserType> threadedBrowser = new ThreadLocal<>();
    public WebDriver getDriver(BrowserType browser) {
        if(threadedDriver.get() != null) {
            return threadedDriver.get();
        }
        WebDriver driver = null;
        threadedBrowser.set(browser);
        switch (browser) {
            case CHROME:
                ChromeDriverService chromeservice = new ChromeDriverService.Builder()
                        .withLogOutput(System.out)
                        .build();
                driver = new ChromeDriver(chromeservice, setChromeOptions());
                break;
            case FIREFOX:
                try {
                    FirefoxDriverService ffservice = new GeckoDriverService.Builder()
                            .withLogOutput(System.out)
                            .build();
                    driver = new FirefoxDriver(ffservice, setFFOptions());
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                break;
            case EDGE:
                driver = new EdgeDriver();
                break;
        }
        if(driver != null) {
            threadedDriver.set(driver);
            threadedDriver.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
            threadedDriver.get().manage().window().maximize();
            driver = threadedDriver.get();
        }
        return driver;
    }

    public void quit() {
        threadedDriver.get().quit();
        threadedDriver.set(null);
    }

    public BrowserType getBrowser() {
        return threadedBrowser.get();
    }

    public enum BrowserType {
        FIREFOX,
        CHROME,
        EDGE
    }

    /***
     * Set Chrome Options
     * @return options
     */
    private ChromeOptions setChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        return options;
    }

    /***
     * Set Firefox Options
     * @return options
     */
    private FirefoxOptions setFFOptions() {
        FirefoxOptions options = new FirefoxOptions();
        return options;
    }

}
