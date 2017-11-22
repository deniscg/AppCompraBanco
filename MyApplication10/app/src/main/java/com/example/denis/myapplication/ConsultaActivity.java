package com.example.denis.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.denis.myapplication.R;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class ConsultaActivity extends AppCompatActivity {
    EditText Consult_txtId,Consult_txtNome,Consult_txtQnt;
    Button Consult_btnAdd,Consult_btnView,Consult_btnUpdate,Consult_btnDelete;
    Realm Consult_realm;
    String Consult_categoria,resultado;
    Spinner Consult_spinner;
    String Consult_cat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta);
        Consult_spinner = (Spinner)findViewById(R.id.spnCat_consulta);
        Consult_btnUpdate =(Button)findViewById(R.id.btnAlter_consulta);
        Consult_btnDelete =(Button)findViewById(R.id.btnDel_consulta);
        Consult_txtQnt =(EditText)findViewById(R.id.edtQnt_consulta);
        Consult_txtNome = (EditText)findViewById(R.id.edtMerc_consulta);
        //-----------------------------------------
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle("Carrinho");     //Titulo para ser exibido na sua Action Bar em frente à seta
        //-------------------------------------------


        String[] categoria = {"Frios", "Vegetais", "Limpeza", "Higiene"};
        final ArrayAdapter adapterlist = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, categoria);
        adapterlist.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Consult_spinner.setAdapter(adapterlist);
        //-----------------------------------------------------------



        Consult_realm = Realm.getDefaultInstance();

        Bundle args = getIntent().getExtras();
        resultado = args.getString("consulta_id");
        final TextView txt_resultado = (TextView)findViewById(R.id.txtConsulta_consulta);
        txt_resultado.setText(resultado);
        String msg = "Erro foi aqui";

        try{
            final ArrayList<Mercadoria> mercadorias = new ArrayList<Mercadoria>();
            Mercadoria usuarioview =Consult_realm.where(Mercadoria.class).equalTo("chave", resultado).findFirst();
            mercadorias.add(usuarioview);
            ArrayAdapter adapter = new MercadoriaAdapter_Pesquisa(this,R.layout.linha2,mercadorias);
            ListView listView = (ListView)findViewById(R.id.lstConsulta_consulta);
            listView.setAdapter(adapter);

        }
        catch (Exception e){
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

        }
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(this, MainActivity.class));
                finishAffinity();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }
    public void clickAction(View view) {
        switch (view.getId()) {
            case R.id.btnAlter_consulta:
                updateRecord();
                break;
            case R.id.btnDel_consulta:
                deleteRecord();
                break;
        }
    }
    public void updateRecord(){
        Consult_cat = Consult_spinner.getSelectedItem().toString();
        Mercadoria usuarioupdata = Consult_realm.where(Mercadoria.class).equalTo("chave",resultado).findFirst();
        Consult_realm.beginTransaction();
        usuarioupdata.setNome(Consult_txtNome.getText().toString());
        usuarioupdata.setQnt(Consult_txtQnt.getText().toString());
        usuarioupdata.setCategoria(Consult_cat);
        Consult_realm.copyToRealm(usuarioupdata);
        Consult_realm.commitTransaction();
        try{
            final ArrayList<Mercadoria> mercadorias = new ArrayList<Mercadoria>();
            Mercadoria usuarioview =Consult_realm.where(Mercadoria.class).equalTo("chave", resultado).findFirst();
            mercadorias.add(usuarioview);
            ArrayAdapter adapter = new MercadoriaAdapter_Pesquisa(this,R.layout.linha2,mercadorias);
            ListView listView = (ListView)findViewById(R.id.lstConsulta_consulta);
            listView.setAdapter(adapter);
        }
        catch (Exception e){
            Toast.makeText(this, "Alterado com Sucesso", Toast.LENGTH_SHORT).show();

        }
    }
    public void deleteRecord(){

        Mercadoria usuario = Consult_realm.where(Mercadoria.class).equalTo("chave",resultado).findFirst();
        Consult_realm.beginTransaction();
        usuario.deleteFromRealm();
        Consult_realm.commitTransaction();
        Toast.makeText(this, "Excluido", Toast.LENGTH_SHORT).show();

    }
}