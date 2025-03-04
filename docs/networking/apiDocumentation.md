# Networking API Specification \- Multiplayer Game System #

## **1\. Overview**

**The Networking System is responsible for handling real-time, turn-based communication between two players in a multiplayer game. This document outlines the core networking functions used for managing player connections, transmitting moves, and synchronizing game state between clients.**

## **2\. Core Components**

### **Server (`GameServerT.java`)**

* **Manages player connections and enforces turn-based gameplay.**  
* **Maintains the game state and updates both players.**  
* **Handles network communication through TCP sockets.**

### **Client (`PlayerT.java`)**

* **Provides a graphical interface for players.**  
* **Connects to the server and sends player moves.**  
* **Receives game updates and processes opponent moves.**

## **3\. API Functions**

### **🔹 Server Methods (GameServerT.java)**

#### **Accept Player Connections**

**public void acceptConnections();**

* **Purpose: Waits for two players to connect.**  
* **Behavior:**  
  * **Accepts two client sockets.**  
  * **Starts a new thread for each player.**  
* **Called by: Server during initialization.**

#### **Send Player Move to Opponent**

**public void sendButtonNum(String buttonNum);**

* **Purpose: Sends the clicked button (move) to the opponent.**  
* **Parameters:**  
  * **`buttonNum` (String) – The button number clicked by the player.**  
* **Used by: Server to relay moves between players.**

#### **Send Updated Game Board**

**public void send2dCharArray();**

* **Purpose: Sends the current game board to the opponent.**  
* **Behavior:**  
  * **Iterates through `server2dChar[][]` and transmits board data.**  
* **Used by: Server after a player makes a move.**

#### **Process Game Logic for Player 1**

**public void processGameLogicP1(String input);**

* **Purpose: Updates the game board for Player 1\.**  
* **Parameters:**  
  * **`input` (String) – Move made by Player 1\.**  
* **Behavior:**  
  * **Converts input into row/column coordinates.**  
  * **Places an 'X' on the board if the move is valid.**

#### **Process Game Logic for Player 2**

**public void processGameLogicP2(String input);**

* **Purpose: Updates the game board for Player 2\.**  
* **Parameters:**  
  * **`input2` (String) – Move made by Player 2\.**  
* **Behavior:**  
  * **Converts input into row/column coordinates.**  
  * **Places an 'O' on the board if the move is valid.**

### **🔹 Client Methods (PlayerT.java)**

#### **Connect to Server**

**public void connectToServer();**

* **Purpose: Establishes a connection to the game server.**  
* **Behavior:**  
  * **Opens a TCP socket to `localhost:30000`.**  
  * **Receives player ID (`1` or `2`) from the server.**  
* **Called by: Player during initialization.**

#### **Send Move to Server**

**public void sendButtonNum(String strBNum);**

* **Purpose: Sends the selected move to the server.**  
* **Parameters:**  
  * **`strBNum` (String) – The button number clicked by the player.**  
* **Behavior:**  
  * **Transmits move using `DataOutputStream`.**  
* **Used by: Player when clicking a button.**

#### **Receive Move from Opponent**

**public String receiveButtonNum();**

* **Purpose: Receives and processes the opponent's move.**  
* **Returns:**  
  * **`String` – The button number clicked by the opponent.**  
* **Behavior:**  
  * **Reads opponent's move from `DataInputStream`.**  
  * **Updates local game board (`server2dChar[][]`).**

#### **Update Turn After Opponent Moves**

**public void updateTurn();**

* **Purpose: Handles turn-based mechanics.**  
* **Behavior:**  
  * **Waits for the opponent to make a move.**  
  * **Updates the game board.**  
  * **Enables the player's buttons after receiving the opponent's move.**  
* **Called by: Background thread after a move is made.**

#### **Check for Game Over**

**private void checkWinner();**

* **Purpose: Determines if the game has ended.**  
* **Behavior:**  
  * **Compares player scores.**  
  * **Displays a message indicating win, lose, or tie.**  
* **Called by: `updateTurn()` when the max number of turns is reached.**

#### **Close Connection**

**public void closeConnection();**

* **Purpose: Gracefully closes the socket connection.**  
* **Called by: The client when the game ends.**

## **4\. Data Flow Example**

**1️⃣ Player 1 clicks a button → Calls `sendButtonNum()` to send move to server.**  
 **2️⃣ Server processes move → Updates board and sends the move to Player 2\.**  
 **3️⃣ Player 2 receives the move → Calls `receiveButtonNum()` and updates UI.**  
 **4️⃣ Player 2 takes a turn → Sends move back to the server.**  
 **5️⃣ Process repeats until max turns are reached.**

