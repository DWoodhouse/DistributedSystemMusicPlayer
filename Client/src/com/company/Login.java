package com.company;
import javax.sound.sampled.AudioInputStream;
import java.net.*;
import java.io.*;

public class Login {

    public static void main(DataInputStream dataInputStream, DataOutputStream dataOutputStream) {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        try {
            String string = "", string2 = "";
            boolean loggedIn = false;

            while (true) {
                string2 = dataInputStream.readUTF();
                System.out.println(string2);
                if(string2.equals("Login Successful")){
                    loggedIn = true;
                    break;
                }
                if(string2.equals("Invalid username try again")||string2.equals("Invalid password try again")){
                    string = dataInputStream.readUTF();
                    System.out.println(string);
                }
                string = br.readLine();
                dataOutputStream.writeUTF(string);
                dataOutputStream.flush();
            }
        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}