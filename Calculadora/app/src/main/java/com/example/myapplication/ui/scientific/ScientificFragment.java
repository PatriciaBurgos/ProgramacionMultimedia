package com.example.myapplication.ui.scientific;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.myapplication.R;

public class ScientificFragment extends Fragment {

    private ScientificViewModel scientificViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        scientificViewModel =
                ViewModelProviders.of(this).get(ScientificViewModel.class);
        View root = inflater.inflate(R.layout.fragment_scientific, container, false);

        return root;
    }
}