package com.example.admin.itrack.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
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
import com.example.admin.itrack.R;
import com.example.admin.itrack.fragment.LocationFragment;
import com.example.admin.itrack.models.MinorLocation;
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

public class TableParentAssignAdapter extends RecyclerView.Adapter<TableParentAssignAdapter.MyViewHolder>{
    private Context context;
    private List<TableParentAssign> tableParentAssignList;
    private Intent sendIntent,home;
    private ProgressDialog pDialog;
//    public static DeploymentModel deploymentModel;
    public static Users users;
    public static MinorLocation minorLocation;
    public TableParentAssignAdapter(Context context, List<TableParentAssign> tableParentAssignList) {
        this.context = context;
        this.tableParentAssignList = tableParentAssignList;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView txtMinorName, txtRelationshipToMinor;
        public ImageView thumbNail;
        public RelativeLayout viewBackground, viewForeground;
        public Button track_location;
        public MyViewHolder(View view){
            super(view);
            txtMinorName =  view.findViewById(R.id.binlist_bin_name);
            thumbNail      =  view.findViewById(R.id.thumbnail);
            txtRelationshipToMinor = view.findViewById(R.id.relationship_to_minor);
//            viewBackground =  view.findViewById(R.id.view_background);
            viewForeground =  view.findViewById(R.id.view_foreground);
            track_location     =  view.findViewById(R.id.track_location);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.table_parent_assign, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(itemView);
        users = Users.getInstance();
        minorLocation = MinorLocation.getInstance();
        initializeProgressDialogState();
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final TableParentAssignAdapter.MyViewHolder holder, final int position) {
        final TableParentAssign tableParentAssign = tableParentAssignList.get(position);
        holder.txtMinorName.setText(tableParentAssign.getMinorFullName());
        //holder.textViewBinId.setVisibility(View.GONE);
        //holder.textViewBinId.setText(tableParentAssign.getParent_id());
        holder.track_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                //fetchUserLocation(String.valueOf(users.getUserId()), tableParentAssign.getMinor_id());
                showpDialog();
                CustomJSONRequest stopDeploymentRequest  = new CustomJSONRequest(Request.Method.POST, ApiUrl.TABLE_MINOR_LOCATION, null,
                        new Response.Listener<JSONObject>(){
                            @Override
                            public void onResponse(JSONObject response) {
                                try {

                                    if(response.getString(KeyConfig.read_status).equals("1")){
                                        minorLocation.setLatitude(response.getString(KeyConfig.latitude));
                                        minorLocation.setLongitude(response.getString(KeyConfig.longitude));
                                        hidepDialog();
                                        ((FragmentActivity) view.getContext()).getSupportFragmentManager().beginTransaction()
                                                .replace(R.id.table_parent_assign_container, new LocationFragment())
                                                .commit();
                                    }else if(response.getString(KeyConfig.read_status).equals("0")){
                                        Toast.makeText(context, "Minor has turned off his/her location.", Toast.LENGTH_LONG).show();
                                        hidepDialog();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        hidepDialog();
                    }
                }){

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put(KeyConfig.parent_id, String.valueOf(users.getUserId()));
                        params.put(KeyConfig.minor_id, tableParentAssign.getMinor_id());
                        return params;
                    }
                };
                VolleySingleton.getInstance(context).addToRequestQueue(stopDeploymentRequest);
            }
        });
        holder.txtRelationshipToMinor.setText(tableParentAssign.getRelationship_to_minor());


    }

    @Override
    public int getItemCount() {
        return tableParentAssignList.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
    public void removeItem(int position){
        tableParentAssignList.remove(position);
        notifyItemRemoved(position);
    }
//    private void fetchUserLocation(final String parentId, final String minorId){
//
//    }

    private void initializeProgressDialogState() {
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Finding out minor location.....");
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


//    public void restoreItem(BinModel binModel, int position) {
//        binList.add(position, binModel);
//        // notify item added by position
//        notifyItemInserted(position);
//    }
//    public void updateList(List<BinModel> list){
//        binList = list;
//        notifyDataSetChanged();
//    }//This is used to update the list after triggering edit text

//}

}
