# Tentative Matchmaking & Session Management Document

## Overview

#### This document (super tentative) is meant to be a preparation for the future as we don't have much to work off of right now, but it is still very helpful to have a file like this to help us know what to expect to some extent. This document explains how the matchmaking and session management system operates within the multiplayer game platform. It ensures that players are assigned to game sessions correctly and details how multiple matches are handled simultaneously.

## Player Assignment & Matchmaking Process

   #### Step 1: A player connects to the server.
   #### Step 2: The server assigns them to a waiting pool if no opponent is available.
   #### Step 3: When a second player connects, the server pairs them into a session.
   #### Step 4: A new game session is created, and both players are notified that the game is starting.
   ### Key Implementation Points
   #### ✅ Each session is assigned a unique game ID.
   #### ✅ Players in one session cannot interact with players in another session.
   #### ✅ The server maintains a list of active game sessions, ensuring that moves are only processed within the correct match.

## Handling Multiple Simultaneous Game Sessions
   #### The server uses a mapping system (gameID → players) to track active matches.
   #### Each player's connection is stored in a session table, ensuring their messages are only forwarded to their opponent.
   #### When a session ends, its game state is removed from memory to free resources.

## What Happens If a Player Leaves Mid-Matchmaking?
   #### If a player disconnects before the game starts, they are removed from the waiting pool.
   #### If a game session is already assigned, the remaining player is given the option to:
   #### Wait for their opponent to reconnect within a time limit.
   #### End the session and return to matchmaking.

## Future Enhancements (tentative ideas)
   #### Implementing a queue system for ranked play.
   #### Expanding to support more than two players per session.
   #### Adding player preference-based matchmaking (e.g., selecting a specific game mode).
