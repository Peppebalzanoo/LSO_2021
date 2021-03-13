package com.example.tictactoe;

import android.graphics.Color;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;



public class MultiPlayerOfflineFragment extends Fragment implements View.OnClickListener{

    ControllerMultiPlayerOffline ctrlMultiPlayerOffline;



    private TextView playerOneScore, playerTwoScore, playerStatus;
    private Button[] buttons = new Button [9];
    private TextView t;
    private Button btnPlayAgain, btnHome;


    public TextView getPlayerOneScore() {
        return playerOneScore;
    }

    public TextView getPlayerTwoScore() {
        return playerTwoScore;
    }

    public TextView getPlayerStatus() {
        return playerStatus;
    }

    public Button[] getButtons() {
        return buttons;
    }

    public TextView getT() {
        return t;
    }



    public MultiPlayerOfflineFragment() {
        // Required empty public constructor
    }


    public MultiPlayerOfflineFragment(ControllerMultiPlayerOffline c){

        ctrlMultiPlayerOffline = c;

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                ctrlMultiPlayerOffline.returnHome(fr);

            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_multi_player_offline, container, false);

        playerOneScore = (TextView)view.findViewById(R.id.playerOneScore);
        playerTwoScore = (TextView)view.findViewById(R.id.playerTwoScore);
        playerStatus = (TextView)view.findViewById(R.id.playerStatus);
        t = (TextView)view.findViewById(R.id.textViewResultMultiPlayer);
        btnPlayAgain = (Button)view.findViewById(R.id.btnPlayAgainMultiPlayer);
        btnHome = (Button)view.findViewById(R.id.btnHomeMultiPlayer);


        for(int i = 0; i < buttons.length; i++) {
            String buttonID = "btn_" + i;
            int resourceID = getResources().getIdentifier(buttonID, "id", getActivity().getPackageName());
            buttons[i] = (Button)view.findViewById(resourceID);
            buttons[i].setOnClickListener((View.OnClickListener) this);
        }

        ctrlMultiPlayerOffline.initialize();

        btnPlayAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ctrlMultiPlayerOffline.playAgain();
            }
        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                ctrlMultiPlayerOffline.returnHome(fr);
            }
        });


        return view;
    }


    public int getID(String str){

        int resourceID = getResources().getIdentifier(str, "id", getActivity().getPackageName());

        return resourceID;

    }


    public void onClick(View btn) {
        ctrlMultiPlayerOffline.updateButton(btn);
    }

}