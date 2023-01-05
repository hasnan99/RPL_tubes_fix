package com.example.rpl_tubes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class Login extends AppCompatActivity {
    EditText username,password;
    Button btn_login;
    DBhelper db;
    int i = 0;
    ImageView adminSayur31;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username=findViewById(R.id.username_login);
        password=findViewById(R.id.password_login);
        btn_login=findViewById(R.id.button_login);
        adminSayur31=findViewById(R.id.imageView);
        db=new DBhelper(this);

        adminSayur31.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i++;

                Handler hand_admin = new Handler();
                hand_admin.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (i == 2) {
                            Intent intent = new Intent(getApplicationContext(), admin_login.class);
                            startActivity(intent);
                        }
                        i=0;
                    }
                },700);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nama=username.getText().toString();
                String pass=password.getText().toString();

                if (TextUtils.isEmpty(nama)||TextUtils.isEmpty(pass))
                    Toast.makeText(Login.this,"Tidak boleh kosong",Toast.LENGTH_SHORT).show();
                else {
                    boolean check_user_password_pembeli = db.check_user_password_pembeli(nama, pass);
                    boolean check_user_password_penjual=db.check_user_password_penjual(nama,pass);
                    if (check_user_password_pembeli == true){
                        Toast.makeText(Login.this, "Login berhasil", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), beranda.class);
                        intent.putExtra("nama_user",nama);
                        startActivity(intent);
                    } else if(check_user_password_penjual==true){
                        Toast.makeText(Login.this, "Login berhasil", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), beranda_penjual.class);
                        intent.putExtra("nama",nama);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(Login.this,"Login Gagal",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


    }
    public void signup(View view){
        Intent intent=new Intent(this,signup.class);
        startActivity(intent);
    }
}