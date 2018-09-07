package com.example.admin.itrack.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.Toast;

import com.example.admin.itrack.R;
import com.example.admin.itrack.models.MinorLocation;
import com.example.admin.itrack.models.TableParentAssign;
import com.example.admin.itrack.models.Users;
import com.example.admin.itrack.utils.CropCircleUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LocationFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LocationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LocationFragment extends Fragment implements OnMapReadyCallback{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private SupportMapFragment mMapFragment; // MapView UI element
    public static MinorLocation minorLocation;
    private GoogleMap mGoogleMap; // object that represents googleMap and allows us to use Google Maps API features

    private Marker driverMarker; // Marker to display driver's location
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public static Users users;
    private OnFragmentInteractionListener mListener;

    public LocationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LocationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LocationFragment newInstance(String param1, String param2) {
        LocationFragment fragment = new LocationFragment();
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
        minorLocation = MinorLocation.getInstance();
        users = Users.getInstance();
        View view = inflater.inflate(R.layout.fragment_location, container, false);
        mMapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mMapFragment.getMapAsync(this);
        return view;
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
    /*
         This method is called when the map is completely set up. After the map is setup,
         the passenger will be subscribed to the driver's location channel, so their location
         can be updated on the MapView. We use the reference to the GoogleMap object googleMap
         to utilize any Google Maps API features.
      */
     /*
        This method is called when the map is completely set up. After the map is setup,
        the passenger will be subscribed to the driver's location channel, so their location
        can be updated on the MapView. We use the reference to the GoogleMap object googleMap
        to utilize any Google Maps API features.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        Bitmap bMap = BitmapFactory.decodeResource(getResources(), R.drawable.kid);
        Bitmap resized = CropCircleUtil.resizeImage(bMap, 100, 100);
        LatLng location = new LatLng(Double.valueOf(minorLocation.getLatitude()), Double.valueOf(minorLocation.getLongitude()));
        mGoogleMap.addMarker(new MarkerOptions().position(location).title(users.getMinorFullName()).icon(BitmapDescriptorFactory.fromBitmap(CropCircleUtil.getCroppedBitmap(resized))));
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(location));
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 15));

//        try {
//            mGoogleMap = googleMap;
//            mGoogleMap.setMyLocationEnabled(false);
//        } catch (SecurityException e) {
//            e.printStackTrace();
//        }
//
//        // This code adds the listener and subscribes passenger to channel with driver's location.
//        LoginActivity.pubnub.addListener(new SubscribeCallback() {
//            @Override
//            public void status(PubNub pub, PNStatus status) {
//
//            }
//
//            @Override
//            public void message(PubNub pub, final PNMessageResult message) {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            Map<String, String> newLocation = JsonUtil.fromJson(message.getMessage().toString(), LinkedHashMap.class);
//                            updateUI(newLocation);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });
//            }
//
//            @Override
//            public void presence(PubNub pub, PNPresenceEventResult presence) {
//
//            }
//        });
//        LoginActivity.pubnub.subscribe()
//                .channels(Arrays.asList(Constants.PUBNUB_CHANNEL_NAME)) // subscribe to channels
//                .execute();

    }

    /*
        This method gets the new location of driver and calls method animateCar
        to move the marker slowly along linear path to this location.
        Also moves camera, if marker is outside of map bounds.
     */
    private void updateUI(Map<String, String> newLoc) {
        LatLng newLocation = new LatLng(Double.valueOf(newLoc.get("lat")), Double.valueOf(newLoc.get("lng")));
        if (driverMarker != null) {
            animateCar(newLocation);
            boolean contains = mGoogleMap.getProjection()
                    .getVisibleRegion()
                    .latLngBounds
                    .contains(newLocation);
            if (!contains) {
                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(newLocation));
            }
        } else {
            mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                    newLocation, 15.5f));
            driverMarker = mGoogleMap.addMarker(new MarkerOptions().position(newLocation).title("Test").
                    icon(BitmapDescriptorFactory.fromResource(R.drawable.car)));
        }
    }

    /*
        Animates car by moving it by fractions of the full path and finally moving it to its
        destination in a duration of 5 seconds.
     */
    private void animateCar(final LatLng destination) {
        final LatLng startPosition = driverMarker.getPosition();
        final LatLng endPosition = new LatLng(destination.latitude, destination.longitude);
        final LatLngInterpolator latLngInterpolator = new LatLngInterpolator.LinearFixed();

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.setDuration(5000); // duration 5 seconds
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                try {
                    float v = animation.getAnimatedFraction();
                    LatLng newPosition = latLngInterpolator.interpolate(v, startPosition, endPosition);
                    driverMarker.setPosition(newPosition);
                } catch (Exception ex) {
                }
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }
        });
        valueAnimator.start();
    }

    /*
        This interface defines the interpolate method that allows us to get LatLng coordinates for
        a location a fraction of the way between two points. It also utilizes a Linear method, so
        that paths are linear, as they should be in most streets.
     */
    private interface LatLngInterpolator {
        LatLng interpolate(float fraction, LatLng a, LatLng b);

        class LinearFixed implements LatLngInterpolator {
            @Override
            public LatLng interpolate(float fraction, LatLng a, LatLng b) {
                double lat = (b.latitude - a.latitude) * fraction + a.latitude;
                double lngDelta = b.longitude - a.longitude;
                if (Math.abs(lngDelta) > 180) {
                    lngDelta -= Math.signum(lngDelta) * 360;
                }
                double lng = lngDelta * fraction + a.longitude;
                return new LatLng(lat, lng);
            }
        }
    }
}
