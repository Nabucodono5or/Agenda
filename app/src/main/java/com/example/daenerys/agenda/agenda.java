package com.example.daenerys.agenda;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.jar.Manifest;

public class agenda extends AppCompatActivity {

    ArrayList<String> dados = new ArrayList<String>();
    EditText nome;
    EditText ender;
    EditText tel;
    EditText email;
    ArrayList<EditText> entrada;
    ArrayList<String> arq = new ArrayList<>();
    boolean nenhumContato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);

        nome = (EditText) findViewById(R.id.editTextNome);
        ender = (EditText) findViewById(R.id.editTextEnd);
        tel = (EditText) findViewById(R.id.editTextTelefone);
        email = (EditText) findViewById(R.id.editTextEmail);

        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[] {android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }//if

        entrada = new ArrayList<>();
        entrada.add(nome);
        entrada.add(ender);
        entrada.add(tel);
        entrada.add(email);

    }//onCreate

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode){
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    entrada = new ArrayList<>();
                    entrada.add(nome);
                    entrada.add(ender);
                    entrada.add(tel);
                    entrada.add(email);
                }else {
                    Toast.makeText(this, "Permission denied to read and write your External storage", Toast.LENGTH_SHORT).show();
                }//else
                return;
            }//case 1
        }//switch
    }//onRequestPermissionsResult


    public void armazenaDados(){
        for(EditText e : entrada){
            dados.add(e.getText().toString());
        }//for

    }//armazenaDados

    private boolean isNull(){

        for(EditText e : entrada){
            if(e.getText().toString().matches("")){
                return true;
            }//if
        }//for e

        return false;
    }//isNull


    //Escreve no arquivo
    public void onClickSalvar(View view){

        if (isNull()){
            Toast.makeText(this, "Erro : Preencha todos os campos",Toast.LENGTH_LONG).show();
        } else {
            //aramazenar os dados em um arquivo
            String nomeArq;
            File file;

            try{

                //armazenamento externo
                nomeArq = nome.getText().toString();
                file = new File(Environment.getExternalStorageDirectory(),nomeArq);

                armazenaDados();

                BufferedWriter buffWrite = new BufferedWriter(new FileWriter(file));

                buffWrite.append(nome.getText().toString() + " \n");
                buffWrite.append("Telefone: " + tel.getText().toString() + " \n");
                buffWrite.append("Endereço: " + ender.getText().toString() + " \n");
                buffWrite.append("email: "+email.getText().toString() + " \n");

                buffWrite.close();

                Toast.makeText(this,"Dados aramazenados com sucesso",Toast.LENGTH_SHORT);
                onClickLimpar(view);
            }catch (Exception e){
                Toast.makeText(this, e.getMessage(),Toast.LENGTH_SHORT);
            }//catch
        }//else
    }//onClickSalvar


    public void onClickLimpar(View view){
        nome.setText("");
        ender.setText("");
        tel.setText("");
        email.setText("");
    }//onClickLimpar


    public void listar(){
        nenhumContato = false;
        File root = android.os.Environment.getExternalStorageDirectory();
        File diretorio = new File(root.toString());
        File[] arquivos = diretorio.listFiles();

        if(arquivos != null) {
            int length = arquivos.length;

            for (int i = 0; i < length; ++i) {
                File f = arquivos[i];

                if (f.isFile()) {

                    arq.add(f.getName());
                }//if f
            }//for i

        }else {
            nenhumContato = true;
        }
    }//Listar


    public void onClickContatos(View view){
        if(!nenhumContato) {
            listar();
            Intent intent = new Intent(this, Contatos.class);
            intent.putExtra("arquivos", arq);
            startActivity(intent);
        }else {
            Toast.makeText(this, "Nenhum registro foi encontrado", Toast.LENGTH_LONG).show();
        }//else
    }//onClickContatos
}//class
