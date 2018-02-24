package br.com.android.whatsappclone.whatsappclone.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import br.com.android.whatsappclone.whatsappclone.R;
import br.com.android.whatsappclone.whatsappclone.adapter.MensagemAdapter;
import br.com.android.whatsappclone.whatsappclone.config.ConfiguracaoFirebase;
import br.com.android.whatsappclone.whatsappclone.helper.Base64Custom;
import br.com.android.whatsappclone.whatsappclone.helper.Preferencias;
import br.com.android.whatsappclone.whatsappclone.model.Conversa;
import br.com.android.whatsappclone.whatsappclone.model.Mensagem;

public class ConversaActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText editMensagem;
    private ImageButton btMensagem;
    private ListView listView;
    private ArrayList<Mensagem> mensagens;
    private ArrayAdapter<Mensagem> adapter;
    private ValueEventListener valueEventListenerMensagens;

    private DatabaseReference databaseReference;

    //Dados destinatario
    private String nomeUsuarioDestinatario;
    private String idUsuarioDestinatario;

    //Dados remetente
    private String nomeUsuarioRemetente;
    private String idUsuarioRemetente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversa);

        Preferencias pref = new Preferencias(ConversaActivity.this);
        nomeUsuarioRemetente = pref.getNome();
        idUsuarioRemetente = pref.getIdentificador();

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            nomeUsuarioDestinatario = bundle.getString("nome");
            idUsuarioDestinatario = Base64Custom.codificarBase64(bundle.getString("email"));
        }

        toolbar = findViewById(R.id.tb_conversa);
        editMensagem = findViewById(R.id.edit_mensagem);
        btMensagem = findViewById(R.id.bt_enviar);
        listView = findViewById(R.id.lv_conversas);

        toolbar.setTitle(nomeUsuarioDestinatario);
        toolbar.setNavigationIcon(R.drawable.ic_action_arrow_left);
        setSupportActionBar(toolbar);

        mensagens = new ArrayList<>();

        databaseReference = ConfiguracaoFirebase.getDatabase().child("mensagens").child(idUsuarioRemetente).child(idUsuarioDestinatario);

        valueEventListenerMensagens = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mensagens.clear();
                for(DataSnapshot dados: dataSnapshot.getChildren()){
                    Mensagem mensagem = dados.getValue(Mensagem.class);
                    mensagens.add(mensagem);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        adapter = new MensagemAdapter(ConversaActivity.this, mensagens);
        listView.setAdapter(adapter);

        btMensagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String texto = editMensagem.getText().toString();

                if(texto.isEmpty()){
                    Toast.makeText(ConversaActivity.this, "Digite uma mensagem para enviar!!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Mensagem mensagem = new Mensagem(idUsuarioRemetente, texto);

                    if(!salvarMensagem(idUsuarioRemetente, idUsuarioDestinatario, mensagem)){
                        Toast.makeText(ConversaActivity.this, "Problema ao enviar a mensagem, tente novamente!!", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        if(!salvarMensagem(idUsuarioDestinatario, idUsuarioRemetente, mensagem)){
                            Toast.makeText(ConversaActivity.this, "Problema ao enviar a mensagem para o destinatário, tente novamente!!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    if(!salvarConversa(idUsuarioRemetente, idUsuarioDestinatario, new Conversa(idUsuarioDestinatario, nomeUsuarioDestinatario, texto))){
                        Toast.makeText(ConversaActivity.this, "Problema ao salvar a conversa, tente novamente!!", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        if(!salvarConversa(idUsuarioDestinatario, idUsuarioRemetente,  new Conversa(idUsuarioRemetente, nomeUsuarioRemetente, texto))){
                            Toast.makeText(ConversaActivity.this, "Problema ao salvar a conversa para o destinatário, tente novamente!!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    editMensagem.setText("");
                }
            }
        });
    }

    private boolean salvarMensagem(String idRemetente, String idDestinatario, Mensagem mensagem){
        try{
            databaseReference = ConfiguracaoFirebase.getDatabase().child("mensagens");
            databaseReference.child(idRemetente).child(idDestinatario).push().setValue(mensagem);
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private boolean salvarConversa(String idRemetente, String idDestinatario, Conversa conversa){
        try{
            databaseReference = ConfiguracaoFirebase.getDatabase().child("conversas");
            databaseReference.child(idRemetente).child(idDestinatario).setValue(conversa);
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseReference.addValueEventListener(valueEventListenerMensagens);
    }

    @Override
    protected void onStop() {
        super.onStop();
        databaseReference.removeEventListener(valueEventListenerMensagens);
    }
}
