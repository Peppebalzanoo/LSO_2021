package com.example.tictactoe;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class HomeFragment extends Fragment {


    Button btnSinglePlayer;
    Button btnMultiPlayerOffline;
    Button btnMultiPlayerOnline;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);


        btnSinglePlayer = (Button) view.findViewById(R.id.btnSinglePlayer);
        btnSinglePlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction fr = getFragmentManager().beginTransaction();
                ControllerSinglePlayer c = new ControllerSinglePlayer();
                c.setFragmentSinglePlayer(fr, c);


            }
        });


        btnMultiPlayerOffline = (Button) view.findViewById(R.id.btnMultiplayerOffline);
        btnMultiPlayerOffline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction fr = getFragmentManager().beginTransaction();
                ControllerMultiPlayerOffline c = new ControllerMultiPlayerOffline();
                c.setFragment(fr, c);



            }
        });



        btnMultiPlayerOnline = (Button) view.findViewById(R.id.btnMultiplayerOnline);
        btnMultiPlayerOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.fragmentAcor, new LoadingFragment());
                fr.commit();


            }
        });

        return view;

    }
}