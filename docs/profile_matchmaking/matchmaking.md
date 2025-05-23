1. How Close Should Players Be in Elo?
- Default range: ±100 Elo for instance a 1200-rated account is matched with 1100–1300 Elo players.
- Beginner-friendly adjustments: If a account is new (Elo < 1000), the system can initially match them more flexibly like with ±150).

2. Should Matchmaking Thresholds Expand Over Time?
- Yes to reduce wait time I think we do need to increase the range
  - After 30 seconds: Expand range by ±50 Elo.
  - After 1 minute: Expand by another ±50 Elo (total ±200).
  - After 2 minutes: Match with the closest available account.

3. How Should a Match Affect a Player’s Elo?
- My initial thoughts: if a lower-rated account wins, they gain more Elo than if a higher-rated account wins. Conversely, a higher-rated account loses more Elo if they lose to a weaker opponent.
  https://stanislav-stankovic.medium.com/elo-rating-system-6196cc59941e#:~:text=After%20each%20match%2C%20the%20Elo,the%20match%20and%20SA%20is
- The Elo system calculates rating changes using the expected vs. actual outcome:
- R'=R+K(S−E)
  - Where:
  - R' = New rating. 
  - R = Current rating. 
  - K = Scaling factor (how much the rating changes). 
  - S = Match result (1 = win, 0.5 = draw, 0 = loss). 
  - E = Expected result (based on opponent’s Elo).

- If you beat a stronger account, you gain more Elo. 
- If you lose to a weaker account, you lose more Elo. 
- If you win against a weaker account, you gain less Elo. 
- If you lose to a stronger account, you lose less Elo.

- When we jump into game specific elo adjustments

| GAME      | K-Factor |
|-----------|----------|
| Chess     | 32       |
| Checkers  | 20       |
| Connect 4 | 15       |
| Tic Tac   | 10       |


4. Matchmaking Logic for Each Game
- Chess (Highly strategic, skill-based)
  - Tighter Elo matching (±50-100) ensures fair competition.
  - High K-factor (32) to reflect skill progression.
  
- Checkers (Moderate complexity)
  - Moderate matchmaking (±100 Elo) keeps it fair but flexible.
  - K-factor = 20 to prevent large Elo swings.
- Connect 4(Pattern recognition-based)
  - Broader matchmaking (±100-150 Elo) since strategies can be learned quickly.
  - Lower K-factor (15) to prevent rapid ranking inflation.
- Tic-Tac-Toe (Too simple, often leads to draws)
  - Wide matchmaking range (±150-200 Elo) since small Elo gaps don’t matter.
  - Very low K-factor (5-10) to prevent rating inflation due to forced draws.
  - Alternative: Use a win-streak-based system instead of Elo.

5. Summary for Players
- You are matched with players within ±100 Elo of your rating.
- If no match is found, the range expands every 30 seconds.
- After 2 minutes, you are paired with the closest available account.
- Win against a higher-rated account → Gain more points. 
- Win against a lower-rated account → Gain fewer points. 
- Lose against a weaker account → Lose more points. 
- Lose against a stronger account → Lose fewer points.



6. Thresholds for each Game



CHESS
- Starts off: ± 25
- After 30 seconds: ±50
- After 1 minute: ± 75
- After 2 minutes: Match with closest available account

CHECKERS
- Starts off: ± 50
- After 30 seconds: ±75
- After 1 minute: ± 100
- After 2 minutes: Match with closest available account

CONNECT 4
- Starts off: ± 50
- After 30 seconds: ±75
- After 1 minute: ± 100
- After 2 minutes: Match with closest available account 

Tic Tac Toe
- Starts off: ± 100
- After 30 seconds: ±150
- After 1 minute: ± 175
- After 2 minutes: Match with closest available account 
