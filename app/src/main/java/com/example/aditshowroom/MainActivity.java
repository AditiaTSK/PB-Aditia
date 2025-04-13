package com.example.aditshowroom;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class MainActivity extends AppCompatActivity {

    private AppCompatButton btnSignIn;
    private AppCompatButton btnSignUp; // Tambahkan variabel untuk tombol Sign Up

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFullScreenMode();
        setContentView(R.layout.awal);
        initializeViews();
        setListeners();
    }

    private void setFullScreenMode() {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

    private void initializeViews() {
        btnSignIn = findViewById(R.id.btn_signin);
        btnSignUp = findViewById(R.id.btn_signup); // Inisialisasi tombol Sign Up
    }

    private void setListeners() {
        btnSignIn.setOnClickListener(v -> navigateToSignin());
        btnSignUp.setOnClickListener(v -> navigateToSignup()); // Tambahkan listener tombol Sign Up
    }

    private void navigateToSignin() {
        startActivity(new Intent(this, Signin.class));
    }

    private void navigateToSignup() {
        startActivity(new Intent(this, Signup.class));
    }
}
