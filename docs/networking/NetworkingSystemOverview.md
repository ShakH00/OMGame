# 🌐 Networking System Overview & Final Implementation (P3)

## 📍 Docs & References

- [📊 FigJam Diagram (Architecture & Flow)](https://www.figma.com/board/dpFR9WEMYuxA74ZvipXcZc/process-loop?node-id=1-25&t=nhEXUiFAzE8vcAcv-1)
- [📄 Google Doc Master Plan](https://docs.google.com/document/d/1O3nZ0WbedHbkeMzC8PnDiTGf0OVOONgV-Xe5WJszYR0/edit?usp=sharing)
- 🎥 Helpful implementation resources:
  - https://www.youtube.com/watch?v=HQoWN28H80w
  - https://www.youtube.com/watch?v=gLfuZrrfKes

---

## 🧠 System Architecture Overview (Live + Stubbed)

Our P3 networking system consists of **two parallel flows**:

1. A **live server-client architecture** (`PlayerT`, `GameServerT`, `ServerSideConnection`, etc.)
2. A ** multiplayer simulation layer** via `Networking.java`, used across all 4 games

Both systems are designed to run independently, and the system is **fully implemented and testable** in P3.

---

## 🔌 Multiplayer Turn System (`Networking.java`)

| Method             | Description                                                         |
|--------------------|----------------------------------------------------------------------|
| `sendGame(Game g)` | Simulates sending the updated game state                            |
| `recieveGame()`    | Simulates receiving the game state from the opponent                |
| `listenMode()`     | Pauses input to simulate turn-based wait                            |
| `getTime()`        | Adds timestamps to debug messages                                   |

### ✅ Integrated Into:

| Game        | `recieveGame()` Location             | `sendGame()` Location                  |
|-------------|--------------------------------------|----------------------------------------|
| Checkers    | `CheckersController → start()`       | `Checkers → move()`                    |
| Connect 4   | `Connect4Controller → start()`       | `Connect4 → move()`                    |
| Chess       | `ChessController → start()`          | `Chess → move()`                       |
| Tic Tac Toe | `TicTacToeController → start()`      | `TicTacToe_Logic → play()`             |

All calls include debug logs with timestamps for traceability.

---

## 🧭 Server-Client Flow (Live System)

### 1. `PlayerT` connects via `connectToServer()`
### 2. `GameServerT` accepts two clients and spawns threads (`ServerSideConnection`)
### 3. Moves are handled via:
- `sendButtonNum()` (client to server)
- `processGameLogicP1/P2()` (validate and apply move)
- `send2dCharArray()` (send updated board to both players)
### 4. `ClientSideConnection` receives update and pushes to GUI
### 5. Disconnects trigger pause, reconnection timer, or forfeit

---

## 🧱 Inter-Team Integration Plan

| Team             | Integration Notes                                                                                   |
|------------------|-----------------------------------------------------------------------------------------------------|
| Game Logic       | Synced turn validation (`GameState`, `isValidMove()`); accepts networking hooks                    |
| GUI              | Triggers networking logic at move points; displays debug + error prints                             |
| Authentication   | Session-based reconnects (planned); userID + reconnect token system proposed                       |
| Leaderboard/MM   | Tied to `PlayerData`, `PlayerDatabase`; matchmaking plan includes ELO filtering, session table      |
| Web              | Nova owns deployment stack; coordination via Cloudflare and GitHub Pages                            |

⏳ Integration Order: **Game Logic → Networking/Auth → GUI → Leaderboard**

---

## 🎯 Networking Objectives (Completed in P3)

- ✅ Fully stubbed `Networking.java` methods
- ✅ Integrated multiplayer logic into all games
- ✅ Connected controllers and logic layers cleanly
- ✅ Simulated turn handoff, blocking, and logging
- ✅ Drafted future real-time connection structure
- ✅ Maintained game functionality and debuggability

---

## 🛠️ Code Architecture

| File/Class             | Role                                                       |
|------------------------|------------------------------------------------------------|
| `Networking.java`      | Multiplayer stub layer, integrated in all games            |
| `GameServerT.java`     | Core live server (accepts clients, processes moves)        |
| `PlayerT.java`         | Main client interface (connects, sends, receives)          |
| `ClientSideConnection` | Reads server updates and forwards to GUI                   |
| `ServerSideConnection` | Threaded handler for incoming client messages              |
| `PlayerDatabase.java`  | ELO and user profile management                            |
| `PlayerData.java`      | Holds session-specific user information                    |

---

## 🧪 Chat, Matchmaking, Reconnect (Planned + Stubbed)

### 🗨️ Chat (Stubbed in `ChatStubs.java`)
- `sendMessage(String)`
- `getChatHistory()`
- `clearChat()`

### 🎮 Matchmaking (Planned)
- Player queue with game type, ELO, acceptable range
- Reconnectable via session ID
- Socket info + player metadata stored in `MATCHMAKING_TABLE`

### ♻️ Reconnection Flow
- `PlayerT.reconnectRequest(sessionID)` to resume
- 30-second window before forced forfeit
- Full flow documented in `errorHandlingImplementation.md`

---

## 🛡️ Safeguards & Error Recovery

Implemented and planned protections:

- ✅ `listenMode()` to block input while waiting
- ✅ `GameState` turn locking
- ✅ `getTime()` logging for all actions
- 🛠️ Planned: `retrySendMove()` with 3-attempt logic
- 🛠️ Planned: `ping()` heartbeat + timeout fallback
- ✅ Reconnect planning in `PlayerTStubs` and `errorHandlingImplementation.md`

---

## 👥 Team Division & Contributions

### 🔹 Hatem + Nova + Uzair + Sultan
- Got together as a team, collectively worked on implemeting functional networking into the codebase based on all stubs and plans, by working with other teams, assigning roles, and getting it done.


## 📋 Deliverables Checklist

| Deliverable              | Status |
|--------------------------|--------|
| `README.md`              | ✅ Includes networking setup summary |
| `NetworkingFunctionDocs.md` | ✅ Final architecture & API usage |
| `errorHandlingImplementation.md` | ✅ Full recovery, retry, reconnect logic |
| `ChangesMade.md`         | ✅ Turn integration, stub system, team summary |
| `meetingNotes.md`        | ✅ Final reviews in progress |
| Git Logs / Commits       | ✅ Traceable and clear contributions |
| Tech Demo (12 min)       | 🧠 To include turn loop, chat, reconnect explanation |

---

## ✅ Summary

- We successfully built a modular, testable multiplayer system via stubs.
- All four games are turn-synchronized using `sendGame()` and `recieveGame()`.
- Full documentation, flow diagrams, and reconnect/error plans are in place.
- We’re prepared for both demo and future socket-based networking extensions.

This document finalizes the P3 networking contribution for internal and cross-team use.
