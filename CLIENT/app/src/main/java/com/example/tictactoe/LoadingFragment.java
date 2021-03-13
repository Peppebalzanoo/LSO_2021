package com.example.tictactoe;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class LoadingFragment extends Fragment {


    ControllerMultiPlayerOnline ctrlOnline;




    public LoadingFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_loading, container, false);

        FragmentTransaction fr = getFragmentManager().beginTransaction();

        ctrlOnline = new ControllerMultiPlayerOnline(getActivity(), getContext(), fr);

        ctrlOnline.setConnection(ctrlOnline);




        return view;
    }



}