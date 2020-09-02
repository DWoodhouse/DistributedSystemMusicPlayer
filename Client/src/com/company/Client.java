package com.company;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.net.*;
import java.io.*;
import java.util.Arrays;
import java.util.Base64;

class Client {

    public static void main(String args[]) {
        try {
            Socket socket = new Socket("localhost", 3333);
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

            Login.main(dataInputStream, dataOutputStream);

            FileSelector.main(dataInputStream, dataOutputStream);

            System.out.println("Connection Lost" );
            dataOutputStream.close();
            socket.close();
        }
        catch(Exception ex){
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    public void connection(){

    }
}