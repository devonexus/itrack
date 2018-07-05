package com.example.admin.itrack.utils;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by apaodevo on 11/20/2017.
 */


public class VolleySingleton{
    private static VolleySingleton mInstance; //instance of Singleton class
    private RequestQueue requestQueue;
    private static Context mContext; //variable to ensure variable lasts entire application lifecycle

    private VolleySingleton(Context context){
        mContext = context;
        requestQueue = getRequestQueue();
    }//Constructor is created after class is created

    public RequestQueue getRequestQueue(){
        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }
        return requestQueue;
    }//Check requestQueue if null initialize
    public static synchronized VolleySingleton getInstance(Context context){
        if(mInstance == null){
            mInstance = new VolleySingleton(context);
        }
        return mInstance;
    }
    public<T> void addToRequestQueue(Request<T> request){

        requestQueue.add(request);
    }
   /* public <T> void addToRequestQueue(Request<T> req) {

        getRequestQueue().add(req);
    }*/

    public void cancelPendingRequests(Object tag) {
        if (requestQueue!= null) {
            requestQueue.cancelAll(tag);
        }
    }
}