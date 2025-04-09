# 📡 Networking Function Docs (P3 Final Consolidated)

## Audience: Internal Developers, Integration Teams (GUI, Game Logic), and Testing

---

## 🧭 Purpose

This document consolidates all networking components for P3, including:
- The full **server-client system** (`PlayerT`, `GameServerT`, etc.)
- **Stubbed systems** for matchmaking, chat, and local simulation
- **Turn-sync integration** into each of the four multiplayer games
- Final implementation details of `Networking.java` as used in Checkers, Connect4, Chess, and Tic Tac Toe

It serves as the master reference for understanding how networking is currently simulated and how it will be upgraded in future phases.

---

## 🔌 System Components Overview

| Class/File                 | Role                                                                 |
|----------------------------|----------------------------------------------------------------------|
| `GameServerT.java`         | Central server logic – receives, validates, and relays moves         |
| `ServerSideConnection.java`| Thread handler for each connected player                             |
| `PlayerT.java`             | Client logic – connects to server, sends/receives data               |
| `ClientSideConnection.java`| Handles incoming server data and GUI handoff                         |
| `PlayerDatabase.java`      | Stores persistent player profiles and rankings                       |
| `PlayerData.java`          | Stores player info, ELO, and session data                            |
| `PlayerTStubs.java`        | Local simulation of multiplayer (no socket)                          |
| `stubs.java`               | Stub hub for server, chat, and database placeholders                 |
| `Networking.java`          | Internal multiplayer abstraction used by all 4 games                 |

---

## 📂 Game Integration (Networking.java)

All four games now use the internal multiplayer sync abstraction from `Networking.java`.

### 🟢 Purpose:
Simulate turn-based multiplayer using `sendGame()` and `recieveGame()` stubs, without actual socket communication.

### 📋 Methods:

- `sendGame(Game g)`  
  Caches the game object and enters listen mode

- `recieveGame()`  
  Returns cached game object for opponent

- `listenMode()`  
  Simulates "waiting" by printing a message and preventing input

- `getTime()`  
  Logs timestamps in `[Net: <timestamp>]` format

### 🧠 Game Integration Summary

| Game        | `recieveGame()` Location             | `sendGame()` Location                  |
|-------------|--------------------------------------|-----------------------------------------|
| Checkers    | `CheckersController → start()`       | `Checkers → move()`                     |
| Connect 4   | `Connect4Controller → start()`       | `Connect4 → move()`                     |
| Chess       | `ChessController → start()`          | `Chess → move()`                        |
| Tic Tac Toe | `TicTacToeController → start()`      | `TicTacToe_Logic → play()`              |

Each call includes timestamped debug messages for visual verification.

---

## 🧠 Server-Side Components

### `GameServerT.java`
- `acceptConnections()` ✅: Accepts two client connections and starts their threads
- `processGameLogicP1()` / `processGameLogicP2()` ✅: Handles move updates to the internal board
- `sendButtonNum()` 🟡: Stub for transmitting moves to clients
- `send2dCharArray()` 🟡: Stub for sending the entire board state

### `ServerSideConnection.java`
- `run()` ✅: Thread loop to handle messages from a connected player
- `sendButtonNum()` / `send2dCharArray()` 🟡: Placeholder methods for future data transfer

---

## 🖥️ Client-Side Components

### `PlayerT.java`
- `connectToServer()` ✅: Establishes socket connection to server and gets player ID
- `sendButtonNum(String)` 🟡: Stub for sending a move
- `receiveButtonNum()` 🟡: Stub for receiving opponent’s move
- `closeConnection()` ✅: Ends the session gracefully

### `ClientSideConnection.java`
- Acts as listener for server messages and updates GUI components accordingly
- Currently ties into GUI stubs or placeholder logic

---

## 🧪 Simulation: `PlayerTStubs.java`

Used for local testing without live sockets. Includes:
- Simulated clicks → move → game state update
- Integration with GUI for demo/testing
- Mimics network flow using internal stubs

---

## 💬 Chat System (Stubbed)

### `ChatStubs.java`
- `sendMessage(String message)`: Appends message to chat history
- `getChatHistory()`: Retrieves all messages
- `clearChat()`: Wipes chat history

Useful for internal testing and proof-of-concept.

---

## 🎯 Matchmaking (Planned)

Matchmaking will rely on:

- `PlayerData.java`: Stores player ELO, preferences, and history
- `PlayerDatabase.java`: Stores all profiles, supports filtering/search
- Planned `MATCHMAKING_TABLE`: Would contain fields like GAME, MATCHMAKING_START_TIME, ELO, ELO_RANGE, SOCKET_INFO

🧩 Players in queue would be compared every few seconds and paired based on matching criteria. Handshake initiation would trigger actual game connection.

---

## 🧠 Backend Utilities

### `PlayerData.java`
- POJO holding player name, ID, and ELO per game

### `PlayerDatabase.java`
- Add, remove, search, and update player data
- Core of future ranking and session tracking systems

---

## 🔄 Full Turn-Based Flow (Simulated or Real)

1. Player connects to the server (or starts game locally)
2. `recieveGame()` is called → loads latest board
3. Player makes a move
4. `sendGame()` is called → sends updated board
5. Opponent calls `recieveGame()` on their turn
6. Loop continues until game ends (win/loss/draw)

In the current version, steps 2 and 4 are stubbed, but **fully implemented and demo-ready**.

---

## 📋 Stub Status Table

| Component/Method         | Status     | Notes                                         |
|--------------------------|------------|-----------------------------------------------|
| `Networking.java`        | ✅ Done    | Fully integrated in all 4 games                |
| `sendGame()`             | ✅ Done    | Simulated send logic with debug print         |
| `recieveGame()`          | ✅ Done    | Simulated receive logic with debug print      |
| `listenMode()`           | ✅ Done    | Simulates player wait mode                    |
| `getTime()`              | ✅ Done    | Timestamps all network actions                |
| `GameServerT.java`       | ✅ Base    | Accepts connections, basic logic functional   |
| `sendButtonNum()`        | 🟡 Stub    | Waiting on move structure                     |
| `send2dCharArray()`      | 🟡 Stub    | Waiting on serialization format               |
| `PlayerT.java`           | ✅ Core    | Connects, listens, sends basic info           |
| `PlayerTStubs.java`      | ✅ Done    | Used for internal simulation + GUI tie-ins    |
| `ChatStubs.java`         | ✅ Basic   | Works in console, needs GUI later             |
| `PlayerDatabase.java`    | ✅ Done    | Add/remove players and ELO                    |

---

## ⚠️ Integration Notes

| Team         | Responsibility                                                                 |
|--------------|----------------------------------------------------------------------------------|
| GUI          | Calls send/receive methods at click and render moments                          |
| Game Logic   | Owns turn flow, validation, and win conditions                                  |
| Networking   | Handles state transfer, turn simulation, and debug logs                         |
| Profile/Auth | Links `PlayerData` and session data to login                                     |

---

## 📎 Additional Resources

- `error_handling_ideas.md`: Reconnect flows, invalid state protection
- `NetworkingConcepts.md`: High-level planning for session handling
- Diagrams in shared whiteboard and planning folders

---

## ✅ Final Notes

- ✅ All game logic now fully supports turn-based networking via stubs
- ✅ Future upgrades (e.g., sockets, matchmaking) will only affect `Networking.java` and backend
- ✅ Code is stable, testable, and ready for demo

This is the current networking architecture at the end of P3.
