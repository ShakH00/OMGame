# 📡 Networking Function Docs (P3 Consolidated)

## Audience: Internal Developers, Integration Teams (GUI, Game Logic), and Testing

---

## 🧭 Purpose

This document consolidates two previous references: `apiDocumentation.md` and `networkingCodeExplanation.md`. It provides a **comprehensive breakdown of both the API interface and the internal code behavior** of the networking system for our multiplayer game. It reflects the **P3 iteration** of development, incorporating stubs, chat features, and integration hooks for the core `Game` system.

---

## 🔌 System Components Overview

| Class/File               | Role |
|--------------------------|------|
| GameServerT.java         | Central server logic – receives, validates, and relays moves |
| ServerSideConnection.java| Thread handler for each server-connected player |
| PlayerT.java             | Main client logic (connects to server, sends/receives moves) |
| ClientSideConnection.java| Handles incoming server data and UI handoff |
| PlayerDatabase.java      | Stores persistent player profiles and rankings |
| PlayerData.java          | POJO for usernames, ELO, session info |
| Game.java                | Abstract logic for turn handling, win checking, etc. |
| Board.java               | Game board container (2D) |
| GameState.java / Enum    | Win/loss/draw/game-in-progress tracking |
| Player.java              | Server-tracked representation of players |

---

## 📋 Server Methods (`GameServerT.java`)

### `acceptConnections()` ✅
Waits for two players to join, assigns roles, and spawns ServerSideConnection threads.

### `processGameLogicP1(String input)` / `processGameLogicP2(String input)` ✅
Parses input like "1,2" and updates the server2dChar[][] board.  
📝 In the future, this will call into `Game.move()` to align with new game state logic.

### `sendButtonNum(String buttonNum, int receiverID)` 🟡
Stubbed. Will transmit moves to the specified opponent.

### `send2dCharArray(int receiverID)` 🟡
Stubbed. Will sync full game state with clients.

---

## 💬 Chat & Future Network Additions

Planned or partially implemented extensions:

- Chat relay system (via socket IO)
- Matchmaking logic (`GameType`, `PlayerData`)
- Graceful disconnect + reconnect handling
- Server-side validation using `GameRules.java`

---

## 🤝 Server-Side Threading (`ServerSideConnection.java`)

### `run()` ✅
Main loop: receives client input and sends responses back.

### `sendButtonNum()` / `send2dCharArray()` 🟡
Stubbed versions of outbound message logic.

---

## 🖥️ Client Methods (`PlayerT.java`)

### `connectToServer()` ✅
Establishes socket to server and initializes session.

### `sendButtonNum(String strBNum)` 🟡
Sends player’s move string to server.

### `receiveButtonNum()` 🟡
Receives opponent's move from server and hands off to GUI.

### `closeConnection()` ✅
Ends the client-server session gracefully.

---

## 📶 Client-Side Socket Handling (`ClientSideConnection.java`)

### `run()` ✅
Monitors socket for server updates.

### `sendButtonNum()` / `receiveButtonNum()` 🟡
Placeholder wrappers for player-server messaging.

---

## 🧠 PlayerData & Backend Utilities

Handled by `PlayerDatabase.java`. These methods support persistence and can be extended for login and leaderboard features.

Example methods:

- `addPlayer(PlayerData pd)`
- `playerExists(String username)`
- `getTopTenPlayers()`

---

## 🧩 Integration with Game Logic System (NEW)

With the addition of `Game.java`, `Board.java`, `Player.java`, and `GameState`, the networking system now connects more deeply with backend logic.

### What This Means:

- The server no longer just sends button numbers – it tracks and transmits full board state using `Board.java`.
- The turn and win logic now lives in `Game.java`, which includes:
    - `move(...)`
    - `checkWinCondition(...)`
    - `getGameState()` (based on `GameState.java`)
- Client messages will eventually map to `Game` method calls, and updated states will be transmitted via `send2dCharArray()`.

🧠 Developers should treat networking as a relay and sync engine for what the `Game` class determines as legal.

---

## 🔄 Networking Flow Summary

1. Client connects to server → gets ID
2. Client clicks a button → `sendButtonNum()`
3. Server thread parses and passes to `processGameLogic()` → calls into `Game.move()`
4. Game logic updates → new board and state sent via `send2dCharArray()`
5. Client receives → GUI updates
6. Repeat until `Game.getGameState()` returns win/draw/end

---

## ⚠️ Integration Roles

| Team         | Responsibility |
|--------------|----------------|
| GUI          | Connect GUI events to client methods like `sendButtonNum()` |
| Game Logic   | Ensure `Game.move()` handles input from server, and returns valid board updates |
| Profiles     | Enable persistence for `Player.java` across sessions if needed |
| Matchmaking  | Interface with `PlayerData` and `GameType` to launch different games or assign fair matchups |

---

## 🧪 Stubbed vs Final – Status Table

| Function             | File/Class                 | Status     |
|----------------------|----------------------------|------------|
| sendButtonNum(...)   | GameServerT.java           | 🟡 Stubbed |
| send2dCharArray(...) | GameServerT.java           | 🟡 Stubbed |
| receiveButtonNum()   | PlayerT.java               | 🟡 Stubbed |
| run()                | ClientSideConnection.java  | 🟡 Stubbed |
| run()                | ServerSideConnection.java  | ✅ Working |
| connectToServer()    | PlayerT.java               | ✅ Working |
| closeConnection()    | PlayerT.java               | ✅ Working |
| move()               | Game.java                  | ✅ Implemented |
| getGameState()       | Game.java / GameState.java | ✅ Implemented |

---

## 📎 Additional References

- `error_handling_ideas.md` – disconnects and retry logic
- `NetworkingSystemOverview.md` – project management and planning
- Flowcharts and class diagrams are hosted on [Figma](https://www.figma.com/board/dpFR9WEMYuxA74ZvipXcZc/process-loop?node-id=1-25)
