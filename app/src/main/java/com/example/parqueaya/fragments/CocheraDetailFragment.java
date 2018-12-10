package com.example.parqueaya.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.balysv.materialripple.MaterialRippleLayout;
import com.bumptech.glide.Glide;
import com.example.parqueaya.R;
import com.example.parqueaya.activities.LoginActivity;
import com.example.parqueaya.activities.MainActivity;
import com.example.parqueaya.api.ParkingApi;
import com.example.parqueaya.api.RetrofitInstance;
import com.example.parqueaya.models.Cochera;
import com.example.parqueaya.models.Favorito;
import com.example.parqueaya.models.Servicio;
import com.example.parqueaya.utils.Tools;
import com.example.parqueaya.utils.ViewAnimation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.net.URL;
import java.util.List;

public class CocheraDetailFragment extends Fragment {

    private Cochera cochera;

    private final String URL_FOTO = "https://sistemaparqueo.azurewebsites.net/Uploads/Cocheras/";

    private TextView cochera_nombre;
    private TextView cochera_espacios;
    private TextView cochera_telefono;
    private TextView cochera_direccion;
    private TextView cochera_descripcion;
    private TextView cochera_horario;
    private TextView cochera_precio;
    private ImageView cochera_foto;
    private MaterialRippleLayout cochera_reservar;
    private FloatingActionButton cochera_favorito;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private ImageButton bt_toggle_reviews, bt_toggle_warranty, bt_toggle_description;
    private View lyt_expand_reviews, lyt_expand_warranty, lyt_expand_description;
    private NestedScrollView nested_scroll_view;
    private View parent_view;

    private List<Servicio> servicios;

    public CocheraDetailFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cochera_detail, container, false);

        assert getArguments() != null;
        cochera = (Cochera) getArguments().getSerializable("cochera");

        getServicios();
        initComponents(view);
        initComponent(view);
        setDataComponents();
        authFirebase();

//        cochera_precio.setText(String.valueOf(servicios.get(0).getCosto()));

        return view;
    }


    private void initComponent(View view) {
        nested_scroll_view = view.findViewById(R.id.nested_scroll_view);

        // section reviews
        bt_toggle_reviews = view.findViewById(R.id.bt_toggle_reviews);
        lyt_expand_reviews = view.findViewById(R.id.lyt_expand_reviews);
        bt_toggle_reviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleSection(view, lyt_expand_reviews);
            }
        });

        // section warranty
        bt_toggle_warranty = view.findViewById(R.id.bt_toggle_warranty);
        lyt_expand_warranty = view.findViewById(R.id.lyt_expand_warranty);
        bt_toggle_warranty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleSection(view, lyt_expand_warranty);
            }
        });

        // section description
        bt_toggle_description = view.findViewById(R.id.bt_toggle_description);
        lyt_expand_description = view.findViewById(R.id.lyt_expand_description);
        bt_toggle_description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleSection(view, lyt_expand_description);
            }
        });

        toggleArrow(bt_toggle_description);
        lyt_expand_description.setVisibility(View.VISIBLE);
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

    private void initComponents(View view) {

        cochera_foto = view.findViewById(R.id.cochera_foto);
        cochera_reservar = view.findViewById(R.id.cochera_reservar);
        cochera_nombre = view.findViewById(R.id.cochera_nombre);
//        cochera_espacios = view.findViewById(R.id.cochera_espacios);
        cochera_telefono = view.findViewById(R.id.cochera_telefono);
        cochera_direccion = view.findViewById(R.id.cochera_direccion);
        cochera_descripcion = view.findViewById(R.id.cochera_descripcion);
        cochera_favorito = view.findViewById(R.id.cochera_favorito);
        cochera_horario = view.findViewById(R.id.cochera_horario);
        cochera_precio = view.findViewById(R.id.cochera_precio);

        cochera_reservar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser firebaseUser = mAuth.getCurrentUser();
                if (firebaseUser != null) {
                    ReservaFragment reservaFragment = new ReservaFragment();

                    Bundle args = new Bundle();
                    args.putSerializable("cochera", cochera);
                    reservaFragment.setArguments(args);

                    getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, reservaFragment)
                        .addToBackStack(null)
                        .commit();
                } else {
                    Toast.makeText(getContext(), "Debe estar registrado", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
            }
        });

        if (MainActivity.reservaRoomDatabase.favoritoDao().isFavorito(cochera.getCocheraId()) == 1) {
            cochera_favorito.setImageResource(R.drawable.ic_favorite);
        } else {
            cochera_favorito.setImageResource(R.drawable.ic_favorite_border_white);
        }

        cochera_favorito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.reservaRoomDatabase.favoritoDao().isFavorito(cochera.getCocheraId()) != 1) {
                    addOrRemoveFavorito(cochera, true);
                    cochera_favorito.setImageResource(R.drawable.ic_favorite);
                } else{
                    addOrRemoveFavorito(cochera, false);
                    cochera_favorito.setImageResource(R.drawable.ic_favorite_border_white);
                }
            }
        });

    }

    private void addOrRemoveFavorito(Cochera cochera, boolean isAdd) {
        Favorito favorito = new Favorito();
        favorito.setId(cochera.getCocheraId());
        favorito.setFoto(cochera.getFoto());
        favorito.setPrecio(String.valueOf(servicios.get(0).getCosto()));
        favorito.setNombre(cochera.getNombre());
        favorito.setDireccion(cochera.getDireccion());


        if (isAdd) {
            MainActivity.reservaRoomDatabase.favoritoDao().insertFavorito(favorito);
        } else {
            MainActivity.reservaRoomDatabase.favoritoDao().delete(favorito);
        }

    }

    private void setDataComponents() {
        cochera_nombre.setText(cochera.getNombre());
//        cochera_espacios.setText(cochera.getCodigoPostal());
        cochera_telefono.setText(cochera.getTelefono());
        cochera_direccion.setText(cochera.getDireccion());
        cochera_descripcion.setText(cochera.getDescripcion());
        cochera_horario.setText(cochera.getHorarioAtencion());

        Glide.with(getContext())
            //        BuildConfig.IMAGE_URL + dataList.get(position).getImageUrl()
            .load(URL_FOTO + cochera.getFoto())
            .thumbnail(0.01f)
            .centerCrop()
            .into(cochera_foto);

    }

    private void authFirebase() {
        mAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

            }
        };
    }

    private void getServicios() {
        ParkingApi parkingApi = RetrofitInstance.createService(ParkingApi.class);
        Call<List<Servicio>> call = parkingApi.getServicios(cochera.getCocheraId());

        call.enqueue(new Callback<List<Servicio>>() {
            @Override
            public void onResponse(Call<List<Servicio>> call, Response<List<Servicio>> response) {
                if (response != null) {
                    servicios = response.body();
                    assert servicios != null;
                    cochera_precio.setText(String.format("S/. %s", String.valueOf(servicios.get(0).getCosto())));
                }
            }

            @Override
            public void onFailure(Call<List<Servicio>> call, Throwable t) {

            }
        });
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
}
