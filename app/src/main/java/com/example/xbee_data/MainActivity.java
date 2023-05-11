package com.example.xbee_data;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;

import org.json.JSONException;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import android.graphics.Color;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private LineChart lineChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lineChart = findViewById(R.id.line_chart);

        new AsyncTask<Void, Void, List<Entry>>() {
            @Override
            protected List<Entry> doInBackground(Void... voids) {
                try {
                    return ThingSpeakAPI.getLastEntries();
                } catch (IOException | JSONException | ParseException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(List<Entry> entries) {
                if (entries != null) {
                    setupLineChart(entries);
                }
            }
        }.execute();

    }

    private void setupLineChart(List<Entry> entries) {
        LineDataSet dataSet = new LineDataSet(entries, "Data");
        dataSet.setColor(Color.RED);
        dataSet.setLineWidth(2f);
        dataSet.setValueTextSize(12f);
        dataSet.setValueTextColor(Color.BLACK);

        List<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(dataSet);

        LineData lineData = new LineData(dataSets);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM HH:mm", Locale.getDefault());

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return dateFormat.format(new Date((long) value));
            }
        });

        lineChart.setData(lineData);
        lineChart.getDescription().setEnabled(false);
        lineChart.setTouchEnabled(true);
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(true);
        lineChart.animateX(1000);
    }
}
