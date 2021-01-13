package com.example.travelquest.fragments;

import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import com.example.travelquest.ErgebnisActivity;
import com.example.travelquest.QuestionActivity;
import com.example.travelquest.R;
import com.example.travelquest.database.util.ObjectHandler;

public class F5_Sternegucken extends Fragment {

    Button btnSterneDach;
    Button btnSterneCandle;

    private F5_Sternegucken_ViewModel mViewModel;

    public static F5_Sternegucken newInstance() {
        return new F5_Sternegucken();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.f5__sternegucken_fragment, container, false);

        /**
         * OnClick: Daten an Datenbank übermittel und replace durch ein neues Fragment
         */
        btnSterneDach = v.findViewById(R.id.btn_daecher);

        btnSterneDach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transferData(true);
                changeFragment();
                startErgebnisActivity();
            }
        });

        /**
         * OnClick: Daten an Datenbank übermittel und replace durch ein neues Fragment
         */
        btnSterneCandle = v.findViewById(R.id.btn_candlelight);
        btnSterneCandle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transferData(false);
                changeFragment();
                startErgebnisActivity();

            }
        });

        return v;
    }

    private void startErgebnisActivity() {
        Intent intent = new Intent(getActivity(), ErgebnisActivity.class);
        startActivity(intent);
    }

    private void transferData (boolean decision) {
            ObjectHandler.INSTANCE.getUserEntry().setF5(decision);
    }

    private void changeFragment() {

        F5_Sternegucken fragmentSternegucken = new F5_Sternegucken();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        //altest Fragment in mainLayout wird ersetzt mit dem neuem Fragment
        transaction.replace(R.id.mainLayoutQuestionActivity, fragmentSternegucken);
        transaction.commit();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(F5_Sternegucken_ViewModel.class);
        // TODO: Use the ViewModel
    }

}