package com.selenium.automation.utils;

import io.github.cdimascio.dotenv.Dotenv;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;

public class TranslateText {
    private static Dotenv dotenv = Dotenv.load();
    private static String apiURL = dotenv.get("API_URL");
    private static String apiKey = dotenv.get("RAPID_API_KEY");

    public static String translateText(String text, String sourceLang, String targetLang){
        try{
            HttpResponse<JsonNode> response = Unirest.post(apiURL)
            .header("x-rapidapi-key", apiKey)
            .header("x-rapidapi-host", "rapid-translate-multi-traduction.p.rapidapi.com")
            .header("Content-Type", "application/json")
            .body("{\"from\":\"" + sourceLang + "\",\"to\":\"" + targetLang + "\",\"q\":\"" + text + "\"}")
            .asJson();

            if(response.getStatus() == 200){
                String translatedText = response.getBody().getArray().getString(0);
                return translatedText;
            }else {
                System.out.println("Error in translating text ");
                return null;
            }
            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }
}
