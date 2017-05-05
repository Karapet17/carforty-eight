package com.example.karo.starter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Start extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        setButtonUse();
    }
    private void setButtonUse() {
        Button msgButton = (Button)findViewById(R.id.button);
        msgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Start.this, "You clicked it", Toast.LENGTH_LONG).show();
                startActivity(new Intent(Start.this, MainActivity.class));
            }
        });
    }
}
