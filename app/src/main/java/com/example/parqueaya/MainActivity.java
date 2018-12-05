package com.example.parqueaya;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import com.example.parqueaya.utils.Tools;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView navigationView;
    private ActionBar actionBar;
    private Toolbar toolbar;
    private GoogleMap mMap;

    private boolean mPermissionDenied = false;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolbar();
        initNavigationMenu();
        initBottomNavigation();




//        FragmentManager fragmentManager = getSupportFragmentManager();
//        fragmentManager.beginTransaction().replace(R.id.container, new MapsFragment()).commit();

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

    private void initNavigationMenu() {
        NavigationView nav_view = findViewById(R.id.nav_view);
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.app_name
            , R.string.app_name) {
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        drawer.setDrawerListener(toggle);
        toggle.syncState();
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull final MenuItem item) {
                selectDrawerItem(item);
                drawer.closeDrawers();
                return true;
            }
        });
        drawer.closeDrawers();
    }

    private void initBottomNavigation() {
        navigationView = findViewById(R.id.navigation);
        navigationView.setBackgroundColor(getResources().getColor(R.color.pink_800));
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_map:
                        navigationView.setBackgroundColor(getResources().getColor(R.color.pink_800));
                        selectDrawerItem(item);
                        return true;
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
                fragmentClass = ClientDetailFragment.class;
                actionBar.setTitle(item.getTitle());
                break;
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



    //    boolean isNavigationHide = false;
//
//    private void animateNavigation(boolean hide) {
//        if (isNavigationHide && hide || !isNavigationHide && !hide) return;
//        isNavigationHide = hide;
//        int moveY = hide ? (2 * navigationView.getHeight()) : 0;
//        navigationView.animate().translationY(moveY).setStartDelay(100).setDuration(300).start();
//    }

//    @Override
//    public boolean onKeyUp(int keyCode, KeyEvent event) {
//        boolean back = false;
//        if(keyCode == KeyEvent.KEYCODE_BACK){
//            back = true;
//            backStack();
//        }
//        return back;
//    }
//
//    private void backStack(){
//        if(getSupportFragmentManager().getBackStackEntryCount()>1){
//            getSupportFragmentManager().popBackStack();
//        }else
//        if(getSupportFragmentManager().getBackStackEntryCount()==1){
//            this.finish();
//        }
//    }



}