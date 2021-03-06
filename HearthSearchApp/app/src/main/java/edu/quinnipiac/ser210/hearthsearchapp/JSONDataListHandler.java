package edu.quinnipiac.ser210.hearthsearchapp;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.InputStream;
import java.util.ArrayList;

public class JSONDataListHandler {

    public ArrayList<String> resultNames;
    public String getCardData(String JSONString) throws JSONException {
        String name, cardSet, type, health, text, imgGold, playerClass, attack, cost;

        JSONArray cardData = new JSONArray(JSONString);
        resultNames = new ArrayList<String>();

        StringBuilder dataString = new StringBuilder();

        try {
            for (int i = 0; i < cardData.length(); i++) {
                cardData.getJSONObject(i);
                name = cardData.getJSONObject(i).getString("name");

                resultNames.add(name);
                dataString.append("Card Name: " + name + "\n");
                Log.d("List", String.valueOf(resultNames));
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