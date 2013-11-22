package com.ben.chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class User {
	private Socket socket;
	private String name;
	private boolean isAdmin;
	private DataInputStream inputStream;
	private DataOutputStream outputStream;
	public String getName() {
		return name;
	}
	public boolean isAdmin() {
		return isAdmin;
	}
	public DataInputStream getInputStream() {
		return inputStream;
	}
	public DataOutputStream getOutputStream() {
		return outputStream;
	}
	public User(Socket socket, boolean isAdmin) {
		super();
		this.socket = socket;
		this.name = "";
		this.isAdmin = isAdmin;
		try {
			this.inputStream = new DataInputStream(socket.getInputStream());
			this.outputStream = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	public synchronized void send(String contents, int type) throws IOException{
		outputStream.writeByte(type);
		outputStream.writeUTF(contents);
		outputStream.flush();
	}
	public synchronized void close() {
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public void setName(String name) {
		this.name = name;
	}

}
