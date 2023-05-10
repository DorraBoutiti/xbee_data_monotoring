package com.example.xbee_data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ThingSpeakAPI {
    private static final String BASE_URL = "https://api.thingspeak.com/channels/";
    private static final String READ_API_KEY = "0O2P3K2VHSYB52YW";
    private static final int CHANNEL_ID = 2136774;
    private static final int NUM_ENTRIES = 10;

    public static List<Entry> getLastEntries() throws IOException, JSONException {
        List<Entry> entries = new ArrayList<>();
        String urlString = BASE_URL + CHANNEL_ID + "/feeds.json?api_key=" + READ_API_KEY + "&results=" + NUM_ENTRIES;
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();
        connection.disconnect();

        JSONObject json = new JSONObject(response.toString());
        JSONArray feeds = json.getJSONArray("feeds");
        for (int i = 0; i < feeds.length(); i++) {
            JSONObject feed = feeds.getJSONObject(i);
            Entry entry = new Entry();
            entry.setField1(feed.getString("field1"));
            entry.SetCreated_at(feed.getString("created_at"));
            // Add more fields as needed
            entries.add(entry);
        }
        return entries;
    }
}