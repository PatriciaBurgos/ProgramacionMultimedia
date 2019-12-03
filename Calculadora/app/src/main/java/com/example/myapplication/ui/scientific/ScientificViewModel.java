package com.example.myapplication.ui.scientific;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ScientificViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ScientificViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is scientific fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}