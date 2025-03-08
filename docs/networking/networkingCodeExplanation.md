# Networking System - Function Explanations (Simplified for other Teams) #
**See suggestions in `apiDocumentation.md` and discuss with other teams for suggestions**


## **1. Overview**
This document is intended to help external teams **understand the networking system** without needing to dive deep into the code. It provides a **clear and simplified explanation** of how key functions work, how they interact, and their role in the multiplayer game system. If you need an in-depth look at the technical implementation, refer to the API documentation.

---

## **2. How the System Works (Simplified Breakdown)**
1️⃣ **Players connect to the server** → The server assigns them Player 1 or Player 2.
2️⃣ **Players take turns making moves** → Each move is sent to the server.
3️⃣ **Server updates the game board** → The updated board is sent to both players.
4️⃣ **Opponent sees the move and takes their turn** → This cycle continues until the game ends.
5️⃣ **Game checks for a winner or tie** → If conditions are met, the game ends.

This process is handled by **two key components**: the **Game Server** and the **Player Client**. Below is a breakdown of their most important functions.

---

## **3. Server Functions (GameServerT.java)**

### **🔹 acceptConnections()**
- **What it does:** Waits for two players to connect.
- **Why it matters:** The game won’t start until both players join.

### **🔹 sendButtonNum(String buttonNum)**
- **What it does:** Sends the player's move to the opponent.
- **Why it matters:** Keeps both players updated in real time.

### **🔹 send2dCharArray()**
- **What it does:** Sends the updated game board to both players.
- **Why it matters:** Ensures both players see the same game state.

### **🔹 processGameLogicP1(String input) & processGameLogicP2(String input)**
- **What they do:** Update the game board based on Player 1 and Player 2’s moves.
- **Why they matter:** Ensure valid moves are processed and stored correctly.

---

## **4. Client Functions (PlayerT.java)**

### **🔹 connectToServer()**
- **What it does:** Connects the player to the server.
- **Why it matters:** A player can't play without a connection.

### **🔹 sendButtonNum(String strBNum)**
- **What it does:** Sends the clicked button (move) to the server.
- **Why it matters:** Ensures the player’s move is registered in the game.

### **🔹 receiveButtonNum()**
- **What it does:** Receives the opponent’s move from the server.
- **Why it matters:** Allows the player to see what their opponent did.

### **🔹 updateTurn()**
- **What it does:** Manages the turn-based system.
- **Why it matters:** Ensures players take turns correctly.

### **🔹 checkWinner()**
- **What it does:** Checks if the game has ended.
- **Why it matters:** Determines when the game is over and declares a winner or tie.

### **🔹 closeConnection()**
- **What it does:** Closes the player’s connection to the server.
- **Why it matters:** Prevents lingering connections after the game ends.

---

## **5. Key Takeaways for Other Teams**
🔹 This system **relies on a client-server model**, where the server **handles game logic and turn management**, while the clients **send moves and update their displays**.
🔹 **Turn-based interactions** are enforced, meaning only one player can move at a time.
🔹 **Game state synchronization** ensures both players see the same board at all times.
🔹 If you are working on other components, (GUI and Game logic teams), this document provides a **high-level understanding** of how to integrate with the existing system.

---

## **6. Note For Other Teams**
- Refer to the API documentation for more technical details.
- The API documentation provides a technical breakdown of function signatures, parameters, and expected behaviors, serving as a developer reference for implementing or modifying networking functions. In contrast, the Networking Function Explanations document is a simplified guide meant for external teams, explaining how the system works at a high level without requiring a deep dive into the code. While the API documentation is used to understand function implementation details, this document helps non-networking teams grasp the overall system flow and interactions to integrate with it effectively.
