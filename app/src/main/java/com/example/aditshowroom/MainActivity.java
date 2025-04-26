package com.example.aditshowroom;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseError;

public class MainActivity extends AppCompatActivity {

    private AppCompatButton btnSignIn;
    private AppCompatButton btnSignUp;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFullScreenMode();
        setContentView(R.layout.awal);

        mAuth = FirebaseAuth.getInstance();
        mAuth.signOut(); // <-- Tambahkan ini, untuk selalu logout user saat buka aplikasi

        initializeViews();
        setListeners();

        // Periksa koneksi ke Firebase Realtime Database
        checkFirebaseConnection();
    }

    private void setFullScreenMode() {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

    private void initializeViews() {
        btnSignIn = findViewById(R.id.btn_signin);
        btnSignUp = findViewById(R.id.btn_signup);
    }

    private void setListeners() {
        btnSignIn.setOnClickListener(v -> navigateToSignin());
        btnSignUp.setOnClickListener(v -> navigateToSignup());
    }

    private void navigateToSignin() {
        startActivity(new Intent(this, Signin.class));
    }

    private void navigateToSignup() {
        startActivity(new Intent(this, Signup.class));
    }

    private void checkFirebaseConnection() {
        // Ambil referensi ke database Firebase
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        // Tambahkan listener untuk memeriksa status koneksi
        reference.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // Berhasil terhubung ke database
                Toast.makeText(MainActivity.this, "Koneksi ke Firebase berhasil!", Toast.LENGTH_SHORT).show();
            } else {
                // Gagal terhubung ke database
                Toast.makeText(MainActivity.this, "Tidak bisa terhubung ke Firebase. Cek koneksi internet.", Toast.LENGTH_LONG).show();
            }
        });
    }
}
