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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    RadioButton nameButton;
    RadioButton typeButton;
    RadioButton classButton;
    RadioButton factionButton;
    Intent intent;
    Intent listIntent;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        nameButton = findViewById(R.id.nameButton);
        typeButton = findViewById(R.id.typeButton);
        classButton = findViewById(R.id.classButton);
        factionButton = findViewById(R.id.factionButton);


        intent = new Intent(this, ResultActivity.class);
        listIntent = new Intent(this, ResultListActivity.class);

    }
	
    //inflates the action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.findItem(R.id.action_favorite).setVisible(false);
        menu.findItem(R.id.action_deck).setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }

    //adds functions to the action bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case R.id.action_help:
                Intent intent = new Intent(this, AboutActivity.class);
                return true;

            case R.id.action_favorite:
                Toast.makeText(this, "You favorited this card!", Toast.LENGTH_LONG).show();
                return true;

            case R.id.action_deck:
                Toast.makeText(this, "Added card to deck!", Toast.LENGTH_LONG).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //functions for the search button
    public void onSearchButtonClick(View view) {
        String urlSpec = "";
        EditText messageView = (EditText)findViewById(R.id.searchEditText);
        String name = messageView.getText().toString();

       if(nameButton.isChecked())
        {
            //default
            urlSpec = "";
            url = "https://omgvamp-hearthstone-v1.p.rapidapi.com/cards/" + urlSpec + name;
            startActivity(intent);
        }
        else if(typeButton.isChecked())
        {
           urlSpec = "types/";
            url = "https://omgvamp-hearthstone-v1.p.rapidapi.com/cards/" + urlSpec + name;
            startActivity(listIntent);
        }
        else if(classButton.isChecked())
        {
            urlSpec = "classes/";
            url = "https://omgvamp-hearthstone-v1.p.rapidapi.com/cards/" + urlSpec + name;
            startActivity(listIntent);

        }
        else if(factionButton.isChecked())
        {
            urlSpec = "factions/";
            url = "https://omgvamp-hearthstone-v1.p.rapidapi.com/cards/" + urlSpec + name;
            startActivity(listIntent);
        }
        new NetworkCall().execute(url);


    }

    public class NetworkCall extends AsyncTask<String, Void, String> {

        Intent intentFromList;
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
            intentFromList = getIntent();
            if (result != null){
                try {
                    JSONDataHandler dataHandler = new JSONDataHandler();
                    String displayString = dataHandler.getCardData(result);
                    ArrayList<String> displayStringList = dataHandler.resultNames;
                    Log.d("String List", String.valueOf(displayStringList));
                    listIntent.putStringArrayListExtra("displayListText",displayStringList);

                   // displayInfo.setText(displayString);
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
