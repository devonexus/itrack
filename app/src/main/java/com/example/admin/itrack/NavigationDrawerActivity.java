package com.example.admin.itrack;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
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
import com.example.admin.itrack.activity.HomeActivity;
import com.example.admin.itrack.fragment.HomeFragment;
import com.example.admin.itrack.fragment.LocationFragment;
import com.example.admin.itrack.fragment.Notifications;
import com.example.admin.itrack.fragment.TableParentAssignFragment;
import com.example.admin.itrack.models.Users;
import com.example.admin.itrack.utils.ApiUrl;
import com.example.admin.itrack.utils.CustomJSONRequest;
import com.example.admin.itrack.utils.KeyConfig;
import com.example.admin.itrack.utils.VolleySingleton;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.context.IconicsContextWrapper;
import com.mikepenz.iconics.context.IconicsLayoutInflater2;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class NavigationDrawerActivity extends AppCompatActivity{

    private NavigationView navigationView;
    public DrawerLayout drawer;
    private View navHeader;
    private ImageView imgNavHeaderBg, imgProfile;
    private TextView txtName, txtWebsite;
    private Toolbar toolbar;
    private static final String location = "fonts/fontawesome-webfont.ttf";

    public static int navItemIndex = 0;

    // tags used to attach the fragments
    private static final String TAG_HOME = "home";
    private static final String TAG_ANNOUNCEMENT = "announcement";
    private static final String TAG_NOTIFICATIONS = "notifications";
    private static final String TAG_LOCATION = "location";
    private static final String TAG_EMERGENCY = "emergency";
    private static final String TAG_LOGOUT = "logout";
    public static  Users userInstance;
    public static String CURRENT_TAG = TAG_HOME;
    private ProgressDialog pDialog;

    // toolbar titles respected to selected nav menu item
    private String[] activityTitles;
    private  Menu menu;
    // flag to load home fragment when user presses back key
    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;
    //Enumerate drawer item
    private FloatingActionButton fab;
    private MenuItem navHome, navLocation, navNotification, navAnnouncement, navLogout;
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(IconicsContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        LayoutInflaterCompat.setFactory2(getLayoutInflater(), new IconicsLayoutInflater2(getDelegate()));
        super.onCreate(savedInstanceState);
        userInstance = Users.getInstance();
        setContentView(R.layout.activity_navigation_drawer);
        toolbar =  findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        mHandler = new Handler();

        drawer =  findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        //navigationView.setItemIconTintList(null);
        navHeader = navigationView.getHeaderView(0);
        txtName =  navHeader.findViewById(R.id.name);
        txtWebsite =  navHeader.findViewById(R.id.website);
        imgNavHeaderBg =  navHeader.findViewById(R.id.img_header_bg);
        imgProfile =  navHeader.findViewById(R.id.img_profile);
        fab             =  findViewById(R.id.fab);
        fab.setVisibility(View.GONE);
        // load toolbar titles from string resources
        activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles);

        // load nav menu header data
        loadNavHeader();

        // initializing navigation menu
        setUpNavigationView();

        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;
           // loadHomeFragment();
        }

        setDrawerItemLook();
    }
    private void setDrawerItemLook(){


        menu = navigationView.getMenu();
        if(userInstance.getUsertype().equals("Parent")){

            navHome = menu.findItem(R.id.nav_home);
            navHome.setIcon(new IconicsDrawable(this)
                    .icon(FontAwesome.Icon.faw_home)
                    .color(Color.RED)
                    .sizeDp(24));

            navLocation = menu.findItem(R.id.nav_location);
            navLocation.setIcon(new IconicsDrawable(this)
                    .icon(FontAwesome.Icon.faw_globe)
                    .color(Color.RED)
                    .sizeDp(24));

            navAnnouncement = menu.findItem(R.id.nav_announcement);
            navAnnouncement.setIcon(new IconicsDrawable(this)
                    .icon(FontAwesome.Icon.faw_bullhorn)
                    .color(Color.RED)
                    .sizeDp(24));

            navNotification = menu.findItem(R.id.nav_notifications);
            navNotification.setIcon(new IconicsDrawable(this)
                    .icon(FontAwesome.Icon.faw_bell)
                    .color(Color.RED)
                    .sizeDp(24));

            navNotification.setVisible(false);

            navLogout = menu.findItem(R.id.nav_logout);
            navLogout.setIcon(new IconicsDrawable(this)
                    .icon(FontAwesome.Icon.faw_sign_out_alt)
                    .color(Color.RED)
                    .sizeDp(24));
        }else{

            navHome = menu.findItem(R.id.nav_home);
            navHome.setIcon(new IconicsDrawable(this)
                    .icon(FontAwesome.Icon.faw_home)
                    .color(Color.RED)
                    .sizeDp(24));

            navLocation = menu.findItem(R.id.nav_location);
//            navLocation.setIcon(new IconicsDrawable(this)
//                    .icon(FontAwesome.Icon.faw_globe)
//                    .color(Color.RED)
//                    .sizeDp(24));
            navLocation.setVisible(false);
            navAnnouncement = menu.findItem(R.id.nav_announcement);
            navAnnouncement.setIcon(new IconicsDrawable(this)
                    .icon(FontAwesome.Icon.faw_bullhorn)
                    .color(Color.RED)
                    .sizeDp(24));

            navNotification = menu.findItem(R.id.nav_notifications);
            navNotification.setIcon(new IconicsDrawable(this)
                    .icon(FontAwesome.Icon.faw_bell)
                    .color(Color.RED)
                    .sizeDp(24));
            navNotification.setVisible(false);

            navLogout = menu.findItem(R.id.nav_logout);
            navLogout.setIcon(new IconicsDrawable(this)
                    .icon(FontAwesome.Icon.faw_sign_out_alt)
                    .color(Color.RED)
                    .sizeDp(24));
        }



        initializeProgressDialogState();
    }


    private void loadNavHeader() {
        // name, website
        txtName.setText(userInstance.getFullname());
        txtWebsite.setText(userInstance.getUsertype());
        //imgNavHeaderBg.setImageResource(R.drawable.itrack_logo);
        imgNavHeaderBg.setImageResource(R.drawable.nav_header);
//        // loading header background image
//        Glide.with(this).load(urlNavHeaderBg)
//                .crossFade()
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .into(imgNavHeaderBg);
//
        // Loading profile image
        Glide.with(this)
                .load(userInstance.getImage())
                .apply(RequestOptions.circleCropTransform())
                .transition(withCrossFade())
                .into(imgProfile);

        // showing dot next to notifications label
        //navigationView.getMenu().getItem(3).setActionView(R.layout.menu_dot);
    }

    private void setUpNavigationView() {
        //Setting Navigation View Item S;elected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.nav_home:
                        navItemIndex = 0;
//                        CURRENT_TAG = TAG_HOME;
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));

                        break;

                    case R.id.nav_location:
                        navItemIndex = 1;
                        CURRENT_TAG = TAG_LOCATION;
                        break;
                    case R.id.nav_notifications:
                        navItemIndex = 2;
                        CURRENT_TAG = TAG_NOTIFICATIONS;

                        break;
                    case R.id.nav_announcement:
                        // launch new intent instead of loading fragment
                        navItemIndex = 3;
                        startActivity(new Intent(NavigationDrawerActivity.this, AnnouncementActivity.class));
                        drawer.closeDrawers();
                        return true;

                    case R.id.nav_logout:
                        CURRENT_TAG = TAG_LOGOUT;
                        signOut();
                        return true;
