package com.android.caraoucoroa.caraoucoroa;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class DetalheActivity extends AppCompatActivity {

    private ImageView imagemMoeda;
    private ImageView imagemVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe);

        imagemMoeda = findViewById(R.id.imagem_moeda);
        imagemVoltar = findViewById(R.id.botao_voltar);

        Bundle extra = getIntent().getExtras();
        if(extra != null){
            String op = extra.getString("opcao");
            if(op.equals("cara")){
                imagemMoeda.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.moeda_cara));
            }
            else{
                imagemMoeda.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.moeda_coroa));
            }
        }

        imagemVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DetalheActivity.this, MainActivity.class));
            }
        });
    }
}
