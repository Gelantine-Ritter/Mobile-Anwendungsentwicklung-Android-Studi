package com.example.abgabe_4.fragments;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.abgabe_4.R;
import com.example.abgabe_4.database.entities.Route;
import com.example.abgabe_4.database.logic.DataTracker;
import com.example.abgabe_4.database.util.ObjectHandler;

public class Neue_Route extends Fragment {

    private NeueRouteViewModel mViewModel;

    Button startStopButton;
    boolean  startStop = false;

    Route route;

    byte[] image;


    public static Neue_Route newInstance() {
        return new Neue_Route();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.neue__route_fragment, container, false);

        DataTracker dataTracker = new DataTracker(v.getContext());


        startStopButton = v.findViewById(R.id.btn_neue_route);
        startStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!startStop) {
                    dataTracker.start();
                }else {
                    dataTracker.stop();
                    buildRoute(dataTracker);

                    ObjectHandler.INSTANCE.getRouteDao().addRoute(route);
                }

            }
        });




        return v;
    }

    void buildRoute (DataTracker dataTracker) {
        route.setBeginn(dataTracker.longLatStart);
        route.setEnde(dataTracker.longLatStop);
        route.setGpx(dataTracker.generateGPX());
        route.setDauer(dataTracker.dauer);
        route.setImage(image);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(NeueRouteViewModel.class);
        // TODO: Use the ViewModel
    }

}