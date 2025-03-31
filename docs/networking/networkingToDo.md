# To-Do

## 1. Finalize & Refine Networking Documentation

### Objective: Ensure everything is clear, consistent, and non-contradictory before any coding.

##### Review and refine:

- apiDocumentation.md – Ensure all endpoints/methods are documented with clear param + return types.

- networkingCodeExplanation.md – Cross-check against updated GameServerT.java and PlayerT.java.

- error_handling_ideas.md – Align with actual handling or fallback designs.

- tentative_matchmaking_sessions_ideas.md – Make sure the speculative nature is clearly labeled.

##### Create or clean up any diagrams associated with each of the above.

##### Update meetingNotes.md with clear summaries of what’s been decided or postponed.

## 2. Contribute to Project Planning & Timeline Definition

### Objective: Help your team stay ahead by clearly outlining when and how networking should evolve.

#### Review and revise:

- planning.md and flowchartPlan.md to reflect new stub code and timeline expectations.

##### Identify and log:

- Major milestones and dependency chains (e.g., GUI must wait for Networking state handling).
- Inter-team dependencies (e.g., Profile Matchmaking feeds into PlayerData.java structure).

## 3. Support Stub Implementation with Context

### Objective: Guide and support without jumping into major code unless assigned.

- Help annotate GameServerT.java and PlayerData.java with insights, assumptions, or next steps.

- Cross-reference your diagrams to actual methods (like acceptConnections(), processGameLogicP1()).

- Contribute simple utility code or suggestions for player state syncing, reconnection handling, etc., only when needed.

## 4. Meet + Collaborate with Other Teams

### Objective: Keep other branches informed and in sync with networking decisions.

- Help organize feedback loops across:

- GUI → Networking (e.g., syncing game states to GUI after server move validation).

- Profile Matchmaking → Networking (e.g., how/when PlayerData is transmitted).

- Maintain meeting logs (in meetingNotes.md) and contribute to collective diagrams or planning docs.

- Work with team leads to ensure integration doesn't break inter-team logic.

## 5. Stretch Goals (Only if Needed)

#### Objective: Fill in other areas if time allows.

- If Networking is complete, move temporarily to GUI to help implement buttons, matchmaking menus, or display of network state.

- Implement Database code.

#### Files we Own or Co-Own

- apiDocumentation.md
- networkingCodeExplanation.md
- planning.md
- flowchartPlan.md
- error_handling_ideas.md
- tentative_matchmaking_sessions_ideas.md

##### Diagrams for:

- Turn handling & disconnection
- Client/Server architecture
- Matchmaking logic
- meetingNotes.md
