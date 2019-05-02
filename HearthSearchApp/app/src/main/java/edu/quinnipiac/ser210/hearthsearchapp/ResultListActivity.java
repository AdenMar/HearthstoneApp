package edu.quinnipiac.ser210.hearthsearchapp;

import android.content.Intent;
import android.net.Network;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class ResultListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView resultListView;
    private ArrayAdapter<String> listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_list);
        Intent intent = getIntent();
        ArrayList<String> resultList = intent.getStringArrayListExtra("displayListText");
        resultListView = (ListView) findViewById(R.id.resultListView);
        resultListView.setOnItemClickListener(this);

        //String[] test = new String[]{"Jill", "Joe", "Aden"};
     //   ArrayList<String> testList = new ArrayList<String>();

       // testList.addAll(Arrays.asList(test));

        listAdapter = new ArrayAdapter<String>(this, R.layout.list_row, resultList);

        if(resultList != null)
        {
            resultListView.setAdapter(listAdapter);
        }
        else
        {
            Log.d("Error", "list is null");
        }
    }
    public void onItemClick(AdapterView<?> l, View v, int position, long id) {
        Log.i("HelloListView", "You clicked Item: " + id + " at position:" + position);
        // Then you start a new Activity via Intent
        String selected =(resultListView.getItemAtPosition(position).toString());
        selected.replaceAll(" ","");
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra("selection", selected);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.findItem(R.id.action_deck).setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }
}
