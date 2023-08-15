package com.llts.test.pageclasses;

import com.llts.test.pageclasses.services.NavigationService;
import com.llts.test.utilities.Tools;
import com.llts.test.base.BasePage;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {
    public LoginPage(WebDriver driver) {
        super(driver);
    }

    private static final String EMAIL_FIELD = "id=>email";
    private static final String PASSWORD_FIELD = "id=>login-password";
    private static final String LOGIN_BUTTON = "id=>login";


    public NavigationService signInWith(String userEmail, String password) {
        sendData(EMAIL_FIELD, userEmail, "email", true);
        sendData(PASSWORD_FIELD, password, "password", true);
        elementClick(LOGIN_BUTTON, "login button");
        Tools.pause(2000);
        return new NavigationService(theDriver);
    }

}
