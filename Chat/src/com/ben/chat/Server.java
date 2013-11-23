package com.ben.chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

	/**
	 * @param args
	 * @throws IOException 
	 */
	private static ArrayList<DataOutputStream> outs;
	public static ArrayList<DataOutputStream> getOuts() {
		return outs;
	}
	public static void addOut(DataOutputStream e) {
		outs.add(e);
	}
	public static void removeOut(DataOutputStream e){
		outs.remove(e);
	}

	public static void main(String[] args) throws IOException{
		outs = new ArrayList<DataOutputStream>(); 
		final ServerSocket serverSocket = new ServerSocket(27015);
		ConnectionListener listener = new ConnectionListener(serverSocket);
		listener.start();
//		Thread listener = new Thread(){
//			public void run(){
//				
//			}
//		};
		listener.start();

		//serverSocket.close();
	}

	public static void printMessage(String s,DataOutputStream dOut2){
		System.out.println(s);
		for(DataOutputStream dOut: outs){
			if(!dOut.equals(dOut2)){
				try {
					dOut.writeByte(1);
					dOut.writeUTF(s);
					dOut.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}	
	}


}
