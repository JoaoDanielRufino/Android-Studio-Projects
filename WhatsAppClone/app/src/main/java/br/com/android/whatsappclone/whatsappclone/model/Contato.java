package br.com.android.whatsappclone.whatsappclone.model;

import com.google.firebase.database.Exclude;

public class Contato {
    private String identificadorUsuario;
    private String email;
    private String nome;

    public Contato(){
    }

    public Contato(String identificadorUsuario, String email, String nome){
        this.identificadorUsuario = identificadorUsuario;
        this.email = email;
        this.nome = nome;
    }

    @Exclude
    public String getIdentificadorUsuario() {
        return identificadorUsuario;
    }

    public String getEmail() {
        return email;
    }

    public String getNome() {
        return nome;
    }
}
