# **Network Planning Document**

## Docs
- [FigJam Diagram](https://www.figma.com/board/dpFR9WEMYuxA74ZvipXcZc/process-loop?node-id=1-25&t=nhEXUiFAzE8vcAcv-1)
- [Google Doc Plan](https://docs.google.com/document/d/1O3nZ0WbedHbkeMzC8PnDiTGf0OVOONgV-Xe5WJszYR0/edit?usp=sharing)
### Networking Implementation
- https://www.youtube.com/watch?v=HQoWN28H80w
- https://www.youtube.com/watch?v=gLfuZrrfKes

## Team Roles
- **Diagrams and Documentation:** Hatem and Uzair

  Creating Network flow diagrams and writing descriptions of how networking will work.
- **Stubs and API Design:** Nova and Sultan

  Writing Placeholder methods and listing function names and parameters.

## Goals
- a
### Stretch Goals
- Actual network implementation

  See this videos (listed in Docs section) for details. Can use Nova's server/VPS, and domain.
- Real Database?

  MongoDB is cool and would be easy. Nova has a `PostgreSQL` server running.


## Assignment Requirements
List of requirements pertaining to networking given in the project document.

"May not have a lot to do and be done quick fast, in like a week" - Sutcliffe
### Email
"don't need to worry about database [...] design how and where the database will connect [...] don't require you to host this platform online, but your code should contain stubs where the server communication logic will reside"
### Multiplayer Interface
- Users should be able to log in, create profiles, and manage their accounts.
- Each profile should display games played, player stats, and win/loss records.
- Players should have visibility into other users’ profiles to view their ranks, current status, and recent matches.
### Matchmaking System
- Implement skill-based matchmaking where players are paired with appropriate opponents.
- Add a leaderboard where the top-ranked players can see how they stack up against the competition.
- Enable players to search for and join ongoing games or queue up for new matches.
### Game Hosting
- While the platform won’t be hosted online in your version, you’ll need to design and demonstrate how an online interface would be integrated.
- You will provide stubs/drivers for essential components, such as the database interface and online hosting logic. This ensures we understand where the platform will connect with other systems once live.
### Design Architecture
You will not need to implement the database or hosting backend (our database team will handle that). Instead, focus on designing where these interfaces will reside within your architecture.

Provide stub functions to simulate how your system will connect to external services such as:
- User authentication database
- Game data storage (match history, leaderboard)
- Cloud hosting or server infrastructure
### GUI
**More for GUI team, but these functions need to be exposed/documented to them**
Select and join games from the available library.
- View and challenge other players by searching their profile.
- Communicate during gameplay using a basic in-game chat.
