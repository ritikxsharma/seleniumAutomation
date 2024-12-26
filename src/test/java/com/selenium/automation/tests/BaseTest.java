package com.selenium.automation.tests;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.selenium.automation.driverManager.DriverManager;
import com.selenium.automation.pages.BasePage;
import com.selenium.automation.pages.ElPaisPage;

import io.github.cdimascio.dotenv.Dotenv;

public class BaseTest {
    protected Dotenv dotenv = Dotenv.load();
    protected WebDriver driver;
    protected DriverManager driverManager = new DriverManager();
    protected BasePage basePage;
    protected ElPaisPage elPaisPage;
    private String url = dotenv.get("WEBPAGE_URL");
    private Boolean skipTests = false;

    @Parameters({"browser"})
    @BeforeClass
    public void setup(@Optional("chrome") String browser){
        System.out.println("-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-\n");
        driver = driverManager.getDriver();
        driver.get(url);
        driver.manage().window().maximize();
        basePage = new BasePage(driver);
        basePage.handleCookieConsent();
        elPaisPage = new ElPaisPage(driver);
    }

    @BeforeMethod
    public void beforeMethod(ITestResult result){

        if(skipTests){
            System.out.println("[INFO] Skipping further automation and testing!");
            throw new SkipException("Skipping subsequent tests due to a previous failure.");
        }

        System.out.println("-----------------------------------------------------");
        System.out.println("[INFO] Execution Started: " + result.getMethod().getDescription());
    }

    @AfterMethod
    public void afterMethod(ITestResult result){
        if(result.getStatus() == ITestResult.SUCCESS){
            System.out.println("\u2714 Test Passed");
        }else if (result.getStatus() == ITestResult.FAILURE) {
            skipTests = true;
            System.out.println("\u2716 Test Failed");
            System.out.println("\u2716 Reason: " + result.getThrowable());
        } else if (result.getStatus() == ITestResult.SKIP) {
            skipTests = true;
            System.out.println("\u25CB Test Skipped");
            System.out.println("\u2716 Reason: " + result.getThrowable());
        }

        System.out.println("\u2714 Execution Completed\n");
    }

    @AfterClass
    public void tearDown(){
        driverManager.quitDriver();
    }
}
