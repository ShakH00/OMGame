# **Networking System Overview & Planning**

## 📍 Docs & References

- [📊 FigJam Diagram (Architecture & Flow)](https://www.figma.com/board/dpFR9WEMYuxA74ZvipXcZc/process-loop?node-id=1-25&t=nhEXUiFAzE8vcAcv-1)
- [📄 Google Doc Master Plan](https://docs.google.com/document/d/1O3nZ0WbedHbkeMzC8PnDiTGf0OVOONgV-Xe5WJszYR0/edit?usp=sharing)
- 🔗 Helpful videos for implementation:
  - https://www.youtube.com/watch?v=HQoWN28H80w
  - https://www.youtube.com/watch?v=gLfuZrrfKes

---

## 🧠 System Architecture: Network Flow (P3 Level)

### **1. Game Creation & Player Join**
- Player connects via GUI → triggers `connectToServer()`
- Assigned ID → passed to `PlayerT`/`Game.java`
- Game session initialized with `GameType`, `GameState`, and player profiles

### **2. Client Sends a Move**
- GUI triggers `sendMoveToServer(gameId, playerId, move)`
- Move sent through `ClientSideConnection` → server thread (`ServerSideConnection`)

### **3. Server Processes the Move**
- `GameServerT` relays input to `processGameLogic()` in `Game.java`
- Validated using `GameRules` and `Board` state
- If valid: updates board + score; if invalid: returns structured error

### **4. Server Broadcasts Updated State**
- Server invokes `sendButtonNum()` and/or `send2dCharArray()` to relay move
- Format handled via shared serialization logic (TBD)

### **5. Clients Receive Update**
- `ClientSideConnection` receives updated state
- `PlayerT` uses GUI handler to refresh display
- Turn indicators, win states updated via `GameState`

### **6. Disconnection & Reconnection Logic**
- Server detects dropped socket → marks player as inactive
- If reconnects in time: restores session using `PlayerData` + preserved `Game`
- Otherwise, match is forfeited and state closed

---

## 📌 Project Planning (P3)

### 🧩 Inter-Team Integration Strategy

| Team            | Integration Plan |
|-----------------|------------------|
| Game Logic      | Integrated with `Game.java`, `GameRules`, and `Board`. Networking validates move format + wraps server control. |
| Authentication  | Collaborate on binding `PlayerData` to user logins; ensure persistent state. |
| Leaderboard/MM  | Use `PlayerDatabase` for ELO stats and rankings. Ensure format consistency. |
| GUI             | GUI must react to all states – moves, turns, errors. Buttons call `sendMove()`, chat triggers `sendMessage()`. |
| Website         | Low priority – potential use for leaderboard display or archived games. |

⚠️ Integration Flow: **Game Logic → Networking → GUI**

---

## 🎯 Networking Objectives (P3 Focus)

- ✅ Deliver stubs across server, chat, session handlers
- ✅ Implement fallback-safe client behavior (disconnects, invalid moves)
- ✅ Begin chat integration via `ChatStubs`
- ✅ Diagram and document all system functions and flows
- 🔄 Respond to feedback re: doc structure and clarity
- 📹 Prepare for recorded demos and worklog documentation

---

## 👥 Team Roles (Latest Breakdown)

### 🔹 **Hatem + Nova – Docs, Planning, Integration**

- Reorganized `networkingFunctionDocs.md`, `NetworkingConcepts.md`, `NetworkErrorHandling.md`
- Consolidated diagrams and flowcharts into final structure
- Supporting code mapping and fallback scenarios

### 🔹 **Sultan + Uzair – Code & Research**

- Built stubs for network connection, messaging, player profile logic
- Developed `ChatStubs`, backend profile/ELO utils
- Working on real-time message handling and reconnection testing

---

## 🧭 System Structure Summary

| Component              | Description |
|------------------------|-------------|
| `Game.java`            | Holds game session metadata, logic, board, and state |
| `GameRules.java`       | Evaluates move legality and win conditions |
| `Board.java`           | Manages piece placement and board config |
| `PlayerData.java`      | Stores username, ELO, ID |
| `PlayerDatabase.java`  | Tracks ranked players, matchmaking eligibility |
| `GameServerT.java`     | Accepts connections, validates moves, distributes updates |
| `PlayerT.java`         | Connects client to server, handles GUI signals |
| `ServerSideConnection` | Thread managing inbound player messages |
| `ClientSideConnection` | Handles inbound messages from server to GUI |

---

## 📆 Timeline & Deliverables

### 🔧 Priorities
- 🔁 Refactor and finalize all docs for alignment
- 📎 Verify methods across files for consistency
- 🎨 Add visual diagrams to support planning

### 🧾 Submission Checklist

| Deliverable           | Status |
|-----------------------|--------|
| `README.md`           | Pending – must describe socket setup |
| `ChangesMade.md`      | Pending – summarize P3 work |
| `team.md`             | ✅ Completed |
| `git_log.csv`         | Ongoing – track actual work |
| `meetingNotes.md`     | In progress – ensure decisions documented |

---

## ✅ Individual Deliverables (To Submit on D2L)

- `[UCID]_worklog_p3.xlsx`
- `[UCID]_peer_evaluations_p3.xlsx`
- `[UCID]_reflection_p3.pdf`
- `[UCID]_tech_demo_pNN.mp4`
- `AI_disclosure.txt`

---

## 🧪 Stubbed Requirements (Must Exist)

| Feature Area       | Stubs Required |
|--------------------|----------------|
| Server Hosting     | `acceptConnections()`, session manager |
| Game Mechanics     | `Game`, `Board`, `GameRules` |
| Communication      | `sendButtonNum()`, `sendMessage()` |
| Reconnection       | Fallback if client drops mid-turn |
| Matchmaking        | ELO tracking, timed queue expansion |
