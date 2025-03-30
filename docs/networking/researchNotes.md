# Game Logic
## Current game implementation based on what is visible:
- Logic on client side?
  - Can keep on client side for now (and for stub iteration) 
  - Eventually move to central server? Increases game security and reduces complexity. 
  - **DELIVER STUBS ON CLIENT AS A PROTOTYPE**
- **Game Class** Defines player1/2 (Player class, randomly assigned), score (int), GameType enum, GameState (enum, turn/win/draw/setup), Board (class), GameRules (Class)
  - Some of these values will need to be communicated: score, type, state, board
  - Rules can be client side
- uses Board class to house all the pieces for every game, gameType defined by enum
  - Communicate Board directly or find way to encode/decode it
  - Use enum for picking game
  - Should make things quite modular as so long as game boards can be communicated
- Piece class extended to piece type
  - Positions will be communicated via Board class (2D Array of Pieces)
- **MORE?**
# Auth
- 
# Leaderboard/Matchmaking
- 