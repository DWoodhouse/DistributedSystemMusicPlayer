package com.company;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import java.io.*;

public class FileSelector {

    public static void main(DataInputStream dataInputStream, DataOutputStream dataOutputStream) {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        try {
            String string = "", string2 = "";
            boolean quit = false;
            boolean validEntry = false;
            boolean anotherSong = false;

            System.out.println("Enter the number for the song you would like to play:");

            // Loops while the list of options appear and waits until the end message is sent from server
            while (!quit) {
               while(true){
                   string2 = dataInputStream.readUTF();
                   if(string2.equals("end")){
                       break;
                   }
                   System.out.println(string2);
               }
               validEntry = false;

                // Loop waits until a valid number is selected
               while(!validEntry) {
                   string = br.readLine();
                   dataOutputStream.writeUTF(string);
                   dataOutputStream.flush();
                   string2 = dataInputStream.readUTF();
                   System.out.println(string2);
                   if(!string2.equals("Invalid number. Try again.")){
                       validEntry = true;
                       playSong(dataInputStream, dataOutputStream);         //This is when we have selected a song
                   }
               }
               System.out.println("Would you like to play another song? Type Y/N");

               // This loop handles whether the user wants to go back and play another song. Or quit the application
               anotherSong = false;
               while(!anotherSong){
                   string2 = br.readLine();
                   if(string2.equals("Y")){
                       dataOutputStream.writeUTF(string2);
                       dataOutputStream.flush();
                       anotherSong = true;
                   }
                   else if(string2.equals("N")){
                       dataOutputStream.writeUTF(string2);
                       dataOutputStream.flush();
                       quit = true;
                       anotherSong = true;
                       break;
                   }
                   else{
                       System.out.println("Invalid Entry. Try again.");
                   }
               }
            }

        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    // This method takes the song as bytes, converts to AudioInputStream then passes to the audioplayer
    private static void playSong(DataInputStream dataInputStream, DataOutputStream dataOutputStream){

        try{
        int length = dataInputStream.readInt();     //takes data from socket
        if(length>0) {
            byte[] message = new byte[length];
            dataInputStream.readFully(message, 0, message.length);      //reads the data in socket into array "message"

            AudioInputStream oAIS = new AudioInputStream(
                    new ByteArrayInputStream(message),
                    new AudioFormat(44100, 16, 2, true, false),
                    length
            );      //creates an AudioInputStream which can be used for playing the data

            AudioPlayer.main(oAIS);       //sent to music player
        }
    }
        catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}