package com.mobileapp.finalproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class TitleFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_title, container, false);

        Button newGameButton = view.findViewById(R.id.newGameButton);
        Button helpButton = view.findViewById(R.id.helpButton);

        newGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("DEBUG", "TITLE FRAGMENT: New Game Button Pressed");
                Navigation.findNavController(view).navigate(R.id.action_titleFragment_to_newGameFragment);
            }
        });
        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("DEBUG", "TITLE FRAGMENT: Help Button Pressed");
                Navigation.findNavController(view).navigate(R.id.action_titleFragment_to_helpFragment);
            }
        });
        return view;
    }
}