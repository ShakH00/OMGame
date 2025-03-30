## Networking System – Code Behavior & Structure (P3 Edition)

## Iteration: P3

## Audience: Internal developers, integration teams (GUI/Game Logic), testing/support

## Purpose: Explain networking system logic and responsibilities without requiring deep code inspection

## 1. System Overview

### The networking system manages:

- Real-time client-server communication

- Player session management

- Turn-based move validation

- Synchronized game state transmission

#### It uses:

- TCP sockets for communication

- Custom logic per player (via ServerSideConnection)

- Internal 2D board state (char[][]) shared across clients

- A planned expansion into player data management via PlayerData & PlayerDatabase

#### The system consists of four core classes:

- GameServerT – Server logic

- ServerSideConnection – Handles each player’s server-side socket

- PlayerT – Client logic

- ClientSideConnection – Client socket handler

## 2. Core Code Behavior

### 🔹 GameServerT.java

#### ✅ acceptConnections()

- Listens on port 30000 for 2 players

- Assigns player1 and player2 as ServerSideConnection instances

- Sends unique player IDs (1 or 2)

- Starts a thread per connection

#### ✅ processGameLogicP1(String input) / processGameLogicP2(String input)

- Parses input as position (e.g., "1,2")

- Updates shared board (server2dChar[][]) with 'X' or 'O'

- Does not currently validate turn order or reject duplicates (relies on game logic to reject)

#### 🟡 sendButtonNum(String buttonNum, int receiverID)

- Sends a move to the opposing player (by ID)

- This is where move relaying occurs

- Currently stubbed — logic to determine target player and send is incomplete

#### 🟡 send2dCharArray(int receiverID)

- Serializes char[][] board and sends it to a client

- Used to sync board between players after a move

- Also stubbed — missing formatting and serialization details

### 🔹 ServerSideConnection.java

#### ✅ run()

- Constantly reads messages from its associated client

- Relays them to GameServerT for logic processing

#### 🟡 sendButtonNum() & send2dCharArray()

- Methods used to send data back to the client (move or board)

- Invoked by GameServerT depending on receiverID

##### 📝 Note: These methods are essential for player-to-player communication. As of P3, they're structured but stubbed — data formatting, transmission, and recovery are pending.

### 🔹 PlayerT.java

#### ✅ connectToServer()

- Connects to localhost:30000

- Receives player ID from server

- Instantiates ClientSideConnection and GUI

#### 🟡 sendButtonNum(String strBNum)

- Sends player move to server via socket

- Assumes input is in valid format (string index or coordinate)

- Needs stronger failure handling

#### 🟡 receiveButtonNum()

- Reads move from server

- Updates local GUI accordingly (not yet tied into full UI system)

#### ✅ closeConnection()

- Closes socket cleanly

### 🔹 ClientSideConnection.java

#### ✅ run()

- Waits for input from server (opponent move or game update)

- Passes it to the GUI via PlayerT methods

#### 🟡 sendButtonNum() / receiveButtonNum()

- These mirror the server-side methods

- Function as socket send/receive wrappers

- Implementation is minimal but foundational

### 🔹 PlayerData.java and PlayerDatabase.java (Internal Modules)

- Used by server only (not part of client-server messages yet)

- Stores player usernames, ELO, and other stats

- May be used for matchmaking, leaderboards, or profiles in later phases

## Interaction Flow Summary

- PlayerT connects to server → receives ID
- Player clicks button → PlayerT.sendButtonNum()
- ClientSideConnection sends move to server
- ServerSideConnection receives move
- GameServerT processes move → updates board
- GameServerT relays move to opponent
- Opponent’s PlayerT receives move → updates UI
- Repeat until game ends

### 📝 Note: Disconnection, timeout, and invalid move handling are discussed in error_handling_ideas.md.

## 4. Integration Considerations for Other Teams

### GUI Team:

- Ensure buttons trigger sendButtonNum() in PlayerT

- Hook up board updates to output of receiveButtonNum()

- Handle “waiting for opponent…” states

### Game Logic Team:

- Clarify where move validation should occur (currently inside GameServerT.processGameLogic)

- Share format for move strings (e.g., "2,3")

### Database/Profiles:

- If player identity becomes persistent, tie PlayerData to login/connection

- Decide whether PlayerDatabase is memory-only or file-backed

## 5. Stub Status Overview

| Function             | File                      | Status     |
| -------------------- | ------------------------- | ---------- |
| sendButtonNum(...)   | GameServerT.java          | 🟡 Stubbed |
| send2dCharArray(...) | GameServerT.java          | 🟡 Stubbed |
| receiveButtonNum()   | PlayerT.java              | 🟡 Stubbed |
| sendButtonNum()      | PlayerT.java              | 🟡 Stubbed |
| run()                | ClientSideConnection.java | 🟡 Stubbed |
| run()                | ServerSideConnection.java | 🟡 Stubbed |
