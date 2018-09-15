package com.example.admin.itrack.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.admin.itrack.LoginActivity;
import com.example.admin.itrack.NavigationDrawerActivity;
import com.example.admin.itrack.R;
import com.example.admin.itrack.models.Announcement;
import com.example.admin.itrack.utils.ApiUrl;
import com.example.admin.itrack.utils.CustomJSONRequest;
import com.example.admin.itrack.utils.KeyConfig;
import com.example.admin.itrack.utils.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class AnnouncementDetails extends NavigationDrawerActivity {
    private ImageView imgSenderProfilePic;
    private TextView tvAnnouncemntTitle, tvAnnouncementSender, tvAnnouncementDetails, txtAnnouncementDate;
    public static Announcement announcement;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_announcement_details, null, false);
        drawer.addView(contentView, 0);
        imgSenderProfilePic = findViewById(R.id.imgSenderProfilePic);
        tvAnnouncemntTitle = findViewById(R.id.txtAnnouncementTitle);
        tvAnnouncementSender = findViewById(R.id.txtSender);
        txtAnnouncementDate = findViewById(R.id.txtAnnouncementDate);
        tvAnnouncementDetails = findViewById(R.id.txtAnnouncementDetails);
        announcement = Announcement.getInstance();

        retrieveAnnouncementDetails(announcement.getAnnouncementId());
    }

    private void retrieveAnnouncementDetails(final int announcementId){

        CustomJSONRequest customJSONRequest = new CustomJSONRequest(Request.Method.POST, ApiUrl.ANNOUNCEMENT_URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(final JSONObject response) {
                        // Loading profile image
                        try {

                            Glide.with(getApplicationContext())
                                    .load(response.getString(KeyConfig.getAnnouncementSenderProfilePic))
                                    .apply(RequestOptions.circleCropTransform())
                                    .transition(withCrossFade())
                                    .into(imgSenderProfilePic);
                            txtAnnouncementDate.setText("Date: "+response.getString(KeyConfig.announcementDate));
                            tvAnnouncementDetails.setText(response.getString(KeyConfig.announcementDescription));
                            tvAnnouncemntTitle.setText("Subject: "+response.getString(KeyConfig.announcementTitle));
                            tvAnnouncementSender.setText("From: "+response.getString(KeyConfig.announcementSender));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(AnnouncementDetails.this, "Error goes ", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(KeyConfig.announcementId, String.valueOf(announcementId));

                return params;
            }
        };
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(customJSONRequest);
    }
}
