package com.estudos.lampada;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private final String BASE_URL = "https://www.aldriano.com.br/lampada/api/?flag=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Button btnLigar = findViewById(R.id.btn_ligar);
        Button btnDesligar = findViewById(R.id.btn_desligar);
        TextView textView = findViewById(R.id.txt_status);

        btnLigar.setOnClickListener(v -> {
            sendFlag(1, textView);

        });

        btnDesligar.setOnClickListener(v -> {
            sendFlag(0, textView);
        });
    }

    public void sendFlag(int flag, TextView t) {
        String address = BASE_URL + flag;
        new Thread(() -> {
            try {
                URL url = new URL(address);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.getInputStream();
                runOnUiThread(() -> {
                    t.setText(flag == 1 ? "Lampada ligada" : "Lampada desligada");
                    Toast.makeText(this,
                            "Comando: " + flag + " - enviado",
                            Toast.LENGTH_SHORT)
                            .show();
                });
                conn.disconnect();
            } catch (Exception e) {
                Toast.makeText(this,
                        "Falha ao enviar comando!",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        }).start();
    }
}