package com.example.admin.itrack.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.admin.itrack.NavigationDrawerActivity;
import com.example.admin.itrack.R;
import com.example.admin.itrack.adapter.EmergencyAdapter;
import com.example.admin.itrack.models.EmergencyType;
import com.example.admin.itrack.utils.ApiUrl;
import com.example.admin.itrack.utils.KeyConfig;
import com.example.admin.itrack.utils.VolleySingleton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmergencyActivity extends NavigationDrawerActivity {
    private RecyclerView recyclerViewEmergency;
    private EmergencyAdapter emergencyAdapter;
    private static List<EmergencyType> emergencyTypeList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_emergency);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_emergency, null, false);
        drawer.addView(contentView, 0);
        recyclerViewEmergency = findViewById(R.id.recyclerviewEmergency);
        recyclerViewEmergency.setLayoutManager(new LinearLayoutManager(EmergencyActivity.this));
        recyclerViewEmergency.setItemAnimator(new DefaultItemAnimator());
        emergencyTypeList = new ArrayList<>();
        showEmergencyType();
        emergencyAdapter = new EmergencyAdapter(EmergencyActivity.this, emergencyTypeList);
        recyclerViewEmergency.setAdapter(emergencyAdapter);
    }

    private void showEmergencyType() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiUrl.EMERGENCY_TYPE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("Recycler View Contents", response.toString());
                        List<EmergencyType> items = new Gson().fromJson(response.toString(), new TypeToken<List<EmergencyType>>() {

                        }.getType());
                        Log.d("Passed to RecyclerView", items.toString());
                        emergencyTypeList.clear();
                        emergencyTypeList.addAll(items);
                        emergencyAdapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error Battery Status " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put(KeyConfig.emergencyType, "Emergency");
                return params;
            }
        };
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }
}
