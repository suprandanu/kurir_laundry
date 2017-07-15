package com.alfian.android.kurirlaudry.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ALFIAN on 17/06/2017.
 */

public class DataKurir {
    @SerializedName("user_kurir")
    String user_kurir;
    @SerializedName("pass_kurir")
    String pass_kurir;
    @SerializedName("nama_kurir")
    String nama_kurir;
    @SerializedName("hp")
    String hp;

    public String getUser_kurir() {
        return user_kurir;
    }

    public String getPass_kurir() {
        return pass_kurir;
    }

    public String getNama_kurir() {
        return nama_kurir;
    }

    public String getHp() {
        return hp;
    }
}
