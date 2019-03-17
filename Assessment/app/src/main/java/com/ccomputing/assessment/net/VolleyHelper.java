package com.ccomputing.assessment.net;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.ccomputing.assessment.interfaces.OnVolleyResult;
import com.ccomputing.assessment.utils.DialogGlobal;

import java.util.HashMap;
import java.util.Map;

public class VolleyHelper {

    private OnVolleyResult onVolleyResult;
    private Context context;
    private int method;
    private boolean isDialog;
    private String url;
    private String from;

    @SuppressLint("StaticFieldLeak")
    private static final VolleyHelper ourInstance = new VolleyHelper();

    public static VolleyHelper getInstance() {
        return ourInstance;
    }

    private VolleyHelper() {
    }

    private void setConstructor(Context context, int method, boolean isDialog, String url, String from) {
        this.context = context;
        this.method = method;
        this.isDialog = isDialog;
        this.url = url;
        this.from = from;
    }

    /**
     *  Handling Webservices Request
     */
    public void sendRequest(final Context context, final int method, final HashMap<String,String> params, final boolean isDialog, final String url, final String from) {

        setConstructor(context,method,isDialog,url,from);

        if(isDialog)
        DialogGlobal.getInstance().showLoadingDialog(context);

        StringRequest stringRequest = new StringRequest(method, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("==888","From:"+ from +"--OnResponse:"+response);
                        if(isDialog)
                        DialogGlobal.getInstance().closeLoadingDialog();

                        onVolleyResult = (OnVolleyResult) context;
                        onVolleyResult.onResultSuccess(response, from);
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        onErrorMessage(context,error);
                        Log.d("==888","error:"+error.getMessage());
                        onVolleyResult = (OnVolleyResult) context;
                        onVolleyResult.onResultSuccess("", from);
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() {
                if(params==null) return new HashMap<>();
                else
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppSingleton.getInstance(context).addToRequestQueue(stringRequest,null);
    }

    /**
     * Handling Server Errors
     */
    private void onErrorMessage(Context context, VolleyError error) {
        if (error instanceof TimeoutError) {
            // Indicates that the request has time out
            if(isDialog){
            DialogGlobal.getInstance().closeLoadingDialog();
            }
        } else if (error instanceof NoConnectionError) {
            // There is no connection
        } else if (error instanceof AuthFailureError) {
            // There was an Authentication Failure while performing the request
        } else if (error instanceof ServerError) {
            // The server responded with a error response
            if(isDialog){
            DialogGlobal.getInstance().closeLoadingDialog();
            }
        } else if (error instanceof NetworkError) {
            // Indicates that there was network error while performing the request
            if(isDialog){
            DialogGlobal.getInstance().closeLoadingDialog();
            }
        } else if (error instanceof ParseError) {
            // Indicates that the server response could not be parsed
            if(isDialog){
            DialogGlobal.getInstance().closeLoadingDialog();
            }
        }
    }

}
