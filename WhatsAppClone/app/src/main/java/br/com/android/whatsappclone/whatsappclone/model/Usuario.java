package br.com.android.whatsappclone.whatsappclone.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

import br.com.android.whatsappclone.whatsappclone.config.ConfiguracaoFirebase;

public class Usuario {
    private String id;
    private String nome;
    private String email;
    private String senha;

    //O firebase exige um construtor vazio
    public Usuario(){
    }

    public Usuario(String nome, String email, String senha){
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public Usuario(String email, String senha){
        this.email = email;
        this.senha = senha;
    }

    public void salvarDados(){
        DatabaseReference databaseReference = ConfiguracaoFirebase.getDatabase();
        databaseReference.child("usuarios").child(id).setValue(this);
    }

    public void setId(String id){
        this.id = id;
    }

    @Exclude //usando Exclude o fireBase nao salvara a informacao
    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    @Exclude
    public String getSenha() {
        return senha;
    }
}
