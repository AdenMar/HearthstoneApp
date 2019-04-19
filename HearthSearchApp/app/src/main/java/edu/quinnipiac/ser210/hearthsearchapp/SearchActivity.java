package edu.quinnipiac.ser210.hearthsearchapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class SearchActivity extends AppCompatActivity {

    RadioButton nameButton;
    RadioButton typeButton;
    RadioButton classButton;
    RadioButton factionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();

        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        nameButton = findViewById(R.id.nameButton);
        typeButton = findViewById(R.id.typeButton);
        classButton = findViewById(R.id.classButton);
        factionButton = findViewById(R.id.factionButton);


    }

    //inflates the support action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //access the result screen by this button
    public void onSearchButtonClick(View view) {
        Intent intent = new Intent(this, ResultActivity.class);
        EditText messageView = (EditText)findViewById(R.id.searchEditText);

       /* if(nameButton.isSelected())
        {
            String nameText = messageView.getText().toString();
            intent.putExtra("name", nameText);
        }
        else if(typeButton.isSelected())
        {
            String typeText = messageView.getText().toString();
            intent.putExtra("type", typeText);
        }
        else if(classButton.isSelected())
        {
            String classText = messageView.getText().toString();
            intent.putExtra("class", classText);
        }
        else if(factionButton.isSelected())
        {
            String factionText = messageView.getText().toString();
            intent.putExtra("faction", factionText);
        }
        */
       String text = messageView.getText().toString();
       intent.putExtra("name", text);
       startActivity(intent);


    }
}
