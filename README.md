# ![tetriscat](src/images/sprites/tetrisCatIcon.png) seng300-project ![tetriscat](src/images/sprites/tetrisCatIcon.png)

seng300-project is our recreation of an <ins> online multiplayer game</ins>, or OMG for short.
***
## Table of Contents:   


* **[Installation](#Installation)**
    - **[JavaFx Installation Instructions](#javafx-installation-instructions)**
    - **[DataBase Installation Instructions](#data-base-installation-instructions)**
    - **[Jakarta Installation Instructions](#jakarta-installation-instructions)**

### Project Layout:
* **[Document](#documentations-)**
  - **[Contract](#contract-)**
  - **[Game Logic](#game-logic-)**
  - **[GUI](#gui)**
  - **[Networking](#networking-)**
  - **[Profile Matchmaking](#profile-matchmaking-)**
  

* **[Diagrams](#diagrams-folder)**
  - **[Abstract](#abstract-)**
  - **[Game Logic](#game-logic--1)**
  - **[GUI](#gui--)**
  - **[Networking](#networking--1)**
  - **[Profile MatchMaking](#profile-matchmaking--1)**


* **[Code Organization](#code-organization)**
  - **[Account](#account)**
  - **[Authentication](#authentication)**
  - **[Com.Example](#comexample)**
  - **[Database](#database)**
  - **[Game](#game)**
  - **[Images](#images)**
  - **[Leaderboard](#leaderboard)**
  - **[Matchmaking](#matchmaking)**
  -  **[Networking](#networking)**
  -  **[Resources.fonts](#resourcesfonts)**
  -  **[Screens](#screens)**


* **[Tests Folder](#test-folder)**
  - WAITING UNTIL ALL TEAMS FINISH THERE TEST cases


* **[website](#website)**
  * **[assets](#assets-)**


* **[Git Log & Git Link](#git-log--git-link)**
  - **[Git Log](#git_log_csv)**
  - **[Git Lab](#gitlab_linktxt)**


* **[Team](#team)**
  - **[Team file](#teammd)**
*** 


# Installation

To launch OMG it would require an installation of javafx and the database our company as used.
* Steps to install [JavaFx](#javafx-installation-instructions) 
* Steps to install [Database](#data-base-installation-instructions)
* Steps to install [Jakarta](#jakarta-installation-instructions)

***

## JavaFX Installation Instructions

Follow these steps to install JavaFX and set up your development environment.

## Prerequisites

Before installing JavaFX, ensure that you have the following prerequisites:

- **Java JDK**: JavaFX requires a Java Development Kit (JDK) version 8 or above.
    - Download the JDK from the [official Oracle website](https://www.oracle.com/java/technologies/javase-downloads.html) or [AdoptOpenJDK](https://adoptopenjdk.net/).
    - Ensure that the `JAVA_HOME` environment variable is set correctly, and Java is added to the system's `PATH`.

## Installation Steps

### 1. Download JavaFX SDK

Go to the official [JavaFX website](https://openjfx.io/) and download the latest JavaFX SDK version compatible with your operating system.

- **Windows**: Choose the `.zip` file.
- **Mac OS**: Choose the `.tar.gz` file.
- **Linux**: Choose the `.tar.gz` file.

### 2. Extract JavaFX SDK

Once the download is complete:

- **Windows**: Extract the `.zip` file to a desired location on your system.
- **Mac/Linux**: Extract the `.tar.gz` file to a desired location on your system.

### 3. Configure Your IDE

If you're using an Integrated Development Environment (IDE) like IntelliJ IDEA, Eclipse, or Visual Studio Code, configure it to include JavaFX libraries.

#### IntelliJ IDEA:

**Adding JavaFX to the Project Structure**
1. Open the game project with IntelliJ IDEA.
2. Press the **Gear Icon** in the top right corner and then **Project Structure**.
3. In the **Libraries** section, click **+** and select **Java**.
4. Navigate to the `lib` folder inside the extracted JavaFX SDK and add it.
5. Press **Apply** and **Ok** to close.

**Creating a Run Configuration**
1. Click **Current File** beside the Run button on the top right.
2. At the bottom of the drop menu press **Edit Configurations**.
3. Click **Add new configuration** and select **Application**.
4. Give the Run Configuration a name and set the **Main Class** to the GUI class you want to run.
    1. Generally this class will be the **StartController** class in the source directory.
5. Click on **Modify Options** and choose **Add VM Options**.
6. Add the following VM options and replace **/path/to/javafx-sdk-<version>/lib** with the path to the JavaFX lib directory on your computer:

    ```bash
    --module-path /path/to/javafx-sdk-<version>/lib --add-modules javafx.controls,javafx.fxml
    ```

#### Eclipse:
1. Create a new Java project.
2. Right-click on the project and select **Build Path > Configure Build Path**.
3. Add the `lib` directory of the JavaFX SDK to the build path.
4. Under **Run Configurations**, add the VM arguments:

    ```bash
    --module-path /path/to/javafx-sdk-<version>/lib --add-modules javafx.controls,javafx.fxml
    ```

#### Visual Studio Code:
1. Install the **Java Extension Pack** from the VS Code marketplace.
2. Open the project folder and create a `launch.json` file inside the `.vscode` folder with the following configuration:

    ```json
    {
      "type": "java",
      "name": "Launch JavaFX",
      "request": "launch",
      "mainClass": "com.example.Main",
      "vmArgs": "--module-path /path/to/javafx-sdk-<version>/lib --add-modules javafx.controls,javafx.fxml"
    }
    ```

## _**Notice:**_
We can not confirm if these methods work on Eclipse or Visual Studio Code. Please use your due diligence and request help if needed.

You can contact the devs of OMG by [email](help@omgame.club) or at our [website](https://omgame.club/).

Thank you for choosing

<img src="src/images/omg_title.png" alt="drawing" width="80"/>

***
# Data Base Installation Instructions

### Prerequisites
- Update based on Nova's server or XAMPP

### Installation Steps

#### 1. Download and Install XAMPP

1. Go to the official [XAMPP website](https://www.apachefriends.org/download.html).
2. Download the version for your operating system (Windows, macOS, or Linux).
3. Run the installer.
4. Follow the installation wizard:
  - Click **Next** on all prompts (you may leave all default components selected).
5. Once installed, open the **XAMPP Control Panel**.
  - On macOS, launch the `manager-osx` app.

---

#### 2. Start Apache and MySQL Servers

1. In the XAMPP Control Panel, click **Start** next to:
  - **Apache** – This launches your local web server.
  - **MySQL** – This starts your local MySQL database.
2. Ensure both services are running. A green status indicator should appear for each.

---

#### 3. Open phpMyAdmin

1. Open your web browser.
2. Navigate to: [http://localhost/phpmyadmin/](http://localhost/phpmyadmin/)
3. You should see the phpMyAdmin dashboard interface.

---

#### 4. Download and Add MySQL Connector

1. Download the MySQL Connector J from the [official website](https://dev.mysql.com/downloads/connector/j/).
2. Choose **Platform Independent** and download the `.zip` file.
3. Extract the archive and locate the `mysql-connector-j-<version>.jar` file.
4. In your Java project:
  - Go to **File > Project Structure > Libraries**
  - Click **+** → **Java**, then select the JAR file.
  - Click **Apply** and then **OK**.

---

#### 5. Initialize the Database

1. In phpMyAdmin, go to the **SQL** tab.
2. Execute the following SQL statements **one at a time**:

```sql
CREATE DATABASE OMGAMEDB;

CREATE TABLE Accounts (
  ID INT AUTO_INCREMENT PRIMARY KEY,
  Username VARCHAR(255) NOT NULL UNIQUE,
  Email VARCHAR(255) NOT NULL UNIQUE,
  Password VARCHAR(255) NOT NULL,
  Friends VARCHAR(1024) NOT NULL,
  Statistics TEXT,
  MatchHistory TEXT
);

CREATE TABLE Matchmaking (
  ID INT PRIMARY KEY,
  STATE VARCHAR(255),
  GAME VARCHAR(255),
  START_TIME FLOAT(32),
  RECENT_TIME FLOAT(32),
  ELO INT,
  ELO_RANGE INT,
  OPPONENT_ID INT,
  NETWORKING_INFO VARCHAR(255),
  ROOM_CODE VARCHAR(255)
);
```

Thank you for choosing

<img src="src/images/omg_title.png" alt="drawing" width="80"/>

***

## Jakarta Installation Instructions

### Step 1: Download & Install Jakarta
- Go to the official SENG p-2 discord
- Open the Authentication-profile-team channel
- Open pinned messages (top right corner)
- Download the jakarta.activation-2.0.1.jar AND jakarta.mail-2.0.1.jar packages

Note: The official jakarta releases can be found on their github repo https://github.com/jakartaee/mail-api/releases.
Jakarta mail official website: https://jakartaee.github.io/mail-api/README-JakartaMail
Jakarta activation official website: https://jakartaee.github.io/jaf-api/


### 3. Configure Your IDE

If you're using an Integrated Development Environment (IDE) like IntelliJ IDEA, Eclipse, or Visual Studio Code, configure it to include jakarta libraries.

#### IntelliJ IDEA:

**Adding JavaFX to the Project Structure**
1. Open the game project with IntelliJ IDEA.
2. Press the **Gear Icon** in the top right corner and then **Project Structure**.
3. In the **Libraries** section, click **+** and select **Java**.
4. Navigate to the `jakarta.activation-2.0.1.jar` package wherever you downloaded it
5. Press **Apply** and **Ok** to close.
6. repeat this step for the 'jakarta.mail-2.0.1.jar' package



Thank you for choosing

<img src="src/images/omg_title.png" alt="drawing" width= "80"/>


***

## Documentations 

- **[Contract](#contract-)**
  - **[SENG300AccountabilityContract.pfd](#seng300accountabilitycontractpdf)**


- **[Game Logic](#game-logic-)**
  - **[GameLogicPlanning.pdf]()**
  - **[use_case_descriptions.pdf](#use_case_descriptionspdf)**


- **[GUI](#gui)**
  - **[gui_design_ideas.pdf](#gui_design_ideaspdf)**
  - **[gui_v1_designs.pdf](#gui_v1_designspdf)** 

- **[Networking](#networking-)**
  - **[ErrorHandlingImplementation.md](#errorhandlingimplementationmd)**
  - **[meetingNotes.md](#meetingnotesmd)**
  - **[NetworkingConcepts.md](#networkingconceptsmd)**
  - **[NetworkingFunctionsDocs.md](#networkingfunctionsdocsmd)**
  - **[NetworkingSystemOverview.md](#networkingsystemoverviewmd)**
  - **[use_case_descriptions.pdf](#use_case_descriptionspdf-)**


- **[Profile Matchmaking](#profile-matchmaking-)**
  - **[matchmaking.md](#matchmakingmd)**
  - **[README.md](README.md)**
  - **[use_case_descriptions_01.pdf](#use_case_descriptions_01pdf)**
  - **[use_case_descriptions_02.pdf](#use_case_descriptions_02pdf)**
  - **[use_case_descriptions_03.pdf](#use_case_descriptions_03pdf)**
  - **[use_case_descriptions_05.png](#use_case_diagram_05png)**


- **[Project_Planning.pdf](#project_planningpdf)**
  

### Contract 
- **[SENG300AccountabilityContract.pfd](#seng300accountabilitycontractpdf)**

 #### `SENG300AccountabilityContract.pdf`
**Purpose:** Companies contract that everyone signed and was created by Project Manager and the Team Leads.
***
### Game Logic 
Game logic documentation developed during the initial phase of the project.
- **[GameLogicPlanning.pdf]()**
- **[use_case_descriptions.pdf](#use_case_descriptionspdf)**

#### `GameLogicPlanning.pdf`
**Purpose:** The GameLogicPlanning.pdf outlines a structured development plan divided into five sprints to implement and test logic for several turn-based games including Chess, Checkers, Connect Four, and Tic Tac Toe. The team focused on classes and use case diagram creation, core gameplay implementation, and feature integration, while assigning specific components and responsibilities to different members to ensure modular and agile development.

#### `use_case_descriptions.pdf`
**Purpose:** This document outlines the use case scenarios for various gameplay mechanics in a multiplayer strategy game system, focusing on features such as playing a game, moving pieces, and performing specific actions like castling, en passant, and offering a draw. Each use case details the actors involved, preconditions, triggers, scenarios, postconditions, exceptions, and priority, aiming to ensure the game operates smoothly and realistically according to chess, checkers, Connect 4, and tic-tac-toe rules.
***

### GUI
**Purpose:** GUI documentation developed during the initial phase of the project.
- **[gui_design_ideas.pdf](#gui_design_ideaspdf)**
- **[gui_v1_designs.pdf](#gui_v1_designspdf)**
#### `gui_design_ideas.pdf`
**Purpose:** Rough concept of the main layout/design of the game. 
#### `gui_v1_designs.pdf`
**Purpose:** Polished version of main layout/design of the game.
***

### Networking  
**Purpose:** Networking documentation developed during the initial phase of the project.
- **[ErrorHandlingImplementation.md](#errorhandlingimplementationmd)**
- **[meetingNotes.md](#meetingnotesmd)**
- **[NetworkingConcepts.md](#networkingconceptsmd)**
- **[NetworkingFunctionsDocs.md](#networkingfunctionsdocsmd)**
- **[NetworkingSystemOverview.md](#networkingsystemoverviewmd)**
- **[use_case_descriptions.pdf](#use_case_descriptionspdf-)**

#### `ErrorHandlingImplementation.md`
**Purpose:** This document outlines the finalized error handling and failure recovery strategies implemented in the multiplayer system, including disconnection detection, reconnection workflows, and turn synchronization. It also details future-ready features like retry logic for move transmission and safeguards to maintain game integrity and prevent exploits.
#### `meetingNotes.md`
**Purpose:** The team meeting on March 26 covered recent progress, including updates to documentation, diagrams, and code stubs, as well as leadership and coordination efforts. Upcoming goals focus on finalizing planning, preparing for code implementation (especially networking and chat features), and ensuring equal contributions from all members ahead of the demo.
#### `NetworkingConcepts.md`
**Purpose:** This document outlines how game logic and networking interact in the multiplayer system, detailing client-side implementation, data structures like GameState, Board, and GameRules, and how they're synchronized over the network. It also proposes a tentative matchmaking and session management plan while emphasizing modularity, future enhancements, and the need for team-wide alignment before finalizing any implementations.
#### `NetworkingFunctionsDocs.md`
**Purpose:** This document consolidates all networking-related components and stubs for P3, including server-client architecture, turn-sync logic across all four games, and placeholder systems for chat and matchmaking. It serves as a comprehensive guide for developers and integration teams, detailing current functionality, planned upgrades, and how networking is simulated via Networking.java.
#### `NetworkingSystemOverview.md`
**Purpose:** This document finalizes the networking system for P3, detailing the dual-layer architecture—live server-client flow and stubbed multiplayer simulation via Networking.java. It outlines implementation across all games, integration with other teams (GUI, Logic, Auth, Leaderboard), and prepares for future features like matchmaking and reconnection. All core networking stubs are fully functional and documented, making the system modular, testable, and demo-ready.
#### `use_case_descriptions.pdf `
**Purpose:** The use case descriptions outline the steps for player connection, gameplay, and connection management in a multiplayer game. They cover scenarios like connecting to the server, matchmaking, making moves, synchronizing game state, handling disconnections, and processing game logic to ensure smooth gameplay.
***
### Profile Matchmaking  
**Purpose:** Profile & Matchmaking documentation developed during the initial phase of the project.
- **[matchmaking.md](#matchmakingmd)**
- **[README.md](README.md)**
- **[use_case_descriptions_01.pdf](#use_case_descriptions_01pdf)**
- **[use_case_descriptions_02.pdf](#use_case_descriptions_02pdf)**
- **[use_case_descriptions_03.pdf](#use_case_descriptions_03pdf)**
- **[use_case_descriptions_05.png](#use_case_diagram_05png)**

#### `matchmaking.md`
**Purpose:** The matchmaking system adjusts player Elo based on their rating, with a default range of ±100 Elo, expanding over time if no match is found. Elo adjustments are game-specific, with higher K-factors for strategic games like Chess and lower K-factors for simpler games like Tic-Tac-Toe, ensuring fair competition while limiting rapid rating inflation.
#### `README.md`
**Purpose:** The Profile & Matchmaking Team has documented the matchmaking process and created class structure, sequence, and use case diagrams for various aspects, such as account creation, matchmaking, and leaderboard generation. These diagrams and related documents help illustrate the key components of the system, including authentication, password encryption, multi-factor authentication, and managing friends.
#### `use_case_descriptions_01.pdf`
**Purpose:** The use cases describe various matchmaking and game interaction scenarios. Players can queue for matches, host or join private matches using a match ID, select the game to play, and even spectate ongoing matches with a match ID. Key priorities include supporting core functionality like game selection and matchmaking, while features like spectating and private matches have lower priority due to being optional or for specific player preferences.
#### `use_case_descriptions_02.pdf`
**Purpose:** The use cases describe account creation, editing, guest account functionality, and identity verification processes. Players can create and edit accounts, play as guests, and verify their identity when logging in, with account creation and guest play being high-priority, frequently used features, while identity verification is lower priority and less frequent.
#### `use_case_descriptions_03.pdf`
**Purpose:** These use cases cover the functionality for players to search for other players, send friend requests, manage incoming requests, and remove friends. While not essential to core gameplay, these features enhance social interaction and are frequently used to build and manage a player's friend network.
#### `use_case_diagram_05.png`
**Purpose:** The use case diagrams shows a rough overview of authentications player to system steps.
***
#### `Project_Planning.pdf`
**Purpose:** A detailed project planning document for various teams working on different aspects of a game development project. Each team, such as the Game Logic, GUI, Authentication/Profile, Leaderboard/Matchmaking, Networking, and Integration teams, has their own set of objectives and tasks, with timelines and statuses.
***
## Diagrams Folder
- **[Abstract](#abstract-)**
  - **[class_diagrams.png](#class_diagrampng)**
  

- **[Game Logic](#game-logic--1)**
  - **[class_diagram.png](#class_diagrampng)**
  - **[use_case_diagram.png](#use_case_diagrampng-)**
  

- **[GUI](#gui--)**
  - **[assets](#assests)**
    - **[screens](#screens)**
    - **[sprites](#sprites)**
  

- **[Networking](#networking)**
  - **[integration](#integration)**
    - **[auth_mm.png](#auth_mmpng)** 
    - **[gameLogic.png](#gamelogicpng)**
    - **[gui.png](#guipng)**
    - **[networking.png](#networkingpng)**
  - **[(DRAFT)error_handling_1 mermaid diagram.png](#drafterror_handling_1-mermaid-diagrampng)**
  - **[(DRAFT)error_handling_2 mermaid diagram.png](#drafterror_handling_2-mermaid-diagrampng)**
  - **[(DRAFT)_use_case.png](#draft_use_casepng)**
  - **[figjamLink.txt](#figjamlinktxt)**
  - **[gameClassDiagram.png](#gameclassdiagrampng)**
  - **[gameProcess.png](#gameprocesspng)**
  - **[player_and_localhostSetup.png](#player_and_localsetuppng)**
  - **[tentativeGameStateTransitionDiagram.png](#tentativegamestatetransistiondiagarmpng)**
  

- **[Profile MatchMaking](#profile-matchmaking--1)**
  - **[class_structure_diagrams](#class_structure_diagrams)**
  - **[sequence_diagrams](#sequence_diagrams)**
  - **[use_case_diagrams](#use_case_diagarms)**
***
###  Abstract               ⠀⠀
#### `class_diagram.png`
**Purpose:** Every class diagrams from all teams.
***
###  Game Logic⠀         ⠀⠀
#### `class_diagram.png`    
**Purpose:** Game's logic class diagram.
***
#### `use_case_diagram.png`         
**Purpose:** Game's logic use case diagrams.
***
###  GUI⠀ ⠀⠀           
#### Assests
**Purpose:** Screens and Sprites that will be used in the main game.
- **[screens](#screens)**
- **[sprites](#sprites)**
***
#### `screens`
**Purpose:** All screens that will be implemented in the project.
***
#### `sprites`
**Purpose:** All sprites that will be implemented in the project.
***
###  Networking
- **[integration](#integration)**
  - **[auth_mm.png](#auth_mmpng)**
  - **[gameLogic.png](#gamelogicpng)**
  - **[gui.png](#guipng)**
  - **[networking.png](#networkingpng)**
- **[(DRAFT)error_handling_1 mermaid diagram.png](#drafterror_handling_1-mermaid-diagrampng)**
- **[(DRAFT)error_handling_2 mermaid diagram.png](#drafterror_handling_2-mermaid-diagrampng)**
- **[(DRAFT)_use_case.png](#draft_use_casepng)**
- **[figjamLink.txt](#figjamlinktxt)**
- **[gameClassDiagram.png](#gameclassdiagrampng)**
- **[gameProcess.png](#gameprocesspng)**
- **[player_and_localhostSetup.png](#player_and_localsetuppng)**
- **[tentativeGameStateTransitionDiagram.png](#tentativegamestatetransistiondiagarmpng)**
***
#### Integration
**Purpose:** Images on how to integrate each part
- **[auth_mm.png](#auth_mmpng)**
- **[gameLogic.png](#gamelogicpng)**
- **[gui.png](#guipng)**
- **[networking.png](#networkingpng)**
***
#### `auth_mm.png`
**Purpose:** Integration diagram with the leaderboard, matchmaking and authentication with buttons.
***
#### `gameLogic.png`
**Purpose:** Integration diagram with the game's included with buttons.
***
#### `gui.png`
**Purpose:** Integration diagram with the gui and buttons.
***
#### `networking.png`
**Purpose:** Integration diagram with the networking and database.
****
#### `(DRAFT)error_handling_1 mermaid diagram.png`
**Purpose:** Diagram for player's turn errors. 
****
#### `(DRAFT)error_handling_2 mermaid diagram.png`
**Purpose:** Diagrams for a player being disconnected. 
****
#### `DRAFT_use_case.png`
**Purpose:** Diagrams for client side connection.
****
#### `figjamLink.txt`
**Purpose:** Link to all figma diagrams in case It's hard to see.
****
#### `gameClassDiagram.png`
**Purpose:** Diagrams for connecting two player with a server.
****
#### `gameProcess.png`
**Purpose:** Diagram for how the game will function.
****
#### `player_and_localSetup.png`
**Purpose:** Diagram to set up the local host to connect the two players.
****
#### `tentativeGameStateTransistionDiagarm.png`
**Purpose:** Diagram for turn base from two players.
***
###  Profile Matchmaking⠀        
- **[class_structure_diagrams](#class_structure_diagrams)**
- **[sequence_diagrams](#sequence_diagrams)**
- **[use_case_diagrams](#use_case_diagarms)**
***
#### `class_structure_diagrams`
**Purpose:** Holds every class structure from Profile and Matchmaking team.
***
#### `sequence_diagrams`
**Purpose:** Holds every sequence diagrams from Profile and Matchmaking team.
***
#### `use_case_diagarms`
**Purpose:** Holds every sequence diagrams from Profile and Matchmaking team.
***

## Code Organization
- **[account](#account)**
  - **[statistics:](#statistics)**
    - **[AStatistic.java](#astatisticsjava)**
    - **[IStatistics.interface](#istatisticsinterface)**
    - **[MatchOutcomeHandler.java](#matchoutcomehandlerjava)**
    - **[MatchOutcomeInvalidError.exception](#matchoutcomeinvaliderrorexecption)**
    - **[StatisticsCheckers.java](#statisticscheckersjava)**
    - **[StatisticsChess.java](#statisticschessjava)**
    - **[StatisticsCombined.java](#statisticscombinedjava)**
    - **[StatisticsConnect4.java](#statisticsconnect4java)**
    - **[StatisticsTicTacToe.java](#statisticstictactoejava)**
    - **[StatisticType.enum](#statisticstypeenum)**
  - **[Account.java](#acccountjava)**
  - **[AccountStorageUtility.java](#accountstorageutilityjava)**
  - **[CreateAccount.java](#createaccountjava)**
  - **[NoAccountError.exception](#noaccounterrorexecption)**


- **[authentication:](#authentication)**
  - **[Authentication:](#authentication-)**
    - **[Admin.java](#adminjava)**
    - **[CAPTCHAAuthentication.java](#captchaauthenticationjava)**
    - **[EmailSender.java](#emailsenderjava)**
    - **[MFAAuthentication.java](#mfaauthenitcationjava)**
    - **[MFAAuthenticationV2.java](#mfaauthenticationv2java)**
    - **[MFAInputPopUp.java](#mfainputpopupjava)**

  - **[CAPTCHImages](#captchaimages)**

  - **[ExceptionAuthentication:](#exceptionauthentications)**
    - **[CAPTCHAuthenticationFailedException.exception](#captchaauthenticationfailedecpectionexception)**
    - **[DecryptionFailedException.exception](#decryptionfailedexceptionexception)**
    - **[EncryptionFailedException.exception](#encryptionfailedexceptionexception)**
    - **[MFAAuthenticationFailedException.exception](#mfaauthenticationfailedexceptionexception)**
  - **[MFAPopUpController.java](#mfapopupcontrollerjava)**


  - **[Com.Example](#comexample)**


- **[database](#database)**
  - **[DatabaseConnection.java](#databaseconnectionjava)**
  - **[DatabaseManager.java](#databasemanagerjava)**
  - **[DecryptionAuthentication.java](#decryptionauthenitactionjava)**
  - **[EncryptionAuthentication.java](#encryptionauthenticationjava)**


- **[game](#game)**
  - **[checkers](#checkers)**
    - **[Checkers.java](#checkersjava)**
    - **[CheckersController.java](#checkerscontrollerjava)**
    - **[CheckersPiece.java](#checkerspiecejava)**
  - **[Chess](#chess)**
    - **[Bishop.java](#bishopjava)**
    - **[Chess.java](#chessjava)**
    - **[King.java](#kingjava)**
    - **[Pawn.java](#pawnjava)**
    - **[Queen.java](#queenjava)**
    - **[Rook.java](#rookjava)**
  - **[connect4](#connect4)**
    -  **[Connect4.java](#connect4java)**
    - **[Connect4Piece](#connect4piecejava)**
  - **[pieces](#pieces)**
    - **[MovingPiece.java](#movingpiecejava)**
    - **[Piece.java](#piecejava)**
    - **[PieceType.enum](#piecetypeenum)**
    - **[StationaryPiece.java](#sattionarypiecejava)**
  - **[tictactoe](#tictactoe)**
    - **[TicTacToe.java](#tictactoejava)**
    - **[TicTacToePiece.java](#tictactoejava)**
  - **[Board.java](#boardjava)**
  - **[Game.java](#gamejava)**
  - **[GameRules.java](#gamerulesjava)**
  - **[GamesEnum.enum](#gamesenumenum)**
  - **[GameState.enum](#gamestateenum)**
  - **[GameType.enum](#gametypeenum)**
  - **[Player.java](#playerjava)**


- **[images](#images)**
  - **[sprites](#sprites)**
  - **[screen](#screens)**


- **[leaderboard](#leaderboard)**
  - **[Leaderboard.java](#leaderboardjava)**


- **[Matchmaking](#matchmaking)**
  - **[MatchmakingHandler](#matchmakinghandlerjava)**
    - **[HostingThread.java](#hostingthreadjava)**
    - **[MatchmakingHandler.java](#matchmakinghandlerjava-)**
    - **[MatchmakingThread.java](#matchmakingthreadjava)**
  - **[MatchmakingState.enum](#matchmakingstateenum)**


- **[networking](#networking--1)**
  - **[test](#test)**
    - **[networkingObjectSending](#networkingobjectsending)**
      - **[GameServer.java](#gameservertjava)**
      - **[Message.java](#messagejava)**
      - **[PlayerClient.java](#playerclientjava)**
      - **[PracticeGameObj.java](#practicegameobjjava)**
    - **[DockerFile-gameS.file](#dockerfile-gamesfile)**
    - **[GameServerT.java](#gameservertjava)**
    - **[PlayerData.java](#playerdatajava)**
    - **[PlayerT.java](#playertjava)**
    - **[stubs.java](#stubsjava)**
  - **[badwords.txt](#badwordstxt)**
  - **[Networking](#networkingjava)**


- **[resources.fonts](#resourcesfonts)**
  - **[Pixelite.ttf](#pixelitetff)**
  - **[PressStart2P-Regular.ttf](#pressstart2p-regulartff)**
  - **[RetroGaming.ttf](#retrogamingttf)**
  

- **[screen](#screens)**
  - **[AdminScreen.fxml](#adminscreenfxml)**
  - **[Connect4.fxml](#connect4fxml)**
  - **[draw_styles.css](#draw_stylescss)**
  - **[DrawScreen.fxml](#drawscreenfxml)**
  - **[GameSelect.fxml](#gameselectfxml)**
  - **[Help.fxml](#helpfxml)**
  - **[LeaderboardScreen.fxml](#leaderboardscreenfxml)**
  - **[LeaderboardStyle.css](#leaderboardstylecss)**
  - **[Login.fxml](#loginfxml)**
  - **[lose_styles.css](#lose_stylescss)**
  - **[LoseScreen.fxml](#losescreenfxml)**
  - **[MatchType.fxml](#matchtypefxml)**
  - **[MenuPopup.fxml](#menupopupfxml)**
  - **[MFAPopup.fxml](#mfapopupfxml)**
  - **[P1Checkers.fxml](#p1checkersfxml)**
  - **[P1Chess.fxml](#p1chessfxml)**
  - **[P2Checker.fxml](#p2checkersfxml)**
  - **[P2Chess.fxml](#p2chessfxml)**
  - **[Signup.fxml](#signupfxml)**
  - **[Start.fxml](#startfxml)**
  - **[styles.css](#stylescss)**
  - **[TicTacToe.fxml](#tictactoefxml)**
  - **[Userpopup.fxml](#userpopupfxml)**
  - **[UserProfile.fxml](#userprofilefxml)**
  - **[UserSettings.fxml](#usersettingfxml)**
  - **[WinScreen.fxml](#winscreenfxml)**

  
- **[AdminController.java](#admincontrollerjava)**
- **[Connect4Controller.java](#connect4controllerjava)**
- **[DrawScreenController.java](#drawscreencontrollerjava)**
- **[GameSelectController.java](#gameselectcontrollerjava)**
- **[HelpController.java](#helpscontrollerjava)**
- **[LeaderboardController.java](#leaderboardcontrollerjava)**
- **[LoginController.java](#logincontrollerjava)**
- **[LoseScreenController.java](#losescreencontrollerjava)**
- **[Main.java](#mainjava-1)**
- **[MatchTypeController](#matchtypecontrollerjava)**
  - **[MatchmakingHandlerWatcher.java](#matchmakinghandlerwatcherjava)**
  - **[MatchTypeController.java](#matchtypecontrollerjava)**
- **[MenuPopUpController.java](#menupopupcontrollerjava)**
- **[P1CheckersController.java](#p1checkerscontrollerjava)**
- **[P1ChessController.java](#p1chesscontrollerjava)**
- **[P2CheckersController.java](#p2checkerscontrollerjava)**
- **[P2ChessController.java](#p2checkerscontrollerjava)**
- **[SceneManager.java](#scenemanagerjava)**
- **[SignUpController.java](#signupcontrollerjava)**
- **[StartController.java](#startcontrollerjava)**
- **[styles.css](#stylescss-)**
- **[TicTacToeController.java](#tictactoecontrollerjava)**
- **[UserProfileController.java](#userprofilecontrollerjava)**
- **[UserSettingsController.java](#usersettingscontrollerjava)**
- **[UtilityManager.java](#utilitymanagerjava)**
- **[WinScreenController.java](#winscreencontroller)**

***
### Account
  - **[statistics](#statistics)**
    - **[AStatistic.java](#astatisticsjava)**
    - **[IStatistics.interface](#istatisticsinterface)**
    - **[MatchOutcomeHandler.java](#matchoutcomehandlerjava)**
    - **[MatchOutcomeInvalidError.exception](#matchoutcomeinvaliderrorexecption)**
    - **[StatisticsCheckers.java](#statisticscheckersjava)**
    - **[StatisticsChess.java](#statisticschessjava)**
    - **[StatisticsCombined.java](#statisticscombinedjava)**
    - **[StatisticsConnect4.java](#statisticsconnect4java)**
    - **[StatisticsTicTacToe.java](#statisticstictactoejava)**
    - **[StatisticType.enum](#statisticstypeenum)**
 

  - **[Account.java](#acccountjava)**
  - **[AccountStorageUtility.java](#accountstorageutilityjava)**
  - **[CreateAccount.java](#createaccountjava)**
  - **[NoAccountError.exception](#noaccounterrorexecption)**
***
### statistics
   - **[AStatistic.java](#astatisticsjava)**
   - **[IStatistics.interface](#istatisticsinterface)**
   - **[MatchOutcomeHandler.java](#matchoutcomehandlerjava)**
   - **[MatchOutcomeInvalidError.exception](#matchoutcomeinvaliderrorexecption)**
   - **[StatisticsCheckers.java](#statisticscheckersjava)**
   - **[StatisticsChess.java](#statisticschessjava)**
   - **[StatisticsCombined.java](#statisticscombinedjava)**
   - **[StatisticsConnect4.java](#statisticsconnect4java)**
- **[StatisticsTicTacToe.java](#statisticstictactoejava)**
- **[StatisticType.enum](#statisticstypeenum)**
- **[Account.java](#acccountjava)**

***
#### `AStatistics.java`

***
#### `IStatistics.interface`
#### `MatchOutcomeHandler.java`
#### `MatchOutcomeInvalidError.execption`
#### `StatisticsCheckers.java`
#### `StatisticsChess.java`
#### `StatisticsCombined.java`
#### `StatisticsConnect4.java`
#### `StatisticsTicTacToe.java`
#### `StatisticsType.enum`
#### `Acccount.java`
#### `AccountStorageUtility.java`
#### `CreateAccount.java`
#### `NoAccountError.execption`

***
### Authentication
- **[Authentication](#authentication-)**
  - **[Admin.java](#adminjava)**
  - **[CAPTCHAAuthentication.java](#captchaauthenticationjava)**
  - **[EmailSender.java](#emailsenderjava)**
  - **[MFAAuthentication.java](#mfaauthenitcationjava)**
  - **[MFAAuthenticationV2.java](#mfaauthenticationv2java)**
  - **[MFAInputPopUp.java](#mfainputpopupjava)**


- **[CAPTCHImages](#captchaimages)**


- **[ExceptionAuthentication](#exceptionauthentications)**
  - **[CAPTCHAuthenticationFailedException.exception](#captchaauthenticationfailedecpectionexception)**
  - **[DecryptionFailedException.exception](#decryptionfailedexceptionexception)**
  - **[EncryptionFailedException.exception](#encryptionfailedexceptionexception)**
  - **[MFAAuthenticationFailedException.exception](#mfaauthenticationfailedexceptionexception)**


- **[MFAPopUpController](#mfapopupcontrollerjava)**
***
### Authentication 
- **[Admin.java](#adminjava)**
- **[CAPTCHAAuthentication.java](#captchaauthenticationjava)**
- **[EmailSender.java](#emailsenderjava)**
- **[MFAAuthentication.java](#mfaauthenitcationjava)**
- **[MFAAuthenticationV2.java](#mfaauthenticationv2java)**
- **[MFAInputPopUp.java](#mfainputpopupjava)**
***
#### `Admin.java`
* This class provides backend functionality for managing and modifying a users account within our system
  Users can update their email, passwords, usernames and more while interacting with the database

***
#### `CAPTCHAAuthentication.java`
* Simple CAPTCHA system to verify that a human is interacting with the application.
  This helps support math, image, and text based CAPTCHA. This was integrated within the Authentication folder to prevent bots from entering the system.
  _* MATH CAPTCHA:_
  * A random equation is generated for the user to answer
    _* TEXT CAPTCHA:_
  * A random set of words are generated for the user to answer
    _* IMAGE CAPTCHA:_
  * A random image is chosen from one of the CAPTCHAImages folder to be output to user
***
#### `EmailSender.java`
* This class sends a 6-digit verification code via email using the Jakarta Mail API. This class connects the Gmail's SMTP server with an app-specific
  password and then sends the code to the users email. Used for MFA verification.
***
#### `MFAAuthenitcation.java`
* Simple MFA is integrated within our system. A random 6-digit code is generated and sent to the users email, the user receives the email on our system, enters in the code to be verified.
* However, before a verification code is sent to the users email, the database checks if the email exists on file first. Then proceeds with further steps.
***
#### `MFAAuthenticationV2.java`
* Sends a code to user's email, user enters the code, if the code is incorrect an Exception is thrown
***
#### `MFAInputPopup.java`
* This class creates a JavaFX pop-up dialog for entering an MFA code. It verifies the input with the expected code, it displays a success or error an message, and returns the result. It will return null if the code is incorrect.
***
### CAPTCHAImages
***
### ExceptionAuthentications
- **[CAPTCHAuthenticationFailedException.exception](#captchaauthenticationfailedecpectionexception)**
- **[DecryptionFailedException.exception](#decryptionfailedexceptionexception)**
- **[EncryptionFailedException.exception](#encryptionfailedexceptionexception)**
- **[MFAAuthenticationFailedException.exception](#mfaauthenticationfailedexceptionexception)**
***
#### `CAPTCHAAuthenticationFailedEcpection.exception`
#### `DecryptionFailedException.exception`
#### `EncryptionFailedException.exception`
#### `MFAAuthenticationFailedException.exception`
***
#### `MFAPopupController.java`
***
### Com.Example
- **[Main.java](#mainjava)**
***
#### `Main.java`
***
### Database
#### `DatabaseConnection.java`
#### `DatabaseManager.java`
#### `DecryptionAuthenitaction.java`
#### `ENcryptionAuthentication.java`
***
### Game
- **[checkers](#checkers)**
  - **[Checkers.java](#checkersjava)**
  - **[CheckersController.java](#checkerscontrollerjava)**
  - **[CheckersPiece.java](#checkerspiecejava)**
- **[Chess](#chess)**
  - **[Bishop.java](#bishopjava)**
  - **[Chess.java](#chessjava)**
  - **[King.java](#kingjava)**
  - **[Pawn.java](#pawnjava)**
  - **[Queen.java](#queenjava)**
  - **[Rook.java](#rookjava)**
- **[connect4](#connect4)**
  -  **[Connect4.java](#connect4java)**
  - **[Connect4Piece](#connect4piecejava)**
- **[pieces](#pieces)**
  - **[MovingPiece.java](#movingpiecejava)**
  - **[Piece.java](#piecejava)**
  - **[PieceType.enum](#piecetypeenum)**
  - **[StationaryPiece.java](#sattionarypiecejava)**
- **[tictactoe](#tictactoe)**
  - **[TicTacToe.java](#tictactoejava)**
  - **[TicTacToePiece.java](#tictactoejava)**
- **[Board.java](#boardjava)**
- **[Game.java](#gamejava)**
- **[GameRules.java](#gamerulesjava)**
- **[GamesEnum.enum](#gamesenumenum)**
- **[GameState.enum](#gamestateenum)**
- **[GameType.enum](#gametypeenum)**
- **[Player.java](#playerjava)**
***
### checkers
- **[Checkers.java](#checkersjava)**
- **[CheckersController.java](#checkerscontrollerjava)**
- **[CheckersPiece.java](#checkerspiecejava)**
***
#### `Checkers.java`
#### `CheckersController.java`
#### `CheckersPiece.java`

***
### chess
- **[Bishop.java](#bishopjava)**
  - **[Chess.java](#chessjava)**
  - **[King.java](#kingjava)**
  - **[Pawn.java](#pawnjava)**
  - **[Queen.java](#queenjava)**
  - **[Rook.java](#rookjava)**
  ***
#### `Bishop.java`
#### `Chess.java`
#### `King.java`
#### `Knight.java`
#### `Pawn.java`
#### `Queen.java`
#### `Rook.java`
***
### connect4
-  **[Connect4.java](#connect4java)**
- **[Connect4Piece](#connect4piecejava)**
***
#### `Connect4.java`
#### `Connect4Piece.java`
***
### pieces
- **[MovingPiece.java](#movingpiecejava)**
- **[Piece.java](#piecejava)**
- **[PieceType.enum](#piecetypeenum)**
- **[StationaryPiece.java](#sattionarypiecejava)**
***
#### `MovingPiece.java`
#### `Piece.java`
#### `PieceType.enum`
#### `SattionaryPiece.java`
***
### tictactoe
- **[TicTacToe.java](#tictactoejava)**
- **[TicTacToePiece.java](#tictactoejava)**
***
#### `TicTacToe.java`
#### `TicTacToePiece`
end of tictactoe
#### `Board.java`
#### `Game.java`
#### `GameRules.java`
#### `GamesEnum.enum`
#### `GameState.enum`
#### `GameType.enum`
#### `Player.java`

***
### Images
- **[sprites](#sprites)**
- **[screen](#screens)**
***
### sprite
#### `sprites`
end of folder sprites
#### `screens` 
All the png that is not in the sprites folder is being used as a screen in the game.
***
### Leaderboard
- **[Leaderboard.java](#leaderboardjava)**
***
#### `Leaderboard.java`
***
### Matchmaking
- **[MatchmakingHandler](#matchmakinghandlerjava)**
  - **[HostingThread.java](#hostingthreadjava)**
  - **[MatchmakingHandler.java](#matchmakinghandlerjava-)**
  - **[MatchmakingThread.java](#matchmakingthreadjava)**
- **[MatchmakingState.enum](#matchmakingstateenum)**
***
### MatchmakingHandler.java
- **[HostingThread.java](#hostingthreadjava)**
- **[MatchmakingHandler.java](#matchmakinghandlerjava-)**
- **[MatchmakingThread.java](#matchmakingthreadjava)**
***
#### `MatchmakingHandler.java`    
#### `MatchmakingThread.java`
#### `HostingThread.java`
end of folder matchmakinghandlertest
#### `MatchmakingState.enum`
***
### Networking                    
- **[test](#test)**
  - **[networkingObjectSending](#networkingobjectsending)**
    - **[GameServer.java](#gameservertjava)**
    - **[Message.java](#messagejava)**
    - **[PlayerClient.java](#playerclientjava)**
    - **[PracticeGameObj.java](#practicegameobjjava)**
  - **[DockerFile-gameS.file](#dockerfile-gamesfile)**
  - **[GameServerT.java](#gameservertjava)**
  - **[PlayerData.java](#playerdatajava)**
  - **[PlayerT.java](#playertjava)**
  - **[stubs.java](#stubsjava)**
- **[badwords.txt](#badwordstxt)**
- **[Networking](#networkingjava)**
***
### test
- **[networkingObjectSending](#networkingobjectsending)**
  - **[GameServer.java](#gameservertjava)**
  - **[Message.java](#messagejava)**
  - **[PlayerClient.java](#playerclientjava)**
  - **[PracticeGameObj.java](#practicegameobjjava)**
- **[DockerFile-gameS.file](#dockerfile-gamesfile)**
- **[GameServerT.java](#gameservertjava)**
- **[PlayerData.java](#playerdatajava)**
- **[PlayerT.java](#playertjava)**
- **[stubs.java](#stubsjava)**
***
### networkingObjectSending
- **[GameServer.java](#gameservertjava)**
- **[Message.java](#messagejava)**
- **[PlayerClient.java](#playerclientjava)**
- **[PracticeGameObj.java](#practicegameobjjava)**
***
#### `GameServer.java`
#### `Message.java`
#### `PlayerClient.java`
#### `PracticeGameObj.java`

#### `Dockerfile-gameS.file`
#### `GameServerT.java`
#### `PlayerData.java`
#### `PlayerDatabase.java`
#### `PlayerT.java`
#### `stubs.java`

#### `badwords.txt`
#### `Networking.java`
***
### Resources.Fonts
- **[Pixelite.ttf](#pixelitetff)**
- **[PressStart2P-Regular.ttf](#pressstart2p-regulartff)**
- **[RetroGaming.ttf](#retrogamingttf)**


#### `Pixelite.tff`
#### `PressStart2P-Regular.tff`
#### `RetroGaming.ttf`
***
### Screens
- **[AdminScreen.fxml](#adminscreenfxml)**
- **[Connect4.fxml](#connect4fxml)**
- **[draw_styles.css](#draw_stylescss)**
- **[DrawScreen.fxml](#drawscreenfxml)**
- **[GameSelect.fxml](#gameselectfxml)**
- **[Help.fxml](#helpfxml)**
- **[LeaderboardScreen.fxml](#leaderboardscreenfxml)**
- **[LeaderboardStyle.css](#leaderboardstylecss)**
- **[Login.fxml](#loginfxml)**
- **[lose_styles.css](#lose_stylescss)**
- **[LoseScreen.fxml](#losescreenfxml)**
- **[MatchType.fxml](#matchtypefxml)**
- **[MenuPopup.fxml](#menupopupfxml)**
- **[MFAPopup.fxml](#mfapopupfxml)**
- **[P1Checkers.fxml](#p1checkersfxml)**
- **[P1Chess.fxml](#p1chessfxml)**
- **[P2Checker.fxml](#p2checkersfxml)**
- **[P2Chess.fxml](#p2chessfxml)**
- **[Signup.fxml](#signupfxml)**
- **[Start.fxml](#startfxml)**
- **[styles.css](#stylescss)**
- **[TicTacToe.fxml](#tictactoefxml)**
- **[Userpopup.fxml](#userpopupfxml)**
- **[UserProfile.fxml](#userprofilefxml)**
- **[UserSettings.fxml](#usersettingfxml)**
- **[WinScreen.fxml](#winscreenfxml)**
***
#### `AdminScreen.fxml`
#### `Checkers.fxml`
#### `Chess.fxml`
#### `Connect4.fxml`
#### `draw_styles.css`
#### `DrawScreen.fxml`
#### `GameSelect.fxml`
#### `Help.fxml`
#### `LeaderboardScreen.fxml`
#### `LeaderboardStyle.css`
#### `Login.fxml`
#### `lose_styles.css`
#### `LoseScreen.fxml`
#### `MatchType.fxml`
#### `MenuPopup.fxml`
#### `MFAPopup.fxml`
#### `P1Checkers.fxml`
#### `P1Chess.fxml`
#### `P2Checkers.fxml`
#### `P2Chess.fxml`
#### `Signup.fxml`
#### `Start.fxml`
#### `styles.css`
#### `TicTacToe.fxml`
#### `Userpopup.fxml`
#### `UserProfile.fxml`
#### `UserSetting.fxml`
#### `WinScreen.fxml`
***

#### `AdminController.java`
#### `Connect4Controller.java`
#### `DrawScreenController.java`
#### `GameSelectController.java`
#### `HelpScontroller.java`
#### `LeaderboardController.java`
#### `LoginController.java`
#### `LoseScreenController.java`
#### `Main.java`
***
### MatchTypeController.java
- **[MatchmakingHandlerWatcher.java](#matchmakinghandlerwatcherjava)**
- **[MatchTypeController.java](#matchtypecontrollerjava)**
***
#### `MatchmakingHandlerWatcher.java`
#### `MatchTypeController.java`
***
#### `MenuPopupController.java`
#### `P1CheckersController.java`
#### `P1ChessController.java`
#### `P2CheckersController.java`
#### `P2ChessController.java`
#### `SceneManager.java`
#### `SignUpController.java`
#### `StartController.java`
#### `styles.css`          
#### `TicTacToeController.java`
#### `UserProfileController.java`
#### `UserSettingsController.java`
#### `UtilityManager.java`
#### `WinScreenController`


***
## Test Folder

## website
- **[assets](#assets-)**
- **[all.css](#allcss)**
- **[checker.css](#checkerscss)**
- **[checkers.html](#checkershtml)**
- **[checkers.js](#checkersjs)**
- **[chess.css](#chesscss)**
- **[chess.html](#chesshtml)**
- **[chess.js](#chessjs)**
- **[connect4.css](#connect4css)**
- **[connect4.html](#connect4html)**
- **[connect4.js](#connect4js)**
- **[help.css](#helpcss-)**
- **[help.html](#helphtml)**
- **[index.css](#indexcss)**
- **[index.html](#indexhtml)**
- **[login.css](#logincss)**
- **[login.html](#loginhtml)**
- **[login.js](#loginjs)**
- **[scripts.js](#scriptsjs)**
- **[signup.css](#signupcss)**
- **[signup.html](#signuphtml)**
- **[signup.js](#signupjs)**
- **[store.css](#storecss)**
- **[store.html](#storehtml)**
- **[store.js](#storejs)**
- **[tictactoe.css](#tictactoecss)**
- **[tictactoe.html](#tictactoehtml)**
- **[tictactoe.js](#tictactoejs)**

### assets     
All sprites and Screen used for the website. **[omgame.club](https://omgame.club/)** 

#### `all.css`
#### `checkers.css`
#### `checkers.html`
#### `checkers.js`
#### `chess.css`
#### `chess.html`
#### `chess.js`
#### `connect4.css`
#### `connect4.html`
#### `connect4.js`
#### `help.css`      
#### `help.html`
#### `index.css`
#### `index.html`
#### `login.css`
#### `login.html`
#### `login.js`
#### `scripts.js`
#### `signup.css`
#### `signup.html`
#### `signup.js`
#### `store.css`
#### `store.html`
#### `store.js`
#### `tictactoe.css`
#### `tictactoe.html`
#### `tictactoe.js`



## Git Log & Git link
- **[git_log_.csv](#git_log_csv)**
- **[gitlab_link.txt](#gitlab_linktxt)**
***
#### `git_log_.csv`
**Purpose:** The list of everyone's commits to the project.
***
#### `gitlab_link.txt`
**Purpose:** The link of the gitlab repository.
***

## Team
- **[Team file](#teammd)**
***
#### `team.md`
**Purpose:** Everyone who worked on the project with the names and UCID.
***