package com.example.travelquest.fragments;

import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.travelquest.QuestionActivity;
import com.example.travelquest.R;
import com.example.travelquest.database.util.ObjectHandler;

public class F1_Reisefuehrer extends Fragment {

    Button btnJa;
    Button btnNein;

    private F1_Reisefuehrer_ViewModel mViewModel;

    public static F1_Reisefuehrer newInstance() {
        return new F1_Reisefuehrer();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.f1__reisefuehrer_fragment, container, false);

        /**
         * OnClick: Daten an Datenbank übermittel und replace durch ein neues Fragment
         */
        btnJa = v.findViewById(R.id.btn_reise_ja);
        btnJa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transferData(true);
                changeFragment();

            }
        });

        /**
         * OnClick: Daten an Datenbank übermittel und replace durch ein neues Fragment
         */
        btnNein = v.findViewById(R.id.btn_reise_nein);
        btnNein.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transferData(false);
                changeFragment();
            }
        });
        return v;
    }

    private void transferData(boolean decision) {
        ObjectHandler.INSTANCE.getUserEntry().setF1(decision);
    }

    private void changeFragment() {

        F2_Kaffee fragmentKaffee = new F2_Kaffee();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        //altest Fragment in mainLayout wird ersetzt mit dem neuem Fragment
        transaction.replace(R.id.mainLayoutQuestionActivity, fragmentKaffee);
        transaction.commit();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(F1_Reisefuehrer_ViewModel.class);
        // TODO: Use the ViewModel
    }

}