package com.mobileapp.finalproject;

import android.os.Bundle;
import android.content.Intent;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ResultFragment extends Fragment {
    View view;
    int numPlayers = 1, numNumbers = 1, iterator = 0;
    int[] tempArray = new int[1]; int[][] mainPlayerNumbers;
    String[] playerNames;

    public static int unique(int mat[][], int R, int C) {
        Map<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                if (map.containsKey(mat[i][j])) {
                    map.put(mat[i][j], 1 + map.get(mat[i][j]));
                } else { map.put(mat[i][j], 1); }
            }
        }
        for (Map.Entry<Integer, Integer> e : map.entrySet()) {
            if (e.getValue() == 1) {
                return e.getKey();
            }
        }
        Log.d("DEBUG - RESULT FRAGMENT", "NO UNIQUE");
        return -1;
    }

    static int[] linearSearch(int[][] arr, int target) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                if (arr[i][j] == target) {
                    return new int[] { i, j };
                }
            }
        }
        return new int[] { -1, -1 };
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_result, container, false);

        Button scoreboardButton = view.findViewById(R.id.scoreboardButton);
        TextView winningPlayerText = view.findViewById(R.id.mainWinText);
        TextView winningNumberText = view.findViewById(R.id.winningNumberText);
        //Get main number list and recreate the 2-d array
        playerNames = new String[1];
        try {
            tempArray = ResultFragmentArgs.fromBundle(requireArguments()).getMainPlayerNums();
            numPlayers = ResultFragmentArgs.fromBundle(requireArguments()).getNumPlayers();
            numNumbers = ResultFragmentArgs.fromBundle(requireArguments()).getNumNumbers();
            playerNames = ResultFragmentArgs.fromBundle(requireArguments()).getPlayerNames();

        } catch (Exception e) {
            Log.d("DEBUG - RESULT FRAGMENT", "EXCEPTION: NO BUNDLE");
        }
        mainPlayerNumbers = new int[numPlayers][numNumbers];
        // Create 2-D array
        for (int row = 0; row < numPlayers; row++) {
            for (int col = 0; col < numNumbers; col++) {
                mainPlayerNumbers[row][col] = tempArray[iterator];
                iterator++;
            }
        }
        //----
        int winningNum = unique(mainPlayerNumbers, numPlayers, numNumbers);
        String mainText = "", subText = "";
        if(winningNum == -1) { // NO PLAYER WON
            Log.d("DEBUG", "NO UNIQUE");
            mainText = "No Winners!";
            subText = "There were no unique numbers";
        } else { // PLAYER WON
            int ans[] = linearSearch(mainPlayerNumbers, winningNum);
            Log.d("DEBUG - RESULT FRAGMENT","Element found at index: " + Arrays.toString(ans));
            Log.d("DEBUG - RESULT FRAGMENT","Player Who Won: " + String.valueOf(ans[0] + 1) + " With Number: " + String.valueOf(mainPlayerNumbers[ans[0]][ans[1]]));

            //Display the winning player and number:
            mainText = "Winning Player: " + playerNames[ans[0]];
            subText = "The winning number was: " + String.valueOf(mainPlayerNumbers[ans[0]][ans[1]]);
        }
        //Change the display text
        winningPlayerText.setText(mainText);
        winningNumberText.setText(subText);
        //Move to scoreboard
        scoreboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("DEBUG - RESULT FRAGMENT", "SCOREBOARD BUTTON HIT");
                NavDirections action = ResultFragmentDirections.actionResultFragmentToScoreboardFragment(tempArray, numPlayers, numNumbers, playerNames);
                Navigation.findNavController(view).navigate(action);
            }
        });
        return view;
    }
}