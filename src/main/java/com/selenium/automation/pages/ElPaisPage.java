package com.selenium.automation.pages;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.selenium.automation.utils.TranslateText;

public class ElPaisPage extends BasePage {

    private By startingImageLocator = By.xpath("//img[@class=\"ep_i\"]");
    private By opinionSectionLink = By.linkText("Opinión");
    private By articlesLocator = By.tagName("article");


    Map<Integer, String[]> articleMap = new HashMap<>();
    Map<Integer, String[]> translatedArticleMap = new HashMap<>();
    Map<String, Integer> wordCount = new HashMap<>();

    public ElPaisPage(WebDriver driver){
        super(driver);
    }

    public String getPageTitle(){
        return driver.getTitle();
    }

    public WebElement checkStartingImage() throws Exception{
        return waitForHomePageToLoad(startingImageLocator);
    }

    public void navigateToOpinionSection(){
        generalHelper.fluentSafeClick(opinionSectionLink);
    }

    public Map<Integer, String[]> getArticlesAndStore(){
        List<WebElement> articles = generalHelper.fluentWaitForElements(articlesLocator);
        for (int i = 0; i < 5; i++) {

            WebElement currEle = articles.get(i);
            String title = currEle.findElement(By.tagName("h2")).getAttribute("textContent");
            String content = currEle.findElement(By.tagName("p")).getAttribute("textContent");
            
            articleMap.put(i+1, new String[]{ title, content });
        
            System.out.println("\n===========================");
            System.out.println("Article " + (i+1));
            System.out.println("===========================");
            System.out.println("Title:");
            System.out.println(" " + title);
            System.out.println("Content:");
            System.out.println(" " + content);

            try {
                WebElement coverImageElement = currEle.findElement(By.tagName("img"));
                String imageURL = coverImageElement.getAttribute("src");

                if(imageURL != null && !imageURL.isEmpty()){
                    System.out.println("\n \u2714 Cover image found for the article.");
                    System.out.println("  Downloading...");
                    generalHelper.downloadImage(imageURL, "Article" + (i+1) + ".jpg");
                    System.out.println(" \u2714 Cover image saved as 'Article" + (i + 1) + ".jpg'.");
                }else{
                    System.out.println("\n [!] No cover image found for this article.");
                }
            } catch (Exception e) {
                continue;
            }
        }

        return articleMap;
    }

    public Map<Integer, String[]> getEnglishTranslatedArticles(){
        System.out.println("\nArticles Translated from Spanish to English: ");

        for (int i = 0; i < 5; i++) {

            String title = articleMap.get(i+1)[0];
            String content = articleMap.get(i+1)[1];
            
            System.out.println("\n===========================");
            System.out.println("Article " + (i+1));
            System.out.println("===========================");
            String translatedTitle = TranslateText.translateText(title, "es", "en");
            String translatedContent = TranslateText.translateText(content, "es", "en");
            
            if(translatedTitle != null && translatedContent != null){
                System.out.print("Title: ");
                System.out.println(translatedTitle);

                System.out.print("Content: ");
                System.out.println(translatedContent);

                translatedArticleMap.put(i+1, new String[]{ translatedTitle, translatedContent });

            }
            System.out.println();
        }
        return translatedArticleMap;
    }

    public Map<String, Integer> analyzeTranslatedArticlesHeaders(){
        for(Map.Entry<Integer, String[]> entry : articleMap.entrySet()){
            String title = entry.getValue()[0];

            generalHelper.tokenizeAndCountWords(title, wordCount);
        }

        Boolean hasWords = false;

        // for(Map.Entry<String, Integer> entry : wordCount.entrySet()){
        //     if(entry.getValue() > 2){
        //         if(!hasWords){
        //             hasWords = true;
        //             System.out.println("The words with 2 or more occurences in headers are: ");
        //         }
        //         System.out.println("  " + entry.getKey() + ": " + entry.getValue());
        //     }
        // }

        if(!hasWords){
            System.out.println("No words found with 2 or more occurences in headers found.");
        }
        return wordCount;
    }
}
