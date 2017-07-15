package com.alfian.android.kurirlaudry.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alfian.android.kurirlaudry.R;
import com.alfian.android.kurirlaudry.model.DataBarang;
import com.alfian.android.kurirlaudry.model.Responses;
import com.alfian.android.kurirlaudry.volley.Constants;
import com.alfian.android.kurirlaudry.volley.WebServiceConnect;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * Created by ALFIAN on 05/07/2017.
 */

public class AdapterBarang extends BaseAdapter {
    Activity mActivity;
    List<DataBarang> mData;
    ArrayList<DataBarang> arrayList;
    ListView lv;
    WebServiceConnect wsc;
    //String id_psn;


    public AdapterBarang(Activity a, List<DataBarang> d){
        mActivity       = a;
        mData           = d;
        arrayList       = new ArrayList<DataBarang>();
        arrayList.addAll(mData);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final DataBarang brg = mData.get(i);
        if (view == null) {
            LayoutInflater li = mActivity.getLayoutInflater();
            view = li.inflate(R.layout.list_barang, null);
        }

        wsc = new WebServiceConnect();

        TextView jenis_brg  = (TextView) view.findViewById(R.id.jenis_brg);
        TextView jumlah_brg = (TextView) view.findViewById(R.id.jumlah_brg);
        TextView jenis_jasa = (TextView) view.findViewById(R.id.jenis_jasa);
        TextView keterangan = (TextView) view.findViewById(R.id.ket_brg);
        final TextView berat      = (TextView) view.findViewById(R.id.berat);
        final TextView biaya      = (TextView) view.findViewById(R.id.biaya);

        jenis_brg.setText(brg.getJenis_brg());
        jumlah_brg.setText(brg.getJumlah_brg());
        jenis_jasa.setText(brg.getJenis_jasa());
        jumlah_brg.setText(brg.getJumlah_brg());
        keterangan.setText(brg.getKeterangan());
        berat.setText(brg.getBerat());
        biaya.setText(brg.getBiaya());

        TextView ubah = (TextView) view.findViewById(R.id.ubah);

        ubah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mActivity);
                LayoutInflater inflater = mActivity.getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.form_edit_barang, null);
                dialogBuilder.setView(dialogView);
                dialogBuilder.setCancelable(true);
                final AlertDialog alertDialog = dialogBuilder.create();

                final TextView judul = (TextView)dialogView.findViewById(R.id.judul);
                final EditText input = (EditText)dialogView.findViewById(R.id.input);
                TextView tambah = (TextView) dialogView.findViewById(R.id.tambah);

                if (brg.getJenis_jasa().equals("Cuci")) {
                    judul.setText("Masukkan BIAYA");
                }else if (brg.getJenis_brg().equals("Jas")){
                    judul.setText("Masukkan BIAYA");
                }else {
                    judul.setText("Masukkan BERAT");
                }

                tambah.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (judul.getText().equals("Masukkan BIAYA")){
                            Map<String, String> param = new Hashtable<String, String>();
                            param.put("id_barang", brg.getId_barang());
                            param.put("berat", "0");
                            param.put("biaya", input.getText().toString());
                            wsc.connectNow(Constants.BASE_API_KURIR + "&state=edit_barang", param, new WebServiceConnect.WscCallBack() {
                                @Override
                                public void onError(String message) {
                                    Toast.makeText(mActivity, "Tidak bisa terkoneksi dengan server", Toast.LENGTH_SHORT).show();
                                    Log.d("WSC", "onError: ");
                                }

                                @Override
                                public void onResponse(String response) {
                                    Log.d("WSC", "onResponse: "+response);
                                    Responses r = new Gson().fromJson(response, Responses.class);
                                    if (r.getStatus().equals("success")){
                                        Toast.makeText(mActivity, "Edit Barang Sukses", Toast.LENGTH_SHORT).show();

                                        Map<String, String> param = new Hashtable<String, String>();
                                        param.put("id_pesanan", brg.getId_pesanan());
                                        Log.d("BARANG", "onResponse: " + brg.getId_pesanan());
                                        param.put("tot_biaya", "0");
                                        param.put("status_brg", "Proses");
                                        wsc.connectNow(Constants.BASE_API_KURIR + "&state=edit_pesanan", param, new WebServiceConnect.WscCallBack() {
                                            @Override
                                            public void onError(String message) {
                                                Toast.makeText(mActivity, "Tidak bisa terkoneksi dengan server", Toast.LENGTH_SHORT).show();
                                                Log.d("WSC", "onError: ");
                                            }

                                            @Override
                                            public void onResponse(String response) {
                                                Log.d("WSC", "onResponse: "+response);
                                                Responses r = new Gson().fromJson(response, Responses.class);
                                                if (r.getStatus().equals("success")){
                                                    Toast.makeText(mActivity, "Edit Pesanan Sukses", Toast.LENGTH_SHORT).show();
                                                }else {
                                                    Toast.makeText(mActivity, "Edit Pesanan Gagal", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });

                                        alertDialog.dismiss();
                                    }else {
                                        Toast.makeText(mActivity, "Edit Barang Gagal", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                        }else if (brg.getJenis_jasa().equals("Setrika")){
                            int berat = Integer.parseInt(input.getText().toString());
                            int biaya = (berat * 2500);
                            Map<String, String> param = new Hashtable<String, String>();
                            param.put("id_barang", brg.getId_barang());
                            param.put("berat", input.getText().toString());
                            param.put("biaya", String.valueOf(biaya));
                            wsc.connectNow(Constants.BASE_API_KURIR + "&state=edit_barang", param, new WebServiceConnect.WscCallBack() {
                                @Override
                                public void onError(String message) {
                                    Toast.makeText(mActivity, "Tidak bisa terkoneksi dengan server", Toast.LENGTH_SHORT).show();
                                    Log.d("WSC", "onError: ");
                                }

                                @Override
                                public void onResponse(String response) {
                                    Log.d("WSC", "onResponse: "+response);
                                    Responses r = new Gson().fromJson(response, Responses.class);
                                    if (r.getStatus().equals("success")){
                                        Toast.makeText(mActivity, "Edit Barang Sukses", Toast.LENGTH_SHORT).show();
                                        editPesanan();
                                        alertDialog.dismiss();
                                    }else {
                                        Toast.makeText(mActivity, "Edit Barang Gagal", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }else if (brg.getJenis_jasa().equals("Cuci + Setrika")){
                            int berat = Integer.parseInt(input.getText().toString());
                            int biaya = (berat * 3000);
                            Map<String, String> param = new Hashtable<String, String>();
                            param.put("id_barang", brg.getId_barang());
                            param.put("berat", input.getText().toString());
                            param.put("biaya", String.valueOf(biaya));
                            wsc.connectNow(Constants.BASE_API_KURIR + "&state=edit_barang", param, new WebServiceConnect.WscCallBack() {
                                @Override
                                public void onError(String message) {
                                    Toast.makeText(mActivity, "Tidak bisa terkoneksi dengan server", Toast.LENGTH_SHORT).show();
                                    Log.d("WSC", "onError: ");
                                }

                                @Override
                                public void onResponse(String response) {
                                    Log.d("WSC", "onResponse: "+response);
                                    Responses r = new Gson().fromJson(response, Responses.class);
                                    if (r.getStatus().equals("success")){
                                        Toast.makeText(mActivity, "Edit Barang Sukses", Toast.LENGTH_SHORT).show();
                                        editPesanan();
                                        alertDialog.dismiss();
                                    }else {
                                        Toast.makeText(mActivity, "Edit Barang Gagal", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }
                });

                alertDialog.show();
            }
        });
        return view;
    }

    public void editPesanan(){

    }
}
