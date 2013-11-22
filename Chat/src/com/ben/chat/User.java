package com.ben.chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class User {
	private Socket socket;
	private String name;
	private boolean isAdmin;
	private DataInputStream input;
	private DataOutputStream output;
	public User(Socket socket, String name, boolean isAdmin) {
		super();
		this.socket = socket;
		this.name = name;
		this.isAdmin = isAdmin;
		try {
			this.input = new DataInputStream(socket.getInputStream());
			this.output = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	public synchronized void send(String contents, int type) throws IOException{
		output.writeByte(type);
		output.writeUTF(contents);
		output.flush();
	}

}
