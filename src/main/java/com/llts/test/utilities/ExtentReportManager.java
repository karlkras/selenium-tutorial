package com.llts.test.utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

public class ExtentReportManager {
    private static final Logger logger = LogManager.getLogger(ExtentReportManager.class.getName());
    private static ExtentReports extent;

    public static ExtentReports getInstance() {
        if (extent == null) {
            extent = createInstance();
        }
        return extent;
    }

    public static synchronized ExtentReports createInstance() {
        String fileName = Tools.getReportName();
        String reportsDirectory = String.format("%s%s", System.getProperty("user.dir"), "/reports");
        //noinspection ResultOfMethodCallIgnored
        new File(reportsDirectory).mkdirs();
        String path = String.format("%s/%s", reportsDirectory, fileName);
        logger.info("*********** Report Path ***********");
        logger.info(path);
        logger.info("*********** Report Path ***********");
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(path);
        htmlReporter.config().setTheme(Theme.DARK);
        htmlReporter.config().setDocumentTitle("Automation Run");
        htmlReporter.config().setEncoding("utf-8");
        htmlReporter.config().setReportName(fileName);

        extent = new ExtentReports();
        extent.setSystemInfo("Organization", "LanguageLine Solutions");
        extent.setSystemInfo("Automation Framework", "Selenium WebDriver");
        extent.attachReporter(htmlReporter);

        return extent;
    }
}
