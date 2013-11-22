package com.ben.chat;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

public class Login extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtName;
	private JTextField txtAddress;
	private JTextField txtPort;
	private JLabel lblIpAddress;
	private JLabel lblPort;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Login() {
		setIconImage(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB_PRE));
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		setResizable(false);
		setTitle("Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300,380);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		txtName = new JTextField();
		txtName.setBounds(68, 53, 158, 20);
		contentPane.add(txtName);
		txtName.setColumns(10);

		JLabel lblNewLabel = new JLabel("Name:");
		lblNewLabel.setFont(new Font("Courier New", Font.PLAIN, 14));
		lblNewLabel.setBounds(124, 31, 46, 14);
		contentPane.add(lblNewLabel);

		txtAddress = new JTextField();
		txtAddress.setBounds(68, 117, 158, 20);
		contentPane.add(txtAddress);
		txtAddress.setColumns(10);

		lblIpAddress = new JLabel("IP Address:");
		lblIpAddress.setFont(new Font("Courier New", Font.PLAIN, 14));
		lblIpAddress.setBounds(96, 99, 102, 14);
		contentPane.add(lblIpAddress);

		txtPort = new JTextField();
		txtPort.setColumns(10);
		txtPort.setBounds(68, 172, 158, 20);
		contentPane.add(txtPort);

		lblPort = new JLabel("Port:");
		lblPort.setFont(new Font("Courier New", Font.PLAIN, 14));
		lblPort.setBounds(124, 154, 46, 14);
		contentPane.add(lblPort);

		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					int port = Integer.parseInt(txtPort.getText());
					//TODO Check bounds of port
					login(txtName.getText(),txtAddress.getText(),port);
				}
				catch(NumberFormatException e){
					System.out.println("Invalid Port");
				}
			}

		});
		btnLogin.setBounds(68, 295, 89, 23);
		contentPane.add(btnLogin);
	}
	/* End of constructor */
	private void login(String name, String address, int port) {
		dispose();
		new Client(name,address,port);
	}	
}
