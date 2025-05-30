package com.example.aditshowroom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Signup extends AppCompatActivity {
    private EditText usernameInput, emailInput, sandiInput, umurInput, alamatInput;
    private Button buttonDaftar;
    private ImageView backButton;
    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.signup);

        backButton = findViewById(R.id.backButton);
        buttonDaftar = findViewById(R.id.buttonDaftar);
        usernameInput = findViewById(R.id.username);
        emailInput = findViewById(R.id.email);
        sandiInput = findViewById(R.id.sandi);
        umurInput = findViewById(R.id.umur);
        alamatInput = findViewById(R.id.alamat);

        database = FirebaseDatabase.getInstance().getReference("users");

        buttonDaftar.setOnClickListener(view -> {
            String username = usernameInput.getText().toString().trim();
            String email = emailInput.getText().toString().trim();
            String sandi = sandiInput.getText().toString().trim();
            String umur = umurInput.getText().toString().trim();
            String alamat = alamatInput.getText().toString().trim();
            String emailKey = email.replace(".", ",");

            if (username.isEmpty() || email.isEmpty() || sandi.isEmpty() || umur.isEmpty() || alamat.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Semua field harus diisi", Toast.LENGTH_SHORT).show();
                return;
            }

            User user = new User(username, email, sandi, umur, alamat);
            database.child(emailKey).setValue(user).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Pendaftaran Berhasil", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), Signin.class));
                } else {
                    Toast.makeText(getApplicationContext(), "Pendaftaran Gagal, coba lagi", Toast.LENGTH_SHORT).show();
                }
            });
        });

        backButton.setOnClickListener(view -> finish());
    }

    public static class User {
        public String username;
        public String email;
        public String password;
        public String umur;
        public String alamat;

        public User() {}

        public User(String username, String email, String password, String umur, String alamat) {
            this.username = username;
            this.email = email;
            this.password = password;
            this.umur = umur;
            this.alamat = alamat;
        }
    }
}
