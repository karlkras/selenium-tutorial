package com.llts.test.pageclasses.services;

import com.llts.test.base.BaseModule;
import com.llts.test.pageclasses.LoginPage;
import com.llts.test.utilities.Tools;
import org.openqa.selenium.WebDriver;

public class NavigationService extends BaseModule {

    private static final String BASE_NAV_LINK = "xpath=>//a[normalize-space()='%s']";
    private static final String HOME_LINK;

    private static final String ALL_COURSES_LINK;

    private static final String MY_COURSES_LINK;
    private static final String INTERVIEW_LINK;
    private static final String SUPPORT_LINK;
    private static final String BLOG_LINK;
    private static final String PRACTICE_LINK;

    private static final String ACCOUNT_ICON = "class=>zl-navbar-rhs-img";


    private static final String LOGIN_LINK = "xpath=>//a[normalize-space()='Sign In']";


    private static final String LOGOUT_LINK = "xpath=>//a[@href='/logout']";

    static{
        ALL_COURSES_LINK = String.format(BASE_NAV_LINK, "ALL COURSES");
        MY_COURSES_LINK = String.format(BASE_NAV_LINK, "MY COURSES");
        HOME_LINK = String.format(BASE_NAV_LINK, "HOME");
        INTERVIEW_LINK = String.format(BASE_NAV_LINK, "INTERVIEW");
        SUPPORT_LINK = String.format(BASE_NAV_LINK, "SUPPORT");
        BLOG_LINK = String.format(BASE_NAV_LINK, "BLOG");
        PRACTICE_LINK = String.format(BASE_NAV_LINK, "PRACTICE");
    }

    public enum PageLink {
        AllCourses(ALL_COURSES_LINK),
        MyCourses(MY_COURSES_LINK),
        Home(HOME_LINK),
        Interview(INTERVIEW_LINK),
        Support(SUPPORT_LINK),
        Blog(BLOG_LINK),
        Practice(PRACTICE_LINK);

        final String value;
        PageLink(String value) {
            this.value = value;
        }
    }

    public NavigationService(WebDriver driver) {
        super(driver);
    }

    public void getPage(PageLink type) {
        Tools.pause(2000);
        elementClick(type.value, type.value);
    }

    public boolean verifyHeader() {
        return false;
    }

    public boolean isUserLoggedIn() {
        try {
            getElement(ACCOUNT_ICON);
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    public SearchResults searchCourse(String courseName) {
        return (new SearchBar(theDriver)).searchCourse(courseName);
    }

    public LoginPage login() {
        if(!isUserLoggedIn()) {
            getElement(LOGIN_LINK).click();
            return new LoginPage(theDriver);
        }
        return null;
    }

    public void logout() {
        if(isUserLoggedIn()) {
            elementClick(ACCOUNT_ICON, "account icon");
            javascriptClick(waitForElementToBeClickable(LOGOUT_LINK, 6),
                    "Logout Link");
        }
    }
}
