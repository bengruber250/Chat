package com.ben.chat;

import java.io.DataInputStream;
import java.io.IOException;

public class SocketListener extends Thread {

	private User user;
	public SocketListener(User user) {
		this.user = user;
	}
	public void run(){
		DataInputStream dIn = user.getInput();
		boolean done = false;
		try {
			while(!done) {
				byte messageType = dIn.readByte();

				switch(messageType)
				{
				case 1: // Chat messg
					Server.printMessage(user.getName() + ": " +dIn.readUTF(), user);
					break;
				case 2: // Command
					if(dIn.readUTF().equalsIgnoreCase("name")){
						String newname = dIn.readUTF();
						Server.printMessage("User " + user.getName() + " is now " + newname, null);
						user.setName(newname);
					}
					break;
				case 3: //  set name
					Server.printMessage("Message C [1]: " + dIn.readUTF(), user);
					Server.printMessage("Message C [2]: " + dIn.readUTF(), user);
					break;
				case -1: 
					Server.removeUser(user);
					dIn.close();
					done = true;
					break;
				default:
					done = false;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
