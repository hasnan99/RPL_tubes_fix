package com.example.rpl_tubes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class admin_login extends AppCompatActivity {
    EditText username,password;
    Button btn_login;
    DBhelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        username=findViewById(R.id.username_login);
        password=findViewById(R.id.password_login);
        btn_login=findViewById(R.id.button_login);
        db=new DBhelper(this);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nama=username.getText().toString();
                String pass=password.getText().toString();

                if (TextUtils.isEmpty(nama)||TextUtils.isEmpty(pass))
                    Toast.makeText(admin_login.this,"Tidak boleh kosong",Toast.LENGTH_SHORT).show();
                else {
                    boolean check_user_password_admin = db.check_user_password_admin(nama, pass);
                    if (check_user_password_admin == true) {
                        Toast.makeText(admin_login.this, "Login berhasil", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), firstHome_admin.class);
                        intent.putExtra("nama_user", nama);
                        startActivity(intent);
                    }else{
                        Toast.makeText(admin_login.this,"Login Gagal",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


    }

}