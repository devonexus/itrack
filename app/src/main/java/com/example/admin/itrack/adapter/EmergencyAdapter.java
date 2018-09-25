package com.example.admin.itrack.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.admin.itrack.R;
import com.example.admin.itrack.models.AppToken;
import com.example.admin.itrack.models.EmergencyType;
import com.example.admin.itrack.models.TableParentAssign;
import com.example.admin.itrack.models.Users;
import com.example.admin.itrack.utils.ApiUrl;
import com.example.admin.itrack.utils.CustomJSONRequest;
import com.example.admin.itrack.utils.KeyConfig;
import com.example.admin.itrack.utils.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class EmergencyAdapter extends RecyclerView.Adapter<EmergencyAdapter.MyViewHolder>{
    private Context context;
    private List<EmergencyType> emergencyTypeList;
    private ProgressDialog pDialog;
    public static Users users;
    public static EmergencyType emergencyType;
    public static TableParentAssign tableParentAssign;
    public static AppToken appToken;
    public EmergencyAdapter(Context context, List<EmergencyType> emergencyTypeList) {
        this.context = context;
        this.emergencyTypeList = emergencyTypeList;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView txtEmergencyName;
        public ImageView imgEmergencyTypeImage;
        public RelativeLayout viewForeground;
        public Button btnToggleEmergency;
        public MyViewHolder(View view){
            super(view);
            txtEmergencyName =  view.findViewById(R.id.tvEmergencyTypeName);
            imgEmergencyTypeImage      =  view.findViewById(R.id.imgEmergencyTypeImage);
            viewForeground =  view.findViewById(R.id.view_foreground);
            btnToggleEmergency     =  view.findViewById(R.id.btnToggleEmergency);
        }
    }




    @Override
    public MyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.emergency_row, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(itemView);
        users = Users.getInstance();
        emergencyType = EmergencyType.getInstance();
        tableParentAssign = TableParentAssign.getInstance();
        appToken = AppToken.getInstance();
        initializeProgressDialogState();
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final EmergencyAdapter.MyViewHolder holder, final int position) {
        final EmergencyType emergencyType = emergencyTypeList.get(position);
        holder.txtEmergencyName.setText(emergencyType.getEmergencyTypeName());

//        holder.txtMinorName.setText(tableParentAssign.getMinorFullName());



        Glide.with(context)
                .load(emergencyType.getEmergencyPic())
                .apply(RequestOptions.centerCropTransform())
                .transition(withCrossFade())
                .into(holder.imgEmergencyTypeImage);

        //holder.textViewBinId.setVisibility(View.GONE);
        //holder.textViewBinId.setText(tableParentAssign.getParent_id());
        holder.btnToggleEmergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
             
                showpDialog();
                CustomJSONRequest sendEmergency  = new CustomJSONRequest(Request.Method.POST, ApiUrl.EMERGENCY_TYPE_URL, null,
                        new Response.Listener<JSONObject>(){
                            @Override
                            public void onResponse(JSONObject response) {


                                try {
                                    if(response.getString(KeyConfig.RESULT).equals("1")){
                                        hidepDialog();
//                                        Toast.makeText(context, "Emergency trigger was sent.", Toast.LENGTH_LONG).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    hidepDialog();
                                }

                            }
                        }, new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        hidepDialog();
                        Toast.makeText(context, "Error sending emergency to server please try again..", Toast.LENGTH_SHORT).show();
                    }
                }){

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put(KeyConfig.userId, String.valueOf(users.getUserId()));
                        params.put(KeyConfig.emergencyType, String.valueOf(emergencyType.getEmergencyTypeId()));
                        return params;
                    }
                };
                VolleySingleton.getInstance(context).addToRequestQueue(sendEmergency);


                sendEmergency(context, emergencyType.getEmergencyTypeName(), emergencyType.getEmergencyTypeName(), users.getAppToken(), emergencyType.getEmergencyPic());

            }
        });
 


    }





    private void sendEmergency(final Context context, final String title, final String message, final String topic, final String imageUrl){
        CustomJSONRequest sendEmergencyRequest = new CustomJSONRequest(Request.Method.POST, ApiUrl.TOPIC_URL, null,
                new Response.Listener<JSONObject>() {


                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if(response.getString(KeyConfig.EMERGENCY_SUCCESS).equals("1")){
                                Toast.makeText(context, "Emergency trigger was sent.", Toast.LENGTH_SHORT).show();
                            }else  if(response.getString(KeyConfig.EMERGENCY_SUCCESS).equals("0")){
                                Toast.makeText(context, "Emergency alert not sent, Tap again!!!!", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(context, "Error gettign response from server...", Toast.LENGTH_SHORT).show();
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put(KeyConfig.EMERGENCY_TITLE, title);
                params.put(KeyConfig.EMERGENCY_MESSAGE, message);
                params.put(KeyConfig.EMERGENCY_TOPIC, topic);
                params.put(KeyConfig.IMAGE_URL, imageUrl);
                return params;
            }
        };
        VolleySingleton.getInstance(context).addToRequestQueue(sendEmergencyRequest);
    }


    @Override
    public int getItemCount() {
        return emergencyTypeList.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
    public void removeItem(int position){
        emergencyTypeList.remove(position);
        notifyItemRemoved(position);
    }
//    private void fetchUserLocation(final String parentId, final String minorId){
//
//    }

    private void initializeProgressDialogState() {
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Sending emergency message..............");
        pDialog.setCancelable(false);
    }
    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }//Display progress dialog

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }//Dismiss progressDialog




}
