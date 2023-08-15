package com.llts.test.utilities;

import com.google.common.collect.Ordering;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@SuppressWarnings({"unused", "JavadocDeclaration"})
public class Tools {
    private static final Logger logger = LogManager.getLogger(Tools.class.getName());

    public static String getResourceAsString(String resourceName) throws URISyntaxException, IOException {
        ClassLoader classLoader = Tools.class.getClassLoader();

        URL resource = classLoader.getResource(resourceName);
        if (resource == null) {
            throw new IllegalArgumentException("file not found! " + resourceName);
        }

        String thePath = new File(resource.toURI()).getAbsolutePath();

        return new String(
                Files.readAllBytes(Paths.get(thePath)));
    }

    /***
     * Get a unique report name
     * @return
     */
    public static String getReportName() {
        return String.format("AutomationReport_%s.html", getCurrentDateTime());
    }

    public static WebElement clearTextField(WebElement elem) {
        elem.sendKeys(Keys.chord(Keys.CONTROL,"a", Keys.DELETE));
        return elem;
    }

    public static void scrollTo(WebElement myElement, WebDriver driver) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", myElement);
    }

    public static String makeUniqueName(String orgName) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuMMddHHmmss");
        LocalDateTime now = LocalDateTime.now();
        return String.format("%s_%s", orgName, dtf.format(now));
    }

    /**
     * Provides the absolute file path to a resource in the package.
     *
     * @param resourceName The name of the resource file to provide the path.
     * @return The absolute path to the resource file.
     * @throws URISyntaxException If there was a problem getting the file's path.
     */
    @SuppressWarnings("unused")
    public static String getFilePath(String resourceName) throws URISyntaxException {
        ClassLoader classLoader = Tools.class.getClassLoader();

        URL resource = classLoader.getResource(resourceName);
        if (resource == null) {
            throw new IllegalArgumentException("file not found! " + resourceName);
        }
        return new File(resource.toURI()).getAbsolutePath();
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public static int getRandomNumberInRange(int min, int max) {
        Random r = new Random();
        return r.ints(min, (max + 1)).limit(1).findFirst().getAsInt();
    }

    public static String getTestUploadFiles() {
        List<String> theFiles = Tools.getResourceFileList("test-files");
        return String.join(" \n ", theFiles);
    }

    public static List<String> getResourceFileList(String location) {
        List<String> fileNameList = new ArrayList<>();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        URL url = loader.getResource(location);
        if(url == null) {
            throw new InvalidPathException(location, "location not found");
        }
        File[] theFileArray = new File(url.getPath()).listFiles();
        assert theFileArray != null;
        for(File aFile : theFileArray) {
            fileNameList.add(aFile.getAbsolutePath());
        }
        return fileNameList;
    }

    public static WebDriver initializeDriver(String startingUrl, long timeout) {


        ChromeDriverService service = new ChromeDriverService.Builder()
                .withLogOutput(System.out)
                .build();

        WebDriver driver = new ChromeDriver(service);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeout));
        driver.get(startingUrl);
        return driver;
    }

    public static WebDriver initializeDriver() {
        ChromeDriverService service = new ChromeDriverService.Builder()
                .withLogOutput(System.out)
                .build();

        WebDriver driver = new ChromeDriver(service);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        return driver;
    }

    /**
     * Provides a utility to have the thread wait for whatever milliseconds are specified.
     * Helpful for aiding in addressing those selenium race conditions.
     *
     * @param millis Number of milliseconds to wait.
     */
    public static void pause (long millis) {
        try {
            Thread.sleep(millis);
        } catch (Exception ex) {
            // do nothing
        }
    }


    /***
     *
     * @param methodName
     * @param browserName
     * @return
     */
    public static String getScreenshotName(String methodName, String browserName) {
        String localDateTime = getCurrentDateTime();
        return browserName +
                "_" +
                methodName +
                "_" +
                localDateTime +
                ".png";
    }

    /***
     * Get Random number within specified range
     * @param min
     * @param max
     * @return a random number
     */
    public static int getRandomNumber(int min, int max) {
        int diff = max - min;
        int randomNum = (int)(min + Math.random() * diff);
        logger.info("Random Number :: " + randomNum +
                " within range :: " + min + " and :: " + max);
        return randomNum;
    }

    /***
     * Get Random number within specified range
     * @param number
     * @return a random number
     */
    public static int getRandomNumber(int number) {
        return getRandomNumber(1, number);
    }

    /***
     * Get random unique string with specified length
     * @param length
     * @return a unique string
     */
    public static String getRandomString(int length) {
        StringBuilder sbuilder = new StringBuilder();
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        for (int i = 0; i<length; i++) {
            int index = (int) (Math.random() * chars.length());
            sbuilder.append(chars.charAt(index));
        }
        String randomString = sbuilder.toString();
        logger.info("Random string with length :: "
                + length + " is :: " + randomString);
        return randomString;
    }

    /***
     * Get random unique string with 10 characters length
     * @return a unique string
     */
    public static String getRandomString() {
        return getRandomString(10);
    }

    /***
     * Get simple date as string in the specified format
     * @param format MMddyy MMddyyyy
     * @return date as string type
     */
    public static String getSimpleDateFormat(String format){
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        String formattedDate = formatter.format(date);
        logger.info("Date with format :: "
                + format + " :: " + formattedDate);
        return formattedDate;
    }

    /***
     * Get simple date time as string
     * @return date time as string type
     */
    public static String getCurrentDateTime(){
        Calendar currentDate = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat(
                "MM/dd/yyyy HH:mm:ss");
        String date = formatter.format(currentDate.getTime()).replace("/", "_");
        date = date.replace(":", "_");
        logger.info("Date and Time :: " + date);
        return date;
    }

    /**
     * Checks whether actual String contains expected string and prints both in log
     * @param actualText - actual Text picked up from application under Test
     * @param expText - expected Text to be checked with actual text
     * @return boolean result
     */
    public static boolean verifyTextContains(String actualText, String expText) {
        if (actualText.toLowerCase().contains(expText.toLowerCase())){
            logger.info("Actual Text From Web Application UI   --> : "+ actualText);
            logger.info("Expected Text From Web Application UI --> : "+ expText);
            logger.info("### Verification Contains !!!");
            return true;
        }
        else{
            logger.info("Actual Text From Web Application UI   --> : "+ actualText);
            logger.info("Expected Text From Web Application UI --> : "+ expText);
            logger.info("### Verification DOES NOT Contains !!!");
            return false;
        }

    }

    /**
     * Checks whether actual string matches with expected string and prints both in log
     * @param actualText - actual Text picked up from application under Test
     * @param expText - expected Text to be matched with actual text
     * @return
     */
    public static boolean verifyTextMatch(String actualText, String expText) {
        if (actualText.equals(expText)){
            logger.info("Actual Text From Web Application UI   --> : "+ actualText);
            logger.info("Expected Text From Web Application UI --> : "+ expText);
            logger.info("### Verification MATCHED !!!");
            return true;
        }else{
            logger.info("Actual Text From Web Application UI   --> : "+ actualText);
            logger.info("Expected Text From Web Application UI --> : "+ expText);
            logger.info("### Verification DOES NOT MATCH !!!");
            return false;
        }
    }

    /**
     * Verify actual list contains items of the expected list
     *
     * @param actList
     * @param expList
     * @return
     */
    public static Boolean verifyListContains(List<String> actList, List<String> expList) {
        int expListSize = expList.size();
        for (String s : expList) {
            if (!actList.contains(s)) {
                return false;
            }
        }
        logger.info("Actual List Contains Expected List !!!");
        return true;
    }

    /***
     * Verify actual list matches expected list
     * @param actList
     * @param expList
     * @return
     */
    public static Boolean verifyListMatch(List<String> actList, List<String> expList) {
        boolean found = false;
        int actListSize = actList.size();
        int expListSize = expList.size();
        if (actListSize != expListSize) {
            return false;
        }

        for (String s : actList) {
            found = false;
            for (String value : expList) {
                if (verifyTextMatch(s, value)) {
                    found = true;
                    break;
                }
            }
        }
        if (found) {
            logger.info("Actual List Matches Expected List !!!");
            return true;
        }
        else {
            logger.info("Actual List DOES NOT Match Expected List !!!");
            return false;
        }
    }

    /**
     * Verifies item is present in the list
     * @param actList
     * @param item
     * @return boolean result
     */
    public static Boolean verifyItemPresentInList(List<String> actList, String item){
        int actListSize = actList.size();
        for (int i = 0; i < actListSize; i++) {
            if (!actList.contains(item)) {
                logger.info("Item is NOT present in List !!!");
                return false;
            }
        }
        logger.info("Item is present in List !!!");
        return true;
    }

    /**
     * Verify if list is in ascending order
     * @param list
     * @return boolean result
     */
    public static boolean isListAscendingOrder(List<Long> list){
        return Ordering.natural().isOrdered(list);
    }
}

