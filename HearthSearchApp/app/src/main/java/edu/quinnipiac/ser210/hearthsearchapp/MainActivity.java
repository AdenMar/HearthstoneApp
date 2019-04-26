/*
    Authors: Jillian Biasotti, Joe Ruiz, Aden Mariyappa
    Date: April 25 2019
    HearthSearch Application
 */
package edu.quinnipiac.ser210.hearthsearchapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    //functions for the about button
    public void onAboutClick(View view) {

        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }

    //functions for the start button
    public void onStartClick(View view) {

        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    //inflates the action bar
    @Override
    public boolean onCreateOptionsMenu (Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.findItem(R.id.action_deck).setVisible(false);
        menu.findItem(R.id.action_favorite).setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }

    //adds functions to the action bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.action_help:
                Intent intent = new Intent(this, AboutActivity.class);
                return true;

            case R.id.action_favorite:
                Toast.makeText(this, "You favorited this card!", Toast.LENGTH_LONG).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);


        }
    }
}
