package com.company;
import javax.sound.sampled.AudioInputStream;
import java.net.*;
import java.io.*;

public class Login {

    public static void main(DataInputStream clientIn, DataOutputStream serverOut) {

        try {
            String string = "";
            String string2 = "";
            String username = "admin";
            String password = "123";
            boolean loggedIn = false;

            while(!loggedIn){
                while(!string.equals(username)) {
                    serverOut.writeUTF("Enter username:");
                    string = clientIn.readUTF();
                    if (string.equals(username)) {
                        while (!string2.equals(password)) {
                            serverOut.writeUTF("Enter password:");
                            string2 = clientIn.readUTF();
                            if (string2.equals(password)) {
                                serverOut.writeUTF("Login Successful");
                                loggedIn = true;
                            } else {
                                serverOut.writeUTF("Invalid password try again");
                            }
                        }
                    } else {
                        serverOut.writeUTF("Invalid username try again");
                    }
                }
                System.out.println("client says: " + string);
            }
        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
