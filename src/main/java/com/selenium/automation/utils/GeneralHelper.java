package com.selenium.automation.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

public class GeneralHelper {
    private FluentWait<WebDriver> wait;
    private JavascriptExecutor jse;

    public GeneralHelper(WebDriver driver){
        this.wait = intializeFluentWait(driver);
        this.jse = (JavascriptExecutor)driver;
    }

    private FluentWait<WebDriver> intializeFluentWait(WebDriver driver){
        FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver);

        return wait
        .withTimeout(Duration.ofSeconds(20))
        .pollingEvery(Duration.ofSeconds(2))
        .ignoring(org.openqa.selenium.NoSuchElementException.class)
        .ignoring(org.openqa.selenium.StaleElementReferenceException.class);
    }

    public WebElement fluentWaitForElement(Object obj){
        try {
            if(obj instanceof By){
                return wait.until(driver -> driver.findElement((By)obj));
            }else{
                return wait.until(ExpectedConditions.visibilityOf((WebElement)obj));
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List<WebElement> fluentWaitForElements(Object obj){
        return wait.until(driver -> driver.findElements((By)obj));
    }

    public WebElement fluentWaitForElementToBeClickable(Object obj){
        try {
            if(obj instanceof By){
                return wait.until(ExpectedConditions.elementToBeClickable((By)obj));
            }else{
                return wait.until(ExpectedConditions.elementToBeClickable((WebElement)obj));
            }
        } catch (Exception e) {
            return null;
        }
    }

    public void fluentSafeClick(Object obj){
        try {
            WebElement ele = fluentWaitForElementToBeClickable(obj);
            ele.click();
            Thread.sleep(2000);
        } catch (Exception e) {
            System.out.println("Failed to click. Retrying...");
            WebElement ele = fluentWaitForElementToBeClickable(obj);
            jse.executeScript("arguments[0].click()", ele);
        }
    }

    public void downloadImage(String imageURL, String fileName){
        try {
            URL url = new URL(imageURL);
            InputStream in = url.openStream();

            File outputDirectory = new File(System.getProperty("user.dir") + "/src/main/resources/images/");

            if(!outputDirectory.exists()){
                outputDirectory.mkdirs();
            }
            File outputFile = new File(outputDirectory, fileName);

            FileOutputStream out = new FileOutputStream(outputFile);
            
            byte[] buffer = new byte[4096];
            int length;

            while((length = in.read(buffer)) != -1){
                out.write(buffer, 0, length);
            }

            in.close();
            out.close();

            //System.out.println("Image saved to: " + outputFile.getAbsolutePath());

        } catch (Exception e) {
            System.out.println("Failed to download or save the image: " + e.getMessage());
        }
    }

    public void tokenizeAndCountWords(String text, Map<String, Integer> map){
        String[] words = text.toLowerCase().split("\\W+");
        for(String word : words){
            if(!word.isEmpty()){
                map.put(word, map.getOrDefault(word, 0) + 1);
            }
        }
    }
}
