package com.android.sharedpreferences.sharedpreferences;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText name;
    private Button saveButton;
    private TextView infoText;
    private Button clearButton;
    private static final String SHARED_PREFERENCE = "SharedPreference";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.name_text);
        saveButton = findViewById(R.id.save_button);
        infoText = findViewById(R.id.info_text);
        clearButton = findViewById(R.id.clear_button);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCE, 0);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                if(name.getText().toString().equals("")){
                    Toast.makeText(MainActivity.this, "Please, enter with a name!", Toast.LENGTH_SHORT).show();
                }
                else{
                    editor.putString("name", name.getText().toString());
                    editor.apply();
                    infoText.setText("Hello, " + name.getText().toString());
                }
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = getSharedPreferences(SHARED_PREFERENCE, 0).edit();
                editor.clear();
                editor.apply();
                infoText.setText("Hello, user not defined.");
            }
        });

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCE, 0);
        if(sharedPreferences.contains("name")){
            String userName = sharedPreferences.getString("name", "user not defined");
            infoText.setText("Hello, " + userName);
        }
        else{
            infoText.setText("Hello, user not defined.");
        }
    }
}
