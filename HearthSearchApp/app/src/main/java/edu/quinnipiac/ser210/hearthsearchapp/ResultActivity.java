/*
    Authors: Jillian Biasotti, Joe Ruiz, Aden Mariyappa
    Date: April 25 2019
    HearthSearch Application
 */
package edu.quinnipiac.ser210.hearthsearchapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ResultActivity extends AppCompatActivity {

    TextView displayInfo;
    String url;
    String result;
    String resultFromList;
    String urlSpec;
    JSONDataHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        displayInfo = findViewById(R.id.displayInfo);

        Intent intent = getIntent();
        result = intent.getStringExtra("displayText");
        resultFromList = intent.getStringExtra("selection");
        //removes spaces from string
        displayInfo.setText("you chose " + result);
        displayInfo.setText(result);
        while(resultFromList != null)
        {
            url = "https://omgvamp-hearthstone-v1.p.rapidapi.com/cards/" + resultFromList;
            new NetworkCall().execute(url);
            Log.d("URL", url);
        }



        //new DownloadImageFromInternet((ImageView) findViewById(R.id.cardImage))
         //       .execute(handler.imgGold);
    }

    //inflates the action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //adds functions for the action bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case R.id.action_help:
                Intent intent = new Intent(this, AboutActivity.class);
                return true;

            case R.id.action_favorite:
                Intent intentfavorite = new Intent(this, DeckActivity.class);
                String displayText = displayInfo.getText().toString();
                displayText = displayText.split("\n")[0];
                String isolatedName = displayText.replace("Card Name: ","");
                Toast.makeText(this, "You added " + isolatedName + " to your favorites!", Toast.LENGTH_LONG).show();
                intentfavorite.putExtra("name", isolatedName);
                String deckName = "favorites";
                intentfavorite.putExtra("deck", deckName);
                startActivity(intentfavorite);
                return true;

            case R.id.action_deck:
                Toast.makeText(this, "Adding card to deck!", Toast.LENGTH_LONG).show();
                Intent intentdeck = new Intent(this, SelectDeckActivity.class);
                String displayTextDeck = displayInfo.getText().toString();
                displayText = displayTextDeck.split("\n")[0];
                String isolatedNameDeck = displayText.replace("Card Name: ","");
                Toast.makeText(this, "You added " + isolatedNameDeck + " to your favorites!", Toast.LENGTH_LONG).show();
                intentdeck.putExtra("name", isolatedNameDeck);
                startActivity(intentdeck);

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public class NetworkCall extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String cardDataJSON = null;

            try{
                URL url = new URL(strings[0]);

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("X-RapidAPI-Key","8267e69849msh50454c6cb8b515dp149b89jsn935699dc0ac6");

                urlConnection.connect();

                InputStream in = urlConnection.getInputStream();
                if(in == null) {
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(in));

                cardDataJSON = getBufferStringFromBuffer(reader).toString();

            } catch (Exception e) {
                Log.e("error","Error" + e.getMessage());
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null){
                    try{
                        reader.close();
                    } catch (IOException e){
                        Log.e("error", "Error" + e.getMessage());
                        return null;
                    }
                }
            }
            return cardDataJSON;
        }

        protected void onPostExecute(String result){
            if (result != null){
                try {
                    String displayString = new JSONDataHandler().getCardData(result);
                    displayInfo.setText(displayString);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }
        private StringBuffer getBufferStringFromBuffer(BufferedReader br) throws Exception{
            StringBuffer buffer = new StringBuffer();

            String line;
            while((line = br.readLine()) != null){
                buffer.append(line + '\n');
            }

            if (buffer.length() == 0)
                return null;

            return buffer;
        }
    }


}
