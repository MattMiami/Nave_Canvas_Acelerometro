package com.example.nave_espacial_canvas;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import static android.view.View.SYSTEM_UI_FLAG_IMMERSIVE;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    SensorManager sensorManager;
    Sensor sensor;

    View decorView;

    DisplayMetrics metrics = new DisplayMetrics();
    int ancho;
    int alto;
    VistaJuego vj;

    boolean restart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vj = new VistaJuego(getApplicationContext());
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Para quitar la barra superior e inferior de navegación si el dispositivo la tiene activa
        decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                SYSTEM_UI_FLAG_IMMERSIVE
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        //Resolucion de pantalla
        ((WindowManager) this.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(metrics);
        ancho = metrics.widthPixels;
        alto = metrics.heightPixels;

        //Sensor
        sensorManager = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_FASTEST);

        setContentView(vj);


    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        //Cuando la nave sale de la pantalla se vuelve a reiniciar el juego
        if (restart) {

            vj.x = ancho / 2;
            vj.y = alto / 4;
            restart = false;
            vj.vueltas++;
            if(vj.vueltas>5){
                Toast.makeText(this, "GAME OVER!", Toast.LENGTH_LONG).show();
                finish();
            }

        }
        if (sensor.getType() == Sensor.TYPE_ACCELEROMETER) {


            synchronized (this) {

                vj.x -= Math.round(event.values[0]);
                vj.X = String.format("%.2f", vj.x / 100);
                if (vj.x < (vj.shipSides)) {
                    vj.x = (vj.shipSides);
                } else if (vj.x > (ancho - (vj.shipSides))) {
                    vj.x = ancho - (vj.shipSides);
                }

                vj.y += Math.round(event.values[1]);
                vj.Y = String.format("%.2f", vj.y / 1000);
                if (vj.y < (vj.shipSides)) {
                    vj.y = (vj.shipSides);
                } else if (vj.y > (alto - vj.shipSides)) {
                    if (vj.x - 10 >= vj.xStart && vj.x + 10 <= vj.xEnd) {
                        if (vj.y > 3000) {
                            restart = true;
                        }
                    } else {
                        vj.y = alto - vj.shipSides;
                    }

                }

                vj.z = Math.round(event.values[2]);
                vj.Z = String.format("%.2f", vj.z);
                vj.invalidate();

            }

        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onPause() {
        super.onPause();

        sensorManager.unregisterListener(this);

    }

    @Override
    protected void onStop() {
        super.onStop();

        sensorManager.unregisterListener(this);

    }
}