package com.alfian.android.kurirlaudry;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alfian.android.kurirlaudry.adapter.AdapterBarang;
import com.alfian.android.kurirlaudry.model.DataBarang;
import com.alfian.android.kurirlaudry.model.ResponseDataBarang;
import com.alfian.android.kurirlaudry.model.ResponseDataPesanan;
import com.alfian.android.kurirlaudry.model.ResponseDataUser;
import com.alfian.android.kurirlaudry.model.Responses;
import com.alfian.android.kurirlaudry.volley.Constants;
import com.alfian.android.kurirlaudry.volley.SessionManager;
import com.alfian.android.kurirlaudry.volley.WebServiceConnect;
import com.google.gson.Gson;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class DetailPesanan extends AppCompatActivity {

    TextView nama, hub, alamat, ket_lokasi, tgl, ongkir, tot_biaya, status_brg, status_bayar;
    AdapterBarang adapter;
    List<DataBarang> list;
    ListView lv;
    WebServiceConnect wsc;
    SessionManager sm;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pesanan);

        nama        = (TextView)findViewById(R.id.nama);
        hub         = (TextView)findViewById(R.id.hub_kami);
        alamat      = (TextView)findViewById(R.id.alamat);
        ket_lokasi  = (TextView)findViewById(R.id.ket_lokasi);
        tgl         = (TextView)findViewById(R.id.tgl);
        ongkir      = (TextView)findViewById(R.id.ongkir);
        tot_biaya   = (TextView)findViewById(R.id.tot_biaya);
        status_brg  = (TextView)findViewById(R.id.status_brg);
        status_bayar= (TextView)findViewById(R.id.status_bayar);

        tampilBarang();
        swipeRefreshLayout  = (SwipeRefreshLayout)findViewById(R.id.swipe);

        //getIntent().getStringExtra("filter").equals("edit");
        //getIntent().getStringExtra("user");
        //Log.d("CAPEK", "onCreate: "+ getIntent().getStringExtra("user"));

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                myUpdateOperation();
                swipeRefreshLayout.setRefreshing(false);

            }
        });
    }

    public void tampilBarang(){
        wsc = new WebServiceConnect();
        Map<String, String> param = new Hashtable<String, String>();
        param.put("username", getIntent().getStringExtra("user"));
        wsc.connectNow(Constants.BASE_API_KURIR + "&state=get_data_pesanan", param, new WebServiceConnect.WscCallBack() {
            @Override
            public void onError(String message) {
                Toast.makeText(getApplicationContext(), "Tidak bisa terkoneksi dengan server", Toast.LENGTH_SHORT).show();
                Log.d("WSC", "onError: ");
            }

            @Override
            public void onResponse(String response) {
                Log.d("WSC", "onResponse: " + response);
                Responses r = new Gson().fromJson(response, Responses.class);
                if (r.getStatus().equals("success")){
                    final ResponseDataPesanan rdp = new Gson().fromJson(response, ResponseDataPesanan.class);
                    final ResponseDataUser rdu = new Gson().fromJson(response, ResponseDataUser.class);
                    nama.setText(rdu.getData().get(0).getNama_plg());
                    alamat.setText(rdp.getData().get(0).getAlamat());
                    ket_lokasi.setText(rdp.getData().get(0).getKet_lokasi());

                    tgl.setText(rdp.getData().get(0).getTanggal());
                    ongkir.setText("Rp "+rdp.getData().get(0).getOngkir());
                    tot_biaya.setText("Rp "+rdp.getData().get(0).getTotal_biaya());
                    status_brg.setText(rdp.getData().get(0).getStatus_brg());
                    status_bayar.setText(rdp.getData().get(0).getStatus_bayar());

                    TextView status = (TextView)findViewById(R.id.status);
                    if (rdp.getData().get(0).getStatus_brg().equals("Proses")){
                        status.setVisibility(View.VISIBLE);
                    }

                    hub.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(Intent.ACTION_DIAL);
                            intent.setData(Uri.parse("tel:" + rdu.getData().get(0).getHp()));
                            if (ActivityCompat.checkSelfPermission(DetailPesanan.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
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

                    WebServiceConnect wsc2 = new WebServiceConnect();
                    Map<String, String> params = new Hashtable<>();
                    //String id = getIntent().getStringExtra("id");
                    //Log.d("ID", "onCreate: "+ id);
                    params.put("id_pesanan", rdp.getData().get(0).getId_pesanan());
                    wsc2.connectNow(Constants.BASE_API_KURIR + "&state=get_data_barang", params, new WebServiceConnect.WscCallBack() {
                        @Override
                        public void onError(String message) {
                            Toast.makeText(getApplicationContext(), "Tidak bisa terkoneksi dengan server", Toast.LENGTH_SHORT).show();
                            Log.d("WSC", "onError: ");
                        }

                        @Override
                        public void onResponse(String response) {
                            Log.d("WSC", "onResponse: " + response);
                            Responses r = new Gson().fromJson(response, Responses.class);
                            if (r.getStatus().equals("success")){
                                ResponseDataBarang rdb = new Gson().fromJson(response, ResponseDataBarang.class);
                                Log.d("DIA", "onResponse: "+ rdb.getData().get(0).getId_pesanan());
                                lv = (ListView)findViewById(R.id.list_barang);
                                AdapterBarang ab = new AdapterBarang(DetailPesanan.this, rdb.getData());
                                lv.setAdapter(ab);
                            }
                        }
                    });

                }else {
                    Log.d("WSC", "onResponse: gagal");
                    ResponseDataPesanan rdp = new Gson().fromJson(response, ResponseDataPesanan.class);
                }
            }
        });
    }
    public void myUpdateOperation(){
        tampilBarang();
    }
}
