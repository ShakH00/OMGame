1. How Close Should Players Be in Elo?
- Default range: ±100 Elo for instance a 1200-rated player is matched with 1100–1300 Elo players.
- Beginner-friendly adjustments: If a player is new (Elo < 1000), the system can initially match them more flexibly like with ±150).

2. Should Matchmaking Thresholds Expand Over Time?
- Yes to reduce wait time I think we do need to increase the range
  - After 30 seconds: Expand range by ±50 Elo.
  - After 1 minute: Expand by another ±50 Elo (total ±200).
  - After 2 minutes: Match with the closest available player.

3. How Should a Match Affect a Player’s Elo?
- My initial thoughts: if a lower-rated player wins, they gain more Elo than if a higher-rated player wins. Conversely, a higher-rated player loses more Elo if they lose to a weaker opponent.
  https://stanislav-stankovic.medium.com/elo-rating-system-6196cc59941e#:~:text=After%20each%20match%2C%20the%20Elo,the%20match%20and%20SA%20is
- The Elo system calculates rating changes using the expected vs. actual outcome:
- R'=R+K(S−E)
  - Where:
  - R' = New rating. 
  - R = Current rating. 
  - K = Scaling factor (how much the rating changes). 
  - S = Match result (1 = win, 0.5 = draw, 0 = loss). 
  - E = Expected result (based on opponent’s Elo).

- If you beat a stronger player, you gain more Elo. 
- If you lose to a weaker player, you lose more Elo. 
- If you win against a weaker player, you gain less Elo. 
- If you lose to a stronger player, you lose less Elo.

- When we jump into game specific elo adjustments

| GAME      | K-Factor |
|-----------|----------|
| Chess     | 32       |
| Checkers  | 20       |
| Connect L | 15       |
| Tic Tac   | 10       |


4. Matchmaking Logic for Each Game
- Chess (Highly strategic, skill-based)
  - Tighter Elo matching (±50-100) ensures fair competition.
  - High K-factor (32) to reflect skill progression.
- Checkers (Moderate complexity)
  - Moderate matchmaking (±100 Elo) keeps it fair but flexible.
  - K-factor = 20 to prevent large Elo swings.
- Connect L (Pattern recognition-based)
  - Broader matchmaking (±100-150 Elo) since strategies can be learned quickly.
  - Lower K-factor (15) to prevent rapid ranking inflation.
- Tic-Tac-Toe (Too simple, often leads to draws)
  - Wide matchmaking range (±150-200 Elo) since small Elo gaps don’t matter.
  - Very low K-factor (5-10) to prevent rating inflation due to forced draws.
  - Alternative: Use a win-streak-based system instead of Elo.