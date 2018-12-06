package com.example.parqueaya;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.*;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.example.parqueaya.api.ParkingApi;
import com.example.parqueaya.api.RetrofitInstance;
import com.example.parqueaya.models.Cochera;
import com.example.parqueaya.services.DataService;
import com.example.parqueaya.utils.PermissionUtils;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsFragment extends Fragment implements OnMapReadyCallback {

    private TextView cochera_nombre;
    private TextView cochera_direccion;
    private TextView cochera_telefono;
    private TextView cochera_horario;

    private Button mostrar;
    private BottomSheetBehavior bottomSheetBehavior;
    private FloatingActionButton fabReserve;
    private Context context;

    private Cochera cochera;

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

        cochera_nombre = view.findViewById(R.id.cochera_nombre);
        cochera_direccion = view.findViewById(R.id.cochera_direccion);
        cochera_telefono = view.findViewById(R.id.cochera_telefono);
        cochera_horario = view.findViewById(R.id.cochera_horario);

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
        //                cochera_nombre.setText("Hola perro");
        //            }
        //        });
    }

    private void showDetalleCochera(View view) {
        view.findViewById(R.id.fab_reserve).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

                CocheraDetailFragment cocheraDetailFragment = new CocheraDetailFragment();

                Bundle args = new Bundle();
                args.putSerializable("cochera", cochera);
                cocheraDetailFragment.setArguments(args);

                getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, cocheraDetailFragment)
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
            List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                        updateMap(23000);
        } catch (IOException exception) {

        }
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
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
        }

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker mark) {
                String idS = mark.getId();
                idS = idS.replaceAll("\\D+", "");
                int id = Integer.parseInt(idS);
                showCochera(id);
                return false;
            }
        });
    }

    private void showCochera(int id) {

        ParkingApi parkingApi = RetrofitInstance.createService(ParkingApi.class);
        Call<Cochera> callCochera = parkingApi.getCochera(id);

        callCochera.enqueue(new Callback<Cochera>() {
            @Override
            public void onResponse(Call<Cochera> call, Response<Cochera> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    cochera = response.body();

                    if (cochera != null) {
                        cochera_nombre.setText(cochera.getNombre());
                        cochera_direccion.setText(cochera.getDireccion());
                        cochera_telefono.setText(cochera.getTelefono());
                        cochera_horario.setText(cochera.getHorarioAtencion());
                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    }
                }
            }

            @Override
            public void onFailure(Call<Cochera> call, Throwable t) {

            }

        });

        fabReserve.setEnabled(true);

    }
}
