package com.ben.chat;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DefaultCaret;

public final class Client extends JFrame implements Runnable {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private String name, address;
	private int port;
	private JTextField txtMessage;
	private JTextArea history;
	private DateFormat dateFormat;
	private DefaultCaret caret;
	private Socket socket;
	private InetAddress ip;
	private Thread send;
	private DataOutputStream out;
	private DataInputStream in;
	private boolean close;
	/**
	 * Create the frame.
	 * @param port 
	 * @param address 
	 * @param name 
	 */
	public Client(String name, String address, int port) {

		dateFormat = new SimpleDateFormat("(MM/dd/yy HH:mm:ss)");
		setTitle("Chat Client");
		this.port = port;
		this.name = name;
		this.address = address;

		createWindow();
		run();
		final Thread listen = new Thread(){
			public void run(){
				receive();
			}
		};
		listen.start();
		addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				try {
					out.writeByte(-1);
					out.flush();
					close = true;
					listen.join();
					in.close();
					out.close();
				} 
				catch(EOFException e){

				}catch (IOException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				System.exit(0);

			}
		});

	}
	/**
	 * @return false is failure
	 */

	private void receive(){
		try {

			close = false;
			while(!close){
				byte type = in.readByte();
				switch(type){
				case 1:
					console(in.readUTF());
				default:
					close = false;
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;
	}
	private boolean openConnection(String address, int port){
		try {		
			ip = InetAddress.getByName(address);
			socket = new Socket(ip, port);
			out = new DataOutputStream(socket.getOutputStream());
			in = new DataInputStream(socket.getInputStream());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return false;
		}
		return true;
	}

	private void createWindow(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(880,550);
		this.setMinimumSize(new Dimension(880,550));
		setLocationRelativeTo(null);
		setIconImage(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB_PRE));

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{26-7, 804+7, 43+3, 7-3};
		gbl_contentPane.rowHeights = new int[]{37+15, 480-15, 23};
		gbl_contentPane.columnWeights = new double[]{1.0, 1.0};
		gbl_contentPane.rowWeights = new double[]{1.0, Double.MIN_VALUE};

		contentPane.setLayout(gbl_contentPane);
		history = new JTextArea();
		history.setEditable(false);
		caret = (DefaultCaret)history.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		JScrollPane scroll = new JScrollPane(history);
		GridBagConstraints scrollConstraints = new GridBagConstraints();
		scrollConstraints.insets = new Insets(0, 0, 5, 5);
		scrollConstraints.fill = GridBagConstraints.BOTH;
		scrollConstraints.gridx = 0;
		scrollConstraints.gridwidth = 3;
		scrollConstraints.gridy = 0;
		scrollConstraints.gridheight = 2;
		contentPane.add(scroll, scrollConstraints);

		txtMessage = new JTextField();
		txtMessage.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					send(txtMessage.getText());
				}
			}
		});
		GridBagConstraints gbc_txtMessage = new GridBagConstraints();
		gbc_txtMessage.insets = new Insets(0, 0, 0, 5);
		gbc_txtMessage.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtMessage.gridx = 0;
		gbc_txtMessage.gridy = 2;
		gbc_txtMessage.gridwidth = 2;
		contentPane.add(txtMessage, gbc_txtMessage);
		txtMessage.setColumns(10);

		JButton btnSend = new JButton("Send");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				send(txtMessage.getText());
			}
		});
		GridBagConstraints gbc_btnSend = new GridBagConstraints();
		gbc_btnSend.insets = new Insets(0, 0, 0, 5);
		gbc_btnSend.gridx = 2;
		gbc_btnSend.gridy = 2;
		contentPane.add(btnSend, gbc_btnSend);
		setVisible(true);
		txtMessage.requestFocusInWindow();
	}
	public void console(Object message){
		history.append(message.toString() + "\n\r");
	}
	private void send(String message){
		send(dateFormat.format(Calendar.getInstance().getTime()) +  name + ": " + message, 1);
		if (message.equals(""))
			return;

		console(dateFormat.format(Calendar.getInstance().getTime()) +  name + ": " + message);
		txtMessage.setText("");
	}
	private void send(final String data, final int type){
		send = new Thread("Send"){
			public void run(){
				try {
					out.writeByte(type);
					out.writeUTF(data);
					out.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		};
		send.start();
	}
	@Override
	public void run() {
		boolean connect = false;
		for(int i =0; i<10&&!connect; i++){
			console("Attempting a connection with name " + name + " at " + address + ":" + port);
			connect = openConnection(address,port);
			if(!connect){
				console("Connection failed.");
			}
		}
		if(!connect){
			System.err.println("fail");
		}
		else
			console("Connected to server.");
	}

}
