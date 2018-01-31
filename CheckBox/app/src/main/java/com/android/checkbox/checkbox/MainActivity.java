package com.android.checkbox.checkbox;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private CheckBox darkSouls;
    private CheckBox counterStrike;
    private CheckBox lifeIsStange;
    private CheckBox theLastOfUs;
    private Button checkButton;
    private TextView checkText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        darkSouls = findViewById(R.id.darkSouls_checkbox);
        counterStrike = findViewById(R.id.cs_checkbox);
        lifeIsStange = findViewById(R.id.lifeisstrange_checkbox);
        theLastOfUs = findViewById(R.id.thelastofus_checkbox);
        checkButton = findViewById(R.id.check_button);
        checkText = findViewById(R.id.check_text);

        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selectedItens;

                selectedItens = darkSouls.getText() + ": " + darkSouls.isChecked() + "\n";
                selectedItens += counterStrike.getText() + ": " + counterStrike.isChecked() + "\n";
                selectedItens += lifeIsStange.getText() + ": " + lifeIsStange.isChecked() + "\n";
                selectedItens += theLastOfUs.getText() + ": " + theLastOfUs.isChecked() + "\n";

                checkText.setText(selectedItens);
            }
        });
    }
}
