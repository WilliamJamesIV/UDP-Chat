/**
*	UDP Client Program
*	Connects to a UDP Server
*	Receives a line of input from the keyboard and sends it to the server
*	Receives a response from the server and displays it.
*
*	@author: William James
@	version: 2.1
*/

import java.io.*;
import java.net.*;

class UDPClient {
    public static void main(String args[]) throws Exception
    {

      BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

      DatagramSocket clientSocket = new DatagramSocket(9876);

      InetAddress IPAddress = InetAddress.getByName("127.0.0.1");

      byte[] sendData = new byte[1024];
      byte[] receiveData = new byte[1024];
      int state = 0;
      String message = "Hello Red";
      String response = "";
      DatagramPacket sendPacket = null;
      DatagramPacket receivePacket = null;
/*
      switch(state){

        case 0:
          sendData = message.getBytes();
          sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
          clientSocket.send(sendPacket);
          receivePacket = new DatagramPacket(receiveData, receiveData.length);
          clientSocket.receive(receivePacket);
          response = new String(receivePacket.getData());
          System.out.println("Response is " + response);
          if (response.substring(0,3).equals("100")){
            state = 1; //You are first client.  wait for second client to connect
            System.out.println("You are first client.  wait for second client to connect");
            System.out.println("state = "+ state+ " IP = "+ IPAddress);
          }
          else if (response.substring(0,3).equals("200")){
            state = 2; //you are second client.  Wait for message from first client
            System.out.println("you are second client.  Wait for message from first client");
            System.out.println("state = "+ state+ " IP = "+ IPAddress);
            break;
          }
        case 1:


        case 2:
      }
*/
///*
      //String sentence = inFromUser.readLine();

      sendData = message.getBytes();
      sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
      clientSocket.send(sendPacket);

      while (state == 0){
        receivePacket = new DatagramPacket(receiveData, receiveData.length);
        clientSocket.receive(receivePacket);
        response = new String(receivePacket.getData());
        switch(state){
          case 0:
            if(response.substring(0,3).equals("100")){
                state =1;
                System.out.println(state);
            }
            else if(response.substring(0,3).equals("200")){
                state = 2;
                System.out.println(state);
            }

        }
      }

//*/
      //System.out.println("FROM SERVER:" + modifiedSentence);
      clientSocket.close();
      }
}

/*
Variables:
 BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
 DatagramSocket clientSocket = new DatagramSocket();
 InetAddress IPAddress = InetAddress.getByName("localhost");
 byte[] sendData = new byte[1024];
 byte[] receiveData = new byte[1024];
 int state = 0;
 String message = "Hello Red";
 String response = "";
 DatagramPacket sendPacket = null;
 DatagramPacket receivePacket = null;
Main loop:
 while (state < 3){
 sendData = new byte[1024];
 receiveData = new byte[1024];
 switch (state){
 case 0: // send initial message to server and wait for response
 …
 if (response.substring(0,3).equals("100")) {
 state = 1; //You are first client. wait for second client to connect
 }
 else if (response.substring(0,3).equals("200")){
 state = 2; //you are second client. Wait for message from first client
 }
 break;
 case 1: // Waiting for notification that the second client is ready
//get message from user and send it to server
 state = 2; //transition to state 2: chat mode
 break;
 case 2:
 //Chat mode
Red Client
 //Chat mode
 //receive message from other client
 //check for Goodbye message
 if (response.length()>=7 && response.substring(0,7).equals("Goodbye")){
 state = 3; //prepare to exit the while loop
 break;
 }
 //if not Goodbye, get next message from user and send it;

 //stay in state 2
 break;
 } //end switch
 } // end while
 //close the socket
*/
