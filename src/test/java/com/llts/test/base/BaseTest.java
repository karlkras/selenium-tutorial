package com.llts.test.base;

import com.llts.test.pageclasses.LoginPage;
import com.llts.test.pageclasses.services.NavigationService;
import com.llts.test.utilities.Tools;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import com.llts.test.base.WebDriverFactory.BrowserType;
import org.testng.annotations.Parameters;

import java.io.IOException;
import java.io.StringReader;
import java.net.URISyntaxException;
import java.util.Properties;

public class BaseTest extends  CustomDriver {
    protected static Properties constants;
    protected LoginPage loginPage;
    protected NavigationService navService;

    static {
        constants = new Properties();
        try {
            constants.load(new StringReader(Tools.getResourceAsString("constants.properties")));
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeClass
    @Parameters({"browser"})
    public void setup(String browser) {
        BrowserType theTargetType = BrowserType.valueOf(browser.toUpperCase());
        initialize(WebDriverFactory.getInstance().getDriver(theTargetType));
        setUrl(constant("baseUrl"));
        navService = new NavigationService(theDriver);
        loginPage = navService.login();
    }

    protected String constant(String key) {
        return constants.getProperty(key);
    }

    @AfterClass()
    public void cleanup() {
        if(navService != null) {
            navService.logout();
        }
        WebDriverFactory.getInstance().quit();
    }
}
