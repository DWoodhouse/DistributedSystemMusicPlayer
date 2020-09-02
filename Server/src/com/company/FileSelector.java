package com.company;

import java.io.*;

public class FileSelector {

    public static void main(DataInputStream clientIn, DataOutputStream serverOut) {

        String[][] songlist = {{"Bensound - Summer", "G:\\bensound-summer.wav"},{"Unknown Brain - Superhero", "G:\\Unknown Brain - Superhero.wav"},{"Disfigure - Blank", "G:\\Disfigure - Blank _NCS Release_.wav"}};
        int arrayLength = songlist.length;

            try {
                String fromClient = "";
                String string2 = "";
                int songNumberi;

                String username = "admin";
                String password = "123";
                boolean songPlaying = false;
                boolean quit = false;

                while(!quit) {
                    songPlaying = false;
                    for (int i = 0; i < arrayLength; i++) {
                        String songNumber = Integer.toString(i);
                        String songString = songNumber + " " + songlist[i][0];
                        serverOut.writeUTF(songString);
                        serverOut.flush();
                    }

                    serverOut.writeUTF("end");

                    while (!songPlaying) {
                        fromClient = clientIn.readUTF();
                        System.out.println(fromClient);
                        songNumberi = Integer.parseInt(fromClient);
                        if (songNumberi < arrayLength) {
                            String nowPlaying = "Now Playing - " + songlist[songNumberi][0];
                            serverOut.writeUTF(nowPlaying);
                            byte[] bFile = readBytesFromFile(songlist[songNumberi][1]);
                            serverOut.writeInt(bFile.length);
                            serverOut.write(bFile);
                            serverOut.flush();
                            songPlaying = true;
                        } else {
                            serverOut.writeUTF("Invalid number. Try again.");
                        }
                    }
                    fromClient = clientIn.readUTF();
                    if(fromClient.equals("N")){
                        quit = true;
                    }
                }

            } catch (IOException ex) {
                System.out.println("Server exception: " + ex.getMessage());
                ex.printStackTrace();
            }
    }
    private static byte[] readBytesFromFile(String filePath) {

        FileInputStream fileInputStream = null;
        byte[] bytesArray = null;

        try {
            File file = new File(filePath);
            bytesArray = new byte[(int) file.length()];

            //read file into bytes[]
            fileInputStream = new FileInputStream(file);
            fileInputStream.read(bytesArray);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return bytesArray;
    }
}
