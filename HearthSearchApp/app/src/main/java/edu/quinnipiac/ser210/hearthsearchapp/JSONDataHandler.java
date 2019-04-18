package edu.quinnipiac.ser210.hearthsearchapp;

import android.graphics.Bitmap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;

public class JSONDataHandler {


    public String getCardData(String JSONString) throws JSONException {
        String name,cardSet,type,health,text,playerClass,imgGold,attack;

        JSONArray cardData = new JSONArray(JSONString);

        JSONObject main = cardData.getJSONObject(0);

        //imgGold = main.getString("imgGold");
        name = main.getString("name");
        cardSet = main.getString("cardSet");
        type = main.getString("type");
        health = main.getString("health");
        text = main.getString("text");
        playerClass = main.getString("playerClass");
        attack = main.getString("attack");


        String toset = //imgGold
                  "\nCard Name: "+ name
                + "\nPlayer Class: "+ playerClass
                + "\nCard Set: " + cardSet
                + "\nCard Type: " + type
                + "\nAttack: " + attack
                + "\nHealth: " + health;
                //+ "\n"+ text;
        return toset;

    }


}