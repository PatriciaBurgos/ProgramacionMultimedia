package com.example.moviespatri.ui.peliculas;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DetallesViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public DetallesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Detalles View Model");
    }

    public LiveData<String> getText() {
        return mText;
    }

}
