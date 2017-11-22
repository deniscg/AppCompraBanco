package com.example.denis.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.Realm;
import io.realm.RealmResults;


import java.util.ArrayList;
import java.util.StringTokenizer;

public class Carrinho extends AppCompatActivity {
    EditText txtId,txtNome,txtQnt;
    Button btnAdd,btnView,btnUpdate,btnDelete;
    Realm realm;
    String categoria;
    Spinner spinner;
    ListView listView;

    String msg="Erro no KEy";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrinho);
        //-----------------------------------------
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle("Carrinho");     //Titulo para ser exibido na sua Action Bar em frente à seta
        //-------------------------------------------

        spinner = (Spinner)findViewById(R.id.spnCategoria);
        btnAdd =(Button)findViewById(R.id.btnEnviar);
        btnView =(Button)findViewById(R.id.btnConsultar);
        btnUpdate =(Button)findViewById(R.id.btnListar);


        txtId =(EditText)findViewById(R.id.id);
        txtQnt =(EditText)findViewById(R.id.txtQnt);
        txtNome = (EditText)findViewById(R.id.edtcompra);
        TextView txtQuantidade = (TextView) findViewById(R.id.txtQnt);
        TextView txtnome = (TextView) findViewById(R.id.edtcompra);
        realm = Realm.getDefaultInstance();

        Button bnt_salva = (Button)findViewById(R.id.btnEnviar);

//------------------------------------------------------------
        Bundle args = getIntent().getExtras();
        String resultado = args.getString("compra");
        final TextView txt_resultado = (TextView)findViewById(R.id.txtcompra);
        txt_resultado.setText(resultado);

//-----------------------------------------------------------
        final Spinner spnCategorias = (Spinner)findViewById(R.id.spnCategoria);
        String[] categoria = {"Frios", "Vegetais", "Limpeza", "Higiene"};
        final ArrayAdapter adapterlist = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, categoria);
        adapterlist.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCategorias.setAdapter(adapterlist);
//-----------------------------------------------------------

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
    private void alert(String msg)
    {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    //-------------------------------------------------------------------------------------------------------------------------

    public void clickAction(View view){
        switch (view.getId()){
            case R.id.btnEnviar:
                addRecord();
                break;
            case R.id.btnConsultar:

                String id_da_compra = txtId.getText().toString();
                Intent intent = new Intent(this, ConsultaActivity.class);
                Bundle params = new Bundle();
                params.putString("consulta_id", id_da_compra);
                intent.putExtras(params);
                startActivity(intent);
                break;
            case R.id.btnListar:
                Listar();
                //updateRecord();
                break;


        }
    }
    public void addRecord(){
        Mercadoria compra = new Mercadoria();
        categoria = spinner.getSelectedItem().toString();

       if (compra.autincrement()==0){
           Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
       }
        long key2=realm.where(Mercadoria.class).count();
        int numeroid =(int)key2;
        compra.setId(2);
        compra.setChave(Integer.toString(numeroid));
        compra.setNome(txtNome.getText().toString());
        compra.setQnt(txtQnt.getText().toString());
        compra.setCategoria(categoria);


        realm.beginTransaction();
        realm.copyToRealm(compra);
        realm.commitTransaction();

        RealmResults<Mercadoria> realmusuario = realm.where(Mercadoria.class).findAll();
        ArrayAdapter adapter = new MercadoriaAdapter(this,R.layout.linha2,realmusuario);
        listView = (ListView)findViewById(R.id.lstequipes);
        //ListView listView = (ListView)findViewById(R.id.lstequipes);
        listView.setAdapter(adapter);


    }

    public void viewRecord(){

    }
    public void updateRecord(){
        RealmResults<Mercadoria> realmusuario = realm.where(Mercadoria.class).findAll();
        ArrayAdapter adapter = new MercadoriaAdapter(this,R.layout.linha2,realmusuario);
        listView = (ListView)findViewById(R.id.lstequipes);
        listView.setAdapter(adapter);

    }
    public void deleteRecord(){
        int idusuario = Integer.parseInt(txtId.getText().toString());
        Mercadoria usuario = realm.where(Mercadoria.class).equalTo("id",idusuario).findFirst();
        realm.beginTransaction();
        usuario.deleteFromRealm();
        realm.commitTransaction();

    }
    public void Listar(){
        RealmResults<Mercadoria> realmusuario = realm.where(Mercadoria.class).findAll();
        ArrayAdapter adapter = new MercadoriaAdapter(this,R.layout.linha2,realmusuario);
        listView = (ListView)findViewById(R.id.lstequipes);
        listView.setAdapter(adapter);
    }
    @Override
    protected void onDestroy(){
        realm.close();
        super.onDestroy();
    }


}
