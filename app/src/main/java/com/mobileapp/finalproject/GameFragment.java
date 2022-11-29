package com.mobileapp.finalproject;
/**
 * THINGS TO FINISH/ADD
 * Not allowed to enter dupe numbers -- DONE
 * send array to result fragment
 * reorganize array and confirm with user about numbers before switching to new player -- DONE
 */

import android.content.Intent;
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
import android.widget.Toast;

import java.util.Arrays;


public class GameFragment extends Fragment {
    View view;
    int[][] mainPlayerNumbers;
    int incrementPlayer = 1, incrementNumber = 1, numPlayers = 2, numNumbers = 1, displayMode = 1; // 1 = main display | 0 = confirm display
    Button enterButton, confirm_sendButton, confirm_rejectButton;
    EditText enterNumbers;
    TextView playerName, playerNumberDisplay, confirm_confirmMessage, confirm_playerNumbers;

    static void sortRowWise(int m[][]) {
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[i].length; j++) {
                for (int k = 0; k < m[i].length - j - 1; k++) {
                    if (m[i][k] > m[i][k + 1]) {
                        int t = m[i][k];
                        m[i][k] = m[i][k + 1];
                        m[i][k + 1] = t;
                    }
                }
            }
        }
    }





    public void testPrintToLog(){
        String log = "\nCurrent Player: " + incrementPlayer + "\nCurrent Number: " + incrementNumber + "\n" + Arrays.deepToString(mainPlayerNumbers);
        Log.d("DEBUG", log);
    }

    public boolean testForDupe(int numEntered){
        for(int i = 0; i < numNumbers; i++) {
            if (mainPlayerNumbers[incrementPlayer - 1][i] == numEntered) { return true; }
        }
        return false;
    }

    public void switchDisplay(){
        if(displayMode == 1) {
            displayMode = 0;
            enterNumbers.setAlpha(0);
            enterButton.setAlpha(0);
            playerName.setAlpha(0);
            playerNumberDisplay.setAlpha(0);
            confirm_sendButton.setAlpha(1);
            confirm_rejectButton.setAlpha(1);
            confirm_confirmMessage.setAlpha(1);
            confirm_playerNumbers.setAlpha(1);
        } else {
            displayMode = 1;
            enterNumbers.setAlpha(1);
            enterButton.setAlpha(1);
            playerName.setAlpha(1);
            playerNumberDisplay.setAlpha(1);
            confirm_sendButton.setAlpha(0);
            confirm_rejectButton.setAlpha(0);
            confirm_confirmMessage.setAlpha(0);
            confirm_playerNumbers.setAlpha(0);
        }

    }

    public void testForProgression(){
        // GET RID OF :
        if(incrementNumber-1 == numNumbers) {
            switchDisplay();
            sortRowWise(mainPlayerNumbers);
            String temp = "";
            for(int i = 0; i < numNumbers;i++) {
                temp = temp + mainPlayerNumbers[incrementPlayer - 1][i] + " ";
            }
            confirm_playerNumbers.setText(temp);

        }
    }
    public void addNumToDisplay(int newNum){
        int tempIncrement = 0;
        String displayText = playerNumberDisplay.getText().toString();
        String temp = "";
        for(int i = 0; i < incrementNumber-1; i++) {
            temp = temp + mainPlayerNumbers[incrementPlayer-1][i] + " ";
            tempIncrement++;
        }
        for(int i = tempIncrement; i < numNumbers; i++) {
            temp = temp + "X ";
        }
        playerNumberDisplay.setText(temp);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_game, container, false);

        //Main UI Elements
        enterNumbers = view.findViewById(R.id.enterNumber);
        enterButton = view.findViewById(R.id.enterNumberButton);
        playerName = view.findViewById(R.id.playerName);
        playerNumberDisplay = view.findViewById(R.id.playerNumberDisplay);
        //Confirm UI Elements
        confirm_sendButton = view.findViewById(R.id.confirm_sendButton);
        confirm_rejectButton = view.findViewById(R.id.confirm_rejectButton);
        confirm_confirmMessage = view.findViewById(R.id.confirm_confirmMessage);
        confirm_playerNumbers = view.findViewById(R.id.confirm_playerNumbers);

        //Display the main UI
        enterNumbers.setAlpha(1);
        enterButton.setAlpha(1);
        playerName.setAlpha(1);
        playerNumberDisplay.setAlpha(1);
        //Hide confirm UI
        confirm_sendButton.setAlpha(0);
        confirm_rejectButton.setAlpha(0);
        confirm_confirmMessage.setAlpha(0);
        confirm_playerNumbers.setAlpha(0);


        try {
            numPlayers = GameFragmentArgs.fromBundle(requireArguments()).getPlayerNum();
            numNumbers = GameFragmentArgs.fromBundle(requireArguments()).getInputNum();
        } catch (Exception e) {
            Log.d("DEBUG", "EXCEPTION: NO BUNDLE");
        }
        mainPlayerNumbers = new int[numPlayers][numNumbers];
        Log.d("DEBUG", "NumPlayers: " + numPlayers +"\nNumNumbers: " + numNumbers);

        //Set up display for numbers
        String temp = "";
        for(int i = 0; i < numNumbers;i++) {
            temp = temp + "X ";
        }
        playerNumberDisplay.setText(temp);
        //-----------


        confirm_rejectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("DEBUG", "REJECT BUTTON HIT");
                if(displayMode == 0) {
                    incrementNumber = 1;
                    String temp = "";
                    for(int i = 0; i < numNumbers;i++) {
                        temp = temp + "X ";
                    }
                    playerNumberDisplay.setText(temp);
                    switchDisplay();
                    for(int i = 0; i < numNumbers;i++) {
                        mainPlayerNumbers[incrementPlayer - 1][i] = 0;
                    }
                }
            }
        });
        confirm_sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("DEBUG", "SEND BUTTON HIT");
                if(displayMode == 0) {
                    if(incrementPlayer == numPlayers) { // If its on the last player
                        sortRowWise(mainPlayerNumbers);
                        int[] tempArray = new int[numPlayers*numNumbers];
                        int i = 0;
                        for (int row = 0; row < numPlayers; row++) {
                            for (int col = 0; col <numNumbers; col++) {
                                tempArray[i] = mainPlayerNumbers[row][col];
                                i++;
                            }
                        }
                        NavDirections action = GameFragmentDirections.actionGameFragmentToResultFragment(tempArray, numPlayers, numNumbers);
                        Navigation.findNavController(view).navigate(action);
                    } else { // If its not on the last player
                        incrementPlayer++;
                        incrementNumber = 1;
                        String temp = "Player " + String.valueOf(incrementPlayer);
                        playerName.setText(temp);
                        temp = "";
                        for (int i = 0; i < numNumbers; i++) {
                            temp = temp + "X ";
                        }
                        playerNumberDisplay.setText(temp);
                        switchDisplay();
                    }
                }
            }
        });

        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("DEBUG", "GAME FRAGMENT: Number Entered");
                if (enterNumbers.length() == 0)
                    Toast.makeText(getActivity(), "Please enter a number greater than 0!", Toast.LENGTH_SHORT).show();
                else {
                    Log.d("DEBUG", "Number entered: " + enterNumbers.getText().toString());
                    if(testForDupe(Integer.parseInt(enterNumbers.getText().toString()))) { //true
                        Toast.makeText(getActivity(), "Do not enter duplicate numbers!", Toast.LENGTH_SHORT).show();
                    } else { //false
                        mainPlayerNumbers[incrementPlayer - 1][incrementNumber - 1] = Integer.parseInt(enterNumbers.getText().toString());
                        incrementNumber++;
                        testPrintToLog();
                        addNumToDisplay(Integer.parseInt(enterNumbers.getText().toString()));
                        testForProgression();
                    }
                    enterNumbers.setText("");
                }
            }
        });

        return view;
    }
}