package com.android.gasolinaoualcool.gasolinooualcool;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private EditText precoAlcool;
    private EditText precoGasolina;
    private Button verificar;
    private TextView texto_resultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        precoAlcool = (EditText) findViewById(R.id.preco_alcool);
        precoGasolina = (EditText) findViewById(R.id.preco_gasolina);
        verificar = (Button) findViewById(R.id.botao_vericar);
        texto_resultado = (TextView) findViewById(R.id.texto_resultado);

        verificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double alcool = Double.parseDouble(precoAlcool.getText().toString());
                double gasolina = Double.parseDouble(precoGasolina.getText().toString());
                double result = alcool / gasolina;

                if(result >= 0.7){
                    texto_resultado.setText("É melhor utilizar gasolina!");
                }
                else{
                    texto_resultado.setText("É melhor utilizar álcool!");
                }
            }
        });
    }
}
