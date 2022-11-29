package com.mobileapp.finalproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NumOfPlayersFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_num_of_players, container, false);

        Button startButton = view.findViewById(R.id.startButton);
        EditText playerEdit = view.findViewById(R.id.playerEdit);
        EditText inputEdit = view.findViewById(R.id.inputEdit);





        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (playerEdit.length() == 0)
                    Toast.makeText(getActivity(), "Type the number of Players!", Toast.LENGTH_SHORT).show();
                else if (inputEdit.length() == 0)
                    Toast.makeText(getActivity(), "Type the number of Inputs!", Toast.LENGTH_SHORT).show();
                else {
                    String playerString = playerEdit.getText().toString();
                    int playerInt = Integer.parseInt(playerString);
                    String inputString = inputEdit.getText().toString();
                    int inputInt = Integer.parseInt(inputString);
                    if (inputInt > 10)
                        Toast.makeText(getActivity(), "Enter 10 or less!", Toast.LENGTH_SHORT).show();
                    else {
                        NavDirections action = NumOfPlayersFragmentDirections.actionNumOfPlayersFragmentToGameFragment(playerInt, inputInt);
                        Navigation.findNavController(view).navigate(action);
                    }
                }
            }
        });

        return view;
    }
}