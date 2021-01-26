package com.example.abgabe_4.fragments;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RouteListViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public RouteListViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is AlleRouten fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}