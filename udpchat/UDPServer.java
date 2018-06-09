/**
*	UDP Server Program
*	Listens on a UDP port
*	Receives a line of input from a UDP client
*	Returns an upper case version of the line to the client
*
*	@author: Michael Fahy: EDITED BY JEFF HASKELL
@	version: 2.0
*/

import java.io.*;
import java.net.*;

class UDPServer {



  public static void main(String args[]) throws Exception
    {
      int state = 0;
    DatagramSocket serverSocket = null;
    int port = 0, port1 = 0, port2 = 0;
    InetAddress IPAddress = null, IPAddress1 = null, IPAddress2 = null;
    String message = "";
    String name1 = "";
    String name2 = "";
    String response = "";
    DatagramPacket receivePacket = null;
    DatagramPacket sendPacket = null;

    byte[] receiveData = new byte[1024];
    byte[] sendData  = new byte[1024];
    byte[] messageBytes = new byte[1024];

	try
		{
			serverSocket = new DatagramSocket(9876);

		}

	catch(Exception e)
		{
			System.out.println("Failed to open UDP socket");
			System.exit(0);
		}

      receiveData = new byte[1024];
      sendData  = new byte[1024];

      while(true) {

          switch(state)  {

            case 0 : //WAIT FOR FIRST PACKET

              //FIRST PACKET - GREETING - PULL DATA
               receivePacket =
                 new DatagramPacket(receiveData, receiveData.length);

               serverSocket.receive(receivePacket);

               String sentence = new String(receivePacket.getData());

               IPAddress = receivePacket.getAddress();

               port = receivePacket.getPort();

               name1 = sentence.substring(6,sentence.length());

               System.out.println(name1 + " has signed in.");

              //CHANGE STATE
              state = 1;

              // SEND STATUS REPONSE
              String alert = "100 Client 1 (" + IPAddress + ") ("+ port + ")"
                              + "\n Greetings " + name1;

              sendData = alert.getBytes();

              sendPacket =
                new DatagramPacket(sendData, sendData.length, IPAddress, port);

              serverSocket.send(sendPacket);

              System.out.println(alert);

              case 1 : // WAIT FOR SECOND CLIENT

              //RECEIVE PACKET FROM SECOND CLIENT
              receivePacket =
               new DatagramPacket(receiveData, receiveData.length);

              serverSocket.receive(receivePacket);

              //PULL DATA
              sentence = new String(receivePacket.getData());

              IPAddress1 = receivePacket.getAddress();

              port1 = receivePacket.getPort();

              name2 = sentence.substring(6,sentence.length());

              //SEND 200 STATUS CODE TO BOTH CLIENTS
              alert = "200 Client 2 (" + IPAddress + ") (" + port + ")" +
                      "\n Welcome to the party" + name2
                      + name2 + " Go first";

              sendData = alert.getBytes();

              //CLIENT 1
              sendPacket =
                 new DatagramPacket(sendData, sendData.length, IPAddress, port);

              serverSocket.send(sendPacket);

              //CLIENT 2
              sendPacket =
                 new DatagramPacket(sendData, sendData.length, IPAddress1, port1);

              serverSocket.send(sendPacket);

              //CHANGE STATE
              state = 2;

            case 2 : // ALLOW CHAT

            receivePacket =
              new DatagramPacket(receiveData, receiveData.length);

            serverSocket.receive(receivePacket);

            sentence = new String(receivePacket.getData());

            IPAddress2 = receivePacket.getAddress();

            port2 = receivePacket.getPort();

            while(!sentence.equals("Goodbye"))
            {
            if(IPAddress2 == IPAddress) // SEND FROM CLIENT 1 TO 2
             {
               sendData = sentence.getBytes();

               sendPacket =
               new DatagramPacket(sendData, sendData.length, IPAddress1, port1);

               serverSocket.send(sendPacket);
             }
             else // SEND FROM CLIENT 2 TO 1
             {
               sendData = sentence.getBytes();

               sendPacket =
               new DatagramPacket(sendData, sendData.length, IPAddress, port);

               serverSocket.send(sendPacket);
             }
           }
          }
        }
    }
}
