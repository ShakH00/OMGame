# 🧩 Networking Concepts & Game Logic Structure (P3)

## 🔧 Game Logic & Communication Architecture

### Overview
This section outlines our understanding of how the game logic operates in tandem with the networking layer. It serves as a guide to identify what needs to be transmitted across the network and how internal game structures relate to multiplayer synchronization.

---

### 📌 Current Game Implementation (As Observed)

#### Client-Side Logic (for P3)

- Game logic currently resides **on the client side** to simplify the prototyping process.
- Stubs should be delivered client-side first.
- In future phases, logic may **migrate to the server** for better security, fairness, and centralization.

#### Game Class & Related Components

- **Player1 / Player2** → Represented as Player objects, assigned randomly at runtime.
- **Score** → Integer value stored per player.
- **GameType (enum)** → Dictates which game is played (e.g., Chess, Checkers).
- **GameState (enum)** → Indicates game status: `TURN`, `WIN`, `DRAW`, `SETUP`, etc.
- **Board (Class)** → Encodes all positions of game pieces.
- **GameRules (Class)** → Rule validation (client-side for now).

#### Communication & Syncing Requirements

- **GameState** – Sent between client and server to manage turn transitions, victory detection, and session end.
- **Score** – Should be synchronized on game win/loss.
- **Board** – Represented as a **2D array of Piece objects**. Must be encoded for transmission.
- **GameType** – Transmitted to server so proper board logic is initialized.

#### Modularity Notes

- Communication is feasible as long as all games follow consistent **Board encoding/decoding** standards.
- Enums provide a lightweight and flexible structure to handle GameType identification and state changes.

#### Piece Class

- Game pieces extend from a base **Piece class**.
- Positions are tracked and manipulated through the Board's internal structure.

---

## 🎮 Matchmaking & Session Management – Tentative Notes

### Concept Overview

While not fully implemented in P3, this section outlines expected behaviors for matchmaking and session control.

#### Matchmaking Flow:

1. Player connects to server.
2. If no match found, added to waiting pool.
3. When second player connects, both are assigned a unique `gameID` and session starts.

#### Session Tracking

- Server holds a mapping of `gameID → player1, player2`.
- Game state and socket connections are scoped to each session.

#### Disconnection Behavior

- If before session start: removed from pool.
- If during match:
    - Server can pause session.
    - Opponent may wait or exit.
    - Session may auto-terminate after a timeout.

#### Possible Enhancements

- Ranked Elo-based queueing.
- Preference-driven matchmaking (game mode, player type).
- Allow more than two players per session in future iterations.

---

## 🔗 Related Files

- `NetworkingFunctionDocs.md` – Method-level function breakdown
- `error_handling_ideas.md` – Recovery strategies and disconnect flow
- `networking_system_overview_and_planning.md` – Full scope and scheduling plan

---

## ❗ Reminder

This document is exploratory. No networking behavior, session flow, or logic decisions should be finalized without team-wide discussion and input from Game Logic, GUI, and Profile/Leaderboard teams.
