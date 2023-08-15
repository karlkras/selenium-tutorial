package com.llts.test.base;

import com.llts.test.utilities.Tools;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;
import java.util.List;

@SuppressWarnings({"unused", "JavadocDeclaration"})
public class CustomDriver {
    private static final Logger logger = LogManager.getLogger(CustomDriver.class.getName());


    protected WebDriver theDriver;
    @SuppressWarnings("FieldCanBeLocal")
    private JavascriptExecutor js;
    public CustomDriver (WebDriver driver) {
        this.initialize(driver);
    }

    public CustomDriver() {

    }

    public void initialize(WebDriver driver) {
        this.theDriver = driver;
        js = (JavascriptExecutor) driver;
    }


    public boolean isElementPresent(String locator) {
        return getElementList(locator, null).size() > 0;
    }

    /***
     * Builds the By type with given locator strategy
     * @param locator - locator strategy, id=>example, name=>example, css=>#example,
     *      *                tag=>example, xpath=>//example, link=>example
     * @return Returns By Type
     */
    public By getByType(String locator) {
        By by;
        String [] theItems = locator.split("=>");
        if(theItems.length != 2) {
            throw new RuntimeException(String.format("Locator \"%s\" is in incorrect format", locator));
        }
        String locatorType = theItems[0];
        locator = theItems[1];
        switch (locatorType.toLowerCase()) {
            case "id" :
                by = By.id(locator);
                break;
            case "name":
                by = By.name(locator);
                break;
            case "xpath":
                by = By.xpath(locator);
                break;
            case "css":
                by = By.cssSelector(locator);
                break;
            case "class" :
                by = By.className(locator);
                break;
            case "tag":
                by = By.tagName(locator);
                break;
            case "link":
                by = By.linkText(locator);
                break;
            case "partiallink":
                by = By.partialLinkText(locator);
                break;
            default:
                throw new IllegalArgumentException(String.format("locator type %s not supported", locatorType));

        }
        return by;
    }

    public WebDriver rootDriver() {
        return this.theDriver;
    }

    public void refresh() {
        theDriver.navigate().refresh();
    }

    public String getTitle() {
        String title = theDriver.getTitle();
        logger.info(String.format("Title of the element is: %s", title));
        return title;
    }

    public void setUrl(String url) {
        logger.info(String.format("Attempting to get to url: %s", url));
        theDriver.get(url);
    }

    public WebElement getElement(String locator, String info) {
        WebElement theElement;
        try {
            theElement = theDriver.findElement(getByType(locator));
        } catch (Exception ex) {
            String message = String.format("Element not found with: %s", locator);
            logger.info(message);
            throw new IllegalArgumentException(message);
        }
        return theElement;
    }

    public WebElement getElement(String locator) {
        return getElement(locator, null);
    }

    public List<WebElement> getElementList(String locator, String info) {
        List<WebElement> elementList;
        try {
            elementList = theDriver.findElements(getByType(locator));
        } catch (Exception ex) {
            String message = String.format("Elements not found with: %s", locator);
            logger.info(message);
            throw new IllegalArgumentException(message);
        }
        return elementList;
    }

    public List<WebElement> getElementList(String locator) {
        return getElementList(locator, null);
    }

    /**
     * @return Current Browser URL
     */
    public String getURL() {
        String url = theDriver.getCurrentUrl();
        logger.info("Current URL is :: " + url);
        return url;
    }

    /**
     * Navigate browser back
     */
    public void browserBack() {
        theDriver.navigate().back();
        logger.info("Navigate back");
    }

    /**
     * Navigate browser forward
     */
    public void browserForward() {
        theDriver.navigate().back();
        logger.info("Navigate back");
    }

    /**
     * Click element with information about element and
     * time to wait in seconds after click
     *
     * @param element - WebElement to perform Click operation
     * @param info    - information about element
     */
    public WebElement elementClick(WebElement element, String info, long timeToWait) {
        try {
            element.click();
            if (timeToWait == 0) {
                logger.info("Clicked On :: " + info);
            } else {
                Tools.pause(timeToWait);
            }
        } catch (Exception e) {
            logger.info("Cannot click on :: " + info);
            takeScreenshot("Click ERROR", "");
        }
        return element;
    }

    /**
     * Click element with no time to wait after click
     *
     * @param element - WebElement to perform Click operation
     * @param info    - information about element
     */
    public WebElement elementClick(WebElement element, String info) {
        return elementClick(element, info, 0);
    }

