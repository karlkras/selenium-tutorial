package com.llts.test.pageclasses.services;

import com.llts.test.base.BaseModule;
import com.llts.test.base.CustomDriver;
import com.llts.test.utilities.Tools;
import org.openqa.selenium.WebDriver;

public class SearchResults extends BaseModule {
    private static final String NO_SEARCH_RESULT = "css=>.text-center.dynamic-text";

    private static final String RESULT_LIST = "xpath=>//div[@class='zen-course-thumbnail']";

    public SearchResults(WebDriver driver) {
        super(driver);
    }

    public int searchCount() {
        Tools.pause(1000);
        return getElementList(RESULT_LIST).size();
    }

    public boolean verifySearchResult() {
        return searchCount() > 0;
    }
}
