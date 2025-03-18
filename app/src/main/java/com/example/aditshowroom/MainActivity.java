package com.example.aditshowroom;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Menghilangkan status bar
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.awal);

        // Menghubungkan tombol Sign In dengan ID di layout
        AppCompatButton btnSignIn = findViewById(R.id.btn_signin);

        // Menambahkan event click listener untuk berpindah halaman
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Signin.class);
                startActivity(intent);
            }
        });

        // Menghubungkan tombol Sign Up dengan ID di layout
        AppCompatButton btnSignUp = findViewById(R.id.btn_signup);

        // Menambahkan event click listener untuk berpindah halaman
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Signup.class);
                startActivity(intent);
            }
        });
    }
}
