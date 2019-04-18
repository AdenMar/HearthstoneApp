package edu.quinnipiac.ser210.hearthsearchapp;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONDataHandler {


    public String getCardData(String JSONString) throws JSONException {
        String name,cardSet,type,health,text,playerClass,imgGold;
        JSONObject cardData = new JSONObject(JSONString);

        name = cardData.getString("name");
        cardSet = cardData.getString("cardSet");
        type = cardData.getString("type");
        health = cardData.getString("health");
        text = cardData.getString("text");
        playerClass = cardData.getString("playerClass");
        imgGold = cardData.getString("imgGold");


        String toset = imgGold
                + "\n"+ name
                + "\n"+ playerClass
                + "\nCard Set: " + cardSet
                + "\nCard Type: " + type
                + "\nHealth: " + health
                + "\n"+ text;
        return toset;

    }
}