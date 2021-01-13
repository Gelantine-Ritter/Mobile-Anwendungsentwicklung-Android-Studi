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

public class F2_Kaffee extends Fragment {

    Button btnJa;
    Button btnNein;

    private F2_Kaffee_ViewModel mViewModel;

    public static F2_Kaffee newInstance() {
        return new F2_Kaffee();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.f2__kaffee__fragment, container, false);

        /**
         * OnClick: Daten an Datenbank übermittel und replace durch ein neues Fragment
         */
        btnJa = v.findViewById(R.id.btn_kaffee_ja);
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
        btnNein = v.findViewById(R.id.btn_kaffee_nein);
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
        ObjectHandler.INSTANCE.getUserEntry().setF2(decision);
    }

    private void changeFragment() {

        F3_Netflix fragmentNetflix = new F3_Netflix();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        //altest Fragment in mainLayout wird ersetzt mit dem neuem Fragment
        transaction.replace(R.id.mainLayoutQuestionActivity, fragmentNetflix);
        transaction.commit();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(F2_Kaffee_ViewModel.class);
        // TODO: Use the ViewModel
    }

}