package com.example.myrouteplam.ui.neue_route;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NeueRouteViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public NeueRouteViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is NeueRoute fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}