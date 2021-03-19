package com.example.tictactoe;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class MultiPlayerOnlineFragment extends Fragment {

    ControllerMultiPlayerOnline ctrlOnline;

    int i;

    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private Button btn5;
    private Button btn6;
    private Button btn7;
    private Button btn8;
    private Button btn9;
    private Button btnHome;
    private TextView textViewTimer;


    public MultiPlayerOnlineFragment() {
        // Required empty public constructor
    }


    public MultiPlayerOnlineFragment(ControllerMultiPlayerOnline c){
        ctrlOnline = c;
    }


    public void setPlayer(int i ){
        this.i = i;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {

                ctrlOnline.popUp();

            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);

    }




    @Override
    public void onStart() {
        super.onStart();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_multi_playr_online, container, false);

        findButton(view);

        if(i==2){
            ctrlOnline.disableButton();
        } else {
            ctrlOnline.timer();
        }


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = "0";
                ctrlOnline.actionButton(btn1, view, s);
            }
        });


        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = "1";
                ctrlOnline.actionButton(btn2, view, s);
            }
        });


        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = "2";
                ctrlOnline.actionButton(btn3, view, s);
            }
        });


        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = "3";
                ctrlOnline.actionButton(btn4, view, s);
            }
        });


        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = "4";
                ctrlOnline.actionButton(btn5, view, s);
            }
        });


        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = "5";
                ctrlOnline.actionButton(btn6, view, s);
            }
        });


        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = "6";
                ctrlOnline.actionButton(btn7, view, s);
            }
        });


        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = "7";
                ctrlOnline.actionButton(btn8, view, s);
            }
        });


        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = "8";
                ctrlOnline.actionButton(btn9, view, s);
            }
        });



        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                ctrlOnline.returnHome(fr);
            }
        });



        ctrlOnline.recive();


        return view;
    }



    public Button getBtn1() {
        return btn1;
    }

    public Button getBtn2() {
        return btn2;
    }

    public Button getBtn3() {
        return btn3;
    }

    public Button getBtn4() {
        return btn4;
    }

    public Button getBtn5() {
        return btn5;
    }

    public Button getBtn6() {
        return btn6;
    }

    public Button getBtn7() {
        return btn7;
    }

    public Button getBtn8() {
        return btn8;
    }

    public Button getBtn9() {
        return btn9;
    }

    public Button getBtnHome() {
        return btnHome;
    }




    public int idByname(String nameButton){
        int id = getResources().getIdentifier(nameButton, "id", getActivity().getPackageName());
        return id;
    }


    public TextView getTextViewTimer() {
        return textViewTimer;
    }

    public void findButton(View view){

        btn1=(Button)view.findViewById(R.id.button0);
        btn2=(Button)view.findViewById(R.id.button1);
        btn3=(Button)view.findViewById(R.id.button2);
        btn4=(Button)view.findViewById(R.id.button3);
        btn5=(Button)view.findViewById(R.id.button4);
        btn6=(Button)view.findViewById(R.id.button5);
        btn7=(Button)view.findViewById(R.id.button6);
        btn8=(Button)view.findViewById(R.id.button7);
        btn9=(Button)view.findViewById(R.id.button8);
        btnHome=(Button)view.findViewById(R.id.buttonHome);
        textViewTimer=(TextView)view.findViewById(R.id.textViewTimer);

    }

    public FragmentTransaction getFragment(){

        FragmentTransaction fr = getFragmentManager().beginTransaction();
        return fr;
    }



}