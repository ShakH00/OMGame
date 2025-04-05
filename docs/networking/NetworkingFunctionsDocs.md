# 📡 Networking Function Docs (P3 Consolidated)

## Audience: Internal Developers, Integration Teams (GUI, Game Logic), and Testing

---

## 🧭 Purpose

This document consolidates previous references (`apiDocumentation.md` and `networkingCodeExplanation.md`) into one comprehensive breakdown of both the **API interface** and the **internal code behavior** of the networking system for our multiplayer game. It reflects the **P3 iteration** of development and includes:

- Working and stubbed methods
- Planned chat and matchmaking systems
- Server and client behaviors
- Internal profile data utilities

---

## 🔌 System Components Overview

| Class/File                 | Role                                                                 |
|----------------------------|----------------------------------------------------------------------|
| GameServerT.java           | Central server logic – receives, validates, and relays moves         |
| ServerSideConnection.java | Thread handler for each server-connected player                      |
| PlayerT.java               | Main client logic (connects to server, sends/receives moves)         |
| ClientSideConnection.java | Handles incoming server data and UI handoff                          |
| PlayerDatabase.java        | Stores persistent player profiles and rankings                       |
| PlayerData.java            | POJO for usernames, ELO, session info                                |
| stubs.java                 | Central stub hub for game server, chat, database                     |
| PlayerTStubs.java          | Stub simulation of GUI + local networking interactions               |
| Networking.java            | Placeholder for final networking system abstraction                  |

---

## 📋 Server Methods (`GameServerT.java`)

### `acceptConnections()` ✅
Waits for two players to join, assigns roles, and spawns `ServerSideConnection` threads.

### `processGameLogicP1(String input)` / `processGameLogicP2(String input)` ✅
Parses move strings like "1,2" and updates `server2dChar[][]`. Assumes turn order and validity are managed.

### `sendButtonNum(String buttonNum, int receiverID)` 🟡
Stubbed. Transmits a move to the target player.

### `send2dCharArray(int receiverID)` 🟡
Stubbed. Will sync the updated board to a specified client.

---

## 💬 Chat & Future Network Additions

### **Chat System (`stubs.ChatStubs`)**
- `sendMessage()` to append to history
- `getChatHistory()` for retrieval
- `clearChat()` to reset conversation

### **Matchmaking & Sessions**
- Will use `PlayerData`, ELO, and `PlayerDatabase`
- Needs session manager (planned)

---

## 🤝 Server-Side Threading (`ServerSideConnection.java`)

### `run()` ✅
Listens for messages and routes them to `GameServerT` logic.

### `sendButtonNum()` / `send2dCharArray()` 🟡
Stubbed methods for relaying board/move data.

---

## 🖥️ Client Methods (`PlayerT.java`)

### `connectToServer()` ✅
Establishes connection to server, receives player ID, sets up game loop.

### `sendButtonNum(String strBNum)` 🟡
Stubbed. Sends move string to server.

### `receiveButtonNum()` 🟡
Stubbed. Retrieves move from server and updates GUI accordingly.

### `closeConnection()` ✅
Gracefully closes the socket.

---

## 🧪 PlayerTStubs.java – Simulation Logic

### Purpose:
Allows local testing of player move logic, GUI interaction, and networking placeholders.

- Uses fake sockets and simulated players
- Mimics click -> move -> board update loop
- Interfaces with stubs, not live server

Useful for demo, testing, and GUI debugging.

---

## 🧠 PlayerData & Backend Utilities

### `PlayerData.java`
Stores a player's ID, name, and ELO per game mode.

### `PlayerDatabase.java`
- Add, remove, or fetch players
- Adjust ELO scores
- Retrieve top-N players

Both files will serve as the backend for matchmaking and leaderboard features.

---

## 🔄 Flow Summary

1. Client connects via `connectToServer()`
2. Click sends move using `sendButtonNum()`
3. Server receives → `processGameLogic*()` applies it
4. Server updates board → sends via `send2dCharArray()`
5. Opponent receives and sees update via `receiveButtonNum()`
6. Game continues until win/draw condition

---

## ⚠️ Integration Notes

| Team         | Responsibility                                                                 |
|--------------|----------------------------------------------------------------------------------|
| GUI          | UI buttons trigger move send, display updates, handle disconnects               |
| Game Logic   | Owns move format, validation, game rule enforcement                             |
| Profile/Auth | Links `PlayerData` to login and session storage                                 |

---

## 📋 Stub Status Table

| Function/Class             | Status     | Notes                                     |
|---------------------------|------------|-------------------------------------------|
| `acceptConnections()`     | ✅         | Basic socket listener and thread manager  |
| `processGameLogic*()`     | ✅         | Parses + updates board                    |
| `sendButtonNum()`         | 🟡 Stubbed | Pending move structure/target logic       |
| `send2dCharArray()`       | 🟡 Stubbed | Format of board data to be finalized      |
| `PlayerTStubs.java`       | ✅         | Runs locally as GUI + pseudo-network sim  |
| `PlayerDatabase.java`     | ✅         | Internal DB for player tracking           |
| `ChatStubs`               | ✅         | Simple message queue with console output  |

---

## 📎 Additional Resources

- `error_handling_ideas.md` – Disconnection flow, reconnection windows
- `NetworkingConcepts.md` – Stub roles, session planning, high-level overview
- Diagrams available in shared FigJam + docs folder

