package com.example.tictactoe;

import android.app.Activity;
import android.content.Context;
import android.os.StrictMode;
import android.widget.Toast;

import androidx.fragment.app.FragmentTransaction;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;


public class ClientConnection implements Runnable{


    private static final int PORT = ;
    private static final String SERVER_IP = ;
    private Socket socket;
    private DataOutputStream o;
    private BufferedReader in;
    private Thread t1;


    @Override
    public void run() {

        try {

            socket = new Socket();

            socket.connect(new InetSocketAddress(ClientConnection.N, ClientConnection.N), 500);

            o = new DataOutputStream(socket.getOutputStream());

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        } catch (UnknownHostException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {

            e1.printStackTrace();

        }

    }


    public Socket getSocket() {
        return socket;
    }

    public BufferedReader getIn() {
        return in;
    }

    public Thread getT1() {
        return t1;
    }

    public void send(String str){

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        t1 = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    o.writeBytes(str);
                    o.flush();
                }  catch (IOException e) {

                    e.printStackTrace();

                }

                t1.interrupt();
            }
        });
        t1.start();
    }




    public void start(ControllerMultiPlayerOnline c, FragmentTransaction fr, Activity a, Context context){

        t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                while(!Thread.currentThread().isInterrupted()){
                    try{

                        String read = in.readLine().replace("\n", "");
                        if(read.equals("X")){

                            c.setStart(1, fr);

                            t1.interrupt();
                            break;
                        } else if(read.equals("O")){

                            c.setStart(2, fr);
                            t1.interrupt();
                            break;
                        } else {
                            c.returnHomeNoConnection(fr);
                            t1.interrupt();
                        }

                    } catch (IOException e) {


                        if(!socket.isClosed()){
                            c.returnHomeNoConnection(fr);
                            a.runOnUiThread(new Runnable() {
                                public void run() {

                                    Toast.makeText(context, "Errore di connessione...", Toast.LENGTH_SHORT).show();
                                }
                            });
                            break;
                        }

                    }catch (NullPointerException e){

                        if(!socket.isClosed()){

                            c.returnHomeNoConnection(fr);
                            a.runOnUiThread(new Runnable() {
                                public void run() {

                                    Toast.makeText(context, "Errore di connessione...", Toast.LENGTH_SHORT).show();
                                }
                            });
                            break;

                        }

                    }
                }
            }
        });
        t1.start();

     }




    public String rec(){

        try{
            return in.readLine().replace("\n", "");
        } catch (IOException e) {

            if(!socket.isClosed())
                return "error";
        }
        return null;

    }



    public void disconnect(){

        try{

            socket.shutdownInput();
            socket.shutdownOutput();
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
