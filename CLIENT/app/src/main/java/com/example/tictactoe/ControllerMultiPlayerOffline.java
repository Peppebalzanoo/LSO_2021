package com.example.tictactoe;

import android.graphics.Color;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import androidx.fragment.app.FragmentTransaction;

public class ControllerMultiPlayerOffline {

    ControllerMultiPlayerOffline ctrlMultiPlayerOffline;
    MultiPlayerOfflineFragment mpof;


    int [] gamestate= {2,2,2,2,2,2,2,2,2};

    int [][]winningPosition = {
            {0,1,2},{3,4,5},{6,7,8}, //Righe
            {0,3,6},{1,4,7},{2,5,8}, //Colonne
            {0,4,8},{2,4,6}      //Diagonali
    };

    private int playerOneScoreCount,playerTwoScoreCount,rountCount;
    boolean activePlayer;


    public void initialize(){
        rountCount = 0;
        playerOneScoreCount = 0;
        playerTwoScoreCount = 0;
        activePlayer = true;

    }


    public void returnHome(FragmentTransaction fr){

        fr.replace(R.id.fragmentAcor, new HomeFragment());
        fr.commit();

    }


    public void playAgain(){
        rountCount = 0;
        activePlayer = true;
        for(int i = 0; i < mpof.getButtons().length; i++){
            gamestate[i] = 2;
            mpof.getButtons()[i].setEnabled(true);
            mpof.getButtons()[i].setText("");
        }
        mpof.getT().setText("");
        mpof.getT().setVisibility(View.INVISIBLE);
    }



    public void setFragment(FragmentTransaction fr, ControllerMultiPlayerOffline c){

        ctrlMultiPlayerOffline = c;

        mpof = new MultiPlayerOfflineFragment(ctrlMultiPlayerOffline);

        fr.replace(R.id.fragmentAcor, mpof);
        fr.commit();

    }



    public void updateButton(View btn){


        if (!((Button) btn).getText().toString().equals("")) {
            return;
        }
        String buttonID = btn.getResources().getResourceEntryName(btn.getId()); //btn_X
        int gameStatePointer = Integer.parseInt(buttonID.substring(buttonID.length() - 1, buttonID.length())); //X

        if (activePlayer) {
            ((Button) btn).setText("X");
            ((Button) btn).setEnabled(false);
            ((Button) btn).setTextColor(Color.parseColor("#261E1C"));
            gamestate[gameStatePointer] = 0;
        } else {
            ((Button) btn).setText("O");
            ((Button) btn).setEnabled(false);
            ((Button) btn).setTextColor(Color.parseColor("#d4d4d4"));
            gamestate[gameStatePointer] = 1;
        }
        rountCount++;
        if (checkWinner()) {
            if (activePlayer) {
                playerOneScoreCount++;

                mpof.getT().setText("VITTORIA X");
                mpof.getT().setVisibility(View.VISIBLE);
                Animation anim = AnimationUtils.loadAnimation(btn.getContext(),R.anim.animation_textviewresult);
                mpof.getT().startAnimation(anim);

                updatePlayerScore();
                disableButton();
            } else {
                playerTwoScoreCount++;

                mpof.getT().setText("VITTORIA O");
                mpof.getT().setVisibility(View.VISIBLE);
                Animation anim = AnimationUtils.loadAnimation(btn.getContext(), R.anim.animation_textviewresult);
                mpof.getT().startAnimation(anim);

                updatePlayerScore();
                disableButton();
                //playAgain();
            }
        } else if (rountCount == 9) {
            mpof.getT().setText("PAREGGIO");
            mpof.getT().setVisibility(View.VISIBLE);
            Animation anim = AnimationUtils.loadAnimation(btn.getContext(),R.anim.animation_textviewresult);
            mpof.getT().startAnimation(anim);

            disableButton();
        } else {
            activePlayer = !activePlayer;
        }


    }


    public boolean checkWinner(){
        boolean winnerResult = false;
        for(int[]winningPosiiton: winningPosition){
            if(gamestate[winningPosiiton[0]] == gamestate[winningPosiiton[1]]
                    && gamestate[winningPosiiton[1]] == gamestate[winningPosiiton[2]]
                    &&gamestate[winningPosiiton[0]]!=2){
                winnerResult = true;
            }
        }
        return winnerResult;
    }

    public void updatePlayerScore(){
        mpof.getPlayerOneScore().setText(Integer.toString(playerOneScoreCount));
        mpof.getPlayerTwoScore().setText(Integer.toString(playerTwoScoreCount));
    }


    public void disableButton(){
        mpof.getButtons()[0].setEnabled(false);
        mpof.getButtons()[1].setEnabled(false);
        mpof.getButtons()[2].setEnabled(false);
        mpof.getButtons()[3].setEnabled(false);
        mpof.getButtons()[4].setEnabled(false);
        mpof.getButtons()[5].setEnabled(false);
        mpof.getButtons()[6].setEnabled(false);
        mpof.getButtons()[7].setEnabled(false);
        mpof.getButtons()[8].setEnabled(false);
    }

}
