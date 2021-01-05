package com.example.myrouteplam.ui.alle_routen;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AlleRoutenViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AlleRoutenViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is AlleRouten fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}