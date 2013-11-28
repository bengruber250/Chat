package com.ben.chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import javax.net.ssl.SSLSocket;

public class User {
	private SSLSocket socket;
	private String name;
	private boolean isAdmin;
	private DataInputStream input;
	private DataOutputStream output;
	public User(SSLSocket socket, boolean isAdmin) {
		super();
		this.socket = socket;
		this.isAdmin = isAdmin;
		try {
			this.input = new DataInputStream(this.socket.getInputStream());
			this.output = new DataOutputStream(this.socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	public synchronized String getName() {
		return name;
	}
	public synchronized void setName(String name) {
		this.name = name;
	}
	public boolean isAdmin() {
		return isAdmin;
	}
	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	public DataInputStream getInput() {
		return input;
	}
	public synchronized void send(String contents, int type) throws IOException{
		output.writeByte(type);
		output.writeUTF(contents);
		output.flush();
	}

}
