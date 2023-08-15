package com.llts.test.pageclasses.services;

import com.llts.test.base.BaseModule;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SearchBar extends BaseModule {
    private static final String SEARCH_INPUT = "css=>body > div:nth-child(1) > div:nth-child(3) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(3) > div:nth-child(2) > form:nth-child(1) > div:nth-child(2) > input:nth-child(1)";
    private static final String SEARCH_BUTTON = "css=>.fa-search";

    public SearchBar(WebDriver driver) {
        super(driver);
    }

    public SearchResults searchCourse(String course) {
        SearchResults results = null;
        try {
            sendData(elementClick(SEARCH_INPUT, "search input click"), course, "search input");

            elementClick(SEARCH_BUTTON, "search button");
            results = new SearchResults(theDriver);
        } catch (Exception ex) {
            // do nothing.
        }
        return results;

    }
}
