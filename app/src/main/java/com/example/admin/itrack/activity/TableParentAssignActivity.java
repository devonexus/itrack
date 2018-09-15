package com.example.admin.itrack.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;

import com.example.admin.itrack.NavigationDrawerActivity;
import com.example.admin.itrack.R;
import com.example.admin.itrack.fragment.TableParentAssignFragment;

public class TableParentAssignActivity extends NavigationDrawerActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_location);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_location, null, false);
        drawer.addView(contentView, 0);
        Bundle extras = getIntent().getExtras();
        if(extras!=null && extras.containsKey("openF2")) {
            boolean open = extras.getBoolean("openF2");
            if(open){
                Fragment tableParentAssignFragment = new TableParentAssignFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.activity_location_container, tableParentAssignFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        }



    }
}
