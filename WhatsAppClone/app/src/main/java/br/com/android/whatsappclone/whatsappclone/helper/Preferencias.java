package br.com.android.whatsappclone.whatsappclone.helper;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class Preferencias {
    private Context context;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    private static final String NOME_ARQUIVO = "Whatsapp Preference";
    private static final int MODE = 0;
    private static final String CHAVE_IDENTIFICADOR = "Identificador Usuario Logado";
    private static final String CHAVE_NOME = "Nome Usuario Logado";

    public Preferencias(Context cont){
        this.context = cont;
        this.prefs = cont.getSharedPreferences(NOME_ARQUIVO, MODE);
        this.editor = prefs.edit();
    }

    public void salvarDados(String identificador, String nome){
        editor.putString(CHAVE_IDENTIFICADOR, identificador);
        editor.putString(CHAVE_NOME, nome);
        editor.apply();
    }

    public String getIdentificador(){
        return prefs.getString(CHAVE_IDENTIFICADOR, null);
    }

    public String getNome(){
        return prefs.getString(CHAVE_NOME, null);
    }
}
