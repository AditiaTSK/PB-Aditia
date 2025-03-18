package com.example.aditshowroom;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Signin extends AppCompatActivity {
    private EditText emailInput, sandiInput;
    private Button buttonMasuk;
    private ImageView backButton;
    private DatabaseReference database;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Menghilangkan status bar
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.signin);

        buttonMasuk = findViewById(R.id.buttonmasuk);
        emailInput = findViewById(R.id.email);
        sandiInput = findViewById(R.id.sandi);
        backButton = findViewById(R.id.backButton);

        database = FirebaseDatabase.getInstance().getReference("users");
        sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);

        buttonMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailInput.getText().toString().trim();
                String sandi = sandiInput.getText().toString().trim();
                String emailKey = email.replace(".", ","); // Ubah '.' jadi ',' agar cocok di Firebase

                if (email.isEmpty() || sandi.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Email atau Password tidak boleh kosong", Toast.LENGTH_SHORT).show();
                    return;
                }

                database.child(emailKey).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            String storedPassword = snapshot.child("password").getValue(String.class);
                            if (storedPassword != null && storedPassword.equals(sandi)) {
                                // Simpan email pengguna yang login di SharedPreferences
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("email", emailKey);
                                editor.apply();

                                Toast.makeText(getApplicationContext(), "Login Berhasil", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), Home.class));
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "Password Salah", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Data Belum Terdaftar", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getApplicationContext(), "Terjadi kesalahan, coba lagi nanti", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        // Tombol kembali
        backButton.setOnClickListener(view -> finish());
    }
}
