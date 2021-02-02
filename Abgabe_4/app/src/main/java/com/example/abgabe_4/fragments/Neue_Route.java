package com.example.abgabe_4.fragments;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.abgabe_4.R;
import com.example.abgabe_4.database.entities.Route;
import com.example.abgabe_4.database.logic.DataTracker;
import com.example.abgabe_4.database.util.ObjectHandler;

import org.w3c.dom.Text;

public class Neue_Route extends Fragment {

    private static final String TAG = "neue_route";
    private NeueRouteViewModel mViewModel;

    Button startStopButton;
    TextView routeNameTextView;

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



        routeNameTextView = v.findViewById(R.id.txt_neue_route);
        startStopButton = v.findViewById(R.id.btn_neue_route);
        startStopButton.setOnClickListener(new View.OnClickListener() {

            DataTracker dataTracker;

            @Override
            public void onClick(View v) {


                if (!startStop) {
                    switchButtonFunction();
                    dataTracker = new DataTracker(v.getContext());

                    dataTracker.start();
                    Log.d(TAG, "onClick: started tracking");
                }else {
                    switchButtonFunction();
                    dataTracker.stop();

                    buildRoute(dataTracker);

                    Log.d(TAG, "onClick: stopped tracking");
                    ObjectHandler.INSTANCE.getRouteDao().addRoute(route);
                    Log.d(TAG, "onClick: saved track");
                }



            }
            // start / stop function switch
            void switchButtonFunction () {
                Log.d(TAG, "switchButtonFunction: switched start/stop");
                if (startStop){
                    startStopButton.setText("START");
                    startStop = false;
                } else{
                    startStop = true;
                    startStopButton.setText("STOP");
                }

            }
        });
        
        return v;
    }

    void buildRoute (DataTracker dataTracker) {

        route = new Route(String.valueOf(routeNameTextView.getText()));

        route.setBeginn(dataTracker.longLatStart);
        route.setEnde(dataTracker.longLatStop);
        route.setGpx(dataTracker.generateGPX(route.getBezeichnung()));
        route.setKoordinateList(dataTracker.getKoordinatenList());
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