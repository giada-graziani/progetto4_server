package com.example;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.Socket;
import java.util.Random;

public class MioThread extends Thread{
    Socket s;
    public MioThread(Socket s){
        this.s=s;
    }

    public void run(){
        try {
            BufferedReader in= new BufferedReader(new InputStreamReader(s.getInputStream()));
            DataOutputStream out = new DataOutputStream(s.getOutputStream());


            String numero;
            Random random = new Random();
            String messaggio;
            
            String scelta;
            do{
                int numeroDaIndovinare = random.nextInt(100);
                int tentativi=0;

                scelta=in.readLine();
                if(scelta.equals("no")){
                    messaggio="n";
                    out.writeBytes(messaggio+"\n");
                }
                else{
                    messaggio="s";
                    out.writeBytes(messaggio+"\n");

                    do{ 
                        numero = in.readLine();
                        int numeroInt = Integer.parseInt(numero);
                        if(numeroInt<0 || numeroInt>100){
                            messaggio="e";
                            out.writeBytes(messaggio + "\n");
                        }else{
                            if(numeroDaIndovinare == numeroInt){
                                messaggio="=";
                                tentativi++;
                                String tentativiS = Integer.toString(tentativi);
                                out.writeBytes(messaggio + "\n");
                                out.writeBytes(tentativiS + "\n");
                            }
                            else if(numeroDaIndovinare > numeroInt){
                                messaggio="<";
                                tentativi++;
                                out.writeBytes(messaggio + "\n");
                            }
                            else{
                                messaggio=">";
                                tentativi++;
                                out.writeBytes(messaggio + "\n");
                            }
                        }
                    }while(!messaggio.equals("="));
                }
            }while(!messaggio.equals("n"));
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
