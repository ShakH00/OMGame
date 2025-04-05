# **Networking System Overview & Planning (P3 up-to-date)**

## 📍 Docs & References

- [📊 FigJam Diagram (Architecture & Flow)](https://www.figma.com/board/dpFR9WEMYuxA74ZvipXcZc/process-loop?node-id=1-25&t=nhEXUiFAzE8vcAcv-1)
- [📄 Google Doc Master Plan](https://docs.google.com/document/d/1O3nZ0WbedHbkeMzC8PnDiTGf0OVOONgV-Xe5WJszYR0/edit?usp=sharing)
- 🔗 Videos for implementation concepts:
  - https://www.youtube.com/watch?v=HQoWN28H80w
  - https://www.youtube.com/watch?v=gLfuZrrfKes

---

## 🧠 System Architecture: Core Networking Flow

### **1. Game Initialization & Player Join**
- `PlayerT` connects to server via `connectToServer()`
- Receives player ID and matchmaking begins
- GUI launched or updated accordingly

### **2. Client Sends Move**
- Local move input triggers `sendButtonNum(strBNum)` from `PlayerT` (or `PlayerTStubs`)
- Message handled through `ClientSideConnection` or stub connection
- Sent to server

### **3. Server Validates & Updates**
- `GameServerT` receives move and calls `processGameLogicP1/P2()`
- Updates internal 2D board `server2dChar`
- Validity is assumed (to be improved via GameRules integration)

### **4. Server Broadcasts Update**
- Calls `send2dCharArray()` and/or `sendButtonNum()` (stubbed or real)
- Move or board sent to both clients
- Format standardization pending (currently raw char array or string)

### **5. Clients Receive Update**
- `PlayerT` or `PlayerTStubs` receives update via `receiveButtonNum()`
- GUI reflects board state and message updates
- Turn toggles handled locally

### **6. Chat Feature**
- Enabled using `ChatStubs.sendMessage()` and `getChatHistory()`
- Console-only for now, to be linked with GUI if time permits

### **7. Disconnection & Reconnection**
- Placeholder logic to detect socket drop and restore session
- `PlayerData` will be used to retain ELO and status across reconnect

---

## 🗂️ Key Code Components

| Component               | Description |
|-------------------------|-------------|
| `Networking.java`       | Placeholder for future abstraction or socket centralization |
| `GameServerT.java`      | Server entry point: handles sessions, move processing |
| `PlayerT.java`          | Client-side player logic: connects, sends moves, updates UI |
| `PlayerTStubs.java`     | Local GUI simulator for testing logic and flow |
| `PlayerData.java`       | POJO to track username, ID, and game ELOs |
| `PlayerDatabase.java`   | Central hub to manage and rank players |
| `stubs.java`            | Consolidated stubbed behaviors for DB, game, and chat |

---

## 🧩 Inter-Team Integration Strategy

| Team            | Role |
|-----------------|------|
| Game Logic      | Works with `GameRules`, `Board`, `Game.java` to define valid move logic |
| GUI             | Connects buttons and inputs to `PlayerT` methods; handles visual updates |
| Leaderboard/MM  | Integrates with `PlayerDatabase.java` and `PlayerData.java` for ranking |
| Auth/Profile    | May use `PlayerDataStubs` to simulate login/session profiles |

---

## 🎯 Networking Team Goals (P3)

- ✅ Deliver working stubs (server, chat, matchmaking)
- ✅ Simulate all main interactions using `PlayerTStubs`
- ✅ Modularize with clear file-level responsibilities
- 🔁 Integrate move validation + reconnection safety
- 📑 Refine and consolidate all documentation
- 🎨 Add matching diagrams and maintain consistency

---

## 🧪 Required Stub Coverage

| Feature            | Stub Source         | Example Method               |
|--------------------|---------------------|------------------------------|
| Server Hosting     | `GameServerStubs`   | `startServer()`, `acceptConnection()` |
| Player Records     | `PlayerDatabaseStubs` | `addPlayer()`, `getElo()`   |
| Game Session       | `PlayerDataStubs`   | `setUserID()`, `getAPlayerElo()` |
| Communication      | `PlayerTStubs`, `ClientSideConnection` | `sendButtonNum()`, `receiveButtonNum()` |
| Chat               | `ChatStubs`         | `sendMessage()`, `clearChat()` |

---

## 🧾 Deliverables Summary

| Item                      | Status |
|---------------------------|--------|
| Codebase integration      | In progress |
| Documentation (final)     | Ongoing (modular + updated) |
| Demo-ready stubs          | ✅ Complete |
| `README.md` networking    | Pending update |
| `ChangesMade.md`          | Pending (stub strategy + structure) |
| Git commit logs           | Ongoing |

---

## ✅ Individual D2L Deliverables

- `[UCID]_worklog_p3.xlsx`
- `[UCID]_peer_evaluations_p3.xlsx`
- `[UCID]_reflection_p3.pdf`
- `[UCID]_tech_demo_pNN.mp4`
- `AI_disclosure.txt`
