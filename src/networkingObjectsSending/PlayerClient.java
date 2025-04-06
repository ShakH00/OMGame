package networkingObjectsSending;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.util.Arrays;


public class PlayerClient extends JFrame {
    private PracticeGameObj practiceGameObj;

    private int width, height;
    private Container contentPane;
    private JTextArea message;
    private JTextArea textGridMessage;
    private JTextArea testText;
    private JTextArea chatArea;
    private JTextField chatInput;
    private JButton sendButton;
    private JButton b01;
    private JButton b02;
    private JButton b03;
    private JButton b04;
    private JButton b05;
    private JButton b06;
    private JButton b07;
    private JButton b08;
    private JButton b09;

    private ClientSideConnection csc;
    private int playerID;
    private int otherPlayerID;

    private int maxTurns;
    private int turnsMade;
    private int myPoints;
    private int enemyPoints;
    boolean buttonsEnabled;
    private String playerInputSender;
    private JButton startb00;
    private boolean gameIsActive;
    // this will impliment the turn based aspect forcing the player
    // to wait the other players turn

    public PlayerClient(int w, int h) {
        practiceGameObj = null;
        width = w;
        height = h;
        contentPane = this.getContentPane();
        message = new JTextArea();
        textGridMessage = new JTextArea();
        gameIsActive = false;
        testText = new JTextArea();
        // Buttons
        startb00 = new JButton("Start Matchmaking");

        b01 = new JButton("1");
        b02 = new JButton("2");
        b03 = new JButton("3");
        b04 = new JButton("4");
        b05 = new JButton("5");
        b06 = new JButton("6");
        b07 = new JButton("7");
        b08 = new JButton("8");
        b09 = new JButton("9");
        turnsMade = 0;
        myPoints = 0;
        enemyPoints = 0;
    }

    public void playerMenu() {

        this.setSize(width, height);
        this.setTitle("The GameMenu");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        contentPane = this.getContentPane();
        contentPane.setLayout(new BorderLayout());
        testText = new JTextArea("lol");

        JPanel panel = new JPanel();

        startb00 = new JButton("Start Game");
        panel.add(startb00);
        panel.add(testText);
        contentPane.add(panel, BorderLayout.CENTER);

        setUpMenuButtons();
        this.setVisible(true);

    }

    public void setUpGUII(){
        contentPane.removeAll();
        contentPane.revalidate();
        contentPane.repaint();

        gameIsActive = true;
        this.setSize(width, height);
        this.setTitle("The Game for Player #" + playerID);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        contentPane = this.getContentPane();
        contentPane.setLayout(new BorderLayout());

        message = new JTextArea();
        message.setText("Turn-based game");
        message.setWrapStyleWord(true);
        message.setLineWrap(true);
        message.setEditable(false);

        // ASKIED CHATGTP TO CREATE A DIPLAY OF textGridMessage which was not working//
        // this is just for testing purposes and will be replaced by gui team
        contentPane.add(message, BorderLayout.NORTH);
        JPanel centerPanel = new JPanel(new BorderLayout());
        textGridMessage = new JTextArea();
        textGridMessage.setWrapStyleWord(true);
        textGridMessage.setLineWrap(true);
        textGridMessage.setEditable(false);
        centerPanel.add(textGridMessage, BorderLayout.NORTH); // Place it at the top of center panel

        ImageIcon icon = new ImageIcon("image.png");
        JPanel topPanel = new JPanel(new GridLayout(3, 3));
        for (int i = 1; i <= 9; i++) {
            JLabel imageLabel = new JLabel(icon); // Add image labels
            topPanel.add(imageLabel);
        }
        centerPanel.add(topPanel, BorderLayout.CENTER); // Place the images below textGridMessage

        contentPane.add(centerPanel, BorderLayout.CENTER);
        JPanel bottomPanel = new JPanel(new GridLayout(3, 3));
        bottomPanel.add(b01);
        bottomPanel.add(b02);
        bottomPanel.add(b03);
        bottomPanel.add(b04);
        bottomPanel.add(b05);
        bottomPanel.add(b06);
        bottomPanel.add(b07);
        bottomPanel.add(b08);
        bottomPanel.add(b09);
        contentPane.add(bottomPanel, BorderLayout.SOUTH);
        // END OF CHATGTP

        this.setVisible(true);


        if (playerID == 1){
            message.setText("you are player 1, you go first");
            otherPlayerID = 2;
            buttonsEnabled = true;
        }
        else {
            message.setText("you are player 2, wait for your turn");
            otherPlayerID = 1;
            buttonsEnabled = false;
             /* why thread? bc we don't want the net code to interrupt the gui display
            every read can interrupt the code sequence until you receive the request
            Hence you ruin a thread and don't interpret the gui field like messages or toggle button
            */
            Thread t = new Thread(new Runnable() {
                public void run() {
                    updateTurn();
                }
            });
            t.start();
        }

        toggleButtons();

        this.setVisible(true); // set frame visibility true
        //END OF CHATGTP
    }

