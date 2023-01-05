package com.example.rpl_tubes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class signup extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText username,email,password;
    Button buttonregis;
    Spinner spinner;
    String[]status={"penjual","pembeli"};
    DBhelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        username=findViewById(R.id.username_regis);
        email=findViewById(R.id.email_regis);
        password=findViewById(R.id.password_regis);
        buttonregis=findViewById(R.id.btn_regis);
        spinner=findViewById(R.id.spinner);
        db=new DBhelper(this);

        ArrayAdapter<CharSequence>adapter=ArrayAdapter.createFromResource(this,R.array.spinner1, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text=parent.getItemAtPosition(position).toString();

        buttonregis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nama = username.getText().toString();
                String mail = email.getText().toString();
                String pass = password.getText().toString();
                String sta = parent.getItemAtPosition(position).toString();

                if (TextUtils.isEmpty(nama) || TextUtils.isEmpty(mail) || TextUtils.isEmpty(pass))
                    Toast.makeText(signup.this, "Tidak boleh kosong", Toast.LENGTH_SHORT).show();
                else {
                        if (sta.equals("penjual")) {
                            boolean insert = db.insert_data_penjual(nama, mail, pass, sta);
                            if (insert == true) {
                                Toast.makeText(signup.this, "daftar berhasil", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), Login.class);
                                startActivity(intent);
                            }else
                                Toast.makeText(signup.this, "daftar gagal", Toast.LENGTH_SHORT).show();

                        } if(sta.equals("pembeli")) {
                            boolean insert = db.insert_data_pembeli(nama, mail, pass, sta);
                            if (insert == true) {
                                Toast.makeText(signup.this, "daftar berhasil", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), Login.class);
                                startActivity(intent);
                            }
                        }
                    }
            }
        });


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}