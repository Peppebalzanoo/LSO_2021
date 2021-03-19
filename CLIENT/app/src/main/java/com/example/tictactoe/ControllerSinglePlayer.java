package com.example.tictactoe;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.Random;

public class ControllerSinglePlayer {

    SinglePlayerFragment spf;
    ControllerSinglePlayer csp;
    private int playerOneScoreCount, playerTwoScoreCount, rountCount;
    boolean activePlayer;

    int[] gamestate = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    int[][] winningPosition = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8},    //Righe
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},    //Colonne
            {0, 4, 8}, {2, 4, 6}               //Diagonali
    };


    public void initialize(){
        rountCount = 0;
        playerOneScoreCount = 0;
        playerTwoScoreCount = 0;
        activePlayer = true;
    }

    public void setGamestatePointer(int a, int player){
        if(player == 0){ //PLAYER ONE
            gamestate[a] = 0;
        }
        else if(player == 1) {//PLAYER TWO
            gamestate[a] = 1;
            }
        }

    public void upgradeScore(int i){
        if(i == 0) //UPGRADE PLAYERONE SCORE
            playerOneScoreCount++;
        else if(i ==1){
            playerTwoScoreCount++;
        }
    }

    public void upgradeRountCount(){
        rountCount++;
    }

    public int getRountCount() {
        return rountCount;
    }

    public int getPlayerOneScoreCount(){
        return playerOneScoreCount;
    }

    public int getPlayerTwoScoreCount(){
        return playerTwoScoreCount;
    }

    public int multiplayer(){
        int i = 0;
        ArrayList<Integer> vuote = new ArrayList<Integer>();
        for(i = 0; i <9 ; i++){
            if(gamestate[i] == 2){
                vuote.add(i);
            }
        }
        Random rand = new Random();
        int index = vuote.get(rand.nextInt(vuote.size()));
        return index;
    }

    public void setFragmentHome(FragmentTransaction fr){
        fr.replace(R.id.fragmentAcor, new HomeFragment());
        fr.commit();
    }

    public void setFragmentSinglePlayer(FragmentTransaction fr, ControllerSinglePlayer c) {
        this.csp = c;
        spf = new SinglePlayerFragment(csp);
        fr.replace(R.id.fragmentAcor, spf);
        fr.commit();
    }


    public void playAgain(){
        rountCount = 0;
        activePlayer = true;
        for (int i = 0; i < gamestate.length; i++) {
            gamestate[i] = 2;
            spf.getButtons()[i].setText("");
            spf.getButtons()[i].setEnabled(true);
        }
        spf.getT().setText("");
        spf.getT().setVisibility(View.INVISIBLE);
    }

    public boolean checkWinner() {
        boolean winnerResult = false;
        for (int[] winningPosiiton : winningPosition) {
            if (gamestate[winningPosiiton[0]] == gamestate[winningPosiiton[1]]
                    && gamestate[winningPosiiton[1]] == gamestate[winningPosiiton[2]]
                    && gamestate[winningPosiiton[0]] != 2) {
                winnerResult = true;
            }
        }
        return winnerResult;
    }

    public void upgradeButtons(View v){
        Log.i("TAG", "button is clicked");
        if (!((Button) v).getText().toString().equals("")) {
            return;
        }
        String buttonID = v.getResources().getResourceEntryName(v.getId()); //btn_X
        int gameStatePointer = Integer.parseInt(buttonID.substring(buttonID.length() - 1, buttonID.length())); //X
        ((Button) v).setText("X");
        ((Button) v).setTextColor(Color.parseColor("#261E1C"));
        ((Button)v).setEnabled(false);
        setGamestatePointer(gameStatePointer, 0); //PLAYER ONE
        if(checkWinner()){
            upgradeScore(0); //0 PLAYER ONE
            spf.getT().setText("VITTORIA");
            spf.getT().setVisibility(View.VISIBLE);
            Animation anim = AnimationUtils.loadAnimation(v.getContext(),R.anim.animation_textviewresult);
            spf.getT().startAnimation(anim);
            updatePlayerScore();
            disableButton();
        }else{
            if((getRountCount())<= 3) {
                int index =multiplayer();
                setGamestatePointer(index, 1); //PLAYER TWO

                String s = "btn_" + index;
                int resId = spf.getResources().getIdentifier(s, "id", spf.getActivity().getPackageName());
                Button button = (Button) spf.getActivity().findViewById(resId);
                button.setText("O");
                button.setTextColor(Color.parseColor("#d4d4d4"));
                button.setEnabled(false);
                if (checkWinner()) {
                    upgradeScore(1); //1 PLAYER TWO
                    spf.getT().setText("SCONFITTA");
                    spf.getT().setVisibility(View.VISIBLE);
                    Animation anim = AnimationUtils.loadAnimation(v.getContext(), R.anim.animation_textviewresult);
                    spf.getT().startAnimation(anim);
                    updatePlayerScore();
                    disableButton();
                }
            }
            else{
                spf.getT().setText("PAREGGIO");
                spf.getT().setVisibility(View.VISIBLE);
                Animation anim = AnimationUtils.loadAnimation(v.getContext(),R.anim.animation_textviewresult);
                spf.getT().startAnimation(anim);

                disableButton();
            }
        }
        upgradeRountCount();
    }


    public void updatePlayerScore() {
        spf.getPlayerOneScore().setText(Integer.toString(getPlayerOneScoreCount()));
        spf.getPlayerTwoScore().setText(Integer.toString(getPlayerTwoScoreCount()));
    }


    public void disableButton(){
        spf.getButtons()[0].setEnabled(false);
        spf.getButtons()[1].setEnabled(false);
        spf.getButtons()[2].setEnabled(false);
        spf.getButtons()[3].setEnabled(false);
        spf.getButtons()[4].setEnabled(false);
        spf.getButtons()[5].setEnabled(false);
        spf.getButtons()[6].setEnabled(false);
        spf.getButtons()[7].setEnabled(false);
        spf.getButtons()[8].setEnabled(false);
    }

}
