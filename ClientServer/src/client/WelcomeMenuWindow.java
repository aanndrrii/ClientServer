package client;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class WelcomeMenuWindow extends JFrame{
	
	public WelcomeMenuWindow() {
        

        setBounds(600, 300, 600, 500);
        setTitle("Welcome!");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        JLabel welcomeLabel = new JLabel("Welcome! Please choose an option:");
        welcomeLabel.setHorizontalAlignment((int) CENTER_ALIGNMENT);
        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");
        
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	String name = JOptionPane.showInputDialog("Enter your name:");
            	ClientWindow cw = new ClientWindow();
            	cw.setClientName(name);
            }
        });
        
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	String name = JOptionPane.showInputDialog("Enter your name:");
            	ClientWindow cw = new ClientWindow();
            	cw.setClientName(name);
            }
        });
        
        JPanel panel = new JPanel(new GridLayout(3,1));
        panel.add(welcomeLabel);
        panel.add(loginButton);
        panel.add(registerButton);
        
        setContentPane(panel);
        
        setVisible(true);
       
	}

}
