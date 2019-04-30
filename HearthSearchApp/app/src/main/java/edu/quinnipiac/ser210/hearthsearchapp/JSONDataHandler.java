package edu.quinnipiac.ser210.hearthsearchapp;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.InputStream;

public class JSONDataHandler {


    public String getCardData(String JSONString) throws JSONException {
        String name, cardSet, type, health, text, imgGold, playerClass, attack, cost;

        JSONArray cardData = new JSONArray(JSONString);

        StringBuilder dataString = new StringBuilder();

        try {
            for (int i = 0; i < cardData.length(); i++) {
                cardData.getJSONObject(i);
                name = cardData.getJSONObject(i).getString("name");
                cardSet = cardData.getJSONObject(i).getString("cardSet");
                type = cardData.getJSONObject(i).getString("type");
                health = cardData.getJSONObject(i).getString("health");
                attack = cardData.getJSONObject(i).getString("attack");
                cost = cardData.getJSONObject(i).getString("cost");

                dataString.append("Card Name: " + name + "\n");
                dataString.append("Card Set: " + cardSet+"\n");
                dataString.append("Type: " + type+"\n");
                dataString.append("Health: " + health+"\n");
                dataString.append("Attack: " + attack +"\n");
                dataString.append("Cost: " + cost +"\n");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    return dataString.toString();
    }

    public static class DownloadImageFromInternet extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;

        public DownloadImageFromInternet(ImageView imageView) {
            this.imageView = imageView;
        }

        protected Bitmap doInBackground(String... urls) {
            String imageURL = urls[0];
            Bitmap bimage = null;
            try {
                InputStream in = new java.net.URL(imageURL).openStream();
                bimage = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error Message", e.getMessage());
                e.printStackTrace();
            }
            return bimage;
        }

        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
        }
    }
}