//                    case R.id.nav_about_us:
//                        // launch new intent instead of loading fragment
//                        startActivity(new Intent(MainActivity.this, AboutUsActivity.class));
//                        drawer.closeDrawers();
//                        return true;
//                    case R.id.nav_privacy_policy:
//                        // launch new intent instead of loading fragment
//                        startActivity(new Intent(MainActivity.this, PrivacyPolicyActivity.class));
//                        drawer.closeDrawers();
//                        return true;
                    default:
                        navItemIndex = 0;

                }

                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);
                loadHomeFragment();
//

                return true;
            }
        });


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawer.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }
    private void setToolbarTitle() {
        getSupportActionBar().setTitle(activityTitles[navItemIndex]);
    }

    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }
    private void loadHomeFragment() {
        // selecting appropriate nav menu item
        selectNavMenu();

        // set toolbar title
        setToolbarTitle();

        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();

            // show or hide the fab button
           // toggleFab();
            return;
        }

        // Sometimes, when fragment has huge data, screen seems hanging
        // when switching between navigation menus
        // So using runnable, the fragment is loaded with cross fade effect
        // This effect can be seen in GMail app
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }

        // show or hide the fab button
        //toggleFab();

        //Closing drawer on item click
        drawer.closeDrawers();

        // refresh toolbar menu
        invalidateOptionsMenu();
    }

    private Fragment getHomeFragment() {
        switch (navItemIndex) {
            case 0:
                // home
                HomeFragment homeFragment = new HomeFragment();
                return homeFragment;
            case 1:
                // location
                TableParentAssignFragment locationFragment = new TableParentAssignFragment();
                return locationFragment;
            case 2:
                // notifications fragment
                Notifications notificationFragment = new Notifications();
                return notificationFragment;
            case 3:
                // notifications fragment
                startActivity(new Intent(getApplicationContext(), AnnouncementActivity.class));


//            case 4:
////                // settings fragment
////                SettingsFragment settingsFragment = new SettingsFragment();
////                return settingsFragment;
            default:
                return new HomeFragment();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        // show menu only when home fragment is selected
        if (navItemIndex == 0) {
            getMenuInflater().inflate(R.menu.main, menu);
        }

        // when fragment is notifications, load the menu created for notifications
        if (navItemIndex == 3) {
            getMenuInflater().inflate(R.menu.notifications, menu);
        }
        return true;
    }


    @Override
    public void onBackPressed() {
        signOut();
        //super.onBackPressed();
    }

    public void showMessage(String title, String Message) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setIcon(new IconicsDrawable(this).icon(FontAwesome.Icon.faw_sign_out_alt).color(Color.BLUE).sizeDp(24));
        builder.setMessage(Message);
        builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                showpDialog();
                Thread thread = new Thread() {

                    @Override
                    public void run() {

                        // Block this thread for 4 seconds.al
                        try {
                            Thread.sleep(1500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        // After sleep finished blocking, create a Runnable to run on the UI Thread.
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(userInstance.getUsertype().equals("Minor")){
                                    updateLocation(userInstance.getUserId());
                                }


                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                intent.putExtra("LOGIN_STATUS", false);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                        Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                        Intent.FLAG_ACTIVITY_NEW_TASK); // To clean up all activities
                                startActivity(intent);
                                finish();
                                hidepDialog();
                            }
                        });
                    }

                };
                thread.start();
            }

        });

        builder.show();
    }
    private void initializeProgressDialogState() {
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Signing out, Please wait...");
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

    private void signOut() {
        showMessage("Logout", "Are you sure you want to logout?");
    }


    private void updateLocation(final int minorId ){
        showpDialog();
        CustomJSONRequest customJSONRequest = new CustomJSONRequest(Request.Method.POST, ApiUrl.TABLE_MINOR_LOCATION, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response.getString(KeyConfig.RESULT).equals("1")){
                        hidepDialog();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    hidepDialog();

                } catch (NullPointerException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put(KeyConfig.minor_id, String.valueOf(minorId));

                return params;
            }
        };
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(customJSONRequest);
    }

}
