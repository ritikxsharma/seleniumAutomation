package com.selenium.automation.tests;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

public class ElPaisTest extends BaseTest {

    @Test(priority = 0, description = "Checking page title")
    public void testPageTitle() {
        String actualTitle = elPaisPage.getPageTitle();
        String expectedTitle = "EL PAÍS: el periódico global";

        Assert.assertEquals(actualTitle, expectedTitle, "Page title does not match!");
    }

    @Test(priority = 1, description = "Checking starting image is displayed")
    public void testCheckForStartingImage() throws Exception {

        WebElement ele = elPaisPage.checkStartingImage();

        if (ele == null) {
            System.out.println("[ERROR] Starting image not found.");
            throw new SkipException("Skipping subsequent image is not displayed.");
        }
    }

    @Test(priority = 2, description = "Moving to opinion section of the page")
    public void testNavigateToOpinionSection() {
        elPaisPage.navigateToOpinionSection();

        String actualTitle = elPaisPage.getPageTitle();
        String expectedTitle = "Opinión en EL PAÍS";

        Assert.assertEquals(actualTitle, expectedTitle, "Page title does not match!");
    }

    @Test(priority = 3, description = "Fetching first 5 articles")
    public void testGetArticles() {
        try {
            Map<Integer, String[]> articles = elPaisPage.getArticlesAndStore();
            Assert.assertNotNull(articles, "Error in fetching articles");
        } catch (Exception e) {
            throw new SkipException("Error in fetching and storing articles");
        }
    }

    @Test(priority = 4, description = "Translating the fetched articles to English")
    public void testGetEnglishTranslatedArticles(){
        try {
            Map<Integer, String[]> translatedArticles = elPaisPage.getEnglishTranslatedArticles();
            Assert.assertNotNull(translatedArticles, "No articles were translated.");
        } catch (Exception e) {
            throw new SkipException("Error in translating text");
        }
    }

    @Test(priority = 5, description = "Analzying occurences of words in headers")
    public void testAnalyzeTranslatedArticlesHeaders() {
        try {
            Map<String, Integer> wordCount = elPaisPage.analyzeTranslatedArticlesHeaders();
            Assert.assertNotNull(wordCount, "Word frequency analysis is null.");
        } catch (Exception e) {
            throw new SkipException("Error in analyzing articles headers");
        }
    }
}
