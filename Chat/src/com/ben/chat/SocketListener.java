package com.ben.chat;

import java.io.DataInputStream;
import java.io.IOException;

public class SocketListener extends Thread {
	private User user;
	private DataInputStream dIn;
	public SocketListener(User user) {
		this.user = user;
		dIn = user.getInputStream();
	}
	public void run(){
		boolean done = false;
		try {
			while(!done) {
				byte messageType = dIn.readByte();
				switch(messageType)
				{
				case 1: // Message
					Server.printMessage(dIn.readUTF(), user);
					break;
				case 2: // Command
					switch(dIn.readUTF()){
					case "name":
						user.setName(dIn.readUTF());
					}
					break;
				case -1: // Close Connection
					Server.removeUser(user);
					user.close();
					done = true;
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}