Flowchart Diagram Plan

1. Client Sends a Move

   - Player initiates a move in the GUI.

   - The move is sent via sendMoveToServer(gameId, playerId, move).

   - NetworkingStub forwards the request to GameServer.

2. Server Processes the Move

   - GameServer validates the move via processMove(gameId, move).

   - If valid, the server updates the game state.

   - If invalid, the server sends an error response.

3. Server Broadcasts Updated Game State

   - GameServer calls sendGameStateToClients(gameId, gameState).

   - The updated state is transmitted to all clients.

4. Clients Receive Update

   - Clients call receiveUpdatedGameState(gameId).

   - GUI updates the board visually.

5. Handling Disconnections & Reconnections

   - If a player disconnects, the server marks them as inactive.

   - Upon reconnection, the latest game state is sent.

   - If a move was in progress, it is revalidated before resuming.
