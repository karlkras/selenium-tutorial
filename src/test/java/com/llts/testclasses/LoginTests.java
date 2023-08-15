package com.llts.testclasses;

import com.llts.test.base.BaseTest;
import com.llts.test.base.CheckPoint;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

public class LoginTests extends BaseTest {

    @AfterMethod
    public void afterMethod() {
        if(navService != null) {
            navService.logout();
            setUrl(constant("baseUrl"));
            navService.login();
        }
    }

    @Test
    public void testLogin(){
        navService = loginPage.signInWith(constant("userName"), constant("password"));
        CheckPoint.mark("test.01", navService.verifyHeader(), "header verification");
        CheckPoint.markFinal("test.01", navService.isUserLoggedIn(), "login verification");
    }

    @Test
    public void testInvalidLogin() {
        navService = loginPage.signInWith("junk@blah.com", "passwordJunk");
        assertFalse(navService.isUserLoggedIn());
    }
}
