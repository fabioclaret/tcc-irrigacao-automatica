package com.android.tcc;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private static final String ESP32_IP = "http://<ESP32_IP_ADDRESS>/"; // Substitua pelo IP do seu ESP32

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonOn = findViewById(R.id.button_on);
        Button buttonOff = findViewById(R.id.button_off);
        Button buttonAuto = findViewById(R.id.button_auto);
        Button buttonplanta = findViewById(R.id.button_go_to_main_activity3);
        Button buttoninicio = findViewById(R.id.button_activity2);



        buttonOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCommandToESP32("on");
            }
        });

        buttonOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCommandToESP32("off");
            }
        });

        buttonAuto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCommandToESP32("auto");
            }
        });

        buttonplanta.setOnClickListener(new View.OnClickListener() {
                                                 @Override
                                                 public void onClick(View view) {
                                                     Intent intent = new
                                                             Intent(MainActivity.this,
                                                             MainActivity3.class);

                                                     startActivity(intent);

                                                 }
                                             });

        buttoninicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new
                        Intent(MainActivity.this,
                        MainActivity2.class);

                startActivity(intent);

            }
        });


    }

    private void sendCommandToESP32(final String command) {
        new AsyncTask<Void, Void, Void>() {
            @SuppressLint("StaticFieldLeak")
            @Override
            protected Void doInBackground(Void... voids) {
                try {

                    // Defina o endereço IP do ESP32
                    String ESP32_IP = "http://192.168.1.100/"; // Substitua pelo IP do seu ESP32

                    // Defina o comando específico
                    String command = "ligar_bonba"; // ou qualquer outra ação que você definiu no ESP32

                    URL url = new URL(ESP32_IP + command);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("POST");
                    urlConnection.setDoOutput(true);
                    OutputStream os = urlConnection.getOutputStream();
                    os.write(command.getBytes());
                    os.flush();
                    os.close();
                    urlConnection.getResponseCode();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(MainActivity.this, "Comando enviado: " + command, Toast.LENGTH_SHORT).show();
            }
        }.execute();
    }
}