package com.example.myrouteplam.ui.alle_routen;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myrouteplam.MainActivity;
import com.example.myrouteplam.R;
import com.example.myrouteplam.entities.Route;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class AlleRoutenFragment extends Fragment {

    Context c;

    private AlleRoutenViewModel alleRoutenViewModel;
    //Variable beinhaltet Recyclerview aus xml layout
    private RecyclerView mRecyclerView;
    //Adapter ist die Brücke zwischen Data (ArrayList) und dem RecyclerView --> Adapter sorgt für Performance (so viele Items wie gebraucht werden, werden dargestellt)
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        alleRoutenViewModel =
                new ViewModelProvider(this).get(AlleRoutenViewModel.class);
        View root = inflater.inflate(R.layout.liste_routen, container, false);
        alleRoutenViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

                //Items für die Listendarstellung
                List<Route> routeList = MainActivity.routeDB.routeDao().getRouten();
                ArrayList<Route> routenListeArr = new ArrayList<>();
                for (Route r : routeList){
                    String bezeichnung = r.getBezeichnung();
                    String beginn = r.getBeginn();
                    String ende = r.getEnde();
                    byte[] gpx = r.getGpxData();
                    double dauer=r.getDauer();
                    //Poi poi = r.getPoi();

                    routenListeArr.add(new Route(bezeichnung, beginn, ende, gpx,dauer));

                }



                mRecyclerView = root.findViewById(R.id.liste_routen);
                mRecyclerView.setHasFixedSize(true);
                mLayoutManager = new LinearLayoutManager(c);
                mAdapter = new AdapterListe(routenListeArr);

                mRecyclerView.setLayoutManager(mLayoutManager);
                mRecyclerView.setAdapter(mAdapter);
            }
        });
        return root;


    }


}