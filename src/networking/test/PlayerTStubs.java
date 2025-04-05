import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PlayerTStubs extends JFrame {

    private int width, height;
    private Container contentPane;
    private JTextArea message, textGridMessage, testText;
    private JButton b01, b02, b03, b04, b05, b06, b07, b08, b09;
    private JButton startb00;

    // Networking-related stubs
    private ClientSideConnection csc; // stub
    private int playerID, otherPlayerID; // stub
    private boolean gameIsActive;
    private boolean buttonsEnabled;
    private int turnsMade;
    private int maxTurns, myPoints, enemyPoints;
    private String playerInputSender; // stub

    private int[] values;
    private char[][] server2dChar;

    public PlayerTStubs(int w, int h) {
        width = w;
        height = h;
        contentPane = this.getContentPane();
        message = new JTextArea();
        textGridMessage = new JTextArea();
        testText = new JTextArea();
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

        values = new int[4];
        server2dChar = new char[3][3];
        turnsMade = 0;
        myPoints = 0;
        enemyPoints = 0;
        buttonsEnabled = true;
        gameIsActive = false;
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

    public void setUpGUII() {
        contentPane.removeAll();
        contentPane.revalidate();
        contentPane.repaint();

        gameIsActive = true;
        this.setSize(width, height);
        this.setTitle("The Game (Local Mode)");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        contentPane = this.getContentPane();
        contentPane.setLayout(new BorderLayout());

        message = new JTextArea();
        message.setText("Turn-based game (local)");
        message.setWrapStyleWord(true);
        message.setLineWrap(true);
        message.setEditable(false);
        contentPane.add(message, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout());

        textGridMessage = new JTextArea();
        textGridMessage.setWrapStyleWord(true);
        textGridMessage.setLineWrap(true);
        textGridMessage.setEditable(false);
        centerPanel.add(textGridMessage, BorderLayout.NORTH);

        ImageIcon icon = new ImageIcon("image.png");
        JPanel topPanel = new JPanel(new GridLayout(3, 3));
        for (int i = 1; i <= 9; i++) {
            JLabel imageLabel = new JLabel(icon);
            topPanel.add(imageLabel);
        }
        centerPanel.add(topPanel, BorderLayout.CENTER);
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

        this.setVisible(true);
        toggleButtons();
    }

    public void toggleButtons() {
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

    public void setUpMenuButtons() {
        ActionListener alStart = new ActionListener() {
            public void actionPerformed(ActionEvent StartAe) {
                System.out.println("YOU PRESSED THE BUTTON");
                gameIsActive = true;
                connectToServer(); // stubbed
                setUpGame1Buttons();
                setUpGUII();
            }
        };
        startb00.addActionListener(alStart);
    }

    public void setUpGame1Buttons() {
        ActionListener al = new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                JButton b = (JButton) ae.getSource();
                String strBNum = b.getText();
                message.setText("You clicked button #" + strBNum);
                textGridMessage.setText(server2dCharToString());
                turnsMade++;

                // Stub: simulate sending/receiving with server
                sendButtonNum(strBNum);
                updateTurn();
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

    public String server2dCharToString() {
        StringBuilder sb = new StringBuilder();
        for (char[] row : server2dChar) {
            sb.append("[");
            for (char cell : row) {
                sb.append("['").append(cell).append("'], ");
            }
            sb.setLength(sb.length() - 2);
            sb.append("]\n");
        }
        return sb.toString();
    }

    // === STUBS FOR NETWORKING FEATURES ===

    public void connectToServer() {
        System.out.println("(Stub) Connecting to server...");
    }

    public void updateTurn() {
        System.out.println("(Stub) Receiving opponent move...");
        buttonsEnabled = true;
        toggleButtons();
    }

    public void sendButtonNum(String buttonNum) {
        System.out.println("(Stub) Sending button number: " + buttonNum);
    }

    private void checkWinner() {
        System.out.println("(Stub) Checking winner...");
    }

    // Stubbed inner class
    private class ClientSideConnection {
        public ClientSideConnection() {
            System.out.println("(Stub) ClientSideConnection initialized");
        }

        public void sendButtonNum(String strBNum) {}
        public String receiveButtonNum() { return "N"; }
        public void closeConnection() {}
    }

    public static void main(String[] args) {
        PlayerT p = new PlayerT(800, 400);
        p.playerMenu();
    }
}