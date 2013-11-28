package com.ben.chat;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

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
					int iterations = dIn.readByte();
					ArrayList<String> command = new ArrayList<String>();
					for(int i=0;i<iterations;i++){
						command.add(dIn.readUTF());
					}
					Iterator<String> commandIterator = command.iterator();
					commandIterator.next();
					
					/*Command Handler*/
					switch(command.get(0).toLowerCase()){
					case "name":
						StringBuilder sb = new StringBuilder();
						sb.append(commandIterator.next());
						while(commandIterator.hasNext()){
							sb.append(" " +commandIterator.next());
						}
						Server.printMessage("User " + user.getName() + " is now " + sb.toString(), null);
						user.setName(sb.toString());
					}
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
