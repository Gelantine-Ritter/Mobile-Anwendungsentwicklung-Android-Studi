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

public class F3_Netflix extends Fragment {

    Button btnNetflix;
    Button btnBerghain;

    private F3_Netflix_ViewModel mViewModel;

    public static F3_Netflix newInstance() {
        return new F3_Netflix();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.f3__netflix_fragment, container, false);

        /**
         * OnClick: Daten an Datenbank übermittel und replace durch ein neues Fragment
         */
        btnNetflix = v.findViewById(R.id.btn_netflix);

        btnNetflix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transferData(true);
                changeFragment();
            }
        });

        /**
         * OnClick: Daten an Datenbank übermittel und replace durch ein neues Fragment
         */
        btnBerghain = v.findViewById(R.id.btn_berghain);
        btnBerghain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transferData(false);
                changeFragment();
            }
        });

        return v;
    }
    private void transferData(boolean decision) {
        ObjectHandler.INSTANCE.getUserEntry().setF3(decision);
    }


    private void changeFragment() {

        F4_Muell fragmentMuell = new F4_Muell();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        //altest Fragment in mainLayout wird ersetzt mit dem neuem Fragment
        transaction.replace(R.id.mainLayoutQuestionActivity, fragmentMuell);
        transaction.commit();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(F3_Netflix_ViewModel.class);
        // TODO: Use the ViewModel
    }

}