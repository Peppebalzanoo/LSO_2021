package com.example.tictactoe;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
    CountDownTimer countDownTimer = null;
    CountDownTimer problemTimer = null;

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
       timerProblem();

   }

    final String[] read = {null};

   public void recive(){

       t = new Thread(new Runnable() {
           @Override
           public void run() {
               while(true){
                   try{

                       read[0] = clientConnection.rec();
                       stopTimer(problemTimer);

                       if(read[0].equals("vittoria")){

                           upgradeTextView(read[0], mpof.getBtnHome());

                       } else if(read[0].equals("sconfitta")){

                           upgradeTextView(read[0], mpof.getBtnHome());

                       } else if(read[0].equals("pareggio")) {

                           upgradeTextView(read[0], mpof.getBtnHome());

                       }else if(read[0].equals("vfd")){

                           upgradeTextView("Avversario disconnesso\nVittoria", mpof.getBtnHome());

                       }else if(read[0].equals("error")){

                           activity.runOnUiThread(new Runnable() {
                               public void run() {

                                   Toast.makeText(context, "Errore di connessione...", Toast.LENGTH_SHORT).show();
                               }
                           });
                           fr = mpof.getFragment();
                           returnHome(fr);

                       }else{
                           String nameButton = "button"+read[0];

                           int resId = mpof.idByname(nameButton);


                           if(resId==mpof.getBtn1().getId()){
                               upgradeButton(mpof.getBtn1());
                               timer();
                           } else if(resId==mpof.getBtn2().getId()){
                               upgradeButton(mpof.getBtn2());
                               timer();
                           }else if(resId==mpof.getBtn3().getId()){
                               upgradeButton(mpof.getBtn3());
                               timer();
                           }else if(resId==mpof.getBtn4().getId()){
                               upgradeButton(mpof.getBtn4());
                               timer();
                           }else if(resId==mpof.getBtn5().getId()){
                               upgradeButton(mpof.getBtn5());
                               timer();
                           }else if(resId==mpof.getBtn6().getId()){
                               upgradeButton(mpof.getBtn6());
                               timer();
                           }else if(resId==mpof.getBtn7().getId()){
                               upgradeButton(mpof.getBtn7());
                               timer();
                           }else if(resId==mpof.getBtn8().getId()){
                               upgradeButton(mpof.getBtn8());
                               timer();
                           }else if(resId==mpof.getBtn9().getId()){
                               upgradeButton(mpof.getBtn9());
                               timer();
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


    public void stopTimer(CountDownTimer timer){
        if(timer !=  null){
            timer.cancel();
        }
    }



    public void returnHome(FragmentTransaction fr){

        clientConnection.disconnect();
        if(countDownTimer !=  null){
            countDownTimer.cancel();
        }
        stopTimer(problemTimer);

        fr.replace(R.id.fragmentAcor, new HomeFragment());
        fr.commit();

    }

    public void returnHomeNoConnection(FragmentTransaction fr){

        clientConnection.disconnect();
        fr.replace(R.id.fragmentAcor, new HomeFragment());
        fr.commit();
        stopTimer(problemTimer);

    }


    public void popUp(){
        AlertDialog.Builder builder = new AlertDialog.Builder(mpof.getActivity());
        builder.setMessage("Sei sicuro di voler uscire? ")
                .setPositiveButton("voglio uscire", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        clientConnection.disconnect();
                        if(countDownTimer !=  null){
                            countDownTimer.cancel();
                        }
                        stopTimer(problemTimer);
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
                if(result.equals("Avversario disconnesso\nVittoria")){
                    t.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                }
                t.setText(result.toUpperCase());
                t.setVisibility(View.VISIBLE);
                startAnimationTextViewResult(t);
                disableButton();
                if(countDownTimer!=null){
                    countDownTimer.cancel();
                }
                stopTimer(problemTimer);
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
        countDownTimer.cancel();
        mpof.getTextViewTimer().setVisibility(View.INVISIBLE);
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




public void timer(){

    final int[] sec = {31};


    mpof.getActivity().runOnUiThread(new Runnable() {
        @Override
        public void run() {

            countDownTimer = new CountDownTimer(30000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    sec[0] = sec[0] - 1;
                    updateTimerText((int)sec[0]);
                }

                @Override
                public void onFinish() {

                    activity.runOnUiThread(new Runnable() {
                        public void run() {

                            Toast.makeText(context, "Sconfitta per inattività", Toast.LENGTH_SHORT).show();
                        }
                    });

                    fr = mpof.getFragment();
                    returnHome(fr);
                }
            };

            countDownTimer.start();

        }
    });



}


public void updateTimerText(int secondLeft){


        mpof.getActivity().runOnUiThread(new Runnable() {
        @Override
        public void run() {

            String pippo = String.valueOf(secondLeft);

            mpof.getTextViewTimer().setText(pippo);
            mpof.getTextViewTimer().setVisibility(View.VISIBLE);

        }
    });



}


    public void pippo(FragmentTransaction fr ){

        if(!clientConnection.getSocket().isClosed()){
            returnHome(fr);
        } else {
            fr.replace(R.id.fragmentAcor, new HomeFragment());
            fr.commit();
        }

    }




    public void timerProblem(){

        problemTimer = new CountDownTimer(45000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
             }
             @Override
             public void onFinish() {
                activity.runOnUiThread(new Runnable() {
                    public void run() {

                        Toast.makeText(context, "Oops qualcosa è andato storto...", Toast.LENGTH_SHORT).show();
                    }
                });

                fr = mpof.getFragment();
                returnHome(fr);
            }
        };

        problemTimer.start();




    }











}
