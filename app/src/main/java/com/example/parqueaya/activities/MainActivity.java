package com.example.parqueaya.activities;

import android.Manifest;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.example.parqueaya.MyApplication;
import com.example.parqueaya.R;
import com.example.parqueaya.api.ParkingApi;
import com.example.parqueaya.api.RetrofitInstance;
import com.example.parqueaya.dataSource.FavoritoDataSource;
import com.example.parqueaya.dataSource.FavoritoRepository;
import com.example.parqueaya.dataSource.ReservaRoomDatabase;
import com.example.parqueaya.fragments.*;
import com.example.parqueaya.models.Cliente;
import com.example.parqueaya.models.Historial;
import com.example.parqueaya.utils.Common;
import com.example.parqueaya.utils.Tools;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener,
    GoogleApiClient.ConnectionCallbacks, LocationListener {

    private BottomNavigationView navigationView;
    private ActionBar actionBar;
    private Toolbar toolbar;
    private GoogleMap mMap;
    private MapsFragment mainFragment;
    public static ReservaRoomDatabase reservaRoomDatabase;
    private Cliente cliente;

    final int PERMISSION_LOCATION = 111;
    private final static int LOADING_DURATION = 3500;

    private TextView header_nombre;
    private TextView header_direccion;
    private CircleImageView header_foto;

    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        reservaRoomDatabase = Room.databaseBuilder(getApplicationContext(), ReservaRoomDatabase.class, "parqueo.db")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build();

        authFirebase();

        getCliente();
        initToolbar();
        initNavigationMenu();
        initBottomNavigation();
        initMap();
    }

    private void authFirebase() {

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

            }
        };

        mAuth = FirebaseAuth.getInstance();
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setTitle("Parquea Ya!!!");
        Tools.setSystemBarColor(this);
    }

    private void initMap() {

        mGoogleApiClient = new GoogleApiClient.Builder(this)
            .enableAutoManage(this, this)
            .addConnectionCallbacks(this)
            .addApi(LocationServices.API)
            .build();

        mainFragment = (MapsFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        if (mainFragment == null) {
            mainFragment = MapsFragment.newInstance();
            getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, mainFragment)
                .commit();
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
            PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_LOCATION);
            Log.v("Wiwi", "Solicitando permisos");
        } else {
            Log.v("Wiwi", "Servicio de Ubicacion conectado");
            startLocationServices();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        Log.v("Wiwi", "Long: " + location.getLongitude() + " - Lat: " + location.getLatitude());
        mainFragment.setUserMarker(new LatLng(location.getLatitude(), location.getLongitude()));
    }

    @Override
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
        mAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
        mAuth.removeAuthStateListener(authStateListener);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case PERMISSION_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startLocationServices();
                    Log.v("Wiwi", "Permission otorgado - iniciando servicio");
                } else {
                    Log.v("Wiwi", "Permission denegado");
                }
            }
        }
    }

    public void startLocationServices() {
        Log.v("Wiwi", "Iniciando servicio de ubicacion");

        try {
            LocationRequest request = LocationRequest.create().setPriority(LocationRequest.PRIORITY_LOW_POWER);
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, request, this);
            Log.v("Wiwi", "Ubicacion actualizada");
        } catch (SecurityException exception) {
            Log.v("Wiwi", exception.toString());
        }
    }

    private void initNavigationMenu() {
        final FirebaseUser firebaseUser = mAuth.getCurrentUser();
        NavigationView nav_view = findViewById(R.id.nav_view);
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);


        header_nombre = nav_view.getHeaderView(0).findViewById(R.id.header_nombre);
        header_direccion = nav_view.getHeaderView(0).findViewById(R.id.header_direccion);
        header_foto = nav_view.getHeaderView(0).findViewById(R.id.header_foto);

        header_direccion.setTextColor(getResources().getColor(R.color.blue_700));
        header_direccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (firebaseUser == null) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Ya estas logueado", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.app_name
            , R.string.app_name) {
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        //        drawer.setDrawerListener(toggle);
        toggle.syncState();
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull final MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_profile:
                        if (firebaseUser != null) {
                            ClientDetailFragment clientDetailFragment = new ClientDetailFragment();
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            fragmentManager.beginTransaction()
                                .replace(R.id.container, clientDetailFragment)
                                .addToBackStack(null)
                                .commit();
                            break;
                        } else {
                            Toast.makeText(getApplicationContext(), "DEBE DE ESTAR REGISTRADO", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case R.id.nav_favorite:
                        if (firebaseUser != null) {
                            FavoritoFragment favoritoFragment = new FavoritoFragment();
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            fragmentManager.beginTransaction()
                                .replace(R.id.container, favoritoFragment)
                                .addToBackStack(null)
                                .commit();
                            break;
                        } else {
                            Toast.makeText(getApplicationContext(), "DEBE DE ESTAR REGISTRADO", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case R.id.nav_logout:
                        if (firebaseUser != null) {
                            FirebaseAuth.getInstance().signOut();
                            Intent intent = new Intent(MainActivity.this, MainActivity.class);
                            startActivity(intent);
                            break;
                        } else {
                            Toast.makeText(getApplicationContext(), "DEBE DE ESTAR REGISTRADO", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case R.id.nav_historial:
                        if (firebaseUser != null) {
                            HistorialFragment historialFragment = new HistorialFragment();
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            fragmentManager.beginTransaction()
                                    .replace(R.id.container, historialFragment)
                                    .addToBackStack(null)
                                    .commit();
                            break;
                        } else {
                            Toast.makeText(getApplicationContext(), "DEBE DE ESTAR REGISTRADO", Toast.LENGTH_SHORT).show();
                        }
                        break;
                }
                drawer.closeDrawers();
                return true;
            }
        });
        drawer.closeDrawers();
    }

    private void initBottomNavigation() {
        navigationView = findViewById(R.id.navigation);
        final FirebaseUser firebaseUser = mAuth.getCurrentUser();
        navigationView.setBackgroundColor(getResources().getColor(R.color.blue_grey_900));
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_map:
                        navigationView.setBackgroundColor(getResources().getColor(R.color.blue_grey_900));
                        intent = new Intent(MainActivity.this, MainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.navigation_favorite:
                        if (firebaseUser != null) {
                            navigationView.setBackgroundColor(getResources().getColor(R.color.blue_grey_900));
                            FavoritoFragment favoritoFragment = new FavoritoFragment();
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            fragmentManager.beginTransaction()
                                .replace(R.id.container, favoritoFragment)
                                .addToBackStack(null)
                                .commit();
                            return true;
                        } else {
                            Toast.makeText(getApplicationContext(), "Debes de estar registrado para ver tus favoritos",
                                Toast.LENGTH_SHORT).show();
                            return false;
                        }
                    case R.id.navigation_books:
                        if (firebaseUser != null) {
                            navigationView.setBackgroundColor(getResources().getColor(R.color.blue_grey_900));

                            int reservaId = ((MyApplication) getApplicationContext()).getReservaId();

                            Log.d("Reserva", String.valueOf(reservaId));
                            if (reservaId != 0) {
                                ReservaDetailFragment reservaDetailFragment = new ReservaDetailFragment();
                                FragmentManager fragmentManager = getSupportFragmentManager();
                                fragmentManager.beginTransaction()
                                    .replace(R.id.container, reservaDetailFragment)
                                    .addToBackStack(null)
                                    .commit();
                                return true;
                            } else {
                                Toast.makeText(MainActivity.this, "No tienes reservas", Toast.LENGTH_SHORT).show();
                            }
                            return false;
                        } else {
                            Toast.makeText(getApplicationContext(), "Debe de estar registrado", Toast.LENGTH_SHORT).show();
                            return false;
                        }
                }
                return false;
            }
        });

        //        NestedScrollView nested_content = findViewById(R.id.nested_content);
        //        nested_content.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
        //            @Override
        //            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        //                if (scrollY < oldScrollY) {
        //                    animateNavigation(false);
        //                    //                    animateToolbar(false);
        //                }
        //                if (scrollY > oldScrollY) {
        //                    animateNavigation(true);
        //                    //                    animateToolbar(true);
        //                }
        //            }
        //        });

        Tools.setSystemBarColor(this, R.color.grey_5);
        Tools.setSystemBarLight(this);
    }

    private void selectDrawerItem(MenuItem item) {
        Fragment fragment = null;
        Class fragmentClass = null;
        switch (item.getItemId()) {
            case R.id.nav_profile:
                if (mAuth != null) {
                    fragmentClass = ClientDetailFragment.class;
                    actionBar.setTitle(item.getTitle());
                    break;
                } else {
                    Toast.makeText(this, "DEBE DE ESTAR REGISTRADO", Toast.LENGTH_SHORT).show();
                }

            case R.id.navigation_map:
                fragmentClass = MapsFragment.class;
                actionBar.setTitle(item.getTitle());
                break;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();


        item.setChecked(true);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void initDB() {

        Common.favoritoRepository =
            FavoritoRepository.getInstance(
                FavoritoDataSource.getInstance(
                    Common.reservaRoomDatabase.favoritoDao()
                ));
    }

    private void getCliente() {
        final FirebaseUser firebaseUser = mAuth.getCurrentUser();

        if (firebaseUser != null) {
            ParkingApi parkingApi = RetrofitInstance.createService(ParkingApi.class);
            Call<Cliente> call = parkingApi.getClienteDetail(firebaseUser.getUid());

            call.enqueue(new Callback<Cliente>() {
                @Override
                public void onResponse(Call<Cliente> call, Response<Cliente> response) {
                    if (response != null) {
                        cliente = response.body();
                        assert cliente != null;
                        header_nombre.setText(cliente.getNombre());
                        header_direccion.setText(cliente.getEmail());
                        Glide.with(getApplicationContext())
                            .load(firebaseUser.getPhotoUrl())
                            .centerCrop()
                            .into(header_foto);
                    }
                }

                @Override
                public void onFailure(Call<Cliente> call, Throwable t) {

                }
            });
        }

    }

}