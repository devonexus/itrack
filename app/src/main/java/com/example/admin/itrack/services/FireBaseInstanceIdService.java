package com.example.admin.itrack.services;

import android.util.Log;

import com.example.admin.itrack.utils.KeyConfig;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class FireBaseInstanceIdService extends FirebaseInstanceIdService {




    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d("Refreshed Token", token);
//        registerToken(token);
        getSharedPreferences(KeyConfig.SHARED_PREF, MODE_PRIVATE).edit().putString("fb", token).apply();
    }

    private void getAppToken(){
        getSharedPreferences(KeyConfig.SHARED_PREF, MODE_PRIVATE).getString("fb", "");
    }


//    private void registerToken(String token) {
//
//        OkHttpClient client = new OkHttpClient();
//        RequestBody body = new FormBody.Builder()
//                .add("Token",token)
//                .build();
//
//        Request request = new Request.Builder()
//                .url("http://192.168.42.25:8080/android/app_token.php")
//                .post(body)
//                .build();
//
//        try {
//            client.newCall(request).execute();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
