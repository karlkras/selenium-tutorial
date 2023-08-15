package com.letskodeit.overview;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class LoginTests {
    WebDriver driver;
    @BeforeMethod
    public void setup() {
        driver = initializeDriver("https://www.letskodeit.com/login",
                3);
    }

    @AfterMethod(alwaysRun=true)
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void testLogin() {
        driver.findElement(By.id("email")).sendKeys("karlkras@yahoo.com");
        driver.findElement(By.id("login-password")).sendKeys("3Mugs_beer");
        driver.findElement(By.id("login")).click();
        driver.findElements(By.xpath("//*[ text() = 'My Courses ' ]"));
    }

    public static WebDriver initializeDriver(String startingUrl, long timeout) {


//        ChromeDriverService service = new ChromeDriverService.Builder()
//                .withLogOutput(System.out)
//                .build();

        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeout));
        driver.get(startingUrl);
        return driver;
    }
}