    /**
     * Click element with locator
     * @param locator - locator strategy, id=>example, name=>example, css=>#example,
     *      *                tag=>example, xpath=>//example, link=>example
     * @param info
     * @param timeToWait
     * @return
     */
    public WebElement elementClick(String locator, String info, long timeToWait) {
        WebElement element = this.getElement(locator, info);
        return elementClick(element, info, timeToWait);
    }

    /**
     * Click element with locator and no time to wait
     * @param locator - locator strategy, id=>example, name=>example, css=>#example,
     *      *                tag=>example, xpath=>//example, link=>example
     * @param info - Information about element
     * @return
     */
    public WebElement elementClick(String locator, String info) {
        WebElement element = getElement(locator, info);
        elementClick(element, info, 0);
        return element;
    }

    /***
     * Click element using JavaScript
     * @param locator - locator strategy, id=>example, name=>example, css=>#example,
     *      *                tag=>example, xpath=>//example, link=>example
     * @param info - Information about element
     */
    public void javascriptClick(String locator, String info) {
        javascriptClick(getElement(locator, info), info);
    }

    public void javascriptClick(WebElement theElement, String info) {
        try {
            js.executeScript("arguments[0].click();", theElement);
            logger.info("Clicked on :: " + info);
        } catch (Exception e) {
            logger.info("Cannot click on :: " + info);
        }
    }

    /***
     * Click element when element is clickable
     * @param locator - locator strategy, id=>example, name=>example, css=>#example,
     *      *                tag=>example, xpath=>//example, link=>example
     * @param timeout - Duration to try before timeout
     */
    public void clickWhenReady(By locator, int timeout) {
        try {
            theDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
            WebElement element;
            logger.info("Waiting for max:: " + timeout + " seconds for element to be clickable");

            WebDriverWait wait = new WebDriverWait(theDriver, Duration.ofSeconds(15));
            element = wait.until(
                    ExpectedConditions.elementToBeClickable(locator));
            element.click();
            logger.info("Element clicked on the web page");
            theDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        } catch (Exception e) {
            logger.info("Element not appeared on the web page");
            theDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        }
    }

    /***
     * Send Keys to element
     * @param element - WebElement to send data
     * @param data - Data to send
     * @param info - Information about element
     * @param clear - True if you want to clear the field before sending data
     */
    public void sendData(WebElement element, String data, String info, Boolean clear) {
        try {
            if (clear) {
                Tools.clearTextField(element);
            }
            //Util.sleep(1000, "Waiting Before Entering Data");
            element.sendKeys(data);
            logger.info("Send Keys on element :: "
                    + info + " with data :: " + data);
        } catch (Exception e) {
            logger.info("Cannot send keys on element :: "
                    + info + " with data :: " + data);
        }
    }

    /***
     * Send Keys to element with locator
     * @param locator - locator strategy, id=>example, name=>example, css=>#example,
     *      *                tag=>example, xpath=>//example, link=>example
     * @param data - Data to send
     * @param info - Information about element
     * @param clear - True if you want to clear the field before sending data
     */
    public void sendData(String locator, String data, String info, Boolean clear) {
        WebElement element = this.getElement(locator, info);
        sendData(element, data, info, clear);
    }

    /***
     * Send Keys to element with clear
     * @param element - WebElement to send data
     * @param data - Data to send
     * @param info - Information about element
     */
    public void sendData(WebElement element, String data, String info) {
        sendData(element, data, info, true);
    }

    /***
     * Send Keys to element with locator and clear
     * @param locator - locator strategy, id=>example, name=>example, css=>#example,
     *      *                tag=>example, xpath=>//example, link=>example
     * @param data - Data to send
     * @param info - Information about element
     */
    public void sendData(String locator, String data, String info) {
        WebElement element = getElement(locator, info);
        sendData(element, data, info, true);
    }

    /**
     * Get text of a web element
     *
     * @param element - WebElement to perform click action
     * @param info    - Information about element
     */
    public String getText(WebElement element, String info) {
        logger.info("Getting Text on element :: " + info);
        String text;
        text = element.getText();
        if (text.length() == 0) {
            text = element.getAttribute("innerText");
        }
        if (!text.isEmpty()) {
            logger.info(" The text is : " + text);
        } else {
            text = "NOT_FOUND";
        }
        return text.trim();
    }

    /**
     * Get text of a web element with locator
     * @param locator
     * @param info
     * @return
     */
    public String getText(String locator, String info) {
        WebElement element = this.getElement(locator, info);
        return this.getText(element, info);
    }

