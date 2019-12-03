package com.example.myapplication.ui.mortgage;

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

public class MortgageFragment extends Fragment {

    private MortgageViewModel mortgageViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mortgageViewModel =
                ViewModelProviders.of(this).get(MortgageViewModel.class);
        View root = inflater.inflate(R.layout.fragment_mortgage, container, false);
        final TextView textView = root.findViewById(R.id.text_slideshow);
        mortgageViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}