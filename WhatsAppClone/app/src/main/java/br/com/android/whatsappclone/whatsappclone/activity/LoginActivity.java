package br.com.android.whatsappclone.whatsappclone.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import br.com.android.whatsappclone.whatsappclone.R;
import br.com.android.whatsappclone.whatsappclone.config.ConfiguracaoFirebase;
import br.com.android.whatsappclone.whatsappclone.helper.Base64Custom;
import br.com.android.whatsappclone.whatsappclone.helper.Preferencias;
import br.com.android.whatsappclone.whatsappclone.model.Usuario;

public class LoginActivity extends AppCompatActivity {

    private EditText email;
    private EditText senha;
    private Button logar;
    private Usuario usuario;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private ValueEventListener valueEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        verificarUsuarioLogado();

        email = findViewById(R.id.email_editText);
        senha = findViewById(R.id.senha_editText2);
        logar = findViewById(R.id.logar_button2);

        logar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(email.getText().length() == 0 || senha.getText().length() == 0){
                    Toast.makeText(LoginActivity.this, "Preencha todos os campos para logar!!", Toast.LENGTH_SHORT).show();
                }
                else{
                    usuario = new Usuario(email.getText().toString(), senha.getText().toString());
                    validarLogin();
                }
            }
        });
    }

    private void verificarUsuarioLogado(){
        firebaseAuth = ConfiguracaoFirebase.getFirebaseAuth();
        if(firebaseAuth.getCurrentUser() != null){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
    }

    private void validarLogin(){
        firebaseAuth = ConfiguracaoFirebase.getFirebaseAuth();
        firebaseAuth.signInWithEmailAndPassword(usuario.getEmail(), usuario.getSenha())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            final String identificadorUsuario = Base64Custom.codificarBase64(usuario.getEmail());

                            databaseReference = ConfiguracaoFirebase.getDatabase().child("usuarios").child(identificadorUsuario);

                            valueEventListener = new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    Usuario usuario = dataSnapshot.getValue(Usuario.class);

                                    Preferencias pref = new Preferencias(LoginActivity.this);
                                    pref.salvarDados(identificadorUsuario, usuario.getNome());
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            };

                            databaseReference.addListenerForSingleValueEvent(valueEventListener);

                            Toast.makeText(LoginActivity.this, "Socesso ao fazer login", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        }
                        else{
                            String erro = "";
                            try{
                                throw task.getException();
                            }
                            catch (FirebaseAuthInvalidUserException e) {
                                erro = "E-mail inválido!!";
                            }
                            catch(FirebaseAuthInvalidCredentialsException e){
                                erro = "Senha incorreta!!";
                            }
                            catch (Exception e) {
                                erro = "Tente novamente mais tarde!!";
                                e.printStackTrace();
                            }
                            Toast.makeText(LoginActivity.this, "Erro ao fazer login. " + erro, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void abrirCadastroUsuario(View view){
        startActivity(new Intent(LoginActivity.this, CadastroUsuarioActivity.class));
    }
}
