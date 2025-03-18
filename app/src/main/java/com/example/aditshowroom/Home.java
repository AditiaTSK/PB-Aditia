package com.example.aditshowroom;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Menghilangkan status bar
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        // Cek apakah user sudah login
        SharedPreferences sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
        String emailKey = sharedPreferences.getString("email", null);

        if (emailKey == null) {
            // Jika tidak ada user yang login, kembali ke MainActivity
            Intent intent = new Intent(Home.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish(); // Tutup Home agar tidak bisa diakses lagi
            return;
        }

        setContentView(R.layout.home);

        // Menghubungkan tombol Profile dengan ID di layout
        AppCompatButton btnProfile = findViewById(R.id.btn_profile);

        // Event click listener untuk membuka halaman Profile
        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, Profile.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        // Jika tombol back ditekan, keluar dari aplikasi
        finishAffinity(); // Menutup semua aktivitas dan keluar dari aplikasi
    }
}
