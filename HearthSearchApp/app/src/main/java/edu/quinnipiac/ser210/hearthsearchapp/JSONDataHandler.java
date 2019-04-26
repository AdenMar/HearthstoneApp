/*
    Authors: Jillian Biasotti, Joe Ruiz, Aden Mariyappa
    Date: April 25 2019
    HearthSearch Application
 */
package edu.quinnipiac.ser210.hearthsearchapp;


import android.util.JsonReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
//gathers all the data from the API

public class JSONDataHandler {

    String imgGold;


    public String getCardData(String JSONString) throws JSONException {
        String name,cardSet,type,health,text,playerClass,attack,cost;

        JSONArray cardData = new JSONArray(JSONString);

        JSONObject main = cardData.getJSONObject(0);

        imgGold = main.getString("imgGold");



        name = main.getString("name");
        cardSet = main.getString("cardSet");
        type = main.getString("type");
        health = main.getString("health");
        text = main.getString("text");
        playerClass = main.getString("playerClass");
        attack = main.getString("attack");
        cost = main.getString("cost");


        String toset =
                  "Card Name: "+ name
                + "\nPlayer Class: "+ playerClass
                + "\nCard Set: " + cardSet
                + "\nCard Type: " + type
                + "\nCost: " + cost
                + "\nAttack: " + attack
                + "\nHealth: " + health;
                //+ "\n"+ text;
        return toset;

    }

}