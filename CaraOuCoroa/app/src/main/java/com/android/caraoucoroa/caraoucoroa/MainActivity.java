package com.android.caraoucoroa.caraoucoroa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ImageView imagemJogar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imagemJogar = findViewById(R.id.botao_jogar);
        imagemJogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String op[] = {"cara", "coroa"};
                Random rand = new Random();
                Intent intent = new Intent(MainActivity.this, DetalheActivity.class);

                intent.putExtra("opcao", op[rand.nextInt(2)]);
                startActivity(intent);
            }
        });
    }
}
