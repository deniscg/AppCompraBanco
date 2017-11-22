package com.example.denis.myapplication;

import android.widget.Toast;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by Aluno on 13/09/2017.
 */

public class Mercadoria extends RealmObject {
    private int id;
    private String nome;
    private String qnt;
    private String categoria;
    private  String chave;

    public Mercadoria(String nomes, String qant, String categorias) {
        this.nome = nomes;
        this.qnt = qant;
        this.categoria = categorias;
    }
    public Mercadoria(){

    }

    public String getChave() {
        return chave;
    }

    public void setChave(String chave) {
        this.chave = chave;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getQnt() {
        return qnt;
    }

    public void setQnt(String qnt) {
        this.qnt = qnt;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria =  categoria;
    }

    public static int autincrement(){
        int key=1;
        Realm realm = Realm.getDefaultInstance();
        try{
            key = realm.where(Mercadoria.class).max("id").intValue()+1;
        }catch(NullPointerException e){
            e.printStackTrace();
        }
        return key;
    }

}