package com.alfian.android.kurirlaudry.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ALFIAN on 17/06/2017.
 */

public class ResponseDataKurir {
    @SerializedName("status")
    String status;
    @SerializedName("data")
    List<DataKurir> data;

    public String getStatus() {
        return status;
    }

    public List<DataKurir> getData() {
        return data;
    }
}
