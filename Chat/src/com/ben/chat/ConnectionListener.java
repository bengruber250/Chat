package com.ben.chat;

import java.io.IOException;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLSocket;


public class ConnectionListener extends Thread {
	private SSLServerSocket serverSocket;
	public ConnectionListener(SSLServerSocket serverSocket){
		this.serverSocket = serverSocket;
	}
	@Override
	public void run(){
		try {
			System.out.println("Listening on port 27015");
			while(true){
				User user = new User((SSLSocket) serverSocket.accept(), false);
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
