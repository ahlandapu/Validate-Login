package com.ahlanda.validatelogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;

public class LoginActivity extends AppCompatActivity {

    public static final String FILENAME = "Login";

    EditText editUsername, editPassword;
    Button btnLogin, btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editPassword = findViewById(R.id.editPassword);
        editUsername = findViewById(R.id.editUsername);
        btnLogin = findViewById(R.id.butLogin);
        btnRegister = findViewById(R.id.butRegister);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
        void simpanFileLogin(){
            String isiFile = editUsername.getText().toString() + ";" +editPassword.getText().toString();
            File file = new File(getFilesDir(), FILENAME);
            FileOutputStream outputStream = null;
            try {
                file.createNewFile();
                outputStream = new FileOutputStream(file, false);
                outputStream.write(isiFile.getBytes());
                outputStream.flush();
                outputStream.close();
            }catch (Exception e){
                e.printStackTrace();
            }
            Toast.makeText(this, "Login Berhasil",Toast.LENGTH_SHORT).show();
            onBackPressed();
        }
        void login(){
            File sdcard = getFilesDir();
            File file = new File(sdcard, editUsername.getText().toString());
            if (file.exists()) {
                StringBuilder text = new StringBuilder();
                try {
                    BufferedReader br = new BufferedReader(new FileReader(file));
                    String line = br.readLine();
                    while (line != null) {
                        text.append(line);
                        line = br.readLine();
                    }
                    br.close();
                } catch (IOException e) {
                    System.out.println("Error : "+ e.getMessage());
                }
                String data = text.toString();
                String[] dataUser = data.split(";");

                if (dataUser[1].equals(editPassword.getText().toString())) {
                    simpanFileLogin();
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(this, "Password TIdak Sesuai", Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(this, "Username TIdak Ditemukan", Toast.LENGTH_SHORT).show();
            }
        }
}