package com.example.abgabe_4.fragments;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.abgabe_4.R;
import com.example.abgabe_4.activities.RouteView;
import com.example.abgabe_4.adapters.RouteListAdapter;
import com.example.abgabe_4.database.entities.Route;
import com.example.abgabe_4.database.util.ObjectHandler;

import java.util.ArrayList;
import java.util.List;

public class Route_List extends Fragment implements RouteListAdapter.OnRouteListener {
    private static final String TAG = "";
    Context c;
    private RouteListViewModel alleRoutenViewModel;
    ArrayList<Route> routenListeArr = new ArrayList<>();
    //Variable beinhaltet Recyclerview aus xml layout
    private RecyclerView mRecyclerView;
    //Adapter ist die Brücke zwischen Data (ArrayList) und dem RecyclerView --> Adapter sorgt für Performance (so viele Items wie gebraucht werden, werden dargestellt)
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private RouteListViewModel mViewModel;

    public static Route_List newInstance() {
        return new Route_List();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        alleRoutenViewModel =
                new ViewModelProvider(this).get(RouteListViewModel.class);
        View root = inflater.inflate(R.layout.route__list_fragment, container, false);
        alleRoutenViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

                //Items für die Listendarstellung
                routenListeArr = (ArrayList<Route>) ObjectHandler.INSTANCE.getRouteDao().getRouten();
                // ArrayList<Route> routenListeArr = new ArrayList<>();

                mRecyclerView = root.findViewById(R.id.liste_routen);
                mRecyclerView.setHasFixedSize(true);
                mLayoutManager = new LinearLayoutManager(c);
                mAdapter = new RouteListAdapter(routenListeArr, Route_List.this::onRouteClick);
                mRecyclerView.setLayoutManager(mLayoutManager);
                mRecyclerView.setAdapter(mAdapter);
            }
        });
        return root;
    }

    @Override
    public void onRouteClick(int position) {
        // id of the item to hand to intent (single route)
        Route route = routenListeArr.get(position);

        Intent intent = new Intent(getActivity(), RouteView.class);
        Bundle b = new Bundle();
        b.putString("route_bezeichnung", route.getBezeichnung());
        intent.putExtras(b);
        getActivity().startActivity(intent);

    }
}