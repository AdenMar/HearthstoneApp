package edu.quinnipiac.ser210.hearthsearchapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class PickDeckActivity extends AppCompatActivity {

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
    Switch slotEdit;
    EditText createDeckName;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_deck);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
        createDeckName = (EditText) findViewById(R.id.createDeckName);

        loadAllFavorites();
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
    public void cardClick(View view) {

        if (slotEdit.isChecked() == false) {

            Intent intent = new Intent(this, DeckActivity.class);
            if (view.getId() == slotOne.getId()) {
                intent.putExtra("deck", slotOne.getText().toString());
            } else if (view.getId() == slotTwo.getId()) {
                intent.putExtra("deck", slotTwo.getText().toString());
            } else if (view.getId() == slotThree.getId()) {
                intent.putExtra("deck", slotThree.getText().toString());
            } else if (view.getId() == slotFour.getId()) {
                intent.putExtra("deck", slotFour.getText().toString());
            } else if (view.getId() == slotFive.getId()) {
                intent.putExtra("deck", slotFive.getText().toString());
            } else if (view.getId() == slotSix.getId()) {
                intent.putExtra("deck", slotSix.getText().toString());
            } else if (view.getId() == slotSeven.getId()) {
                intent.putExtra("deck", slotSeven.getText().toString());
            } else if (view.getId() == slotEight.getId()) {
                intent.putExtra("deck", slotEight.getText().toString());
            } else if (view.getId() == slotNine.getId()) {
                intent.putExtra("deck", slotNine.getText().toString());
            } else if (view.getId() == slotTen.getId()) {
                intent.putExtra("deck", slotTen.getText().toString());
            } else if (view.getId() == slotEleven.getId()) {
                intent.putExtra("deck", slotEleven.getText().toString());
            } else if (view.getId() == slotTwelve.getId()) {
                intent.putExtra("deck", slotTwelve.getText().toString());
            }

            String nameText = "";
            intent.putExtra("type", nameText);
            startActivity(intent);

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
    public void removeCard(String slot) {
        updateCard("", false, slot);
    }

    // cycles through each document, checking occupation status, if space is occupied, continue,
    // if not, assign card name to card and update occupation status
    public void updateFavorite(final String text) {

        db.collection("user_decks")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("FB", document.getId() + " => " + document.getData());
                                if (document.getData().get("isOccupied").equals(false)) {
                                    updateCard(text, true, document.getId());
                                    break;
                                }
                            }
                        } else {
                            Log.d("FB", "Error getting documents: ", task.getException());
                        }
                    }
                });

    }

    // the proper function designed to update a given cards fields base on params
    public void updateCard(String card, Boolean isOccupied, String slot) {
        db.collection("user_decks").document(slot)
                .update(
                        "card", card,
                        "isOccupied", isOccupied
                );
        loadAllFavorites();
    }

    // applies proper card information based on params
    public void loadFavorites(String fav, final Button num) {
        DocumentReference docRef = db.collection("user_decks").document(fav);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("FB", "DocumentSnapshot data: " + document.getData());
                        num.setText(document.getData().get("card").toString());
                        if (document.getData().get("isOccupied").equals(true)) {
                            num.setVisibility(View.VISIBLE);
                        } else {
                            num.setVisibility(View.GONE);
                        }
                    } else {
                        Log.d("FB", "No such document");
                    }
                } else {
                    Log.d("FB", "get failed with ", task.getException());
                }
            }
        });
    }

    // utilizes previous function to apply all cards
    public void loadAllFavorites() {
        loadFavorites("slot01", slotOne);
        loadFavorites("slot02", slotTwo);
        loadFavorites("slot03", slotThree);
        loadFavorites("slot04", slotFour);
        loadFavorites("slot05", slotFive);
        loadFavorites("slot06", slotSix);
        loadFavorites("slot07", slotSeven);
        loadFavorites("slot08", slotEight);
        loadFavorites("slot09", slotNine);
        loadFavorites("slot10", slotTen);
        loadFavorites("slot11", slotEleven);
        loadFavorites("slot12", slotTwelve);
    }

    public void onCreateDeck(View view) {
        for (int i = 1; i < 13; i++) {
            if (i < 10) {
                addDoc("slot0" + i);
            } else {
                addDoc("slot" + i);
            }
        }

        updateFavorite(createDeckName.getText().toString());
        createDeckName.setText("");

    }

    public void addDoc(String doc) {
        Map<String, Object> decks = new HashMap<>();
        decks.put("card", "");
        decks.put("isOccupied", false);

        db.collection(createDeckName.getText().toString()).document(doc)
                .set(decks)
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