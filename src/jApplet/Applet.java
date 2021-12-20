package jApplet;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Panel;
import java.awt.Button;
import javax.swing.SwingConstants;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.FlowLayout;
import java.awt.CardLayout;
import java.awt.GridLayout;

public class Applet extends JFrame {

	private JPanel contentPane;
	private JTextField textField;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Applet frame = new Applet();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Applet() {
		
		setTitle("RSA/MD5");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		// JPanels
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		
		Panel panel_1 = new Panel();
		contentPane.add(panel_1, BorderLayout.NORTH);
		
		JPanel panel_2 = new JPanel();
		contentPane.add(panel_2, BorderLayout.WEST);
		panel_2.setLayout(new CardLayout(0, 0));
		
		JPanel panel_3 = new JPanel();
		contentPane.add(panel_3, BorderLayout.SOUTH);
		panel_3.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_4 = new JPanel();
		panel.add(panel_4);
		panel_4.setLayout(new GridLayout(0, 2, 0, 0));
		
		// Others declarations
		
		textField = new JTextField();
		panel.add(textField);
		textField.setColumns(20);
		
		
		JLabel lblNewLabel = new JLabel("Cryptographie de niveau militaire");
		panel_1.add(lblNewLabel);
		
		
		JLabel lblNewLabel_3 = new JLabel("");
		panel_3.add(lblNewLabel_3);
		
		JButton btnNewButton = new JButton("Chiffrer mon message.");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					lblNewLabel_3.setText(RSA(textField.getText()));
				} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException
						| IllegalBlockSizeException | BadPaddingException e1) {
					e1.printStackTrace();
				}
			}
		});
		panel.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Hasher mon message sous MD5");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MessageDigest md = null;
				try {
					md = MessageDigest.getInstance("MD5");
				} catch (NoSuchAlgorithmException e1) {
					e1.printStackTrace();
				}
			    md.update(textField.getText().getBytes());
			    byte[] digest = md.digest();
			    lblNewLabel_3.setText(Base64.getEncoder().encodeToString(digest));
			}
		});
		panel.add(btnNewButton_1);

		
		JLabel label = new JLabel("R\u00E9sultat :");
		panel_4.add(label);
		
		JLabel lblNewLabel_2 = new JLabel("");
		panel_4.add(lblNewLabel_2);
		
				
	}
	
	public String RSA(String plain) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		String secretMessage = plain;
		KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
		generator.initialize(2048);
		KeyPair pair = generator.generateKeyPair();
		PrivateKey privateKey = pair.getPrivate();
		PublicKey publicKey = pair.getPublic();
		
		Cipher encryptCipher = Cipher.getInstance("RSA");;
		encryptCipher = Cipher.getInstance("RSA");
		encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey);
		
		byte[] secretMessageBytes = secretMessage.getBytes(StandardCharsets.UTF_8);
		String cipherStr = Base64.getEncoder().encodeToString(secretMessageBytes);
		byte[] encryptedMessageBytes = encryptCipher.doFinal(secretMessageBytes);
		
		Cipher decryptCipher = Cipher.getInstance("RSA");
		decryptCipher.init(Cipher.DECRYPT_MODE, privateKey);
		
		byte[] decryptedMessageBytes = null;
		decryptedMessageBytes = decryptCipher.doFinal(encryptedMessageBytes);

		String decryptedMessage = new String(decryptedMessageBytes, StandardCharsets.UTF_8);
		
		if(secretMessage.equalsIgnoreCase(decryptedMessage)) {
			return cipherStr;
		}
		return "Une erreur est survenue";
	}
}
