package com.example.moviespatri.ui.peliculas;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PeliculasViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public PeliculasViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is peliculas fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}