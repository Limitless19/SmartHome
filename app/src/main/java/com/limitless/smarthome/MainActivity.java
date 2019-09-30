package com.limitless.smarthome;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {
    ImageButton fanImageButton,bulbImageButton,televisionImageButton,fridgeImageButton,pumpImageButton;
    EditText editText;
    Button speechButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fanImageButton = findViewById(R.id.fanImageButton);
        bulbImageButton = findViewById(R.id.bulbImageButton);
        televisionImageButton = findViewById(R.id.televisionImageButton);
        fridgeImageButton = findViewById(R.id.fridgeImageButton);
        pumpImageButton = findViewById(R.id.pumpImageButton);
        editText = findViewById(R.id.editText);
        speechButton = findViewById(R.id.speechButton);



    }
}
