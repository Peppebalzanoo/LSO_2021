package com.example.tictactoe;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class SinglePlayerFragment extends Fragment implements  View.OnClickListener {
    ControllerSinglePlayer controller;

    private TextView playerOneScore, playerTwoScore, playerStatus;
    private Button[] buttons = new Button[9];
    private TextView t;
    private Button btnPlayAgain, btnHome;



    public SinglePlayerFragment() {
        // Required empty public constructor
    }

    public SinglePlayerFragment(ControllerSinglePlayer c)
    {
        this.controller = c;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                controller.setFragmentHome(fr);

            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_single_player, container, false);
        playerOneScore = (TextView) view.findViewById(R.id.playerOneScore);
        playerTwoScore = (TextView) view.findViewById(R.id.playerTwoScore);
        playerStatus = (TextView) view.findViewById(R.id.playerStatus);
        t = (TextView)view.findViewById(R.id.textViewResultSinglePlayer);
        btnPlayAgain = (Button)view.findViewById(R.id.btnPlayAgainSinglePlayer);
        btnHome = (Button)view.findViewById(R.id.btnHomeSinglePlayer);

        for (int i = 0; i < buttons.length; i++) {
            String buttonID = "btn_" + i;
            int resourceID = getResources().getIdentifier(buttonID, "id", getActivity().getPackageName());
            buttons[i] = (Button) view.findViewById(resourceID);
            buttons[i].setOnClickListener((View.OnClickListener) this);
        }

        controller.initialize();

        btnPlayAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.playAgain();
            }
        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                controller.setFragmentHome(fr);
            }
        });
        return view;
    }

    @Override
    public void onClick(View v) {
        controller.upgradeButtons(v);
    }

    public TextView getPlayerStatus() {
        return playerStatus;
    }

    public Button[] getButtons() {
        return buttons;
    }

    public Button getBtnPlayAgain() {
        return btnPlayAgain;
    }

    public Button getBtnHome() {
        return btnHome;
    }

    public TextView getPlayerOneScore() {
        return playerOneScore;
    }

    public TextView getPlayerTwoScore() {
        return playerTwoScore;
    }

    public TextView getT() {
        return t;
    }
}
