package br.com.android.whatsappclone.whatsappclone.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import br.com.android.whatsappclone.whatsappclone.R;
import br.com.android.whatsappclone.whatsappclone.activity.ConversaActivity;
import br.com.android.whatsappclone.whatsappclone.activity.MainActivity;
import br.com.android.whatsappclone.whatsappclone.adapter.ContatoAdapter;
import br.com.android.whatsappclone.whatsappclone.config.ConfiguracaoFirebase;
import br.com.android.whatsappclone.whatsappclone.helper.Preferencias;
import br.com.android.whatsappclone.whatsappclone.model.Contato;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContatosFragment extends Fragment {

    private ListView listView;
    private ArrayAdapter adapter;
    private ArrayList<Contato> contatos;
    private ValueEventListener valueEventListenerContatos;

    private DatabaseReference databaseReference;

    public ContatosFragment() {
        // Required empty public constructor
    }

    //Metodos(onStart & onStop) utilizados para receber dados do firebase apenas quando o ContatosFragment estiver sendo utilizado
    @Override
    public void onStart() {
        super.onStart();
        databaseReference.addValueEventListener(valueEventListenerContatos);
    }

    @Override
    public void onStop() {
        super.onStop();
        databaseReference.removeEventListener(valueEventListenerContatos);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contatos, container, false);

        listView = view.findViewById(R.id.lv_contatos);
        contatos = new ArrayList<>();

        Preferencias pref = new Preferencias(getActivity());
        databaseReference = ConfiguracaoFirebase.getDatabase().child("contatos").child(pref.getIdentificador());

        valueEventListenerContatos = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                contatos.clear();
                for(DataSnapshot data: dataSnapshot.getChildren()){
                    Contato contato = data.getValue(Contato.class);
                    contatos.add(contato);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        adapter = new ContatoAdapter(getActivity(), contatos);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), ConversaActivity.class);

                intent.putExtra("nome", contatos.get(i).getNome());
                intent.putExtra("email", contatos.get(i).getEmail());

                startActivity(intent);
            }
        });

        return view;
    }

}
