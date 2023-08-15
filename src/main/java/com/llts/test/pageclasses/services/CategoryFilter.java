package com.llts.test.pageclasses.services;

import com.llts.test.base.BaseModule;
import com.llts.test.utilities.Tools;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class CategoryFilter extends BaseModule {

    private static final String CATEGORY_SELECT = "css=>select.categories";
    private static final String CATEGORY_OPTION = "xpath=>//option[contains(text(), '%s')]";
    public CategoryFilter(WebDriver driver) {
        super(driver);
    }

    public SearchResults select(String categoryName) {
        Select categorySelect =
                new Select(getElement(CATEGORY_SELECT));

        WebElement selectOption = getElement(String.format(CATEGORY_OPTION, categoryName));

        categorySelect.selectByValue(selectOption.getAttribute("value"));
        Tools.pause(2000);

        return new SearchResults(theDriver);
    }
}
