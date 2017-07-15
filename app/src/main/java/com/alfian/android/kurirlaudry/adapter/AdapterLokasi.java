package com.alfian.android.kurirlaudry.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.alfian.android.kurirlaudry.model.DataPesanan;
import com.alfian.android.kurirlaudry.volley.SessionManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ALFIAN on 19/06/2017.
 */

public class AdapterLokasi extends BaseAdapter {
    Activity mActivity;
    List<DataPesanan> mData;
    ArrayList<DataPesanan> arrayList;
    SessionManager sm;

    public AdapterLokasi(Activity a, List<DataPesanan> d){
        mActivity       = a;
        mData           = d;
        arrayList       = new ArrayList<DataPesanan>();
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        final DataPesanan dp = mData.get(i);
        sm = new SessionManager(mActivity);

        sm.savePesanan(new String[]{ dp.getId_pesanan(), dp.getUser_plg(), dp.getLongitute(), dp.getLatitute()});

        return view;
    }
}
