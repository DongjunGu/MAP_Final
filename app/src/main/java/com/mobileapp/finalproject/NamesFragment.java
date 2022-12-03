package com.mobileapp.finalproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class NamesFragment extends Fragment {
    View view;
    String[] playerNames;
    String playerString = "Player", tempString, nameEntered;
    int numPlayers = 1, numNumbers = 1, playerIncrement = 1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_names, container, false);

        try {
            numPlayers = NamesFragmentArgs.fromBundle(requireArguments()).getNumPlayers();
            numNumbers = NamesFragmentArgs.fromBundle(requireArguments()).getNumNumbers();
        } catch (Exception e) {
            Log.d("DEBUG", "EXCEPTION: NO BUNDLE");
        }

        playerNames = new String[numPlayers];
        Button confirmButton = view.findViewById(R.id.startButton);
        EditText playerNameEnter = view.findViewById(R.id.playerNameEdit);
        TextView playerNameText = view.findViewById(R.id.playerNameText);

        tempString = playerString + " 1";
        playerNameText.setText(tempString);


        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("DEBUG - PLAYERNAME FRAG", "CONFIRM BUTTON HIT");
                if(playerNameEnter.getText().toString().length() > 0) {
                    playerNames[playerIncrement - 1] = playerNameEnter.getText().toString();
                    if(playerIncrement == numPlayers) {
                        NavDirections action = NamesFragmentDirections.actionNamesFragmentToGameFragment(numPlayers, numNumbers, playerNames);
                        Navigation.findNavController(view).navigate(action);
                    } else {
                        playerIncrement++;
                        tempString = playerString + " " + playerIncrement;
                        playerNameText.setText(tempString);
                        playerNameEnter.setText("");
                    }
                }

            }
        });
        return view;
    }
}