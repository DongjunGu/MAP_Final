package com.mobileapp.finalproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class ScoreboardFragment extends Fragment {
    View view;
    int numPlayers = 1, numNumbers = 1, iterator = 0;
    int[] tempArray = new int[1]; int[][] mainPlayerNumbers;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_scoreboard, container, false);
        try {
            tempArray = ResultFragmentArgs.fromBundle(requireArguments()).getMainPlayerNums();
            numPlayers = ResultFragmentArgs.fromBundle(requireArguments()).getNumPlayers();
            numNumbers = ResultFragmentArgs.fromBundle(requireArguments()).getNumNumbers();
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