package com.alfian.android.kurirlaudry.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ALFIAN on 02/06/2017.
 */

public class ResponseDataPesanan {
    @SerializedName("status")
    String status;
    @SerializedName("data")
    List<DataPesanan> data;

    public String getStatus() {
        return status;
    }

    public List<DataPesanan> getData() {
        return data;
    }
}
