/*
    Authors: Jillian Biasotti, Joe Ruiz, Aden Mariyappa
    Date: April 25 2019
    HearthSearch Application
 */
package edu.quinnipiac.ser210.hearthsearchapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.android.gms.tasks.*;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONException;

public class DeckActivity extends AppCompatActivity {

    Button slotOne;
    Button slotTwo;
    Button slotThree;
    Button slotFour;
    Button slotFive;
    Button slotSix;
    Button slotSeven;
    Button slotEight;
    Button slotNine;
    Button slotTen;
    Button slotEleven;
    Button slotTwelve;
    TextView deckName;
    Switch slotEdit;
    String text;
    String deck;
    String deckCollection;
    Intent resultIntent;

    // creates an instance of our FireBase FireStore database
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deck);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        text = intent.getStringExtra("name");
        deck = intent.getStringExtra("deck");
        deckCollection = deck;

        resultIntent = new Intent(this, ResultActivity.class);

        slotOne = (Button) findViewById(R.id.slotOne);
        slotTwo = (Button) findViewById(R.id.slotTwo);
        slotThree = (Button) findViewById(R.id.slotThree);
        slotFour = (Button) findViewById(R.id.slotFour);
        slotFive = (Button) findViewById(R.id.slotFive);
        slotSix = (Button) findViewById(R.id.slotSix);
        slotSeven = (Button) findViewById(R.id.slotSeven);
        slotEight = (Button) findViewById(R.id.slotEight);
        slotNine = (Button) findViewById(R.id.slotNine);
        slotTen = (Button) findViewById(R.id.slotTen);
        slotEleven = (Button) findViewById(R.id.slotEleven);
        slotTwelve = (Button) findViewById(R.id.slotTwelve);
        slotEdit = (Switch) findViewById(R.id.editSwitch);
        deckName = (TextView) findViewById(R.id.deckView);

        deckName.setText(deck);

        // updates and fills the buttons with the favorited cards
        updateCards();
        loadAllCards();

    }


    // inflates action bar men
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.findItem(R.id.action_favorite).setVisible(false);
        menu.findItem(R.id.action_deck).setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }


    // adds functions to the action bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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

    // when a card (button) is pressed, either search it or remove it base on the switch position
    public void cardClick(View view){
        String name = "";

        if (slotEdit.isChecked() == false) {

            if (view.getId() == slotOne.getId()) {
                name = slotOne.getText().toString();
            } else if (view.getId() == slotTwo.getId()) {
                name = slotTwo.getText().toString();
            } else if (view.getId() == slotThree.getId()) {
                name = slotThree.getText().toString();
            } else if (view.getId() == slotFour.getId()) {
                name = slotFour.getText().toString();
            } else if (view.getId() == slotFive.getId()) {
                name = slotFive.getText().toString();
            } else if (view.getId() == slotSix.getId()) {
                name = slotSix.getText().toString();
            } else if (view.getId() == slotSeven.getId()) {
                name = slotSeven.getText().toString();
            } else if (view.getId() == slotEight.getId()) {
                name = slotEight.getText().toString();
            } else if (view.getId() == slotNine.getId()) {
                name = slotNine.getText().toString();
            } else if (view.getId() == slotTen.getId()) {
                name = slotTen.getText().toString();
            } else if (view.getId() == slotEleven.getId()) {
                name = slotEleven.getText().toString();
            } else if (view.getId() == slotTwelve.getId()) {
                name = slotTwelve.getText().toString();
            }

            String url = "https://omgvamp-hearthstone-v1.p.rapidapi.com/cards/" + name;
            startActivity(resultIntent);
            new NetworkCall().execute(url);

        } else {

            if (view.getId() == slotOne.getId()) {
                removeCard("slot01");
            } else if (view.getId() == slotTwo.getId()) {
                removeCard("slot02");
            } else if (view.getId() == slotThree.getId()) {
                removeCard("slot03");
            } else if (view.getId() == slotFour.getId()) {
                removeCard("slot04");
            } else if (view.getId() == slotFive.getId()) {
                removeCard("slot05");
            } else if (view.getId() == slotSix.getId()) {
                removeCard("slot06");
            } else if (view.getId() == slotSeven.getId()) {
                removeCard("slot07");
            } else if (view.getId() == slotEight.getId()) {
                removeCard("slot08");
            } else if (view.getId() == slotNine.getId()) {
                removeCard("slot09");
            } else if (view.getId() == slotTen.getId()) {
                removeCard("slot10");
            } else if (view.getId() == slotEleven.getId()) {
                removeCard("slot11");
            } else if (view.getId() == slotTwelve.getId()) {
                removeCard("slot12");
            }
        }
    }


    // sets both document fields to base on param,
    // passed through to proper function
    public void removeCard(String slot){
        updateCard("", false, slot);
    }

    // cycles through each document, checking occupation status, if space is occupied, continue,
    // if not, assign card name to card and update occupation status
    public void updateCards() {
        if (text == null) {} else {

            db.collection(deckCollection)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d("FBU", document.getId() + " => " + document.getData());
                                    if (document.getData().get("isOccupied").equals(false)){
                                        updateCard(text, true, document.getId());
                                        break;
                                    }
                                }
                            } else {
                                Log.d("FBU", "Error getting documents: ", task.getException());
                            }
                        }
                    });
        }
    }

    // the proper function designed to update a given cards fields base on params
    public void updateCard(String card,Boolean isOccupied, String slot){
        db.collection(deckCollection).document(slot)
                .update(
                        "card", card,
                        "isOccupied", isOccupied
                );
        loadAllCards();
    }

    // applies proper card information based on params
    public void loadCard(String fav, final Button num) {
        DocumentReference docRef = db.collection(deckCollection).document(fav);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("FBL", "DocumentSnapshot data: " + document.getData());
                        num.setText(document.getData().get("card").toString());
                        if (document.getData().get("isOccupied").equals(true)) {
                            num.setVisibility(View.VISIBLE);
                        } else {
                            num.setVisibility(View.GONE);
                        }
                    } else {
                        Log.d("FBL", "No such document");
                    }
                } else {
                    Log.d("FBL", "get failed with ", task.getException());
                }
            }
        });
    }

    // utilizes previous function to apply all cards
    public void loadAllCards() {
        loadCard("slot01", slotOne);
        loadCard("slot02", slotTwo);
        loadCard("slot03", slotThree);
        loadCard("slot04", slotFour);
        loadCard("slot05", slotFive);
        loadCard("slot06", slotSix);
        loadCard("slot07", slotSeven);
        loadCard("slot08", slotEight);
        loadCard("slot09", slotNine);
        loadCard("slot10", slotTen);
        loadCard("slot11", slotEleven);
        loadCard("slot12", slotTwelve);
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
                    JSONDataHandler dataHandler = new JSONDataHandler();
                    String displayString = dataHandler.getCardData(result);
                    ArrayList<String> displayStringList = dataHandler.resultNames;
                    Log.d("String List", String.valueOf(displayStringList));
                    resultIntent.putExtra("displayText", displayString);

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
