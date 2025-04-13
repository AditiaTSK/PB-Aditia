package com.example.aditshowroom;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        SharedPreferences sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
        String emailKey = sharedPreferences.getString("email", null);

        if (emailKey == null) {
            Intent intent = new Intent(Home.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
            return;
        }

        setContentView(R.layout.home);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_home);

        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.nav_home) {
                    return true;
                } else if (itemId == R.id.nav_profile) {
                    startActivity(new Intent(Home.this, Profile.class));
                    overridePendingTransition(0, 0);
                    return true;
                } else if (itemId == R.id.nav_settings) {
                    startActivity(new Intent(Home.this, Settings.class));
                    overridePendingTransition(0, 0);
                    return true;
                }
                return false;
            }
        });

        CardView katalogMobil = findViewById(R.id.menu_katalog_mobil);
        CardView preorderMobil = findViewById(R.id.menu_preorder_mobil);
        CardView riwayatPreorder = findViewById(R.id.menu_riwayat_preorder);

        katalogMobil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, KatalogMobil.class));
            }
        });

        preorderMobil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, PreOrderMobil.class));
            }
        });

        riwayatPreorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, RiwayatPreorder.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}
