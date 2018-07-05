package com.example.admin.itrack;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.admin.itrack.models.Users;
import com.example.admin.itrack.utils.CustomJSONRequest;
import com.example.admin.itrack.utils.VolleySingleton;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.iconics.IconicsDrawable;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private MaterialButton mbLogin;
    private static final String username = "username";
    private static final String password = "password";
    private static final String message = "message";
    private static final String success = "success";
    private static final String fullname = "fullname";
    private static final String image = "image";
    private static final String userId = "userId";
    private static final String usertype = "usertype";
    private ProgressDialog pDialog;
    private static final String WEB_API_LOGIN_URL = "http://192.168.1.136:8080/android/login.php";
//    private static final String WEB_API_LOGIN_URL = "http://192.168.1.119:8080/android/login.php";

    //private static final String WEB_API_LOGIN_URL = "http://cel.x10host.com/android/login.php";

    private String enteredUsername, enteredPassword;
    private TextInputEditText etUsername, etPassword;
    public static Users userInstance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mbLogin = findViewById(R.id.btn_login);
        etUsername = findViewById(R.id.username);
        etPassword = findViewById(R.id.password);
        userInstance = Users.getInstance();
        initializeProgressDialog();
        mbLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(etUsername.getText().toString()) && TextUtils.isEmpty(etPassword.getText().toString())) {
                    etUsername.setError("Username is required.");
                    etPassword.setError("Password is required.");
                    requestFocus(etUsername);
                } else if(TextUtils.isEmpty(etUsername.getText().toString())){
                    etUsername.setError("Username is required.");
                    requestFocus(etUsername);
                } else if(TextUtils.isEmpty(etPassword.getText().toString())){
                    etPassword.setError("Password is required.");
                    requestFocus(etPassword);
                }else{
                    enteredUsername = etUsername.getText().toString().trim();
                    enteredPassword = etPassword.getText().toString().trim();
                    loginUser(enteredUsername, enteredPassword);
                }
            }
        });
    }

    private void loginUser(final String user_name, final String user_pword){
        showpDialog();
        CustomJSONRequest customJSONRequest = new CustomJSONRequest(Request.Method.POST, WEB_API_LOGIN_URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(final JSONObject response) {
                        try {

                            if(response.getString(success).equals("0")) {

                                Toast.makeText(LoginActivity.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                                hidepDialog();
                                requestFocus(etPassword);

                            }
                            else if(response.getString(success).equals("1")){
                                Thread thread = new Thread() {

                                    @Override
                                    public void run() {

                                        // Block this thread for 4 seconds.al
                                        try {
                                            Thread.sleep(1000);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }

                                        // After sleep finished blocking, create a Runnable to run on the UI Thread.
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                try {
                                                    userInstance.setFullname(response.getString(fullname));
                                                    userInstance.setImage(response.getString(image));
                                                    userInstance.setUserId(Integer.valueOf(response.getString(userId)));
                                                    userInstance.setUsertype(response.getString(usertype));
                                                    startActivity(new Intent(getApplicationContext(), NavigationDrawerActivity.class));
                                                    finish();
                                                    hidepDialog();
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        });
                                    }
                                };
                                thread.start();


                            }
                            else if(response.getString(success).equals("2")) {
                                Thread thread = new Thread() {

                                    @Override
                                    public void run() {

                                        // Block this thread for 4 seconds.al
                                        try {
                                            Thread.sleep(1000);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }

                                        // After sleep finished blocking, create a Runnable to run on the UI Thread.
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(LoginActivity.this, "Invalid username and password.", Toast.LENGTH_SHORT).show();
                                                hidepDialog();
                                                requestFocus(etUsername);
                                            }
                                        });
                                    }
                                };
                                thread.start();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, "Could not fetch data from server, Please try again.", Toast.LENGTH_LONG).show();
                hidepDialog();
                error.printStackTrace();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(username, user_name);
                params.put(password, user_pword);
                return params;
            }
        };
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(customJSONRequest);
    }//End login function

    private void initializeProgressDialog(){
        pDialog = new ProgressDialog(this);
        pDialog.setTitle("Processing");
        pDialog.setMessage("Signing in...");
        pDialog.setIcon(new IconicsDrawable(this).icon(FontAwesome.Icon.faw_sign).color(Color.BLUE).sizeDp(24));
        pDialog.setCancelable(false);
        pDialog.setIndeterminate(true);
    }

    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }//Display progress dialog

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }//Dismiss progressDialog
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }//Focus the cursor to where the error originated
}
