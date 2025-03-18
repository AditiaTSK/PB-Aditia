package com.example.aditshowroom;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {

    private TextView usernameTextView, emailTextView, umurTextView, alamatTextView;
    private ImageView backButton;
    private DatabaseReference databaseReference;
    private SharedPreferences sharedPreferences;
    private Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Menghilangkan status bar
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.profile);

        usernameTextView = findViewById(R.id.usernameTextView);
        emailTextView = findViewById(R.id.emailTextView);
        umurTextView = findViewById(R.id.umurTextView);
        alamatTextView = findViewById(R.id.alamatTextView);
        logoutButton = findViewById(R.id.logoutButton);
        backButton = findViewById(R.id.backButton);

        sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
        String emailKey = sharedPreferences.getString("email", null);

        if (emailKey != null) {
            databaseReference = FirebaseDatabase.getInstance().getReference("users").child(emailKey);
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        String username = snapshot.child("username").getValue(String.class);
                        String email = snapshot.child("email").getValue(String.class);
                        String umur = snapshot.child("umur").getValue(String.class);
                        String alamat = snapshot.child("alamat").getValue(String.class);

                        usernameTextView.setText("Username: " + username);
                        emailTextView.setText("Email: " + email);
                        umurTextView.setText("Umur: " + umur);
                        alamatTextView.setText("Alamat: " + alamat);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(Profile.this, "Gagal mengambil data", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "Pengguna belum login!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Profile.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }

        // Logout Button
        logoutButton.setOnClickListener(view -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear(); // Hapus data login
            editor.apply();

            // Hapus semua aktivitas dan kembali ke MainActivity
            Intent intent = new Intent(Profile.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        // Tombol kembali
        backButton.setOnClickListener(view -> finish());
    }

    @Override
    public void onBackPressed() {
        // Jika tombol back ditekan, keluar dari aplikasi
        finishAffinity();
    }
}
