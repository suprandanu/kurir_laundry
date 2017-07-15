package com.alfian.android.kurirlaudry.volley;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.Map;

/**
 * Created by Alfian on 15/06/2017.
 */
public class WebServiceConnect {
    public WebServiceConnect() {
    }

    public interface WscCallBack {
        void onError(String message);

        void onResponse(String response);
    }

    WscCallBack mCallback;

    public void connectNow(String URL, Map<String, String> p, WscCallBack callBack) {
        mCallback = callBack;
        final Map<String, String> params = p;
        Log.d("WSC", URL);
        StringRequest jsonObjReq = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String json) {
                        Log.d("WSC al", json);
                        mCallback.onResponse(json);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mCallback.onError(error.getMessage());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };
        RetryPolicy pl = new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsonObjReq.setRetryPolicy(pl);
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }
}
