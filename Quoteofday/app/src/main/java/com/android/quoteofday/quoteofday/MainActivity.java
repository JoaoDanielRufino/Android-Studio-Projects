package com.android.quoteofday.quoteofday;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private int pos_quote = 1;
    private TextView quote;
    private Button new_quote;
    private String quotes[] = {
            "Our entire life - consists ultimately in accepting ourselves as we are.",
            "Life is 10% what happens to you and 90% how you react to it.",
            "It does not matter how slowly you go as long as you do not stop.",
            "If you can dream it, you can do it.",
            "Don't think, just do.",
            "Well done is better than well said."
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        quote = (TextView) findViewById(R.id.quote_text);
        new_quote = (Button) findViewById(R.id.new_quote_button);
        quote.setText(quotes[0]);

        new_quote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quote.setText(quotes[pos_quote%quotes.length]);
                pos_quote++;
            }
        });
    }
}
