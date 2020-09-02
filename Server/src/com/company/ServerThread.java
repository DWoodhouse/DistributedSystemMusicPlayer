package com.company;

import java.io.*;
import java.net.*;
import java.io.File;
import java.util.Arrays;

/**
 * This thread is responsible to handle client connection.
 *
 * @author www.codejava.net
 */
public class ServerThread extends Thread {
    private Socket socket;

    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            DataInputStream clientIn = new DataInputStream(socket.getInputStream());
            DataOutputStream serverOut = new DataOutputStream(socket.getOutputStream());

            Login.main(clientIn, serverOut);

            FileSelector.main(clientIn, serverOut);

            clientIn.close();
            socket.close();
            System.out.println("Connection ended");
        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}