package com.example.pikot.sugophapp.Requests;

import android.app.ProgressDialog;
import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.pikot.sugophapp.RandomClasses.CallBacks;
import com.example.pikot.sugophapp.RandomClasses.Constants;
import com.example.pikot.sugophapp.RandomClasses.RequestHandler;
import com.example.pikot.sugophapp.RandomClasses.SharedPrefManager;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

public class SendMessageRequest {

    private static ProgressDialog progressDialog;

    public static void sendMessage(final Context context, final String errand_id, final String message, final CallBacks callBacks) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Sending");
        progressDialog.show();
        String URL = Constants.URL_SEND_MESSAGE;

        final StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    callBacks.onSuccess(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.hide();
                try {
                    callBacks.onSuccess(null);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("errand_id", errand_id);
                params.put("sender", SharedPrefManager.getInstance(context).getKeyUsername());
                params.put("message", message);

                return params;
            }
        };
        RequestHandler.getInstance(context).addToRequestQueue(stringRequest);
    }
}