    /**
     * Check if element is enabled
     * @param element
     * @param info
     * @return
     */
    public Boolean isEnabled(WebElement element, String info) {
        boolean enabled = false;
        if (element != null) {
            enabled = element.isEnabled();
            logger.info(String.format("Element :: %s is %s", info,
                    enabled ? "Enabled" : "Disabled"));
        }
        return enabled;
    }

    /***
     * Check if element is enabled with locator
     * @param locator
     * @param info
     * @return
     */
    public Boolean isEnabled(String locator, String info) {
        WebElement element = getElement(locator, info);
        return this.isEnabled(element, info);
    }

    /**
     * Check if element is displayed
     * @param element
     * @param info
     * @return
     */
    public Boolean isDisplayed(WebElement element, String info) {
        boolean displayed = false;
        if (element != null) {
            displayed = element.isDisplayed();
            logger.info(String.format("Element :: %s is %s", info,
                    displayed ? "displayed" : "NOT displayed"));
        }
        return displayed;
    }

    /***
     * Check if element is displayed with locator
     * @param locator
     * @param info
     * @return
     */
    public Boolean isDisplayed(String locator, String info) {
        WebElement element = getElement(locator, info);
        return this.isDisplayed(element, info);
    }

    /**
     * @param element
     * @param info
     * @return
     */
    public Boolean isSelected(WebElement element, String info) {
        boolean selected = false;
        if (element != null) {
            selected = element.isSelected();
            logger.info(String.format("Element :: %s is %s", info,
                    selected ? "selected" : "already selected"));
        }
        return selected;
    }

    /**
     * @param locator
     * @param info
     * @return
     */
    public Boolean isSelected(String locator, String info) {
        WebElement element = getElement(locator, info);
        return isSelected(element, info);
    }

    /**
     * Selects a checkbox irrespective of its state
     *
     * @param element
     * @param info
     */
    public void Check(WebElement element, String info) {
        if (!isSelected(element, info)) {
            elementClick(element, info);
            logger.info("Element :: " + info + " is checked");
        } else
            logger.info("Element :: " + info + " is already checked");
    }

    /**
     * Selects a checkbox irrespective of its state, using locator
     *
     * @param locator
     * @param info
     * @return
     */
    public void Check(String locator, String info) {
        WebElement element = getElement(locator, info);
        Check(element, info);
    }

    /**
     * UnSelects a checkbox irrespective of its state
     *
     * @param element
     * @param info
     * @return
     */
    public void UnCheck(WebElement element, String info) {
        if (isSelected(element, info)) {
            elementClick(element, info);
            logger.info("Element :: " + info + " is unchecked");
        } else
            logger.info("Element :: " + info + " is already unchecked");
    }

    /**
     * UnSelects a checkbox irrespective of its state, using locator
     *
     * @param locator
     * @param info
     * @return
     */
    public void UnCheck(String locator, String info) {
        WebElement element = getElement(locator, info);
        UnCheck(element, info);
    }

    /**
     * @param element
     * @param info
     * @return
     */
    public boolean Submit(WebElement element, String info) {
        if (element == null) {
            return false;
        }
        element.submit();
        logger.info("Element :: " + info + " is submitted");
        return true;

    }

    /**
     * @param locator
     * @param attribute
     * @return
     */
    public String getElementAttributeValue(String locator, String attribute) {
        WebElement element = getElement(locator, "info");
        return element.getAttribute(attribute);
    }

    /**
     * @param element
     * @param attribute
     * @return
     */
    public String getElementAttributeValue(WebElement element, String attribute) {
        return element.getAttribute(attribute);
    }

