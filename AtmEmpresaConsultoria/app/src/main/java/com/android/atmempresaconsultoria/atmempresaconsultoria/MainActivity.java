package com.android.atmempresaconsultoria.atmempresaconsultoria;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView imagemEmpresa;
    private ImageView imagemServico;
    private ImageView imagemCliente;
    private  ImageView imagemContato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imagemEmpresa = findViewById(R.id.empresa);
        imagemServico = findViewById(R.id.servicos);
        imagemCliente = findViewById(R.id.clientes);
        imagemContato = findViewById(R.id.contato);

        imagemEmpresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, EmpresaActivity.class));
            }
        });

        imagemServico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ServicoActivity.class));
            }
        });

        imagemCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ClienteActivity.class));
            }
        });

        imagemContato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ContatoActivity.class));
            }
        });
    }
}
