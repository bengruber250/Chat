package com.ben.chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class ConnectionListener extends Thread {
	private ServerSocket serverSocket;
	public ConnectionListener(ServerSocket serverSocket){
		this.serverSocket = serverSocket;
	}
	@Override
	public void run(){
		try {
			System.out.println("Listening on port 27015");
			while(true){
				Socket socket = serverSocket.accept();
//				DataInputStream input = new DataInputStream(socket.getInputStream());
//				DataOutputStream output = new DataOutputStream(socket.getOutputStream());
				System.out.println("Connection established");
				handleConnection(socket).start();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static Thread handleConnection(Socket socket){
		//Server.addOut(dOut);
		return new Thread(){
			public void run(){
				boolean done = false;
//				try {
//					while(!done) {
//						byte messageType = dIn.readByte();
//
//						switch(messageType)
//						{
//						case 1: // Type A
//							printMessage(dIn.readUTF(), dOut);
//							break;
//						case 2: // Type B
//							printMessage("Message B: " + dIn.readUTF(), dOut);
//							break;
//						case 3: // Type C
//							printMessage("Message C [1]: " + dIn.readUTF(), dOut);
//							printMessage("Message C [2]: " + dIn.readUTF(), dOut);
//							break;
//						case -1: 
//							outs.remove(dOut);
//							dIn.close();
//							done = true;
//							break;
//						default:
//							done = false;
//						}
//					}
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
			}
		};


	}
	
	

}
