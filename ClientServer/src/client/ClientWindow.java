package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClientWindow extends JFrame {

    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 3443;
    private Socket clientSocket;
    private Scanner inMessage;
    private PrintWriter outMessage;

    private JTextField jtfMessage;
    private JTextArea jtaTextAreaMessage;
    private String clientName = "";
    
    public void setClientName(String clientName) {
        this.clientName = clientName;
    }
    
    public String getClientName() {
        return this.clientName;
    }

    public ClientWindow() {
        try {

            clientSocket = new Socket(SERVER_HOST, SERVER_PORT);
            inMessage = new Scanner(clientSocket.getInputStream());
            outMessage = new PrintWriter(clientSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        setBounds(600, 300, 600, 500);
        setTitle("Client");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jtaTextAreaMessage = new JTextArea();
        jtaTextAreaMessage.setEditable(false);
        jtaTextAreaMessage.setLineWrap(true);
        JScrollPane jsp = new JScrollPane(jtaTextAreaMessage);
        add(jsp, BorderLayout.CENTER);

        JLabel jlNumberOfClients = new JLabel("Number of clients in the chat: ");
        add(jlNumberOfClients, BorderLayout.NORTH);
        JPanel bottomPanel = new JPanel(new BorderLayout());
        add(bottomPanel, BorderLayout.SOUTH);
        JButton jbSendMessage = new JButton("Send");
        bottomPanel.add(jbSendMessage, BorderLayout.EAST);
        jtfMessage = new JTextField("Your message: ");
        bottomPanel.add(jtfMessage, BorderLayout.CENTER);
        
        jbSendMessage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!jtfMessage.getText().trim().isEmpty() && !clientName.trim().isEmpty()) {
                    sendMsg();
                    jtfMessage.grabFocus();
                }
            }
        });
        
        jtfMessage.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                jtfMessage.setText("");
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    
                    while (true) {
                        if (inMessage.hasNext()) {
                            String inMes = inMessage.nextLine();
                            String clientsInChat = "Clients in chat = ";
                            if (inMes.indexOf(clientsInChat) == 0) {
                                jlNumberOfClients.setText(inMes);
                            } else {
                                jtaTextAreaMessage.append(inMes);
                             
                                jtaTextAreaMessage.append("\n");
                            }
                        }
                    }
                } catch (Exception e) {
                }
            }
        }).start();
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                try {
                    if (!clientName.isEmpty() && clientName != "Enter your name: ") {
                        outMessage.println(clientName + " left the chat!");
                    } else {
                        outMessage.println("The client left the chat!");
                    }
                    outMessage.println("##session##end##");
                    outMessage.flush();
                    outMessage.close();
                    inMessage.close();
                    clientSocket.close();
                } catch (IOException exc) {

                }
            }
        });
        setVisible(true);
    }


    public void sendMsg() {
        String messageStr = clientName + ": " + jtfMessage.getText();
        
        outMessage.println(messageStr);
        outMessage.flush();
        jtfMessage.setText("");
    }
}

