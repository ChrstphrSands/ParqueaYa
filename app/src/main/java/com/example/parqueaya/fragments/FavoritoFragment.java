package com.example.parqueaya.fragments;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.parqueaya.FavoritoClickListener;
import com.example.parqueaya.R;
import com.example.parqueaya.activities.MainActivity;
import com.example.parqueaya.adapters.FavoritoAdapter;
import com.example.parqueaya.models.Favorito;

import java.util.List;

@SuppressLint("ValidFragment")
public class FavoritoFragment extends Fragment implements FavoritoClickListener {

    private Context context;
    private FavoritoAdapter rcAdapter;
    private RecyclerView rView;

    public FavoritoFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        int layout_id = R.layout.fragment_favorito;
        View view = inflater.inflate(layout_id, container, false);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        rView = view.findViewById(R.id.recyclerView);
        rView.setHasFixedSize(true);
        rView.setLayoutManager(layoutManager);
        rView.setNestedScrollingEnabled(false);
        rView.setHasFixedSize(false);

        getFavorites();
        return view;
    }


    private void getFavorites() {

        List<Favorito> favoritos;

        favoritos = MainActivity.reservaRoomDatabase.favoritoDao().getFavoritos();

        displayFavoritoItems(favoritos);
    }

    private void displayFavoritoItems(List<Favorito> favoritos) {
        rcAdapter = new FavoritoAdapter(getContext(), favoritos);
        rView.setAdapter(rcAdapter);
        rcAdapter.setClickListener(this);
    }

    @Override
    public void itemClicked(View view, int position) {

    }
}
