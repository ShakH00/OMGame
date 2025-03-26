# API DOCUMENTATION
## File Name: apiDocumentation.md
## Iteration: P3
## Status: Being Finalized for current codebase, stubs, and backend design

### Networking API Specification – Multiplayer Game System (P3)
### Overview
   The Networking System handles real-time, turn-based multiplayer gameplay, including player input transmission, game state updates, and server-client synchronization.

This document outlines:

Core methods on both the client and server

Communication behavior

Stub status (implemented or placeholder)

Internal backend access points (e.g. PlayerDatabase.java) used to track player data, ELO, and rankings

This version reflects the Iteration 3 (P3) status of the codebase, including stubbed logic, profile data integration, and planned extensions. All function behaviors and interactions are up-to-date and designed for cross-team clarity.

#### Core Components

#### Server – GameServerT.java
   Accepts player connections

Validates moves and enforces turn order

Maintains game state (server2dChar[][])

Sends updates and move data to clients

(Eventually) communicates with player profiles / stats

#### Client – PlayerT.java
Connects to server

Sends player moves

Receives opponent moves and board updates

Handles client-side game state and UI hooks

#### Internal Utility – PlayerDatabase.java
Stores PlayerData (ELO, username)

Provides ranked lists, lookups, and mutation methods

Not directly exposed to clients – only server-side usage

#### API Functions
   🔹 Server Methods (GameServerT.java)
   
#### public void acceptConnections();

Purpose: Accepts two clients and starts sessions.

Behavior: Creates ServerSocket, accepts two clients, initializes threads.

Called by: Server main method

Status: ✅ Implemented

#### public void sendButtonNum(String buttonNum, int receiverID);
Purpose: Sends move from one player to the opponent.

##### Parameters:

buttonNum – the button number selected

receiverID – target client

Behavior: Sends move to client via DataOutputStream

Status: 🟡 Stubbed – logic pending final structure from Game Logic

#### public void send2dCharArray(int receiverID);
Purpose: Sends full game board to a player

Parameters: receiverID – client to update

Behavior: Sends serialized char[][] of board state

Status: 🟡 Stubbed – pending board format finalization

#### public void processGameLogicP1(String input);
#### public void processGameLogicP2(String input);
Purpose: Validate and apply move to board for P1/P2

Parameters: input – move (String)

Behavior: Parses position, updates board, switches turns

Note: May later be merged into a shared method

Status: ✅ Implemented

### 🔹 Client Methods (PlayerT.java)
#### Public void connectToServer();
Purpose: Establish socket connection to server

Behavior: Opens socket at port 30000, receives player ID

Called by: Client UI on startup

Status: ✅ Implemented

#### public void sendButtonNum(String strBNum);
Purpose: Sends player move to server

Parameters: strBNum – move as string

Behavior: Writes to DataOutputStream

Status: 🟡 Stubbed – behavior depends on finalized GUI/button interface

#### public String receiveButtonNum();
Purpose: Receives opponent move from server

Returns: Opponent's move string

Behavior: Reads from DataInputStream

Status: 🟡 Stubbed – structure may change once board sync is finalized

#### public void closeConnection();
Purpose: Ends connection cleanly

Behavior: Closes socket

Status: ✅ Implemented

### Player Profile / Backend Access (PlayerDatabase.java)
   These functions are not part of the direct client-server protocol, but are used by the server to manage persistent player data.

They may be exposed to other components (like GUI or matchmaking) through future APIs.

#### public boolean addPlayer(PlayerData pd);
Adds new player to database

Returns true on success

#### public boolean removePlayer(String username);
Removes player by username

Returns true if found and removed

#### public boolean playerExists(String username);
Returns true if player is in DB

#### public PlayerData getPlayer(String username);
Returns PlayerData object for given user

#### public List<PlayerData> getTopTenPlayers();
Returns top 10 players by ELO rating

#### public int size();
Returns number of players in the DB

## Data Flow Example

   1️⃣ Player 1 clicks button → `sendButtonNum()`  
   2️⃣ Server receives move → `processGameLogicP1()`  
   3️⃣ Server updates game state → `send2dCharArray()` → Player 2  
   4️⃣ Player 2 receives update → `receiveButtonNum()`  
   5️⃣ Repeat until `checkWinner()` returns true

## Notes & Integration Planning
   Stubbed methods will be gradually updated based on GUI/game logic input.

PlayerDatabase is expected to be integrated in P4 or used for leaderboard/matchmaking hooks.

Diagrams in the flowchart doc supplement this API for external teams.

Some client-server behavior may move to a SessionManager class later – TBD.

