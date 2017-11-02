package com.example.daenerys.agenda;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class Contatos extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    ArrayList<String> arqs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contatos);

        Intent intent = getIntent();

        Bundle os = intent.getExtras();
        arqs = os.getStringArrayList("arquivos");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, arqs);
        ListView listView = (ListView) findViewById(R.id.listViewContatos);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(this);

    }//onCreate


    @Override
    public void onClick(View view) {
        //null, usado para teste
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(this, InfoActivity.class);
        intent.putExtra("file", arqs.get(i));
        startActivity(intent);
        //configurar para que esse método chame uma intent e envie o nome do arquivo a ser buscado
    }
}//class
