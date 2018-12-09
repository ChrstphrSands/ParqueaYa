package com.example.parqueaya.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.example.parqueaya.R;
import com.example.parqueaya.activities.LoginActivity;
import com.example.parqueaya.api.ParkingApi;
import com.example.parqueaya.api.RetrofitInstance;
import com.example.parqueaya.models.Cliente;
import com.example.parqueaya.models.Cochera;
import com.example.parqueaya.utils.Tools;
import com.example.parqueaya.utils.ViewAnimation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CocheraDetailFragment extends Fragment {

    private Cochera cochera;

    private TextView cochera_nombre;
    private TextView cochera_espacios;
    private TextView cochera_telefono;
    private TextView cochera_direccion;
    private TextView cochera_descripcion;
    private AppCompatButton reservar;
    private ImageButton bt_toggle_reviews, bt_toggle_warranty, bt_toggle_description;
    private View lyt_expand_reviews, lyt_expand_warranty, lyt_expand_description;
    private NestedScrollView nested_scroll_view;
    private View parent_view;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    public CocheraDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cochera_detail, container, false);

        assert getArguments() != null;
        cochera = (Cochera) getArguments().getSerializable("cochera");
        Log.d("Cochera", String.valueOf(cochera));

        initComponents(view);
        setDataComponents();
        authFirebase();
        initComponent(view);

        return view;
    }

    private void initComponents(View view) {

//        reservar = view.findViewById(R.id.reservar);
        cochera_nombre = view.findViewById(R.id.cochera_nombre);
//        cochera_espacios = view.findViewById(R.id.cochera_espacios);
        cochera_telefono = view.findViewById(R.id.cochera_telefono);
        cochera_direccion = view.findViewById(R.id.cochera_direccion);
        cochera_descripcion = view.findViewById(R.id.cochera_descripcion);

//        reservar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FirebaseUser firebaseUser = mAuth.getCurrentUser();
//                if (firebaseUser != null) {
//                    ReservaFragment reservaFragment = new ReservaFragment();
//
//                    Bundle args = new Bundle();
//                    args.putSerializable("cochera", cochera);
//                    reservaFragment.setArguments(args);
//
//                    getActivity().getSupportFragmentManager().beginTransaction()
//                            .replace(R.id.container, reservaFragment)
//                            .addToBackStack(null)
//                            .commit();
//                } else {
//                    Toast.makeText(getContext(), "Debe estar registrado", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(getContext(), LoginActivity.class);
//                    startActivity(intent);
//                }
//            }
//        });
    }

    private void setDataComponents() {
        cochera_nombre.setText(cochera.getNombre());
//        cochera_espacios.setText(cochera.getCodigoPostal());
        cochera_telefono.setText(cochera.getTelefono());
        cochera_direccion.setText(cochera.getDireccion());
        cochera_descripcion.setText(cochera.getDescripcion());
    }

    private void authFirebase() {
        mAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(authStateListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(authStateListener);
    }

    private void initComponent(View view) {
        // nested scrollview
        nested_scroll_view = (NestedScrollView) view.findViewById(R.id.nested_scroll_view);

        // section reviews
        bt_toggle_reviews = (ImageButton) view.findViewById(R.id.bt_toggle_reviews);
        lyt_expand_reviews = (View) view.findViewById(R.id.lyt_expand_reviews);
        bt_toggle_reviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleSection(view, lyt_expand_reviews);
            }
        });

        // section warranty
        bt_toggle_warranty = (ImageButton) view.findViewById(R.id.bt_toggle_warranty);
        lyt_expand_warranty = (View) view.findViewById(R.id.lyt_expand_warranty);
        bt_toggle_warranty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleSection(view, lyt_expand_warranty);
            }
        });

        // section description
        bt_toggle_description = (ImageButton) view.findViewById(R.id.bt_toggle_description);
        lyt_expand_description = (View) view.findViewById(R.id.lyt_expand_description);
        bt_toggle_description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleSection(view, lyt_expand_description);
            }
        });

        // expand first description
        toggleArrow(bt_toggle_description);
        lyt_expand_description.setVisibility(View.VISIBLE);

        ((FloatingActionButton) view.findViewById(R.id.fab)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(parent_view, "Add to Cart", Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private void toggleSection(View bt, final View lyt) {
        boolean show = toggleArrow(bt);
        if (show) {
            ViewAnimation.expand(lyt, new ViewAnimation.AnimListener() {
                @Override
                public void onFinish() {
                    Tools.nestedScrollTo(nested_scroll_view, lyt);
                }
            });
        } else {
            ViewAnimation.collapse(lyt);
        }
    }

    public boolean toggleArrow(View view) {
        if (view.getRotation() == 0) {
            view.animate().setDuration(200).rotation(180);
            return true;
        } else {
            view.animate().setDuration(200).rotation(0);
            return false;
        }
    }
}
