package com.example.parqueaya.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.parqueaya.R;
import com.example.parqueaya.adapters.FavoritoAdapter;
import com.example.parqueaya.adapters.HistorialClickListener;
import com.example.parqueaya.models.Historial;

import java.util.List;

public class HistorialFragment extends Fragment  implements HistorialClickListener {


    public HistorialFragment() {
        // Required empty public constructor
    }

    private Context context;
    private HistorialAdapter rcAdapter;
    private RecyclerView rView;

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

    private void displayFavoritoItems(List<Historial> historial) {
        rcAdapter = new FavoritoAdapter(getContext(), historial);
        rView.setAdapter(rcAdapter);
        rcAdapter.setClickListener(this);
    }

    @Override
    public void itemClicked(View view, int position) {

    }
}
