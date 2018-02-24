package br.com.android.whatsappclone.whatsappclone.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import br.com.android.whatsappclone.whatsappclone.R;
import br.com.android.whatsappclone.whatsappclone.adapter.TabAdapter;
import br.com.android.whatsappclone.whatsappclone.config.ConfiguracaoFirebase;
import br.com.android.whatsappclone.whatsappclone.helper.Base64Custom;
import br.com.android.whatsappclone.whatsappclone.helper.Preferencias;
import br.com.android.whatsappclone.whatsappclone.helper.SlidingTabLayout;
import br.com.android.whatsappclone.whatsappclone.model.Contato;
import br.com.android.whatsappclone.whatsappclone.model.Usuario;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private String identificadorContato;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    private SlidingTabLayout slidingTabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("WhatsApp");
        setSupportActionBar(toolbar);

        slidingTabLayout = findViewById(R.id.stl_tabs);
        viewPager = findViewById(R.id.vp_pagina);

        TabAdapter tabAdapter = new TabAdapter(getSupportFragmentManager());
        viewPager.setAdapter(tabAdapter);

        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setSelectedIndicatorColors(ContextCompat.getColor(this, R.color.colorSliding));
        slidingTabLayout.setViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.sair:
                deslogarUsuario();
                return true;
            case R.id.configuracoes:
                return true;
            case R.id.adicionar:
                adicionarContato();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void deslogarUsuario(){
        firebaseAuth = ConfiguracaoFirebase.getFirebaseAuth();
        firebaseAuth.signOut();
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        finish();
    }

    private void adicionarContato(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);

        alertDialog.setTitle("Novo contato");
        alertDialog.setMessage("E-mail do usuário");
        alertDialog.setCancelable(false);

        final EditText editText = new EditText(MainActivity.this);
        alertDialog.setView(editText);

        alertDialog.setPositiveButton("Adicionar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String email = editText.getText().toString();

                if(email.isEmpty()){
                    Toast.makeText(MainActivity.this, "Preencha o e-mail para adicionar!", Toast.LENGTH_SHORT).show();
                }
                else{
                    identificadorContato = Base64Custom.codificarBase64(email);
                    databaseReference = ConfiguracaoFirebase.getDatabase().child("usuarios").child(identificadorContato);

                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                                Preferencias pref = new Preferencias(MainActivity.this);
                                String identificadorUsuario = pref.getIdentificador();

                                firebaseAuth = ConfiguracaoFirebase.getFirebaseAuth();
                                databaseReference = ConfiguracaoFirebase.getDatabase();
                                databaseReference = databaseReference.child("contatos").child(identificadorUsuario).child(identificadorContato);

                                try {
                                    //Estava dando erro por causa que a classe Usuario nao possuia um construtor vazio
                                    Usuario usuarioContato = dataSnapshot.getValue(Usuario.class);
                                    Contato contato = new Contato(identificadorContato, usuarioContato.getEmail(), usuarioContato.getNome());

                                    databaseReference.setValue(contato);
                                }
                                catch(Exception e){
                                    e.printStackTrace();
                                    Toast.makeText(MainActivity.this, "Erro ao adicionar. Tente novamente mais tarde.", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else{
                                Toast.makeText(MainActivity.this, "Usuário não encontrado", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }
        });

        alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        alertDialog.create();
        alertDialog.show();
    }
}
