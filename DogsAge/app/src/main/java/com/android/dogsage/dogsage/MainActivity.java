package com.android.dogsage.dogsage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText ageEntered;
    private Button findAge;
    private TextView humanAge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findAge = (Button) findViewById(R.id.find_age);
        ageEntered = (EditText) findViewById(R.id.age_entered);
        humanAge = (TextView) findViewById(R.id.human_age);

        findAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = ageEntered.getText().toString();

                if(text.isEmpty()){
                    humanAge.setText("Enter with an age please.");
                }
                else{
                    int newAge = Integer.parseInt(text)*7;
                    humanAge.setText("The human age of your dog is " + newAge + " years!!");
                }
            }
        });
    }
}
