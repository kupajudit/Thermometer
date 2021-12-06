package com.example.myapplicationthermo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private TextView textView;
    private SensorManager sensorManager;
    private Sensor hofok;
    private Boolean isrunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);


        if(sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)!=null)
        {
            hofok = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
            isrunning = true;
        }
        else
        {
            textView.setText("Nincs ilyen hőfok szenzor");
            isrunning = false;
        }

    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if(event.values[0] <= 0 ) {
            textView.setText("Most " + event.values[0] + "°C van. Még akár havazásra is számíthatsz.");
        }else if(event.values[0] <= 20)
        {
            textView.setText("Most " + event.values[0] + "°C van. Ötlözz melegen!");
        }else if(event.values[0] <= 37)
        {
            textView.setText("Most " + event.values[0] + "°C van. Ma nagyon meleg lesz.");
        }
        else
        {
            textView.setText("Most " + event.values[0] + "°C van. Kánikula van, húzódj az árnyékba!");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(isrunning)
        {
            sensorManager.registerListener(this, hofok, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(isrunning)
        {
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}







