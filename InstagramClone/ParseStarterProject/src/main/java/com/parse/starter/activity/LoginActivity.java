package com.parse.starter.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.starter.R;

public class LoginActivity extends AppCompatActivity {

    private EditText textoUsuario;
    private EditText textoSenha;
    private Button botaoLogar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textoUsuario = findViewById(R.id.usuario_login);
        textoSenha = findViewById(R.id.senha_login);
        botaoLogar = findViewById(R.id.botao_logar);

        //ParseUser.logOut();

        verificarUsuarioLogado();

        botaoLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(textoUsuario.length() == 0 || textoSenha.length() == 0){
                    toastMessage("Preencha todos os campos para cadastrar!!");
                }
                else{
                    verificarLogin(textoUsuario.getText().toString(), textoSenha.getText().toString());
                }
            }
        });
    }

    private void verificarUsuarioLogado(){
        if(ParseUser.getCurrentUser() != null){
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    private void verificarLogin(String usuario, String senha){
        ParseUser.logInInBackground(usuario, senha, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if(e == null){
                    toastMessage("Sucesso ao fazer login!");
                    abrirMainActivity();
                }
                else{
                    toastMessage("Erro ao fazer login: " + e.getMessage());
                }
            }
        });
    }

    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void abrirMainActivity(){
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    public void abrirCadastroUsuario(View view){
        startActivity(new Intent(this, CadastroActivity.class));
        finish();
    }
}
