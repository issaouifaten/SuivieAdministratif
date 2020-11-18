package com.example.suivieadministratif.ui.parametrage;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ParametrageViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ParametrageViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Parametrage fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}