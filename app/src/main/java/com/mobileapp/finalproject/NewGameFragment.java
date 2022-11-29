package com.mobileapp.finalproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class NewGameFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_game, container, false);

        Button computerButton= view.findViewById(R.id.vsComputer);
        Button playerButton = view.findViewById(R.id.vsPlayer);
        playerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_newGameFragment_to_numOfPlayersFragment);
            }
        });

        return view;
    }
}