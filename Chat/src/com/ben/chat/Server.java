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
	private static ArrayList<User> users;
	public static ArrayList<User> getOuts() {
		return users;
	}
	public static void addUser(User e) {
		users.add(e);
	}
	public static void removeUser(User e){
		users.remove(e);
	}

	public static void main(String[] args) throws IOException{
		users = new ArrayList<User>(); 
		final ServerSocket serverSocket = new ServerSocket(27015);
		ConnectionListener listener = new ConnectionListener(serverSocket);
		listener.start();
	}

	public static void printMessage(String s,User user2){
		System.out.println(s);
		for(User user: users){
			if(!user.equals(user2)){
				try {
					user.send(s, 1);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}	
	}


}
