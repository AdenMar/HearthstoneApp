package edu.quinnipiac.ser210.hearthsearchapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SearchActivity extends AppCompatActivity {

    String url;
    EditText searchEditText;
    Button searchEnterBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
    }

    public void onSearchButtonClick(View view) {
        Intent intent = new Intent(this, ResultActivity.class);
        startActivity(intent);
        searchEnterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url = "https://omgvamp-hearthstone-v1.p.rapidapi.com/cards/search/{name}" + searchEditText.getText();
                new ResultActivity.NetworkCall().execute(url);
            }
        });
    }
}
