package com.alfian.android.kurirlaudry.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Alfian on 15/02/2017.
 */
public class ResponseDataUser {

    @SerializedName("status")
    String status;
    @SerializedName("data")
    List<DataUser> data;

    public String getStatus() {
        return status;
    }

    public List<DataUser> getData() {
        return data;
    }
}
