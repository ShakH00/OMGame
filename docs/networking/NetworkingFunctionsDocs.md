# 📡 Networking Function Docs (P3 Consolidated)

## Audience: Internal Developers, Integration Teams (GUI, Game Logic), and Testing

---

## 🧭 Purpose

This document consolidates two previous references: `apiDocumentation.md` and `networkingCodeExplanation.md`. It provides a **comprehensive breakdown of both the API interface and the internal code behavior** of the networking system for our multiplayer game. It reflects the **P3 iteration** of development, incorporating stubs, new features like chat, and backend integration plans.

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

---

## 📋 Server Methods (`GameServerT.java`)

### `acceptConnections()` ✅
Waits for two players to join, assigns roles, and spawns ServerSideConnection threads.

### `processGameLogicP1(String input)` / `processGameLogicP2(String input)` ✅
Parses input like "1,2" and updates the server2dChar[][] board. Assumes valid turn order.

### `sendButtonNum(String buttonNum, int receiverID)` 🟡
Stubbed. Will transmit moves to the specified opponent.

### `send2dCharArray(int receiverID)` 🟡
Stubbed. Will sync full game state with clients.

---

## 💬 Chat & Future Network Additions

Planned extension of networking layer includes:

- Chat relay system (via socket IO)
- Matchmaking state logic (see tentative_matchmaking_sessions_ideas.md)
- Server message formatting protocols

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

All backend profile operations occur through `PlayerDatabase.java`, which is internal (not part of network protocol).

### Sample Methods:
- `addPlayer(PlayerData pd)` – Add player
- `getTopTenPlayers()` – Return top ELO users
- `playerExists(String username)` – Lookup

These will support leaderboard/matchmaking or future login extensions.

---

## 🔄 Flow Summary

1. Player connects → receives ID
2. Player clicks UI → `sendButtonNum()` fires
3. Server thread receives → processes via `processGameLogic*()`
4. Board updated → sent via `send2dCharArray()`
5. Opponent receives move → `receiveButtonNum()`
6. Repeat

---

## ⚠️ Integration Notes

| Team         | Responsibility |
|--------------|----------------|
| GUI          | Connect UI buttons to networking triggers |
| Game Logic   | Define move structure + board validation |
| Auth/Profile | Tie PlayerData to login if added |

---

## 🧪 Stubbed vs Final – Function Status Table

| Function             | File/Class                 | Status     |
|----------------------|----------------------------|------------|
| sendButtonNum(...)   | GameServerT.java           | 🟡 Stubbed |
| send2dCharArray(...) | GameServerT.java           | 🟡 Stubbed |
| receiveButtonNum()   | PlayerT.java               | 🟡 Stubbed |
| run()                | ClientSideConnection.java  | 🟡 Stubbed |
| run()                | ServerSideConnection.java  | ✅ Working |
| connectToServer()    | PlayerT.java               | ✅ Working |
| closeConnection()    | PlayerT.java               | ✅ Working |

---

## 📎 Additional Resources

- See `error_handling_ideas.md` for disconnect flow
- See `tentative_matchmaking_sessions_ideas.md` for lobby planning
- Flowchart diagrams supplement this doc visually