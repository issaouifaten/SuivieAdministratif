package com.example.suivieadministratif.ui.workflow;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class WorkfowViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public WorkfowViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Workfow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}