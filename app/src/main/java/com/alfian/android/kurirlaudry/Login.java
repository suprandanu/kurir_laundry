package com.alfian.android.kurirlaudry;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alfian.android.kurirlaudry.model.ResponseDataKurir;
import com.alfian.android.kurirlaudry.model.Responses;
import com.alfian.android.kurirlaudry.volley.Constants;
import com.alfian.android.kurirlaudry.volley.SessionManager;
import com.alfian.android.kurirlaudry.volley.WebServiceConnect;
import com.google.gson.Gson;

import java.util.Hashtable;
import java.util.Map;

public class Login extends AppCompatActivity {
    SessionManager sm;
    WebServiceConnect wsc;
    TextView hub;
    EditText username, password;
    Button bmasuk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sm = new SessionManager(this);
        if (sm.getStatus().equals("loged")){
            startActivity(new Intent(this, Beranda.class));
            this.finish();
        }
        hub = (TextView) findViewById(R.id.hub_kami);
        bmasuk = (Button) findViewById(R.id.bmasuk);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    bmasuk.setBackgroundResource(R.drawable.press_biru);
                    bmasuk.setTextColor(Color.WHITE);
                } else {
                    bmasuk.setTextColor(Color.BLACK);
                    bmasuk.setBackgroundResource(R.drawable.bg_abu);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        hub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + "085642191767"));
                if (ActivityCompat.checkSelfPermission(Login.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(intent);
            }
        });

        bmasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.getText().length() > 0){
                    if (password.getText().length() > 0){
                        wsc = new WebServiceConnect();
                        Map<String, String> param = new Hashtable<String, String>();
                        param.put("username", username.getText().toString());
                        param.put("password", password.getText().toString());
                        wsc.connectNow(Constants.BASE_API_KURIR + "&state=login", param, new WebServiceConnect.WscCallBack() {
                            @Override
                            public void onError(String message) {
                                Toast.makeText(getApplicationContext(), "Tidak bisa terkoneksi dengan server", Toast.LENGTH_SHORT).show();
                                Log.d("WSC", "onError: ");
                            }

                            @Override
                            public void onResponse(String response) {
                                Log.d("WSC", "onResponse: "+response);
                                Responses r = new Gson().fromJson(response, Responses.class);
                                if (r.getStatus().equals("success")){
                                    ResponseDataKurir rd = new Gson().fromJson(response, ResponseDataKurir.class);
                                    Toast.makeText(Login.this, "Berhasil login"+" "+rd.getData().get(0).getNama_kurir(), Toast.LENGTH_SHORT).show();
                                    Log.d("WSC", "onResponse: jos");
                                    sm.saveLogin(new String[]{"loged", rd.getData().get(0).getUser_kurir()});

                                    startActivity(new Intent(Login.this, Beranda.class));
                                    Login.this.finish();
                                }else {
                                    Log.d("WSC", "onResponse: gagal");
                                    ResponseDataKurir rd = new Gson().fromJson(response, ResponseDataKurir.class);
                                    Toast.makeText(Login.this, "Username/Password salah", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });

                    }else {
                        Toast.makeText(Login.this, "Password masih kosong!", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(Login.this, "Username masih kosong!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
