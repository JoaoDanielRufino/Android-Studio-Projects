package br.com.android.whatsappclone.whatsappclone.model;

public class Conversa {
    private String idUsuario;
    private String nome;
    private String mensagem;

    public Conversa(){}

    public Conversa(String id, String nome, String mensagem){
        this.idUsuario = id;
        this.nome = nome;
        this.mensagem = mensagem;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public String getMensagem() {
        return mensagem;
    }
}
