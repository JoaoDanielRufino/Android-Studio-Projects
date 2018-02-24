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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import br.com.android.whatsappclone.whatsappclone.R;
import br.com.android.whatsappclone.whatsappclone.activity.ConversaActivity;
import br.com.android.whatsappclone.whatsappclone.adapter.ConversaAdapter;
import br.com.android.whatsappclone.whatsappclone.config.ConfiguracaoFirebase;
import br.com.android.whatsappclone.whatsappclone.helper.Base64Custom;
import br.com.android.whatsappclone.whatsappclone.helper.Preferencias;
import br.com.android.whatsappclone.whatsappclone.model.Conversa;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConversasFragment extends Fragment {
    private ListView listView;
    private ArrayAdapter<Conversa> adapter;
    private ArrayList<Conversa> conversas;

    private DatabaseReference databaseReference;
    private ValueEventListener valueEventListener;

    public ConversasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_conversas, container, false);

        listView = view.findViewById(R.id.lv_conversas);

        conversas = new ArrayList<>();
        adapter = new ConversaAdapter(getActivity(), conversas);
        listView.setAdapter(adapter);

        Preferencias pref = new Preferencias(getActivity());
        String idUsuarioLogado = pref.getIdentificador();

        databaseReference = ConfiguracaoFirebase.getDatabase().child("conversas").child(idUsuarioLogado);

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                conversas.clear();
                for(DataSnapshot dados: dataSnapshot.getChildren()){
                    conversas.add(dados.getValue(Conversa.class));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), ConversaActivity.class);

                intent.putExtra("nome", conversas.get(i).getNome());

                String email = Base64Custom.decodificarBase64(conversas.get(i).getIdUsuario());
                intent.putExtra("email", email);

                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        databaseReference.addValueEventListener(valueEventListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        databaseReference.removeEventListener(valueEventListener);
    }
}
