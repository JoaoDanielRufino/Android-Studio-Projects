package com.android.notes.notes;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    private EditText text;
    private ImageView saveButton;
    private static final String FILE_NAME = "notes_app_files.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = findViewById(R.id.notes_text);
        saveButton = findViewById(R.id.save_button);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String notes = text.getText().toString();
                if(write(notes)){
                    Toast.makeText(MainActivity.this, "Anotação salva com sucesso!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(MainActivity.this, "Anotação não foi salva!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        String notes;
        if(!(notes = read()).equals("")){
            text.setText(notes);
        }
    }

    private boolean write(String text){
        try{

            OutputStreamWriter output = new OutputStreamWriter(openFileOutput(FILE_NAME, Context.MODE_PRIVATE));
            output.write(text);
            output.close();

        }catch(Exception ex){
            Log.v("MainActivity", ex.toString());
            return false;
        }
        return true;
    }

    private String read(){
        StringBuilder res = new StringBuilder();

        try{
            InputStream file = openFileInput(FILE_NAME);

            if(file != null){
                InputStreamReader reader = new InputStreamReader(file);
                BufferedReader buffer = new BufferedReader(reader);
                String aux = "";

                while( (aux = buffer.readLine()) != null){
                    res.append(aux + "\n");
                }
            }
            file.close();

        }catch(Exception ex){
            Log.v("MainActivity", ex.toString());
        }
        return res.toString();
    }
}
