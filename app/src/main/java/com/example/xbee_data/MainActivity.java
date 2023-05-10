package com.example.xbee_data;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.text_view);

        new AsyncTask<Void, Void, List<Entry>>() {
            @Override
            protected List<Entry> doInBackground(Void... voids) {
                try {
                    return ThingSpeakAPI.getLastEntries();
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(List<Entry> entries) {
                if (entries != null) {
                    StringBuilder sb = new StringBuilder();
                    for (Entry entry : entries) {
                        sb.append(entry.getField1()).append(", ").append(entry.getCreated_at()).append("\n");
                    }
                    textView.setText(sb.toString());
                }
            }
        }.execute();
    }
}
