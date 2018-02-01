package com.android.colorpreference.colorpreference;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity {

    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private Button saveButton;
    private ConstraintLayout layout;
    private static final String SHARED_PREFERENCE = "SheredPreference";
    private static final String COLOR = "Color";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        radioGroup = findViewById(R.id.radioGroup);
        saveButton = findViewById(R.id.save_button);
        layout = findViewById(R.id.layout);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               int selectedButton = radioGroup.getCheckedRadioButtonId();

               if(selectedButton > 0){
                   radioButton = findViewById(selectedButton);
                   String selectedColor = radioButton.getText().toString();

                   SharedPreferences.Editor editor = getSharedPreferences(SHARED_PREFERENCE, 0).edit();
                   editor.putString(COLOR, selectedColor);
                   editor.apply();
                   changeBackground(selectedColor);
               }
            }
        });

        SharedPreferences pref = getSharedPreferences(SHARED_PREFERENCE, 0);
        if(pref.contains(COLOR)){
            String color = pref.getString(COLOR, "White");
            changeBackground(color);
        }
    }

    private void changeBackground(String color){
        switch(color){
            case "Orange":
                layout.setBackgroundColor(Color.parseColor("#ffce26"));
                break;
            case "Blue":
                layout.setBackgroundColor(Color.parseColor("#495bc7"));
                break;
            case "Green":
                layout.setBackgroundColor(Color.parseColor("#009670"));
                break;
            default:
                layout.setBackgroundColor(Color.parseColor("#ffffff"));
        }
    }
}
