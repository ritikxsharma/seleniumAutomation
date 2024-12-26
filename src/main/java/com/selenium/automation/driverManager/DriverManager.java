package com.selenium.automation.driverManager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DriverManager {
    private WebDriver driver;
    
    public DriverManager(){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        driver = new ChromeDriver(options);
    }

    public WebDriver getDriver(){
        return driver;
    }

    public void quitDriver(){
        if(driver!=null){
            driver.quit();
        }
    }
}
