package com.example.parqueaya;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.parqueaya.utils.ClientEditFragment;

public class ClientDetailFragment extends Fragment {

    private Context context;
    private FloatingActionButton actionButton;
    public ClientDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_client_detail, container, false);
        actionButton = view.findViewById(R.id.cliente_detail_editar);
        onClick();

        return view;
    }

    private void onClick() {
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClientEditFragment clientEditFragment = new ClientEditFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, clientEditFragment)
                    .addToBackStack(null)
                    .commit();
            }
        });
    }
}
