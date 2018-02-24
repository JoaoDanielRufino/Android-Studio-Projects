package br.com.android.whatsappclone.whatsappclone.model;

public class Mensagem {
    private String idUsuario;
    private String mensagem;

    public Mensagem(){
    }

    public Mensagem(String id, String m){
        this.idUsuario = id;
        this.mensagem = m;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public String getMensagem() {
        return mensagem;
    }
}
