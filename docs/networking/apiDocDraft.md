# **Networking Team \- API Specification (P-1 Draft)**

##  Core Functions

These are the functions we are providing to connect the networking system with the rest of the project.

### Send a Move to the Server

public void sendMoveToServer(String gameId, String playerId, String move);

* **Purpose:** Sends a move made by a player to the game server.
* **Parameters:**
  * `gameId` (String) – Unique identifier for the game session.
  * `playerId` (String) – The ID of the player making the move.
  * `move` (String) – The move in a predefined format (e.g., "E2 to E4" for Chess).
* **Used by:** Game Logic Team (when a player makes a move).

### Process the Move on the Server

public String processMove(String gameId, String move);

* **Purpose:** Processes the move, checks if it's valid, and updates the game state.
* **Parameters:**
  * `gameId` (String) – The game session ID.
  * `move` (String) – The move to be processed.
* **Returns:** JSON-like String containing the updated game state.
* **Used by:** Networking Team (to validate and apply moves).

### Send Updated Game State to Clients

public void sendGameStateToClients(String gameId, String gameState);

* **Purpose:** Broadcasts the latest game state to both players.
* **Parameters:**
  * `gameId` (String) – The game session ID.
  * `gameState` (String) – The updated board state in JSON format.
* **Used by:** Networking Team (to sync players).

### Receive Updated Game State (Client Side)

public String receiveUpdatedGameState(String gameId);

* **Purpose:** Retrieves the latest board state from the server.
* **Parameters:**
  * `gameId` (String) – The game session ID.
* **Returns:** JSON-like String containing the latest game state.
* **Used by:** GUI Team (to update the board visually).

##  Data Flow Example

1. **Player A makes a move** → Calls `sendMoveToServer()`.
2. **Move is sent to the Game Server** → Calls `processMove()`.
3. **Game Server updates the state** → Calls `sendGameStateToClients()`.
4. **Both players receive the updated board** → Calls `receiveUpdatedGameState()`.

##  Next Steps for Implementation (me/team depending on roles)

* **Write function stubs** for these functions (placeholders, no real networking yet).
* **Discuss edge cases** (e.g., disconnects, invalid moves, lag).
* **Test API integration** with the Game Logic and GUI teams.

**Potential Diagrams after stubs ?**

## Sequence Diagram

### Purpose:

* Shows how moves are sent, processed, and received in real-time.
* Helps visualize how networking functions will interact before coding starts.

### What to Include:

* **Actors:** Player A, Networking Stub, Game Server, Player B.
* **Lifelines:** Vertical lines showing the actions over time for each component.
* **Messages:**
  * `sendMoveToServer`() (Player A → Networking Stub)
  * `processMove`() (Server validates the move)
  * `sendGameStateToClients`() (Server updates both players)
  * `receiveUpdatedGameState`() (Player B updates board)

## Class Diagram

### Purpose:

* Defines the key components (classes) in the networking system.
* Helps structure the code before writing it.

### What to Include:

* **Classes:** GameServer, NetworkingStub, ClientHandler, etc.
* **Methods:** sendMoveToServer(), processMove(), receiveUpdatedGameState().
* **Relationships:**
  * The GUI team calls sendMoveToServer() when a move is made.
  * The server calls processMove() to validate the move.
  * The server calls sendGameStateToClients() to update players.


## Flowchart (For Handling Edge Cases)

### Purpose:

* Maps out decision-making processes in networking.
* Helps identify edge cases like disconnections or lag.

### Example Scenarios to Map:

* What happens if two players move at the same time?
* How does the game handle a player disconnecting mid-game?
* How does the server validate moves before applying them?

### Why It's Useful:

* Ensures your team plans for potential failures before writing code.
* Helps prevent game-breaking issues in later stages.
