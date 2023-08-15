package com.llts.test.base;

import org.openqa.selenium.WebDriver;

public class BasePage extends CustomDriver {
    protected BasePage(WebDriver driver) {
        super(driver);
    }

    public boolean verifyTitle(String expected) {
        return getTitle().equals(expected);
    }
}
