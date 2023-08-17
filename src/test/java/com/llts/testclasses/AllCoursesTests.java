package com.llts.testclasses;

import com.llts.test.base.CheckPoint;
import com.llts.test.pageclasses.services.CategoryFilter;
import com.llts.test.pageclasses.services.NavigationService.PageLink;
import com.llts.test.base.BaseTest;
import com.llts.test.utilities.ExcelUtility;
import com.llts.test.utilities.Tools;
import org.testng.annotations.*;

import java.net.URISyntaxException;

public class AllCoursesTests extends BaseTest {
    CategoryFilter catFilter;
    @DataProvider(name="verify_search_course")
    public Object[][] getSearchCourseData(){
        return ExcelUtility.getTestData("verify_search_course");
    }

    @BeforeClass
    @Parameters({"browser"})
    public void setup(@Optional("chrome") String browser) {
        super.setup(browser);
        navService = loginPage.signInWith(constant("userName"), constant("password"));
        try {
            ExcelUtility.setExcelFile(Tools.getFilePath("testdata/ExcelTestData.xlsx"),
                    "AllCoursesTests");
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @Test(dataProvider = "verify_search_course" )
    public void testSearchCourses(String courseName) {
        navService.getPage(PageLink.AllCourses);
        (navService.searchCourse(courseName)).verifySearchResult();
        CheckPoint.markFinal("test-02", (navService.searchCourse(courseName)).verifySearchResult(),
                String.format("testSearchCourses : %s", courseName));
    }

    @Test(enabled = false)
    public void testFilterByCategory() {
        navService.getPage(PageLink.AllCourses);
        catFilter = new CategoryFilter(theDriver);
        CheckPoint.markFinal("test-03",
                (catFilter.select("Sotfware Testing")).verifySearchResult(),
                "testFilterByCategory");
    }
}
