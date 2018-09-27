package com.example.admin.itrack.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.admin.itrack.NavigationDrawerActivity;
import com.example.admin.itrack.R;
import com.example.admin.itrack.models.EmergencyType;
import com.example.admin.itrack.utils.ApiUrl;
import com.example.admin.itrack.utils.CustomJSONRequest;
import com.example.admin.itrack.utils.KeyConfig;
import com.example.admin.itrack.utils.VolleySingleton;
import com.google.android.gms.common.api.Api;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class NotificationDetails extends NavigationDrawerActivity{
    private TextView tvFrom, tvDate, tvMessage, tvEmergencyTypeName;
    private ImageView imgMinorPic, imgEmergencyPic;
    private static String emergencyId;
    private TextView tvDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        setContentView(R.layout.activity_emergency);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_notification_details, null, false);
        drawer.addView(contentView, 0);
        tvFrom   = findViewById(R.id.tvEmergencyFrom);
        tvDate = findViewById(R.id.tvEmergencyDate);
        tvDetails = findViewById(R.id.textView5);
        tvMessage = findViewById(R.id.tvEmergencyMessage);
        imgMinorPic = findViewById(R.id.imgMinorPic);
        imgEmergencyPic = findViewById(R.id.imgEmergencyPic);
        tvEmergencyTypeName = findViewById(R.id.tvEmergencyTypeName);
        retrieveCurrentEmergency("latest");
    }

    @Override
    protected void onPause() {
        super.onPause();
        updateEmergencyStatus(emergencyId, "updateEmergency");
    }


    private void updateEmergencyStatus(final String eId, final String emergencyType){

        CustomJSONRequest updateEmergency  = new CustomJSONRequest(Request.Method.POST, ApiUrl.EMERGENCY_TYPE_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put(KeyConfig.EID, eId);
                map.put(KeyConfig.emergencyType, emergencyType);
                return map;
            }
        };
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(updateEmergency);
    }


    private void retrieveCurrentEmergency(final String emergencyType){
        CustomJSONRequest currentEmergency  = new CustomJSONRequest(Request.Method.POST, ApiUrl.EMERGENCY_TYPE_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    if(response.getString(KeyConfig.EMERGENCY_SUCCESS).equals("0")){
                            tvEmergencyTypeName.setVisibility(View.INVISIBLE);
                            tvFrom.setVisibility(View.INVISIBLE);
                            tvMessage.setVisibility(View.INVISIBLE);
                            tvEmergencyTypeName.setVisibility(View.INVISIBLE);
                            tvDate.setVisibility(View.INVISIBLE);
                            tvDetails.setVisibility(View.INVISIBLE);
                    }else if(response.getString(KeyConfig.EMERGENCY_SUCCESS).equals("1")){
                        String emergency_type = response.getString(KeyConfig.EMERGENCY_TYPE_NAME);
                        String fullName = response.getString(KeyConfig.USER_FULLNAME);
                        tvEmergencyTypeName.setVisibility(View.VISIBLE);
                        tvDetails.setVisibility(View.VISIBLE);
                        tvFrom.setVisibility(View.VISIBLE);
                        tvMessage.setVisibility(View.VISIBLE);
                        tvEmergencyTypeName.setVisibility(View.VISIBLE);
                        tvDate.setVisibility(View.VISIBLE);

                        emergencyId = response.getString(KeyConfig.EMERGENCY_ID);
                        tvFrom.setText("From: "+response.getString(KeyConfig.USER_FULLNAME));
                        tvDate.setText("Date: "+response.getString(KeyConfig.EMERGENCY_DATE));
                        tvEmergencyTypeName.setText("Emergency Type: "+emergency_type);
                        tvMessage.setText(fullName+" is in "+emergency_type);
                        Glide.with(getApplicationContext())
                                .load(response.getString(KeyConfig.EMERGENCY_PIC))
                                .apply(RequestOptions.circleCropTransform())
                                .transition(withCrossFade())
                                .into(imgEmergencyPic);

                        Glide.with(getApplicationContext())
                                .load(response.getString(KeyConfig.MINOR_PIC))
                                .apply(RequestOptions.circleCropTransform())
                                .transition(withCrossFade())
                                .into(imgMinorPic);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(NotificationDetails.this, "No latest emergency found.", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put(KeyConfig.emergencyType, emergencyType);

                return params;
            }
        };
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(currentEmergency);
    }


}
