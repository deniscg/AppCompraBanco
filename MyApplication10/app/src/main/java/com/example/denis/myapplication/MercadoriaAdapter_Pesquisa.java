package com.example.denis.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import io.realm.RealmResults;

/**
 * Created by Aluno on 13/09/2017.
 */

public class MercadoriaAdapter_Pesquisa extends ArrayAdapter<Mercadoria>{

    Context contexto;
    ArrayList<Mercadoria> compra;
    public MercadoriaAdapter_Pesquisa(Context context, int resouce, ArrayList<Mercadoria> objects){
        super(context, resouce,objects);//super executa um construtor da classe mae
        this.contexto = context;
        this.compra=objects;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View linhaView = LayoutInflater.from(contexto).inflate(R.layout.linha2, parent,false);
        TextView chave =(TextView)linhaView.findViewById(R.id.linha_chave2);
        TextView nome =(TextView)linhaView.findViewById(R.id.linha_nome2);
        TextView quantidade =(TextView)linhaView.findViewById(R.id.Linha_qnt2);
        TextView produtoComprado =(TextView)linhaView.findViewById(R.id.Linha_produto2);
        chave.setText(compra.get(position).getChave());
        nome.setText(compra.get(position).getNome());
        quantidade.setText(compra.get(position).getQnt());
        produtoComprado.setText(compra.get(position).getCategoria());


        return linhaView;
    }
}
