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
import android.widget.TextView;
import android.widget.Toast;


public class ScoreboardFragment extends Fragment {
    View view;
    int numPlayers = 1, numNumbers = 1, iterator = 0;
    int[] tempArray = new int[1]; int[][] mainPlayerNumbers;
    String[] playerNames = new String[1];
    TextView mainText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_scoreboard, container, false);
        try {
            tempArray = ResultFragmentArgs.fromBundle(requireArguments()).getMainPlayerNums();
            numPlayers = ResultFragmentArgs.fromBundle(requireArguments()).getNumPlayers();
            numNumbers = ResultFragmentArgs.fromBundle(requireArguments()).getNumNumbers();
            playerNames = ResultFragmentArgs.fromBundle(requireArguments()).getPlayerNames();
        } catch (Exception e) {
            Log.d("DEBUG - RESULT FRAGMENT", "EXCEPTION: NO BUNDLE");
        }
        mainPlayerNumbers = new int[numPlayers][numNumbers];
        Button newGameButton = view.findViewById(R.id.newGameButtonScoreboard);
        // Create 2-D array
        for (int row = 0; row < numPlayers; row++) {
            for (int col = 0; col < numNumbers; col++) {
                mainPlayerNumbers[row][col] = tempArray[iterator];
                iterator++;
            }
        }
        mainText = view.findViewById(R.id.scorboardMainText);
        String tempString = "";
        for(int i = 0; i < numPlayers; i++) {
            tempString = tempString +  playerNames[i] + ":\n";
            for(int j = 0; j < numNumbers; j++) {
                tempString = tempString + mainPlayerNumbers[i][j] + " ";
            }
            tempString +="\n\n";
        }
        mainText.setText(tempString);
        newGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_scoreboardFragment_to_newGameFragment);
            }
        });

        /**
         * Dont change anything above unless you have to...
         *
         * The main array for all the numbers is mainPlayerNumbers[PLAYER][NUMBER]
         * numPlayers is how many total players
         * numNumbers is how many numbers per player
         *
         * So numPlayers * numNumbers is the total size of the array.
         */

        return view;
    }
}