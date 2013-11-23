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
				User user = new User(serverSocket.accept(), false);
				System.out.println("Connection established");
				handleConnection(user).start();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static Thread handleConnection(User user){
		Server.addUser(user);
		try {
			user.setName(user.getInput().readUTF());
			Server.printMessage( "User " + user.getName() + " has joined.", null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new SocketListener(user);
	}
	
	

}
