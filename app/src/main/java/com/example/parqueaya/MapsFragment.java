package com.example.parqueaya;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.*;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.RequestManager;
import com.example.parqueaya.models.Cochera;
import com.example.parqueaya.services.DataService;
import com.example.parqueaya.utils.PermissionUtils;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MapsFragment extends Fragment implements OnMapReadyCallback {

    private TextView textView;
    private Button mostrar;
    private BottomSheetBehavior bottomSheetBehavior;
    private FloatingActionButton fabReserve;
    private Context context;

    int location = -1;

    LocationManager locationManager;
    private SupportMapFragment mapFragment;
    private MapView mMapView;
    private GoogleMap mMap;
    private MarkerOptions userMarker;

    private boolean mPermissionDenied = false;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    public MapsFragment() {
        // Required empty public constructor
    }

    public static MapsFragment newInstance() {
        MapsFragment fragment = new MapsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        DataService.getInstance().getCocheras();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
//        mostrar = view.findViewById(R.id.mostrar);
        textView = view.findViewById(R.id.probando);
        fabReserve = view.findViewById(R.id.fab_reserve);
        fabReserve.setEnabled(false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
            .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        initComponent(view);
//        showBottomSheet();
        showDetalleCochera(view);
        return view;
    }

//    public void gettingLocationBasedOnApiVersion(GoogleMap mMap){
//        if (location != -1 && location != 0) {
//
//            locationManager.removeUpdates(this);
//
//            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(MainActivity.locations.get(location), 10));
//            mMap.addMarker(new MarkerOptions().position(MainActivity.locations.get(location)).title(MainActivity.places.get(location)));
//            mMap.moveCamera(CameraUpdateFactory.newLatLng(MainActivity.locations.get(location)));
//
//            mMap.setOnMapLongClickListener(this);
//        } else {
//            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                // TODO: Consider calling
//                //    ActivityCompat#requestPermissions
//                // here to request the missing permissions, and then overriding
//                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                //                                          int[] grantResults)
//                // to handle the case where the user grants the permission. See the documentation
//                // for ActivityCompat#requestPermissions for more details.
//                return;
//            }
//            locationManager.requestLocationUpdates(provider, 400, 1, this);
//        }
//
//        mMap.setOnMapLongClickListener(this);
//    }

    private void initComponent(View view) {
        CoordinatorLayout llBottomSheet = view.findViewById(R.id.bottom_sheet);

        // init the bottom sheet behavior
        bottomSheetBehavior = BottomSheetBehavior.from(llBottomSheet);

        // change the state of the bottom sheet
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        // set callback for changes
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        view.findViewById(R.id.fab_directions).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                try {
                    //                    mMap.animateCamera(zoomingLocation());
                } catch (Exception e) {
                }
            }
        });
    }

    private void showBottomSheet() {
//        mostrar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
//                fabReserve.setEnabled(true);
//                textView.setText("Hola perro");
//            }
//        });
    }

    private void showDetalleCochera(View view) {
        view.findViewById(R.id.fab_reserve).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

                ReservaFragment reservaFragment = new ReservaFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, reservaFragment)
                        .addToBackStack(null)
                        .commit();

                try {
                    //                    mMap.animateCamera(zoomingLocation());
                } catch (Exception e) {
                }
            }
        });
    }

    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            PermissionUtils.requestPermission((AppCompatActivity) getActivity(), LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (mMap != null) {
            mMap.setMyLocationEnabled(true);
        }
    }

//    @Override
//    public boolean onMyLocationButtonClick() {
//        Toast.makeText(context,"My location button clicked", Toast.LENGTH_LONG).show();
//        return false;
//    }
//
//    @Override
//    public void onMyLocationClick(@NonNull Location location) {
//        Toast.makeText(context,"Current location:\n" + location, Toast.LENGTH_LONG).show();
//    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults, Manifest.permission.ACCESS_FINE_LOCATION)) {
            enableMyLocation();
        } else {
            mPermissionDenied = true;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mPermissionDenied) {
            showMissingPermissionError();
            mPermissionDenied = false;
        }
    }

    private void showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog.newInstance(true).show(getFragmentManager(), "dialog");
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    public void setUserMarker(LatLng latLng) {
        if (userMarker == null) {
            userMarker = new MarkerOptions().position(latLng).title("Ubicacion actual");
            mMap.addMarker(userMarker);
            Log.v("Wiwi", "Latitud actual: " + latLng.latitude + " Long: " + latLng.longitude);
        }

        try {
            Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(latLng.latitude,latLng.longitude, 1);
            updateMap(23000);
        } catch (IOException exception){

        }

        updateMap(23000);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));
    }

    private void updateMap(int zipcode) {

        List<Cochera> cocheras = DataService.getInstance().getCocheras();

        for (int x = 0; x < cocheras.size(); x++) {
            Cochera coch = cocheras.get(x);
            Double lat = Double.valueOf(coch.getLatitud());
            Double lng = Double.valueOf(coch.getLongitud());

            Log.d("Latitud", String.valueOf(lat));
            Log.d("Longitud", String.valueOf(lng));

            MarkerOptions marker = new MarkerOptions().position(new LatLng(lat, lng));
            marker.title(coch.getNombre());
            marker.snippet(coch.getDescripcion());
            marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.pin));
            mMap.addMarker(marker);
            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker mark) {
                    if (mark.equals(mark)) {
                        Marker marker1;
                    }
                    return false;
                }
            });
        }
    }
}
