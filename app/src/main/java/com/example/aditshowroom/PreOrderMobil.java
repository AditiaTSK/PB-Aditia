package com.example.aditshowroom;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PreOrderMobil extends AppCompatActivity {

    private Spinner spinnerMobil, spinnerWarna, spinnerPembayaran;
    private EditText inputUangMuka;
    private Button buttonPreOrder;
    private ImageView backButton;
    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFullScreenMode();
        setContentView(R.layout.preordermobil);

        initViews();
        setupFirebase();
        setupSpinners();
        setupListeners();
    }

    private void setFullScreenMode() {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        getSupportActionBar().hide();
    }

    private void initViews() {
        spinnerMobil = findViewById(R.id.spinnerMobil);
        spinnerWarna = findViewById(R.id.spinnerWarna);
        spinnerPembayaran = findViewById(R.id.spinnerPembayaran);
        inputUangMuka = findViewById(R.id.inputUangMuka);
        buttonPreOrder = findViewById(R.id.buttonPreOrder);
        backButton = findViewById(R.id.backButton);
    }

    private void setupFirebase() {
        database = FirebaseDatabase.getInstance().getReference("preorders");
    }

    private void setupSpinners() {
        setupSpinner(spinnerMobil, R.array.mobil_options);
        setupSpinner(spinnerWarna, R.array.warna_options);
        setupSpinner(spinnerPembayaran, R.array.pembayaran_options);
    }

    private void setupSpinner(Spinner spinner, int arrayResId) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                arrayResId, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void setupListeners() {
        backButton.setOnClickListener(view -> finish());
        buttonPreOrder.setOnClickListener(view -> submitPreOrder());
    }

    private void submitPreOrder() {
        String mobil = spinnerMobil.getSelectedItem().toString();
        String warna = spinnerWarna.getSelectedItem().toString();
        String pembayaran = spinnerPembayaran.getSelectedItem().toString();
        String uangMuka = inputUangMuka.getText().toString().trim();

        if (uangMuka.isEmpty()) {
            showToast("Uang muka harus diisi");
            return;
        }

        String orderId = database.push().getKey();
        if (orderId == null) {
            showToast("Gagal membuat Order ID");
            return;
        }

        PreOrder order = new PreOrder(mobil, warna, pembayaran, uangMuka);
        database.child(orderId).setValue(order).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                showToast("Pre Order berhasil!");
                navigateToRiwayatPreorder();
            } else {
                Log.e("FirebaseError", "Error: ", task.getException());
                showToast("Gagal melakukan pre order, coba lagi");
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void navigateToRiwayatPreorder() {
        Intent intent = new Intent(this, RiwayatPreorder.class);
        startActivity(intent);
        finish();
    }

    public static class PreOrder {
        public String mobil, warna, pembayaran, uangMuka;

        public PreOrder() {}

        public PreOrder(String mobil, String warna, String pembayaran, String uangMuka) {
            this.mobil = mobil;
            this.warna = warna;
            this.pembayaran = pembayaran;
            this.uangMuka = uangMuka;
        }
    }
}
