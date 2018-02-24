package br.com.android.whatsappclone.whatsappclone.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

import br.com.android.whatsappclone.whatsappclone.R;
import br.com.android.whatsappclone.whatsappclone.config.ConfiguracaoFirebase;
import br.com.android.whatsappclone.whatsappclone.helper.Preferencias;
import br.com.android.whatsappclone.whatsappclone.model.Mensagem;

public class MensagemAdapter extends ArrayAdapter<Mensagem> {
    private Context context;
    private ArrayList<Mensagem> mensagens;

    public MensagemAdapter(@NonNull Context c, @NonNull ArrayList<Mensagem> objects) {
        super(c, 0, objects);
        this.context = c;
        this.mensagens = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = null;

        if(!mensagens.isEmpty()){
            Preferencias pref = new Preferencias(context);
            String idUsuarioRemetente = pref.getIdentificador();

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            if(idUsuarioRemetente.equals(mensagens.get(position).getIdUsuario())){
                view = inflater.inflate(R.layout.item_mensagem_direita, parent, false);
            }
            else {
                view = inflater.inflate(R.layout.item_mensagem_esquerda, parent, false);
            }

            TextView textoMensagem = view.findViewById(R.id.tv_mensagem);
            textoMensagem.setText(mensagens.get(position).getMensagem());
        }

        return view;
    }
}
