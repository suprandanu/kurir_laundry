package com.alfian.android.kurirlaudry;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.alfian.android.kurirlaudry.volley.SessionManager;

public class Beranda extends AppCompatActivity {
    SessionManager sm;
    Button ambil, kembali, keluar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beranda);

        sm = new SessionManager(this);

        ambil   = (Button)findViewById(R.id.bambil);
        kembali = (Button)findViewById(R.id.bkembali);
        keluar  = (Button)findViewById(R.id.bkeluar);

        ambil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ambil = new Intent(Beranda.this, LokasiPelanggan.class);
                ambil.putExtra("filter", "ambil");
                startActivity(ambil);
            }
        });

        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent kembali = new Intent(Beranda.this, LokasiPelanggan.class);
                kembali.putExtra("filter", "kembali");
                startActivity(kembali);
            }
        });

        keluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Beranda.this);
                builder.setMessage("Apakah yakin ingin keluar dari aplikasi Kurir Laundry?").setCancelable(false).setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sm.clearData();
                        Beranda.this.finish();
                    }
                }).setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).show();
            }
        });
    }
}
