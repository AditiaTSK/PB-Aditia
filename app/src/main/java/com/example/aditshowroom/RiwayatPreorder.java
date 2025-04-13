package com.example.aditshowroom;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RiwayatPreorder extends AppCompatActivity {

    private ListView listView;
    private TextView emptyTextView;
    private DatabaseReference databaseReference;
    private SharedPreferences sharedPreferences;
    private ArrayList<PreorderItem> preorderList;
    private PreorderAdapter adapter;
    private ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.riwayatpreorder);

        listView = findViewById(R.id.listView);
        emptyTextView = findViewById(R.id.emptyTextView);
        preorderList = new ArrayList<>();
        backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(view -> finish());

        sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
        String emailKey = sharedPreferences.getString("email", null);

        if (emailKey != null) {
            databaseReference = FirebaseDatabase.getInstance().getReference("preorders");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    preorderList.clear();
                    for (DataSnapshot data : snapshot.getChildren()) {
                        PreorderItem item = data.getValue(PreorderItem.class);
                        preorderList.add(item);
                    }
                    if (preorderList.isEmpty()) {
                        emptyTextView.setVisibility(View.VISIBLE);
                        listView.setVisibility(View.GONE);
                    } else {
                        emptyTextView.setVisibility(View.GONE);
                        listView.setVisibility(View.VISIBLE);
                        adapter = new PreorderAdapter(RiwayatPreorder.this, preorderList);
                        listView.setAdapter(adapter);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(RiwayatPreorder.this, "Gagal mengambil data", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "Pengguna belum login!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(RiwayatPreorder.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
    }
}
