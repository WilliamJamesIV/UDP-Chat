/*
partner is Jeffrey Haskell
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



class Blue {

    public static void main(String args[]) throws Exception{



      BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));

      DatagramSocket clientSocket = new DatagramSocket();

      InetAddress IPAddress = InetAddress.getByName("localhost");



      byte[] sendData = new byte[1024];

      byte[] receiveData = new byte[1024];

      int state = 0;

      String name = "Blue";

      String message = "Hello Blue";

      String response = "";

      DatagramPacket sendPacket = null;

      DatagramPacket receivePacket = null;



      //loops until state is greator than 3

      while(state <3){



        sendData = new byte[1024];



        receiveData = new byte[1024];



        switch(state){



          case 0: //SEND GREETING



          //SEND MESSAGE

          sendData = message.getBytes();



          sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);



          clientSocket.send(sendPacket);



          //RETRIEVE STATUS

          receivePacket = new DatagramPacket(receiveData, receiveData.length);



          clientSocket.receive(receivePacket);



          response = new String(receivePacket.getData());



          System.out.println(response);



          if (response.substring(0,3).equals("100")) // FIRST CLIENT

          {

              state = 1;

          }

          else if (response.substring(0,3).equals("200")) //SECOND CLIENT

          {

              state = 2;

          }

          else // SOMEBODY MESSED UP

          {

            System.out.println("Error: Unknown status code\nDisconnecting Client...");



            clientSocket.close();



            break;

          }



          case 1:// RETRIEVE SECOND CLIENT CONNECTION CONFIRMATION



          //RETRIEVE 200 MESSAGE FROM SERVER

          receivePacket = new DatagramPacket(receiveData, receiveData.length);



          clientSocket.receive(receivePacket);



          response = new String(receivePacket.getData());



          System.out.println(response);



          //CHECK STATUS

          if (response.substring(0,3).equals("200"))

            {

              receivePacket = new DatagramPacket(receiveData, receiveData.length);



              clientSocket.receive(receivePacket);



              response = new String(receivePacket.getData());



              System.out.println(response);



              state = 2; //CHANGE STATE TO CHAT MODE

            }

            else{} //SOMEBODY MESSED UP!



          case 2:



          //SEND FIRST MESSAGE // READ THE INPUT DUMMY

          System.out.print("");

          String chat = userIn.readLine();

          System.out.print("");

          sendData = chat.getBytes();



          sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);



          clientSocket.send(sendPacket);



          //ENABLE CHAT & CHECK FOR GOODBYE

          while(!response.equals("Goodbye"))

          {

            //RETRIEVE MESSAGE

            receivePacket = new DatagramPacket(receiveData, receiveData.length);



            clientSocket.receive(receivePacket);



            response = new String(receivePacket.getData());



            System.out.println(response);



            //SEND MESSAGE

            sendData = message.getBytes();



            sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);



            clientSocket.send(sendPacket);

         }



        //GOODBYE HAS BEEN ENTERED, EXIT

        clientSocket.close();



        System.out.println("Bye Bye");



        break;

      }

    }

  }

}
