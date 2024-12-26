package com.selenium.automation.pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.selenium.automation.utils.GeneralHelper;

public class BasePage {
    protected WebDriver driver;
    protected GeneralHelper generalHelper;
    private By acceptCookiesBtn = By.id("didomi-notice-agree-button");


    public BasePage(WebDriver driver){
        this.driver = driver;
        this.generalHelper = new GeneralHelper(driver);
    }

    public WebElement waitForHomePageToLoad(By startingImageLocator) throws InterruptedException{
        return generalHelper.fluentWaitForElement(startingImageLocator);
    }

    public void handleCookieConsent(){
        try {
            if(generalHelper.fluentWaitForElementToBeClickable(acceptCookiesBtn).isDisplayed()){
                System.out.println("Cookies consent popup detected. Accepting...");
                generalHelper.fluentSafeClick(acceptCookiesBtn);
                System.out.println("Cookies accepted.\n");
            }
        } catch (Exception e) {
            System.out.println("No consent popup appeared. Continuing with automation.\n");
        }
    }
}
