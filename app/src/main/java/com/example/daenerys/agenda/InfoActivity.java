package com.example.daenerys.agenda;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class InfoActivity extends AppCompatActivity {

    TextView texto1;
    TextView texto2;
    TextView texto3;
    TextView texto4;

    ArrayList<TextView> listaTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        Intent intent = getIntent();

        Bundle b = intent.getExtras();
        String filename = b.getString("file");

        listaTextView = new ArrayList<>();

        texto1 = (TextView) findViewById(R.id.textView5);
        texto2 = (TextView) findViewById(R.id.textView6);
        texto3 = (TextView) findViewById(R.id.textView7);
        texto4 = (TextView) findViewById(R.id.textView8);

        listaTextView.add(texto1);
        listaTextView.add(texto2);
        listaTextView.add(texto3);
        listaTextView.add(texto4);

        String linha;

        try{
            File r = new  File(Environment.getExternalStorageDirectory(), filename);
            BufferedReader reader = new BufferedReader(new FileReader(r));

            for(TextView t : listaTextView){
                linha = reader.readLine();
                t.setText(linha);
            }

            reader.close();

        }catch (Exception e){
            Toast.makeText(this, e.getMessage(),Toast.LENGTH_SHORT);
        }//
    }//onCreate
}//class
