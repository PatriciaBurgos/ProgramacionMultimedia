package com.example.moviespatri.ui.series;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DetallesSeriesViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public DetallesSeriesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Detalles View Model");
    }

    public LiveData<String> getText() {
        return mText;
    }

}
