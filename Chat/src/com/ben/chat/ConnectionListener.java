package com.ben.chat;

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
			System.out.println("Listening on port " + serverSocket.getLocalPort());
			while(true){
				Socket socket = serverSocket.accept();
				User user = new User(socket, false);
				System.out.println("Connection established");
				handleConnection(user).start();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static Thread handleConnection(User user){
		Server.addUser(user);
		return new SocketListener(user);
	}
}
