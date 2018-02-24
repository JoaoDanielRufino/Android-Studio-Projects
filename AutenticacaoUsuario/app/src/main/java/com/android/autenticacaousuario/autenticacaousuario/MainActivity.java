package com.android.autenticacaousuario.autenticacaousuario;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();

        //Login
        /*firebaseAuth.createUserWithEmailAndPassword("joaodaniel@gmail.com", "123456")
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Log.i("createUser", "Sucesso ao criar");
                }
                else {
                    Log.i("createUser", "Erro ao criar");
                }
            }
        });*/

        //Cadastro
        /*firebaseAuth.signInWithEmailAndPassword("joaodaniel@gmail.com", "123456")
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Log.i("signIn", "Sucesso ao logar");
                        }
                        else {
                            Log.i("signIn", "Erro ao logar");
                        }
                    }
                });*/

        //Desloga usuario
        firebaseAuth.signOut();

        //Verifica situacao do usuario
        if(firebaseAuth.getCurrentUser() != null) {
            Log.i("verificaUsuario", "Usuario ja logado");
        }
        else {
            Log.i("verificaUsuario", "Usuario nao logado");
        }
    }
}
