
# **Networking System Overview & Planning**

## 📍 Docs & References

- [📊 FigJam Diagram (Architecture & Flow)](https://www.figma.com/board/dpFR9WEMYuxA74ZvipXcZc/process-loop?node-id=1-25&t=nhEXUiFAzE8vcAcv-1)
- [📄 Google Doc Master Plan](https://docs.google.com/document/d/1O3nZ0WbedHbkeMzC8PnDiTGf0OVOONgV-Xe5WJszYR0/edit?usp=sharing)
- 🔗 Helpful videos for implementation:
    - https://www.youtube.com/watch?v=HQoWN28H80w
    - https://www.youtube.com/watch?v=gLfuZrrfKes

---

## 🧠 System Architecture: Network Flow (Planning-Level)

### **1. Client Sends a Move**
- GUI triggers `sendMoveToServer(gameId, playerId, move)`
- `NetworkingStub` forwards to `GameServer`

### **2. Server Processes the Move**
- Validates move using `processMove(gameId, move)`
- If valid: updates game state
- If invalid: returns error

### **3. Server Broadcasts Game State**
- Uses `sendGameStateToClients(gameId, gameState)`
- Sends updated board to both clients

### **4. Clients Receive Update**
- GUI calls `receiveUpdatedGameState(gameId)`
- Board visually updates

### **5. Disconnection & Reconnection Logic**
- On disconnect → player marked inactive
- On reconnect → server sends latest game state
- Move in progress gets revalidated

---

## 📌 Project Planning (P3)

### 🧩 Inter-Team Integration Strategy

| Team            | Integration Plan                                                                                                                         |
|-----------------|------------------------------------------------------------------------------------------------------------------------------------------|
| Game Logic      | Discuss with first, as how to best suit their needs and wrap around their code. We can offer some feedback as to what helps us most.     |
| Authentication  | Work closely with in a more cohesive manner as to explain technical limitations and come up with a solution.                             |
| Leaderboard/MM  | Work closely in a cohesive manner as to how to store game history and communicate it.                                                    |
| GUI             | Work with GUI last as to how to surface network specific actions (connect, disconnect, authentication, etc.)                             |
| Website         | Nova owns domain, CF Dashboard (DNS and Site Hosting on CF Pages), and Email hosting; speak to her RE: website/email changes for access. |

⚠️ Integration flow: Will have our code merged between Game Logic and GUI phases: **GL -> Net/Auth/LB/MM -> GUI** 

---

## 🎯 Networking Objectives

- ✅ Develop **client-side stubs** as early failsafes.
- 🔁 Transition to **fully functional server-client networking** later.
- 📘 Finalize **clear, complete documentation**.
- 🔗 Coordinate closely with GUI/Game Logic for smooth handoff.
- 🔄 Reflect on **feedback from P2** and act on it.
- 💬 Contribute to planning, diagrams, and Git logs.
- 📹 Prepare for **demo videos** and **individual deliverables**.

---

## 👥 Team Division & Responsibilities

### 🔹 **Hatem + Nova – Docs, Planning, Light Code**

#### ✅ Refined & Delivered Docs:
- `networkingCodeExplanation.md` ✅
- `apiDocumentation.md` ✅
- `error_handling_ideas.md` ✅
- `tentative_matchmaking_sessions_ideas.md` ✅

#### 🧠 Diagrams Completed:
- Error handling (mermaid)
- Game loop
- Client-server comms
- (In Progress) Class + Use Case diagrams

📌 Final reviews pending for:
- `meetingNotes.md`
- **this doc**

---

### 🔹 **Sultan + Uzair – Code & Research**

✅ Completed:
- Stub design in `GameServerT.java`, `PlayerT.java`, `PlayerData.java`, `PlayerDatabase.java`

⏳ In Progress:
- Chat feature
- Reconnection/move validation
- Integration with GUI & Game Logic once finalized

📌 Will sync with documentation team for consistency

---

## 🔨 To-Do Summary

### ✅ Finalize & Crosscheck Docs
- Ensure all files (API, Flow, Code Explainers) align with current Java classes
- Add diagrams where needed
- Update `meetingNotes.md` with current decisions

### 🔁 Support Stub Implementation
- Help annotate key methods with expected logic
- Ensure functions like `acceptConnections()`, `sendButtonNum()` are properly documented and diagrammed

### 🧭 Timeline Milestones
- Merge into main + sync stubs with other teams
- Document networking decisions for easier integration
- Refactor docs based on P2 feedback
- Begin tests for reconnection/chat logic

---

## 🎯 Deliverables Checklist (Networking Contribution)

| Deliverable              | Notes |
|--------------------------|-------|
| `README.md`              | Must describe networking setup |
| `team.md`                | Ensure all UCIDs are added |
| `ChangesMade.md`         | Document stub design and integration choices |
| `git_log.csv`            | Everyone must have real contributions |
| **Video Demo (12 min)**  | Show full network turn loop, chat, reconnection |
| **Tech Demo (10 min)**   | Explain contribution, show snippets, reflect |

---

## ✅ Individual D2L Submissions
- `[UCID]_worklog_p3.xlsx`
- `[UCID]_peer_evaluations_p3.xlsx`
- `[UCID]_reflection_p3.pdf`
- `[UCID]_tech_demo_pNN.mp4`
- `AI_disclosure.txt`

---

## 🧪 Stubbed Components Required (per professor's note)

### Even if full implementation is not possible:

#### Add stub methods for:

- Database connections

- Server hosting

- Chat feature

- Matchmaking integration

