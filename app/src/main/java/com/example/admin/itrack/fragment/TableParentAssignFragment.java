package com.example.admin.itrack.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.admin.itrack.R;
import com.example.admin.itrack.adapter.TableParentAssignAdapter;
import com.example.admin.itrack.models.TableParentAssign;
import com.example.admin.itrack.models.Users;
import com.example.admin.itrack.utils.ApiUrl;
import com.example.admin.itrack.utils.KeyConfig;
import com.example.admin.itrack.utils.VolleySingleton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TableParentAssignFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TableParentAssignFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TableParentAssignFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static List<TableParentAssign> tableParentAssignList;
    private static RecyclerView recyclerView;
    private static TableParentAssignAdapter tableParentAssignAdapter;
    private OnFragmentInteractionListener mListener;
    private ProgressDialog pDialog;
    public static Users usersModel;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button btn_track;
    public TableParentAssignFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TableParentAssignFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TableParentAssignFragment newInstance(String param1, String param2) {
        TableParentAssignFragment fragment = new TableParentAssignFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_table_parent_assign, container, false);
        usersModel = Users.getInstance();
        recyclerView =  rootView.findViewById(R.id.table_parent_assign_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        tableParentAssignList = new ArrayList<>();
        showtableParentAssignListItem(String.valueOf(usersModel.getUserId()));
        tableParentAssignAdapter = new TableParentAssignAdapter(getActivity(), tableParentAssignList);
        recyclerView.setAdapter(tableParentAssignAdapter);
        return rootView;

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }



    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void showtableParentAssignListItem(final String parent_id) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiUrl.TABLE_PARENT_ASSIGN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("Recycler View Contents", response.toString());
                        List<TableParentAssign> items = new Gson().fromJson(response.toString(), new TypeToken<List<TableParentAssign>>() {

                        }.getType());
                        Log.d("Passed to RecyclerView", items.toString());
                        tableParentAssignList.clear();
                        tableParentAssignList.addAll(items);
                        tableParentAssignAdapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Error Battery Status " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put(KeyConfig.parent_id, parent_id);
                return params;
            }
        };
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }


    public void getFragment(){
        Fragment locationFragment = new LocationFragment();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.table_parent_assign_container, locationFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }
}
