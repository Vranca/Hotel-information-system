/***********************************************************************
 * Module:  LoginView.java
 * Author:  User
 * Purpose: Defines the Class LoginView
 ***********************************************************************/

package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.concurrent.CompletableFuture;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.plaf.metal.MetalButtonUI;

import applicationState.ReadyState;
import controller.ApplicationController;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import model.ApplicationModel;

public class LoginView extends JFrame implements ActionListener, MouseListener
{
	private static final long serialVersionUID = 1L;

	JLabel usernameLabel;
	JTextField usernameField;
	JLabel passwordJLabel;
	JPasswordField passwordField;
	JButton loginButton;
	ApplicationModel appModel;
	JButton showPassButton;
	Player ply = null;

	public LoginView(ApplicationModel appModel)
	{
		this.appModel = appModel;
		constructFrame();
		JPanel contentPane = new JPanel();
		contentPane.setSize(this.getSize());
		contentPane.setLayout(null);
		contentPane.setVisible(true);

		this.setContentPane(contentPane);

		usernameLabel = new JLabel("Korisničko ime:");
		usernameLabel.setSize(120, 35);
		usernameLabel.setBounds(contentPane.getWidth() / 2 - 120, contentPane.getHeight() / 4 - 20, 120, 30);
		contentPane.add(usernameLabel);

		usernameField = new JTextField();

		usernameField.setBounds(contentPane.getWidth() / 2, contentPane.getHeight() / 4 - 15, 130, 30);
		contentPane.add(usernameField);

		passwordJLabel = new JLabel("Lozinka:");
		passwordJLabel.setSize(70, 35);
		passwordJLabel.setBounds(contentPane.getWidth() / 2 - 70, 2 * (contentPane.getHeight() / 4) - 20, 120, 30);
		contentPane.add(passwordJLabel);

		passwordField = new JPasswordField();
		passwordField.setEchoChar('*');
		passwordField.setBounds(contentPane.getWidth() / 2, 2 * (contentPane.getHeight() / 4) - 20, 130, 30);
		contentPane.add(passwordField);

		loginButton = new JButton("Prijavite se");
		loginButton.setUI(new MetalButtonUI());

		loginButton.setBounds(contentPane.getWidth() / 2 - 50, 3 * (contentPane.getHeight() / 4) - 20, 100, 30);
		contentPane.add(loginButton);
		loginButton.setActionCommand("login");
		loginButton.addActionListener((this));

		this.getRootPane().setDefaultButton(loginButton);
		usernameField.requestFocus();

		Image image = new ImageIcon("img/icons8-third-eye-symbol-64.png").getImage();
		showPassButton = new JButton((new ImageIcon(image.getScaledInstance(30, 30, Image.SCALE_SMOOTH))));
		showPassButton.setBounds(contentPane.getWidth() / 2 + 135, 2 * (contentPane.getHeight() / 4) - 20, 30, 30);
		contentPane.add(showPassButton);

		showPassButton.addMouseListener(this);

		contentPane.repaint();

		String alarm = "audio/alarm.mp3";
		try
		{
			FileInputStream stream = new FileInputStream(alarm);
			ply = new Player(stream);
		} catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JavaLayerException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void constructFrame()
	{

		this.setTitle("Hotelski informacioni sistem");
		this.getContentPane().setLayout(new BorderLayout());
		this.setIconImage(new ImageIcon("icons/icons8-hotel-building-16.png").getImage());

		Toolkit toolkit = Toolkit.getDefaultToolkit();
		this.setSize(new Dimension(500, 300));
		this.setLocation((int) ((toolkit.getScreenSize().getWidth() / 2) - (this.getWidth() / 2)),
				(int) ((toolkit.getScreenSize().getHeight() / 2) - (this.getHeight() / 2)));

		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{

		if (usernameField.getText() != "" && passwordField.getPassword() != null)
		{
			if (this.appModel.login(usernameField.getText(), String.valueOf(passwordField.getPassword())))
			{
				ApplicationView view = new ApplicationView(this.appModel);
				new ApplicationController(this.appModel, view);
				this.appModel.setState(new ReadyState(view));
				appModel.notifyAllSubscribers();

				try
				{
					ply.close();
				} catch (Exception exception)
				{
					exception.printStackTrace();
				}

				this.dispose();
			} else
			{
				this.usernameField.setText("");
				this.passwordField.setText("");
				CompletableFuture.runAsync(() -> {
					try
					{
						ply.play();
					} catch (JavaLayerException e1)
					{
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				});
				JOptionPane.showMessageDialog(null, "Unijeti kredencijali ne odgovaraju nijednom korisniku!", "Obavijestenje",
						JOptionPane.WARNING_MESSAGE);
			}
		}

	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		this.passwordField.setEchoChar((char) 0);

	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		this.passwordField.setEchoChar('*');

	}

	@Override
	public void mouseEntered(MouseEvent e)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e)
	{
		// TODO Auto-generated method stub

	}

}