    /**
     * @param locator
     * @param timeout
     * @return
     */
    public WebElement waitForElement(String locator, int timeout) {
        By byType = getByType(locator);
        WebElement element = null;
        try {
            theDriver.manage().timeouts().implicitlyWait(Duration.ZERO);
            logger.info("Waiting for max:: " + timeout + " seconds for element to be available");
            WebDriverWait wait = new WebDriverWait(theDriver, Duration.ofSeconds(timeout));
            element = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(byType));
            logger.info("Element appeared on the web page");
            theDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        } catch (Exception e) {
            logger.info("Element not appeared on the web page");
            theDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        }
        return element;
    }

    /***
     * Wait for element to be clickable
     * @param locator - locator strategy, id=>example, name=>example, css=>#example,
     *      *                tag=>example, xpath=>//example, link=>example
     * @param timeout - Duration to try before timeout
     */
    public WebElement waitForElementToBeClickable(String locator, int timeout) {
        By byType = getByType(locator);
        WebElement element = null;
        try {
            theDriver.manage().timeouts().implicitlyWait(Duration.ZERO);
            logger.info("Waiting for max:: " + timeout + " seconds for element to be clickable");

            WebDriverWait wait = new WebDriverWait(theDriver, Duration.ofSeconds(15));
            element = wait.until(
                    ExpectedConditions.elementToBeClickable(byType));
            logger.info("Element is clickable on the web page");
            theDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        } catch (Exception e) {
            logger.info("Element not appeared on the web page");
            theDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        }
        return element;
    }

    /**
     *
     */
    public boolean waitForLoading(String locator, long timeout) {
        By byType = getByType(locator);
        boolean elementInvisible = false;
        try {
            theDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
            logger.info("Waiting for max:: " + timeout + " seconds for element to be available");
            WebDriverWait wait = new WebDriverWait(theDriver, Duration.ofSeconds(timeout));
            elementInvisible = wait.until(
                    ExpectedConditions.invisibilityOfElementLocated(byType));
            logger.info("Element appeared on the web page");
            theDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        } catch (Exception e) {
            logger.info("Element not appeared on the web page");
            theDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        }
        return elementInvisible;
    }

    /**
     * Mouse Hovers to an element
     *
     * @param locator
     */
    public void mouseHover(String locator, String info) {
        WebElement element = getElement(locator, info);
        Actions action = new Actions(theDriver);
        action.moveToElement(element).perform();
    }

    /**
     * @param element
     * @param optionToSelect
     */
    public void selectOption(WebElement element, String optionToSelect) {
        Select sel = new Select(element);
        sel.selectByVisibleText(optionToSelect);
        logger.info("Selected option : " + optionToSelect);
    }

    /**
     * Selects a given option in list box
     *
     * @param locator
     * @param optionToSelect
     */
    public void selectOption(String locator, String optionToSelect, String info) {
        WebElement element = getElement(locator, info);
        this.selectOption(element, optionToSelect);
    }

    /**
     * get Selected drop down value
     *
     * @param element
     * @return
     */
    public String getSelectDropDownValue(WebElement element) {
        Select sel = new Select(element);
        return sel.getFirstSelectedOption().getText();
    }

    /**
     * @param element
     * @param optionToVerify
     */
    public boolean isOptionExists(WebElement element, String optionToVerify) {
        Select sel = new Select(element);
        boolean exists = false;
        List<WebElement> optList = sel.getOptions();
        for (WebElement webElement : optList) {
            String text = getText(webElement, "Option Text");
            if (text.matches(optionToVerify)) {
                exists = true;
                break;
            }
        }
        if (exists) {
            logger.info("Selected Option : " + optionToVerify + " exist");
        } else {
            logger.info("Selected Option : " + optionToVerify + " does not exist");
        }
        return exists;
    }

    /***
     *
     * @param methodName
     * @param browserName
     * @return
     */
    @SuppressWarnings("UnusedReturnValue")
    public String takeScreenshot(String methodName, String browserName) {
        String fileName = Tools.getScreenshotName(methodName, browserName);
        String screenshotDir = System.getProperty("user.dir") + "//" + "test-output/screenshots";
        //noinspection ResultOfMethodCallIgnored
        new File(screenshotDir).mkdirs();
        String path = screenshotDir + "//" + fileName;

        try {
            File screenshot = ((TakesScreenshot)theDriver).
                    getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshot, new File(path));
            logger.info("Screen Shot Was Stored at: "+ path);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return path;
    }

    public void DoubleClick(WebElement element, String info) {
        Actions action = new Actions(theDriver);
        action.doubleClick(element);
        logger.info("Double Clicked on :: " + info);
        action.perform();
    }

    /**
     * Right Click a WebElement
     *
     * @param locator
     */
    public void rightClick(String locator, String info) {
        WebElement element = getElement(locator, info);
        Actions action = new Actions(theDriver);
        action.contextClick(element).build().perform();
        logger.info("Double Clicked on :: " + info);
    }

    /**
     * Right-click a WebElement and select the option
     *
     * @param elementLocator
     * @param itemLocator
     */
    public void selectItemRightClick(String elementLocator, String itemLocator) {
        WebElement element = getElement(elementLocator, "info");
        Actions action = new Actions(theDriver);
        action.contextClick(element).build().perform();
        WebElement itemElement = getElement(itemLocator, "info");
        elementClick(itemElement, "Selected Item");
    }

    /**
     * @param key
     */
    public void keyPress(Keys key, String info) {
        Actions action = new Actions(theDriver);
        action.keyDown(key).build().perform();
        logger.info("Key Pressed :: " + info);
    }
}
