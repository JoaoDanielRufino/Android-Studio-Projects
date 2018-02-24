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
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.database.DatabaseReference;

import br.com.android.whatsappclone.whatsappclone.R;
import br.com.android.whatsappclone.whatsappclone.config.ConfiguracaoFirebase;
import br.com.android.whatsappclone.whatsappclone.helper.Base64Custom;
import br.com.android.whatsappclone.whatsappclone.helper.Preferencias;
import br.com.android.whatsappclone.whatsappclone.model.Usuario;

public class CadastroUsuarioActivity extends AppCompatActivity {

    private EditText nome;
    private EditText email;
    private EditText senha;
    private Button botaoCadastrar;
    private Usuario usuario;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);

        nome = findViewById(R.id.cadastro_nome_editText);
        email = findViewById(R.id.cadastro_email_editText2);
        senha = findViewById(R.id.cadastro_senha_editText3);
        botaoCadastrar = findViewById(R.id.cadastrar_button2);

        botaoCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(nome.getText().length() == 0 || email.getText().length() == 0 || senha.getText().length() == 0){
                    Toast.makeText(CadastroUsuarioActivity.this, "Preencha todos os campos!!", Toast.LENGTH_SHORT).show();
                }
                else {
                    usuario = new Usuario(nome.getText().toString(), email.getText().toString(), senha.getText().toString());
                    cadastrarUsuario();
                }
            }
        });
    }

    //Ao criar o usuario ele ja fica on-line.
    private void cadastrarUsuario(){
        firebaseAuth = ConfiguracaoFirebase.getFirebaseAuth();
        firebaseAuth.createUserWithEmailAndPassword(usuario.getEmail(), usuario.getSenha())
                .addOnCompleteListener(CadastroUsuarioActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Preferencias pref = new Preferencias(CadastroUsuarioActivity.this);
                            String identificadorUsuario = Base64Custom.codificarBase64(usuario.getEmail());
                            pref.salvarDados(identificadorUsuario, usuario.getNome());

                            Toast.makeText(CadastroUsuarioActivity.this, "Sucesso ao cadastar", Toast.LENGTH_SHORT).show();
                            usuario.setId(Base64Custom.codificarBase64(usuario.getEmail()));
                            usuario.salvarDados();
                            abrirUsuarioLogado();
                        }
                        else{
                            String erro = "";
                            try{
                                throw task.getException();
                            }
                            catch(FirebaseAuthWeakPasswordException e){
                                erro = "Digite uma senha mais forte, contendo mais caracteres!!";
                            }
                            catch(FirebaseAuthInvalidCredentialsException e){
                                erro = "E-mail inválido!!";
                            }
                            catch (FirebaseAuthUserCollisionException e) {
                                erro = "E-mail já existe!!";
                            }
                            catch (Exception e) {
                                erro = "Tente novamente mais tarde!!";
                                e.printStackTrace();
                            }
                            Toast.makeText(CadastroUsuarioActivity.this, "Erro ao cadastar. " + erro, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void abrirUsuarioLogado(){
        startActivity(new Intent(CadastroUsuarioActivity.this, LoginActivity.class));
        finish();
    }
}
