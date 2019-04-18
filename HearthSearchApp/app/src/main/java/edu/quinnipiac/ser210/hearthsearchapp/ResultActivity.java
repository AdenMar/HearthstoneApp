package edu.quinnipiac.ser210.hearthsearchapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent intent = getIntent();
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_result, container, false);
        displayInfo = view.findViewById(R.id.displayInfo);
        url = "https://omgvamp-hearthstone-v1.p.rapidapi.com/cards/search/{name}";
        new NetworkCall().execute(url);

        return view;
    }
    private class NetworkCall extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String cardDataJSON = null;

            try{
                URL url = new URL(strings[0]);

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("X-RapidAPI-Key","7c3263565dmsh976e81b3f1c9f2cp1dfd9ejsnc779f8ee1461");

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
