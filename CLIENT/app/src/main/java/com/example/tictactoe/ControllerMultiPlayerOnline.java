package com.example.tictactoe;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.FragmentTransaction;

public class ControllerMultiPlayerOnline {




    ClientConnection clientConnection;
    Thread thread1;
    Thread t;
    Activity activity;
    Context context;
    FragmentTransaction fr;
    ControllerMultiPlayerOnline ctrlmpo;
    MultiPlayerOnlineFragment mpof;


    public ControllerMultiPlayerOnline(Activity activity, Context context, FragmentTransaction fr){
        this.activity=activity;
        this.context=context;
        this.fr=fr;
    }




   public void setConnection(ControllerMultiPlayerOnline c){

       this.ctrlmpo = c;
       clientConnection = new ClientConnection();
       thread1 = new Thread(clientConnection);

       connection();
   }





   public void connection(){

       thread1.start();
       try{

           thread1.join();

           mpof= new MultiPlayerOnlineFragment(ctrlmpo);

           clientConnection.start(ctrlmpo, fr, activity, context);
       } catch (InterruptedException e) {
           e.printStackTrace();
       }


   }


    public void setStart(int i,FragmentTransaction fr){
        setMultyPlayerOnlineFragment(fr);
        mpof.setPlayer(i);

    }


   public void setMultyPlayerOnlineFragment(FragmentTransaction fr){


       fr.replace(R.id.fragmentAcor, mpof);
       fr.commit();

   }



   public void sendMsg(String str){

       clientConnection.send(str);

   }

    final String[] read = {null};

   public void recive(){

       t = new Thread(new Runnable() {
           @Override
           public void run() {
               while(true){
                   try{

                       read[0] = clientConnection.rec();

                       if(read[0].equals("vittoria")){
                           upgradeTextView(read[0], mpof.getBtnHome());
                       } else if(read[0].equals("sconfitta")){
                           upgradeTextView(read[0], mpof.getBtnHome());
                       } else if(read[0].equals("pareggio")) {
                           upgradeTextView(read[0], mpof.getBtnHome());
                       }else if(read[0].equals("vfd")){
                           upgradeTextView("Avversario disconnesso, Vittoria", mpof.getBtnHome());
                       }else{
                           String nameButton = "button"+read[0];

                           int resId = mpof.idByname(nameButton);


                           if(resId==mpof.getBtn1().getId()){
                               upgradeButton(mpof.getBtn1());
                           } else if(resId==mpof.getBtn2().getId()){
                               upgradeButton(mpof.getBtn2());
                           }else if(resId==mpof.getBtn3().getId()){
                               upgradeButton(mpof.getBtn3());
                           }else if(resId==mpof.getBtn4().getId()){
                               upgradeButton(mpof.getBtn4());
                           }else if(resId==mpof.getBtn5().getId()){
                               upgradeButton(mpof.getBtn5());
                           }else if(resId==mpof.getBtn6().getId()){
                               upgradeButton(mpof.getBtn6());
                           }else if(resId==mpof.getBtn7().getId()){
                               upgradeButton(mpof.getBtn7());
                           }else if(resId==mpof.getBtn8().getId()){
                               upgradeButton(mpof.getBtn8());
                           }else if(resId==mpof.getBtn9().getId()){
                               upgradeButton(mpof.getBtn9());
                           }
                       }
                   } catch (NullPointerException e){
                       e.getMessage();
                   }
               }
           }
       });
       t.start();

   }


    public ClientConnection getClientConnection() {
        return clientConnection;
    }



    public void returnHome(FragmentTransaction fr){
        clientConnection.disconnect();
        fr.replace(R.id.fragmentAcor, new HomeFragment());
        fr.commit();

    }


    public void popUp(){
        AlertDialog.Builder builder = new AlertDialog.Builder(mpof.getActivity());
        builder.setMessage("Sei sicuro di voler uscire? ")
                .setPositiveButton("voglio uscire", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        clientConnection.disconnect();
                        mpof.getActivity().finish();
                    }
                })
                .setNegativeButton("ritorna alla partita",null);
        AlertDialog alert = builder.create();
        alert.show();
    }




    public void upgradeTextView(String result, Button btn){
        TextView t = mpof.getView().findViewById(R.id.textViewResult);
        mpof.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(result.equals("Avversario disconnesso, Vittoria")){
                    t.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                }
                t.setText(result.toUpperCase());
                t.setVisibility(View.VISIBLE);
                startAnimationTextViewResult(t);
                disableButton();

                btn.setVisibility(View.VISIBLE);
            }
        });
    }

    public void startAnimationTextViewResult(TextView t){
        Animation anim = AnimationUtils.loadAnimation(mpof.getContext(),R.anim.animation_textviewresult);
        t.startAnimation(anim);
    }

    public void upgradeButton(Button btn){

        mpof.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                btn.setText("O");
                btn.setTextColor(Color.parseColor("#d4d4d4"));
                enableButton();

            }
        });

    }


    public void actionButton(Button btn, View v, String str){

        btn.setText("X");
        btn.setTextColor(Color.parseColor("#261E1C"));
        disableButton();
        sendMsg(str);

    }



    public void disableButton(){

        mpof.getBtn1().setEnabled(false);
        mpof.getBtn2().setEnabled(false);
        mpof.getBtn3().setEnabled(false);
        mpof.getBtn4().setEnabled(false);
        mpof.getBtn5().setEnabled(false);
        mpof.getBtn6().setEnabled(false);
        mpof.getBtn7().setEnabled(false);
        mpof.getBtn8().setEnabled(false);
        mpof.getBtn9().setEnabled(false);

    }



    public void enableButton(){

        if(mpof.getBtn1().getText().equals("")){
            mpof.getBtn1().setEnabled(true);
        }
        if(mpof.getBtn2().getText().equals("")){
            mpof.getBtn2().setEnabled(true);
        }
        if(mpof.getBtn3().getText().equals("")){
            mpof.getBtn3().setEnabled(true);
        }
        if(mpof.getBtn4().getText().equals("")){
            mpof.getBtn4().setEnabled(true);
        }
        if(mpof.getBtn5().getText().equals("")){
            mpof.getBtn5().setEnabled(true);
        }
        if(mpof.getBtn6().getText().equals("")){
            mpof.getBtn6().setEnabled(true);
        }
        if(mpof.getBtn7().getText().equals("")){
            mpof.getBtn7().setEnabled(true);
        }
        if(mpof.getBtn8().getText().equals("")){
            mpof.getBtn8().setEnabled(true);
        }
        if(mpof.getBtn9().getText().equals("")){
            mpof.getBtn9().setEnabled(true);
        }

    }























}
