/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package studentclubserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;


/**
 *
 * @author A
 */



public class StudentClubServer extends Thread {
   private Socket conn;
   private int id;
   ClubSkeleton skeleton;

   public StudentClubServer (Socket sock, int id) {
      this.conn = sock;
      this.id = id;
      skeleton = new ClubSkeleton();
   }

   public void run() {
      try {
         OutputStream outSock = conn.getOutputStream();
         InputStream inSock = conn.getInputStream();
         byte clientInput[] = new byte[4096]; // up to 1024 bytes in a message.
         int numr = inSock.read(clientInput,0,4096);
         if (numr != -1) {
            //System.out.println("read "+numr+" bytes. Available: "+
            //                 inSock.available());
            int ind = numr;
            while(inSock.available()>0){
               numr = inSock.read(clientInput,ind,4096-ind);
               ind = numr + ind;
            }
            String clientString = new String(clientInput,0,ind);
            //System.out.println("read from client: "+id+" the string: "
            //                 +clientString);
            String request = clientString.substring(clientString.indexOf("{"));
            String response = skeleton.callMethod(request);
            byte clientOut[] = response.getBytes();
	    outSock.write(clientOut,0,clientOut.length);
            System.out.println("response is: "+response);
         }
         inSock.close();
         outSock.close();
         conn.close();
      } catch (IOException e) {
         System.out.println("Can't get I/O for the connection.");
      }
   }

   public static void main (String[] args) {
      Socket sock;
      int id=0;
      int portNo = 8080;
      try {
         if (args.length < 1) {
            System.out.println("Usage: java -cp classes:lib/json.jar "+
                               "ser423.StudentClubServer portNum");
            System.exit(0);
         }
         portNo = Integer.parseInt(args[0]);
         if (portNo <= 1024) portNo=8080;
         ServerSocket serv = new ServerSocket(portNo);
         while (true) {
            System.out.println("Java JsonRPC StudentClub service waiting for "+
                               "connects on port "+portNo);
            sock = serv.accept();
            System.out.println("Java JsonRPC StudentClub service connected to "+
                               "client: "+id);
            StudentClubServer myServerThread = new StudentClubServer(sock,id++);
            myServerThread.start();
         }
      } catch(Exception e) {e.printStackTrace();}
   }
}