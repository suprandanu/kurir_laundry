package com.alfian.android.kurirlaudry.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ALFIAN on 06/06/2017.
 */

public class ResponseDataBarang {
    @SerializedName("status")
    String status;
    @SerializedName("data")
    List<DataBarang> data;

    public String getStatus() {
        return status;
    }

    public List<DataBarang> getData() {
        return data;
    }
}
