package com.example.travelquest.fragments;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.travelquest.R;

public class F5_Sternegucken extends Fragment {

    private F5_Sternegucken_ViewModel mViewModel;

    public static F5_Sternegucken newInstance() {
        return new F5_Sternegucken();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.f5__sternegucken_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(F5_Sternegucken_ViewModel.class);
        // TODO: Use the ViewModel
    }

}