    public void toggleButtons(){
        //b1.setEnabled(buttonsEnabled);
        // flips the buttons from javax.swing
        b01.setEnabled(buttonsEnabled);
        b02.setEnabled(buttonsEnabled);
        b03.setEnabled(buttonsEnabled);
        b04.setEnabled(buttonsEnabled);
        b05.setEnabled(buttonsEnabled);
        b06.setEnabled(buttonsEnabled);
        b07.setEnabled(buttonsEnabled);
        b08.setEnabled(buttonsEnabled);
        b09.setEnabled(buttonsEnabled);

    }

    public void connectToServer(){
        csc = new ClientSideConnection();
    }


    public void setUpMenuButtons(){
        ActionListener alStart = new ActionListener() {
            public void actionPerformed(ActionEvent StartAe) {
                System.out.println(" YOU PRESSED THE BUTTON");
                gameIsActive = true;
                connectToServer();
                setUpGame1Buttons();
                setUpGUII();

            }
        };
        startb00.addActionListener(alStart);
    }
    public void setUpGame1Buttons(){

        ActionListener al = new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                JButton b = (JButton) ae.getSource();
                String strBNum = (b.getText()); // this is a string lets parse it

                message.setText(" You clicked button #" + strBNum + "now wait fo next player turn") ;
                textGridMessage.setText(String.valueOf(practiceGameObj.getBoard()[0][0]));
                turnsMade++;
                System.out.println("Turns made: " + turnsMade);

                buttonsEnabled = false;
                toggleButtons();

                System.out.println("we sent bNum: " + strBNum);
                practiceGameObj.setTestString(strBNum);
                csc.sendPracticeGameObj();


                if (playerID == 2 && turnsMade == maxTurns){
                    checkWinner();
                } else {
                    // When you press uour turn, you now need to Wait for your next turn
                    Thread t = new Thread(new Runnable() {

                        public void run() {
                            updateTurn();
                        }

                    });
                    t.start();
                }
            }
        };

        b01.addActionListener(al);
        b02.addActionListener(al);
        b03.addActionListener(al);
        b04.addActionListener(al);
        b05.addActionListener(al);
        b06.addActionListener(al);
        b07.addActionListener(al);
        b08.addActionListener(al);
        b09.addActionListener(al);
    }





    public void updateTurn(){
        String n = "N";
        csc.receivePracticeGameObj();

        message.setText("your opponent clicked #" + practiceGameObj.getTestString() + "now your Turn");
        textGridMessage.setText(String.valueOf(practiceGameObj.getBoard()[0][0]));

        if(playerID == 1 && turnsMade == maxTurns){ // win checker for player 1
            checkWinner();
        } else {
            buttonsEnabled = true;
        }

        toggleButtons();
    }

    private void checkWinner(){
        buttonsEnabled = false;
        if (myPoints > enemyPoints) {
            message.setText("You won! You: " + myPoints + " points, Enemy: " + enemyPoints + " points");
        } else if (myPoints < enemyPoints) {
            message.setText("You lost! You: " + myPoints + " points, Enemy: " + enemyPoints + " points");
        } else {
            message.setText("It's a tie! Both have " + myPoints + " points.");
        }

        csc.closeConnection();

    }

    //Networking instruction for the Client
    private class ClientSideConnection{
        private Socket socket;
        private DataInputStream dataIn;
        private DataOutputStream dataOut;
        private ObjectOutputStream dataOutObj;
        private ObjectInputStream dataInObj;

        private boolean connected;
        public ClientSideConnection(){   // I think this is waht is being send to the server
            System.out.println("Client side connection");

            try{
                socket = new Socket("localhost", 30000);
                dataOut = new DataOutputStream(socket.getOutputStream());
                dataIn = new DataInputStream(socket.getInputStream());
                dataOutObj = new ObjectOutputStream(socket.getOutputStream());
                dataInObj = new ObjectInputStream(socket.getInputStream());

                playerID = dataIn.readInt();
                System.out.println("Player ID: " + playerID);
                this.connected = true;



                // Start a thread to listen for incoming messages
                new Thread(() -> {
                    try {
                        while (true) {
                            String message = dataIn.readUTF();
                            if (message.startsWith("CHAT:")) {
                                receiveChatMessage(message.substring(5)); // Remove "CHAT:" prefix
                            }
                        }
                    } catch (IOException e) {
                        System.out.println("Error receiving chat message: " + e.getMessage());
                    }
                }).start();

            }catch (Exception e){
                System.out.println("IO exception from CSC contructor");

            }
        }

        private void handleDisconnection() {
        connected = false;
        // Attempt reconnection
        new Thread(() -> {
            while (!connected) {
                try {
                    Thread.sleep(5000); // Wait 5 seconds between attempts
                    reconnect();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }).start();
    }
    
        private void reconnect() {
            try {
                socket = new Socket("localhost", 30000);
                dataIn = new DataInputStream(socket.getInputStream());
                dataOut = new DataOutputStream(socket.getOutputStream());
                dataOut.writeInt(playerID); // Send player ID for reconnection
                connected = true;
            } catch (IOException e) {
                System.err.println("Reconnection failed: " + e.getMessage());
            }
        }

        public void sendChatMessage(String message) {
        try {
            dataOut.writeUTF("CHAT:" + message);
        } catch (IOException e) {
            handleDisconnection();
        }
    }
        public void sendPracticeGameObj(){
            try {
                dataOutObj.writeObject(practiceGameObj);
                dataOutObj.flush();
            } catch (IOException e) {
                System.out.println("Error sending practice game obj: ");
            }
        }

        public void receivePracticeGameObj(){
            try {
                Object tempObj = dataInObj.readObject(); // vague object gets "catched first_
                practiceGameObj = (PracticeGameObj) tempObj;
            } catch (IOException e){
                System.out.println("Error receiving practice game obj: ");
            } catch (ClassNotFoundException e) {
                System.out.println("object class not found");
            }
        }



        public void closeConnection(){
            try {
                socket.close();
                System.out.println("Closing connection");

            } catch (IOException e){
                System.out.println("IO exception in ClientSideConnection closeConnection");
            }
        }

        private void initializeChatComponents() {
        chatArea = new JTextArea(10, 30);
        chatArea.setEditable(false);
        JScrollPane chatScroll = new JScrollPane(chatArea);
        
        chatInput = new JTextField(20);
        sendButton = new JButton("Send");
        
        JPanel chatPanel = new JPanel();
        chatPanel.add(chatInput);
        chatPanel.add(sendButton);
        
        contentPane.add(chatScroll, BorderLayout.EAST);
        contentPane.add(chatPanel, BorderLayout.SOUTH);
        
        sendButton.addActionListener(e -> sendChatMessage());
        chatInput.addActionListener(e -> sendChatMessage());
    }
    
    private void sendChatMessage() {
        String message = chatInput.getText().trim();
        if (!message.isEmpty()) {
            csc.sendChatMessage(playerID + ": " + message + "\n");
            chatInput.setText("");
        }
    }
    
    public void receiveChatMessage(String message) {
        chatArea.append(playerID + ": " + message + "\n");
        chatArea.setCaretPosition(chatArea.getDocument().getLength());
    }
}


    public static void main(String[] args) {
        PlayerClient p = new PlayerClient(800, 400);
        p.playerMenu();
        //p.Menu { includes [p.connectToServer();, GHAME SPCFIC :p.setUpGUII()  p.setUpButtons();]
        /*p.connectToServer();
        p.setUpGUII(); //has startReceivingButtonNums in it
        p.setUpButtons();*/
    }
}