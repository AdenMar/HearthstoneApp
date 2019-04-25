package edu.quinnipiac.ser210.hearthsearchapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.android.gms.tasks.*;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.firestore.Source;

public class FavoritesActivity extends AppCompatActivity {

    RadioButton one;
    RadioButton two;
    RadioButton three;
    RadioButton four;
    RadioButton five;
    RadioButton six;
    RadioButton seven;
    RadioButton eight;
    RadioButton nine;
    RadioButton ten;
    RadioButton eleven;
    RadioButton twelve;
    String text;
    String TempDeck[];

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        text = intent.getStringExtra("name");

        one = (RadioButton) findViewById(R.id.favOne);
        two = (RadioButton) findViewById(R.id.favTwo);
        three = (RadioButton) findViewById(R.id.favThree);
        four = (RadioButton) findViewById(R.id.favFour);
        five = (RadioButton) findViewById(R.id.favFive);
        six = (RadioButton) findViewById(R.id.favSix);
        seven = (RadioButton) findViewById(R.id.favSeven);
        eight = (RadioButton) findViewById(R.id.favEight);
        nine = (RadioButton) findViewById(R.id.favNine);
        ten = (RadioButton) findViewById(R.id.favTen);
        eleven = (RadioButton) findViewById(R.id.favEleven);
        twelve = (RadioButton) findViewById(R.id.favTwelve);

        loadFavorites("slot01", one);
        loadFavorites("slot02", two);
        loadFavorites("slot03", three);
        loadFavorites("slot04", four);
        loadFavorites("slot05", five);
        loadFavorites("slot06", six);
        loadFavorites("slot07", seven);
        loadFavorites("slot08", eight);
        loadFavorites("slot09", nine);
        loadFavorites("slot10", ten);
        loadFavorites("slot11", eleven);
        loadFavorites("slot12", twelve);

        //updateFavorite();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.findItem(R.id.action_favorite).setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }

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

    public void onSearchButtonClick(View view) {
        Intent intent = new Intent(this, ResultActivity.class);
        if (one.isChecked()) {
            intent.putExtra("name", one.getText().toString());
        } else if (two.isChecked()) {
            intent.putExtra("name", two.getText().toString());
        } else if (three.isChecked()) {
            intent.putExtra("name", three.getText().toString());
        } else if (four.isChecked()) {
            intent.putExtra("name", four.getText().toString());
        } else if (five.isChecked()) {
            intent.putExtra("name", five.getText().toString());
        } else if (six.isChecked()) {
            intent.putExtra("name", six.getText().toString());
        } else if (seven.isChecked()) {
            intent.putExtra("name", seven.getText().toString());
        } else if (eight.isChecked()) {
            intent.putExtra("name", eight.getText().toString());
        } else if (nine.isChecked()) {
            intent.putExtra("name", nine.getText().toString());
        } else if (ten.isChecked()) {
            intent.putExtra("name", ten.getText().toString());
        } else if (eleven.isChecked()) {
            intent.putExtra("name", eleven.getText().toString());
        } else if (twelve.isChecked()) {
            intent.putExtra("name", twelve.getText().toString());
        }

        String nameText = "";
        intent.putExtra("type", nameText);
        startActivity(intent);
    }

    public void onRemoveButtonClick(View view) {

    }

    public void loadFavorites(String fav, final RadioButton num) {
        DocumentReference docRef = db.collection("favorites").document(fav);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("FB", "DocumentSnapshot data: " + document.getData());
                        num.setText(document.getData().get("card").toString());
                    } else {
                        Log.d("FB", "No such document");
                    }
                } else {
                    Log.d("FB", "get failed with ", task.getException());
                }
            }
        });
    }

    public void updateFavorite(String slot) {
        Map<String, Object> decks = new HashMap<>();

        DocumentReference docRef = db.collection("favorites").document(slot);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("FB", "DocumentSnapshot data: " + document.getData());
                        if (document.getData().get("isOccupied").equals(false)){

                        }
                    } else {
                        Log.d("FB", "No such document");
                    }
                } else {
                    Log.d("FB", "get failed with ", task.getException());
                }
            }
        });





        db.collection("favorites").document("slot")
                .update(decks)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("FB", "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("FB", "Error writing document", e);
                    }
                });
    }


}
