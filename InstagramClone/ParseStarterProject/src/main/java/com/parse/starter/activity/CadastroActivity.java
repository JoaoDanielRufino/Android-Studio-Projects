package com.parse.starter.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.parse.starter.R;

public class CadastroActivity extends AppCompatActivity {

    private EditText textoUsuario;
    private EditText textoEmail;
    private EditText textoSenha;
    private Button botaoCadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        textoUsuario = findViewById(R.id.usuario_cadastro);
        textoEmail = findViewById(R.id.email_cadastro);
        textoSenha = findViewById(R.id.senha_cadastro);
        botaoCadastrar = findViewById(R.id.botao_cadastrar);

        botaoCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(textoUsuario.length() == 0 || textoEmail.length() == 0 || textoSenha.length() == 0){
                    toastMessage("Preencha todos os campos para cadastrar!!");
                }
                else {
                    cadastrarUsuario();
                }
            }
        });
    }

    private void cadastrarUsuario(){
        ParseUser user = new ParseUser();

        user.setUsername(textoUsuario.getText().toString());
        user.setEmail(textoEmail.getText().toString());
        user.setPassword(textoSenha.getText().toString());

        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if(e == null){
                    toastMessage("Cadastro feito com sucesso.");
                    abrirMainActivity();
                }
                else{
                    toastMessage("Erro ao cadastrar: " + e.getMessage());
                }
            }
        });
    }

    private void abrirMainActivity(){
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void abrirLoginUsuario(View view){
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}
