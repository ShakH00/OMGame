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
  - **MySQL** – This starts your local MySQL database
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
      - **[Message.java](#messagejava)**
      - **[PlayerClient.java](#playerclientjava)**
      - **[PracticeGameObj.java](#practicegameobjjava)**
    - **[DockerFile-gameS.file](#dockerfile-gamesfile)**
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
**Purpose:** This abstract class implements the IStatistics interface, providing a structured framework for initializing, validating, and updating game-related statistics for an account. It differentiates between simple statistics and those requiring specialized handling—such as ELO and win rate—which are treated as complex metrics and updated via custom logic. Additionally, the class manages the accumulation and retrieval of statistics, ensuring consistency by automatically recalculating derived values like win rate whenever related statistics change.
***
#### `IStatistics.interface`
**Purpose:** This interface defines the contract for managing game-related statistics for an account, including setting, validating, and retrieving various metrics. It distinguishes between standard statistics and those that require special handling, ensuring that complex statistics cannot be updated through simple addition. Additionally, it provides specific methods to add new statistics and update the ELO rating, thereby standardizing how statistics are maintained across different implementations.
***
#### `MatchOutcomeHandler.java`
**Purpose:** This class manages the recording of match outcomes by updating player statistics and logging match history. It first ensures that the match outcome is valid and then updates both players' statistics, including a detailed log entry that captures the match result, opponent details, and match date. Additionally, if applicable, it recalculates and updates the players’ Elo ratings using game-specific calculations based on the match score and expected outcomes.
***
#### `MatchOutcomeInvalidError.execption`
**Purpose:** This class defines a custom exception specifically for handling invalid match outcomes in the system. It extends the standard Exception class, allowing it to be thrown when match result conditions are not met. By offering both a no-argument constructor and one that accepts an error message, it provides flexibility in conveying detailed error information.
***
#### `StatisticsCheckers.java`
**Purpose:** This class extends a base statistics implementation to specifically handle Checkers game metrics for an account. It defines an array of accepted statistics, including ELO, win rate, wins, losses, draws, matches played, number of turns, pieces captured, and multi-captures. Two constructors ensure that default values (like an initial ELO of 1000) are set up, with one allowing those defaults to be overridden by provided statistic values.
***
#### `StatisticsChess.java`
**Purpose:** This class specializes the base statistics framework for chess by defining a comprehensive set of accepted statistics that include game outcomes, captured pieces across various types, and specific chess actions like checks, checkmates, and promotions. It extends the abstract base class to inherit functionality for initializing, updating, and retrieving these statistics while ensuring consistency with the chess game metrics. Two constructors are provided: one that initializes the statistics with default values via the initializeHashMap() method, and another that accepts a custom statistics map for more flexible initialization.
***
#### `StatisticsCombined.java`
**Purpose:** This class aggregates multiple game statistics objects into a unified set of statistics. It iterates over each provided statistics object, summing non-complex metrics while computing an average for complex ones like ELO. Finally, it updates the overall ELO and recalculates the win rate based on the aggregated data.
***
#### `StatisticsConnect4.java`
**Purpose:** This class specializes the abstract statistics framework for the Connect 4 game by defining specific metrics such as ELO, win rate, wins, losses, draws, matches played, number of turns, and wins blocked. It ensures these statistics are properly initialized, either with default values or using a provided statistics map, to accurately reflect a player's performance in Connect 4. The class leverages inheritance to maintain consistent behavior with other game-specific statistics classes while focusing on metrics unique to Connect 4 gameplay.
***
#### `StatisticsTicTacToe.java`
**Purpose:** This class specializes the abstract statistics framework for Tic Tac Toe by defining metrics such as ELO, win rate, wins, losses, draws, matches played, number of turns, and wins blocked. It initializes these statistics with default values using an initialization method, ensuring a consistent starting point for tracking a player's performance. Additionally, it provides an alternative constructor that allows initialization with a pre-defined statistics map, supporting flexible data management for the game.
***
#### `StatisticsType.enum`
**Purpose:** This enum defines a comprehensive list of statistics that each game must track, ranging from basic metrics like wins, losses, and draws to complex ones like ELO and win rate. It overrides the toString method to convert enum values into human-readable formats by splitting the identifier and capitalizing each word. Additionally, it provides a fromString method to convert a formatted string back into its corresponding enum value, facilitating easy parsing and display of statistics.
***
#### `Acccount.java`
**Purpose:** This class represents a user account in the system, encapsulating key data such as a unique ID, username, email, password, friend list, and game-specific statistics including match history and queue status. It provides constructors for both guest and permanent accounts and includes methods to update and retrieve game statistics, manage matchmaking queues, and log match outcomes. Additionally, it handles account operations like converting guest accounts, validating and updating profile information, and interfacing with the database and matchmaking handler to ensure data consistency and proper account management.
***
#### `AccountStorageUtility.java`
**Purpose:** This class provides utility methods for converting various account data—such as match history, game statistics, and friends lists—into and from string representations. It defines custom delimiters and formats to serialize complex data structures, enabling the data to be stored easily and then reconstructed accurately. Additionally, it reconstructs game-specific statistics objects from the serialized data, ensuring proper mapping for games like chess, checkers, Connect 4, and Tic Tac Toe.
***
#### `CreateAccount.java`
**Purpose:** This class handles the creation of new user accounts by validating input data such as username, email, and password using regular expressions. It ensures the credentials meet defined security and formatting requirements before proceeding. Once validated, it encrypts the account details and saves the account to the database, while displaying error messages when any validation fails.
***
#### `NoAccountError.execption`
**Purpose:** This class defines a custom exception, NoAccountError, that signals an error when an operation tries to access account details not available for a guest account. It extends the standard Exception class, making it compatible with Java’s exception handling mechanism. Two constructors are provided—one default and another that accepts a custom error message—to allow for flexible error reporting.
***
## Authentication
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
**Purpose:**  
* This class provides backend functionality for managing and modifying a users account within our system
  Users can update their email, passwords, usernames and more while interacting with the database

***
#### `CAPTCHAAuthentication.java`
**Purpose:**
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
**Purpose:**
* This class sends a 6-digit verification code via email using the Jakarta Mail API. This class connects the Gmail's SMTP server with an app-specific
  password and then sends the code to the users email. Used for MFA verification.
***
#### `MFAAuthenitcation.java`
**Purpose:**
* Simple MFA is integrated within our system. A random 6-digit code is generated and sent to the users email, the user receives the email on our system, enters in the code to be verified.
* However, before a verification code is sent to the users email, the database checks if the email exists on file first. Then proceeds with further steps.
***
#### `MFAAuthenticationV2.java`
**Purpose:**
* Sends a code to user's email, user enters the code, if the code is incorrect an Exception is thrown
***
#### `MFAInputPopup.java`
**Purpose:**
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
**Purpose:** This class defines a custom exception that is thrown when CAPTCHA authentication fails. It extends Java’s standard Exception class to integrate with the language's error-handling mechanisms. The constructor accepts a custom error message, allowing detailed context to be provided when the exception is raised.
***
#### `DecryptionFailedException.exception`
**Purpose:** This class defines a custom exception called DecryptionFailedException, which extends the standard Exception class. It is used to signal that a decryption process has failed, allowing the error to be caught and handled specifically. Its constructor accepts a custom error message to provide further details about the decryption failure.
***
#### `EncryptionFailedException.exception`
**Purpose:** This class defines a custom exception called EncryptionFailedException, which is used to indicate that an encryption process has failed. It extends the standard Exception class and includes a constructor that accepts a detailed error message, enabling precise error handling during encryption operations.
***
#### `MFAAuthenticationFailedException.exception`
**Purpose:** This class defines a custom exception for signaling a failure during the multi-factor authentication process. It extends the standard Exception class, integrating seamlessly into Java's error handling system. The constructor accepts a custom error message, which provides detailed context when MFA authentication fails.
***
#### `MFAPopupController.java`
**Purpose:** This class serves as the controller for a JavaFX MFA pop-up, handling user verification during multi-factor authentication. It retrieves the code entered by the user from a text field and compares it to a pre-set verification code. If the codes match, it displays a success alert and closes the pop-up window; if not, it shows an error alert prompting the user to try again.
***
### Com.Example
- **[Main.java](#mainjava)**
***
#### `Main.java`
**Purpose:** This class serves as the entry point for a JavaFX application that displays different game outcome screens. It loads an FXML file—in this instance, the win screen—to set up the user interface, assigns a window title ("You Win!"), and sets the scene dimensions to 800 by 600 pixels. Additionally, alternative screens for scenarios such as a draw, loss, or leaderboard display are provided as commented-out options, and the main method launches the application.
***
### Database
#### `DatabaseConnection.java`
**Purpose:** This class manages MySQL database connections by providing static methods to both establish and close connections using the JDBC driver. It attempts to connect to a database with hardcoded credentials—configured by default for a local XAMPP setup—with alternate settings commented out for a different server environment. Additionally, it handles potential exceptions for driver loading and SQL errors, ensuring that connections are properly closed when no longer needed.
***
#### `DatabaseManager.java`
**Purpose:** This class acts as the central gateway between the application and the database for managing account-related data. It provides a variety of static methods to query accounts (by ID, email, or username), retrieve players queued for specific games, and convert complex stored data—such as match history, statistics, and friends lists—into in-memory Java objects. Additionally, it handles account persistence operations, including saving (inserting or updating), deleting, and secure login processes that involve encryption and decryption, as well as generating temporary IDs for guest accounts.
***
#### `DecryptionAuthenitaction.java`
**Purpose:** This class uses a simple Caesar cipher technique with a fixed shift of 3 to decrypt strings by reversing the character shift applied during encryption. It includes internal helper methods that shift characters and reverse that process, ensuring that decryption is applied correctly and handles null or exceptional cases gracefully. Additionally, it provides a driver method for decrypting generic strings and a specialized method for decrypting an Account's email and password fields, throwing a DecryptionFailedException when decryption fails.
***
#### `ENcryptionAuthentication.java`
**Purpose:** This class implements a simple encryption mechanism using a Caesar cipher with a fixed shift of 3, where each character in the input string is shifted by the specified amount. It includes helper methods to perform the character shifting and constructs the encrypted string while handling null inputs and exceptions by throwing an EncryptionFailedException when needed. Additionally, it provides a method to encrypt an Account object's email and password fields, updating the account with the encrypted values.

***
## Game
- **[checkers](#checkers)**
  - **[Checkers.java](#checkersjava)**
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
- **[CheckersPiece.java](#checkerspiecejava)**
***
#### `Checkers.java`
**Purpose:** This class encapsulates the core gameplay logic for a checkers game by managing players, board state, and game state, including tracking turns, captures, multi-captures, and piece promotions. It implements key rules such as forced captures, valid movement checks for both regular and king pieces, and conditions for surrender, draw, and win scenarios. Additionally, it integrates network communication to update and synchronize the game state between opponents during multiplayer gameplay.
***
#### `CheckersPiece.java`
**Purpose:** This class represents an individual checkers piece, extending the functionality of a generic moving piece while adding checkers-specific behavior. It includes a boolean state to track whether the piece has been promoted to a king, which allows it to move backwards. The class overrides the move and isValidMove methods to accommodate checkers rules, although the validity-check logic remains a placeholder for further implementation.

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
**Purpose:** This class implements a bishop piece for chess, extending the generic MovingPiece class with behavior specific to the bishop's diagonal movement. It overrides the move and isValidMove methods to ensure that the bishop only moves diagonally, that all intermediate tiles along the path are empty, and that the destination tile is either empty or occupied by an opposing piece. Additionally, each bishop is assigned a fixed score of 3, reflecting its value in the game, and the class includes boundary checks to prevent illegal moves.
***
#### `Chess.java`
**Purpose:** This class serves as the central controller for a chess game by initializing the board with pieces for two players, managing player turns, and handling move validation including checks for legal moves, exposing the king to check, and detecting special conditions like pinning. It simulates moves to verify safety, updates scores and capture counts, and checks for win conditions such as checkmate, draw, or surrender. Additionally, it integrates network communication to send and receive game state updates, ensuring that both players remain synchronized during multiplayer gameplay.
***
#### `King.java`
**Purpose:** This class models a chess king by extending the generic moving piece functionality, handling both standard one-square moves in any direction and the special castling move. It overrides the move and isValidMove methods to ensure that, beyond normal boundary and occupancy checks, the king can only castle if neither it nor the associated rook has moved and if all squares between them are empty. Additionally, when castling is detected, the class also moves the appropriate rook to complete the maneuver, while tracking the king's movement status to prevent subsequent castling.
***
#### `Knight.java`
**Purpose:** This class defines a chess knight piece by extending the generic moving piece functionality and assigning it a fixed score of 3. It overrides the move and isValidMove methods to enforce the knight’s L-shaped movement pattern, which allows it to jump over other pieces while ensuring that the destination is either vacant or occupied by an opponent’s piece. Through these specialized behaviors, it accurately models the unique movement rules and strategic value associated with the knight in chess.
***
#### `Pawn.java`
**Purpose:** This class models a chess pawn by extending a generic moving piece, with specific attributes (doneFirstMove and doneSecondMove) to track whether it can move two spaces forward on its initial move and whether it is vulnerable to an en passant capture. The move method incorporates standard single and double forward advances, diagonal captures (including en passant), and updates these flags accordingly. Additionally, the class provides methods for checking promotion eligibility and executing promotion to a knight, bishop, rook, or queen when the pawn reaches the opposite end of the board.
***
#### `Queen.java`
**Purpose:** This class represents a queen chess piece, extending generic movement capabilities and assigning it a high scoring value of nine. It overrides the move and isValidMove methods to allow the queen to move any number of unobstructed squares along diagonals, horizontals, or verticals, similar to combining the movements of a rook and a bishop. Additionally, the class ensures that the destination square is either empty or occupied by an opposing piece, allowing the queen to capture enemy pieces efficiently.
***
#### `Rook.java`
**Purpose:** This class models a rook chess piece by extending the generic moving piece functionality while assigning it a specific scoring value. It verifies that the rook's movement is valid by ensuring it travels in a straight line—either horizontally or vertically—with no obstructions along its path. Additionally, the class maintains a flag to track whether the rook has moved before, which is essential for enabling the special castling move in chess.
***
### connect4
-  **[Connect4.java](#connect4java)**
- **[Connect4Piece](#connect4piecejava)**
***
#### `Connect4.java`
**Purpose:** This class encapsulates all Connect 4 game logic by initializing players, board, score, and game state, while also setting up necessary game rules and networking support for multiplayer synchronization. It provides a move method that places a piece into the first available slot in a chosen column and then checks for winning conditions by scanning rows, columns, and both diagonal directions for four consecutive matching pieces. Additionally, it manages game outcome states including wins, draws, and player surrenders, updating and communicating the game state accordingly over the network.
***
#### `Connect4Piece.java`
**Purpose:** This class defines a Connect 4 game piece by extending a stationary piece, which implies that once it is placed on the board it doesn't move. It initializes the piece with its color, type, and the player who owns it, inheriting common properties from its superclass. Essentially, it serves as a marker for a player's move in the Connect 4 game.
***
### pieces
- **[MovingPiece.java](#movingpiecejava)**
- **[Piece.java](#piecejava)**
- **[PieceType.enum](#piecetypeenum)**
- **[StationaryPiece.java](#sattionarypiecejava)**
***
#### `MovingPiece.java`
**Purpose:** This abstract class serves as a foundation for all moving game pieces used in chess and checkers by extending a more general Piece class. It maintains the piece's x and y coordinates and provides getter and setter methods for position management. Additionally, it declares two abstract methods—move and isValidMove—that must be implemented by subclasses to handle the piece-specific logic for movement and move validation.
***
#### `Piece.java`
**Purpose:** This abstract class serves as the base for all game pieces across various board games by encapsulating common properties such as color, owning player, score, and piece type. It provides getter methods to access these attributes and includes a default implementation for checking if a piece is a king, which subclasses can override as needed.
***
#### `PieceType.enum`
**Purpose:** This enum defines two constants—LIGHT and DARK—to differentiate between the two types of pieces in games like chess, representing the opposing sides such as white and black. It is used to determine movement directions and enforce piece-specific rules based on their type. By using an enum, the code ensures type safety and clarity when handling piece attributes throughout the game logic.
***
#### `SattionaryPiece.java`
**Purpose:** This abstract class defines a stationary game piece that, once placed, remains fixed on the board. It extends the base Piece class to inherit common properties such as color, piece type, and the owning player, while setting a default score of one. Essentially, it provides a simple foundation for non-moving game pieces used in various board games.
***
### tictactoe
- **[TicTacToe.java](#tictactoejava)**
- **[TicTacToePiece.java](#tictactoejava)**
***
#### `TicTacToe.java`
**Purpose:** This class implements a complete Tic Tac Toe game by extending a generic game framework. It initializes players, a board, and assigns piece types randomly to the two players, then manages moves by placing pieces if the target cell is empty. Additionally, it continuously checks for win conditions across rows, columns, and diagonals, updates the game state to reflect wins, draws, or a player surrender, and synchronizes the game state over the network for multiplayer gameplay.
***
#### `TicTacToePiece`
**Purpose:** This class defines a Tic Tac Toe game piece by extending a stationary piece, meaning once it is placed on the board it doesn't move. It initializes the piece with its color, type, and owner, and inherits common properties from its superclass, serving as a marker for a player's move in the Tic Tac Toe game.
***
#### `Board.java`
**Purpose:** This class models a game board as a two-dimensional array of pieces, setting its dimensions dynamically based on the specific game type (for example, 8x8 for chess and checkers, 3x3 for tic tac toe, and 6x7 for Connect 4). It offers specialized methods to fill the board with the appropriate pieces for chess (via fillChessBoard) or checkers (via fillBoard), as well as generic utility functions such as placing a piece, checking if the board is full, and resetting the board to its initial state. Overall, it serves as a flexible, game-agnostic foundation for displaying and manipulating the state of various board games.
***
#### `Game.java`
**Purpose:** This abstract class lays out the general framework for any game by defining shared properties such as players, scores, game type, board, state, and rules. It enforces a contract through abstract methods—checkWinCondition, surrender, and matchOutcome—that must be implemented by any concrete subclass to handle game-specific logic. This design promotes consistency and modularity, allowing various game types to extend and customize behavior while reusing common attributes.
***
#### `GameRules.java`
**Purpose:** This class is designed to serve as a container for rules governing a game, centralizing any logic or constraints that might apply. Although it's currently empty, it provides a structural placeholder that can be extended with rule-specific functionality in the future. In the overall game framework, it standardizes the concept of game rules, facilitating uniform management across different game types.
***
#### `GamesEnum.enum`
**Purpose:** This enum lists all the game types supported by the online gaming platform—CHECKERS, CHESS, CONNECT4, and TICTACTOE. It serves as a type-safe way to reference games across the system, helping ensure consistency and clarity in the codebase. Additionally, using an enumeration for game types reduces the likelihood of errors when handling or switching between different games.
***
#### `GameState.enum`
**Purpose:** This enum defines all possible states that a game can be in within the platform. It includes states to indicate whose turn it is (P1_TURN, P2_TURN), outcomes like wins for either player (P1_WIN, P2_WIN) or a draw (DRAW), a condition where a king is in check (IN_CHECK), and the initial setup phase (SETUP). Using this enum standardizes game state management across different game types, ensuring consistent handling of game progress throughout the application.
***
#### `GameType.enum`
**Purpose:** This enum defines the different types of games available on the platform: CHESS, CHECKERS, CONNECT4, and TICTACTOE. It overrides the toString method to provide a human-readable, title-cased representation of each game type. Additionally, it includes a fromString method that converts a string back into the corresponding GameType enum value, ensuring robust handling of game type data.
***
#### `Player.java`
**Purpose:** This class represents a player in the game. It stores basic information, such as the team (as a string), and includes a constructor that outputs a debug message when a player is created. The class also declares methods for retrieving win and lose counts, checking turn status, and surrendering, though these methods currently return default values or are stubs, indicating that further implementation is expected.
***
## Images
- **[sprites](#sprites-1)**
- **[screen](#screens-)**
***
### sprite
####
#### `sprites`
**Purpose:** This folder contains all sprites used in the OMG game.
***
#### `screens` 
**Purpose:** All the png that is not in the sprites folder is being used as a screen in the game.
***
### Leaderboard
- **[Leaderboard.java](#leaderboardjava)**
***
#### `Leaderboard.java`
**Purpose:** This class builds a leaderboard for a specified game by retrieving accounts from the database or a user's friend list and sorting them based on a chosen statistic (e.g., Elo, win rate, wins) in either ascending or descending order. It then formats the sorted results into a two-dimensional String array that includes a header row and subsequent rows containing details such as rank, username, Elo, win rate, total wins, and an additional statistic selected by the user. Additionally, the class handles guest account verification (throwing a NoAccountError for guest accounts in the friends leaderboard) and incorporates pagination parameters (although currently not used) when constructing the leaderboard.
***
## Matchmaking
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
**Purpose:** This class handles the matchmaking process by interfacing with a database to register players who wish to play a game, update their matchmaking details, and match them based on criteria such as game type and Elo differences. It provides methods for both hosting and joining a match, and utilizes two separate thread classes—HostingThread and MatchmakingThread—to continuously monitor and update matchmaking status asynchronously. When a match is found, it sets various parameters (such as player IDs, usernames, Elo ratings, and networking information) so that the game can start with both opponents synchronized.
***
#### `MatchmakingThread.java`
**Purpose:** This thread class handles the matchmaking process for a player looking for an opponent. It continuously updates the player's matchmaking information (e.g., recent activity, Elo range, and networking details) in the database and periodically scans for other players who are also matchmaking and meet the matching criteria. Once a suitable opponent is found, it initiates a match by updating both players' matchmaking states and then removes the player’s record from the matchmaking table.
***
#### `HostingThread.java`
**Purpose:** This thread class represents the hosting component for matchmaking. It registers the host's matchmaking information (including game type, Elo, room code, and networking info) in the database, then enters a loop where it continuously checks whether another player has joined the room by monitoring the host's matchmaking state. Once an opponent is detected (state changes to FOUND_MATCH), it initiates a match and finally removes the host's entry from the matchmaking table.
***
#### `MatchmakingState.enum`
**Purpose:** This enum defines the various states a user can be in during the matchmaking process, including OFFLINE, ONLINE, MATCHMAKING, FOUND MATCH, PLAYING, and HOSTING. It overrides the toString method to provide a human-readable, title-cased representation of each state and includes a fromString method to convert a string back to its corresponding enum value. This standardizes the handling of matchmaking statuses throughout the system.
***
## Networking                    
- **[test](#test)**
  - **[networkingObjectSending](#networkingobjectsending)**
    - **[GameServer.java](#gameserverjava)**
    - **[Message.java](#messagejava)**
    - **[PlayerClient.java](#playerclientjava)**
    - **[PracticeGameObj.java](#practicegameobjjava)**
  - **[DockerFile-gameS.file](#dockerfile-gamesfile)**
  - **[PlayerData.java](#playerdatajava)**
  - **[PlayerT.java](#playertjava)**
  - **[stubs.java](#stubsjava)**
- **[badwords.txt](#badwordstxt)**
- **[Networking](#networkingjava)**
***
### test
- **[networkingObjectSending](#networkingobjectsending)**
  - **[GameServer.java](#gameserverjava)**
  - **[Message.java](#messagejava)**
  - **[PlayerClient.java](#playerclientjava)**
  - **[PracticeGameObj.java](#practicegameobjjava)**
- **[DockerFile-gameS.file](#dockerfile-gamesfile)**
- **[PlayerData.java](#playerdatajava)**
- **[PlayerT.java](#playertjava)**
- **[stubs.java](#stubsjava)**
***
### networkingObjectSending
- **[GameServer.java](#gameserverjava)**
- **[Message.java](#messagejava)**
- **[PlayerClient.java](#playerclientjava)**
- **[PracticeGameObj.java](#practicegameobjjava)**
***
#### `GameServer.java`
**Purpose:** This class, GameServer, sets up and manages a server that listens for incoming socket connections on designated ports for both game-related data and chat communications, accepting two players to initiate a game session. It spawns a separate thread for each connected client via its nested ServerSideConnection class, which handles real-time exchange of game objects (to process moves and update gameplay state) and chat messages, employing integrated censorship logic to filter out prohibited language. Additionally, the class implements mechanisms to process player inputs, maintain game state (such as tracking turns and moves), and relay both game and chat information between clients to facilitate synchronous multiplayer gameplay.
***
#### `Message.java`
**Purpose:** This class defines a simple data structure that encapsulates a player's message along with the player's number. It provides a constructor to initialize these fields and getter and setter methods to access and modify the player number and message. Overall, it serves as a basic model for passing messages between components in a networked environment.
***
#### `PlayerClient.java`
**Purpose:** This class implements a JavaFX client that provides a graphical user interface for a player to participate in a networked turn-based game with integrated chat functionality. It manages the connection to the game server by establishing separate socket connections for game data and chat messages, handling input through button clicks, updating game state, and displaying chat messages while applying censorship to inappropriate content. Additionally, the class uses asynchronous threads to receive game updates and chat messages, ensuring that the user interface remains responsive during real-time multiplayer gameplay.
***
#### `PracticeGameObj.java`
**Purpose:** This class defines a serializable object used for practice gameplay that encapsulates a win flag, a 2D character array representing the game board, and a test string for additional information. It enables instances to be transmitted over a network or saved to disk due to its implementation of the Serializable interface. This object is likely used for testing purposes within the networking system to simulate game state and communication between client and server.
***
#### `Dockerfile-gameS.file`
**Purpose:** This Dockerfile uses the OpenJDK 23 base image in a Linux environment and sets the working directory to /app/src. It copies the source code into the container, exposes port 30000, creates a build directory, and compiles GameServerT.java into that build directory. Finally, it defines the entrypoint to launch the server by running the GameServerT class from the build directory using the Java runtime.
***
#### `PlayerData.java`
**Purpose:** This class implements a serializable container for storing a player's basic matchmaking data, including their user ID, username, and a HashMap of Elo ratings for different games. It also stores the game mode the player is interested in, allowing for dynamic updates and retrievals of this data through various getter and setter methods. Additionally, it provides a method to print a formatted summary of the player's data for debugging or logging purposes.
***
#### `PlayerDatabase.java`
T**Purpose:** his class acts as an in-memory database, mapping global user IDs to their corresponding PlayerData objects and providing methods to add, remove, and retrieve player data. It allows querying and updating of players’ Elo ratings for specific game modes, as well as printing player information. Additionally, it includes a method to sort and return the top N players by Elo in a given game mode.
***
#### `PlayerT.java`
**Purpose:** This class, PlayerT, implements a Swing-based graphical client for a turn-based networked game, providing a user interface with menus, a 3x3 button grid for game moves, and integrated chat functionality. It manages layout updates and interactions by dynamically switching between the game menu and game scenes, handling user input for both game actions and chat messages. Additionally, it encapsulates client-side networking through an inner ClientSideConnection class that sends user moves and chat messages to the server and updates the display based on server responses.
***
#### `stubs.java`
**Purpose:** This class contains several nested stub classes that simulate key networking functions for testing purposes. The PlayerDatabaseStubs, GameServerStubs, PlayerDataStubs, and ChatStubs simulate database connections, Elo management, server hosting and connection acceptance, and chat message operations respectively, using simple logging and dummy data structures. Collectively, these stubs provide placeholder implementations that allow for testing integration and behavior of networking features in the absence of a full backend solution.
***
#### `badwords.txt`
**Purpose:** This txt hold the censored words that the player cannot say. 
***
#### `Networking.java`
**Purpose:** This class serves as a client-side networking utility, managing a cached game state and providing methods to send and receive the full game state between players while printing timestamped debug messages. It includes stub methods for connecting to a player, creating a game, and switching to listening mode, though the actual connection logic remains unimplemented. Additionally, it formats time using a specified pattern for clear debugging output regarding its networking operations.
***
## Resources.Fonts
- **[Pixelite.ttf](#pixelitetff)**
- **[PressStart2P-Regular.ttf](#pressstart2p-regulartff)**
- **[RetroGaming.ttf](#retrogamingttf)**

***
#### `Pixelite.tff`
**Purpose:** This file is a TrueType font file that contains scalable vector-based font data. It is typically used to render text in a specific, custom style within an application’s user interface. 
***
#### `PressStart2P-Regular.tff`
**Purpose:** This file is a TrueType font file that likely provides a retro, pixelated text style. It is commonly used in video games or designs that want to evoke a classic arcade or 8-bit aesthetic.
***
#### `RetroGaming.ttf`
**Purpose:** This file is another TrueType font file, likely designed with a retro gaming or pixelated theme in mind. It can be used to create a vintage video game look, reminiscent of arcade games from the 80s and 90s .
***
***
## Screens
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
**Purpose:** This is an FXML file for a JavaFX user interface layout designed for an "Admin Console" to manage user data. It features various interactive components, including:

1. User Input Fields for editing details such as User ID, Username, Email, and Password, alongside action buttons like "FIND USER," "CLEAR STATS," and "DELETE USER" to manage user accounts.


2. Tabs to organize the interface, with one tab dedicated to editing user information and another to handle graphical content.


3. Styling and Effects such as custom backgrounds, text formatting, gradients, and hover effects for buttons, providing a polished user experience.

The layout also includes a Tetris cat image with a playful message, reinforcing user engagement.
***
#### `Connect4.fxml`
**Purpose:** This FXML file defines a user interface layout for a Connect 4 game, featuring a game board and various interactive components. Key elements include:

1. Game Board Grid: A 6x7 grid created with ImageView elements to represent the Connect 4 cells, where each cell can be clicked to make a move, with visual effects applied for interaction.


2. Player Info: Labels to display the usernames of two players (Player 1 and Player 2) with corresponding styling, including a playful font and color scheme.


3. Buttons and Navigation: Interactive buttons, such as a menu button and a chat button, allowing users to navigate between different game features or interact with the game.

This layout also utilizes background images, shadows, and hover effects for a polished and engaging user experience.
***
#### `draw_styles.css`
**Purpose:** This CSS file provides the styling for the win screen of a game. It includes:

1. Root Style: The font is set to "Press Start 2P", a retro gaming font, applied to the entire screen.


2. Background: The win screen (#drawScreenRoot) has a background color of light blue (#55AAFF) and a desert-themed image (desert_popup.png), with the image set to cover the screen without repeating.


3. Text Styling: The "YOU WIN" text (.draw-text) is styled with a warm brown color, bold font, and the "Press Start 2P" font, making it stand out against the background.


4. Button Styling: The buttons (.game-button) are styled with a transparent background, brown text, and the same retro font, with hover effects that change the text color to dark brown.

This style creates a visually appealing, retro-themed win screen with clear, bold text and interactive buttons.
***
#### `DrawScreen.fxml`
**Purpose:** This FXML layout file defines the draw screen UI for a game, using JavaFX elements:

1. Root Layout: The root is a VBox (vertical box), which aligns its children to the center and has a 15px spacing between elements. The layout is styled using an external CSS file (draw_styles.css).


2. Text: A Label is displayed with the text "It's a Draw!", and it uses the draw-text style class to apply specific styling (such as font color and size).


3. Buttons: Inside an HBox (horizontal box), two buttons are placed:

   - "Main Menu" button, which triggers the handleMainMenu method when clicked.
 
   - "Start Over" button, which triggers the handleStartOver method when clicked. The buttons are styled using the game-button class defined in the external CSS file.

This layout provides a clean and functional draw screen where users can either return to the main menu or restart the game.
***
#### `GameSelect.fxml`
**Purpose:** This FXML layout defines the Game Selection Screen for a game application:


1. Root Layout: The root is an AnchorPane, which provides flexible positioning for its children elements. The background features a linear gradient from #169fd4 to #0174aa, and several images are used to enhance the UI's visual appeal.


2. Game Options: It includes several StackPane elements representing different games (Checkers, Connect4, Chess, TicTacToe) with clickable icons. When clicked, these options trigger methods like #switchToCheckers or #switchToChess.


3. Other UI Components: There are several images, labels, and rectangles to create a visually attractive interface, including a "Game Selection" text, a menu button to return to the home screen, and a leaderboard button for viewing the leaderboard. Additionally, the joinPopup pane is used for displaying a pop-up dialog with more information about the selected game. The overall design features effects like DropShadow to enhance the user experience.
***
#### `Help.fxml`
**Purpose:** This FXML layout defines a Help Popup Screen for an application:


1. Design and Background: The AnchorPane has a background gradient with blue and light blue tones. Several ImageView elements display animated GIFs (e.g., tetriscat_swish.gif and tetriscat_paws.gif), creating a playful, visually engaging interface.


2. Help Text and Interactive Elements: The popup provides helpful information with texts like "need help? visit us at omgame.club for tips and other info!" and a small, humorous icon "(^. .^)" to represent the cat mascot. Additionally, a "SOS!" button is displayed in a tilted rectangular shape, prompting urgent help. The close button allows users to dismiss the popup.


3. Aesthetic Effects: The interface includes visual enhancements such as DropShadow effects on text and buttons, making the elements stand out and improving the user experience.
***

#### `LeaderboardScreen.fxml`
**Purpose:** This FXML layout defines the Leaderboard Screen for a game application:

1. Top Section: It contains buttons for selecting different game types (e.g., Tic Tac Toe, Chess, Checkers, Connect 4) and a back button for navigation. There are also toggle buttons to switch between "Global" and "Friends" leaderboard views and combo boxes to sort the leaderboard by different statistics.


2. Center Section: The main part of the screen features a TableView displaying leaderboard information such as Rank, Username, ELO, Win Rate, Wins, and Additional Stat. Below the table, there is a Pagination control for navigating between different pages of the leaderboard data.


3. Layout and Styling: The interface uses a BorderPane for overall layout, with content arranged in a VBox and HBox for proper alignment and spacing. It also uses a custom stylesheet (LeaderboardStyle.css) for styling. The TableView allows the leaderboard to dynamically display and sort player data.

***
#### `LeaderboardStyle.css`
**Purpose:** This CSS file defines the styling for the Leaderboard screen of a game application:

1. Root and Background Styling: The root container has a background image (leaderboardimg.png) that covers the entire screen. It uses the Segoe UI font for text, creating a sleek and clean look.


2. Labels and Buttons: The labels such as the game type and header have customized fonts, colors, and drop shadow effects for emphasis. The game buttons (e.g., Tic Tac Toe, Chess) have a gradient background with hover and pressed states that change color. Additionally, toggle buttons for the "Global" and "Friends" views have interactive effects and customized background colors.


3. Table and Pagination: The TableView displaying leaderboard data is styled with alternating row colors, a gradient column header, and a hover effect for better visibility. The pagination control has rounded buttons and a clean design to navigate through pages of the leaderboard.


4. Dropdown and ComboBox Styling: The combo boxes for sorting and additional stats have smooth, rounded edges, with hover effects for the user interface.

***
#### `Login.fxml`
**Purpose:** This FXML layout defines the user interface for a Login Screen in a game application:

1. Background and Styling: The background features a gradient color scheme from #169fd4 to #0174aa, and there are decorative ImageView elements showing animated GIFs and a chessboard image, which enhances the visual appeal. It uses a clean and modern aesthetic with rounded rectangles and soft drop shadows to give depth.


2. Login Form: The login form includes TextField for entering the username, PasswordField for the password, and buttons for submitting the login information and navigating to a sign-up or guest mode. The submit button has a pink color with shadow effects to make it stand out, and the design includes text labels for "Username" and "Password," both styled in the Press Start 2P Regular font.


3. Navigation and Interactivity: There are interactive elements such as buttons and StackPane components that allow users to switch between the login screen, game selection, and sign-up pages. Additionally, the layout includes a "guest" option for users to proceed without an account, enhancing the flexibility of the login process. The "Back" button is styled with a yellow background, providing easy navigation for the user.

***
#### `lose_styles.css`
**Purpose:** This CSS file is used for styling the Lose Screen in a game application:

1. Background: The screen background is set to a white color (#FFFFFF), with a night-themed image (night_popup.png) that repeats the background image to cover the entire screen. The image is positioned centrally to maintain focus.


2. Text Style (YOU LOSE): The text displaying "YOU LOSE" is styled with a bold, white font (#fff), using the Press Start 2P font. The font size is set to 14px, ensuring it stands out on the screen.


3. Buttons: The buttons on the loose screen are transparent with a light blue-grey text color (#A0B1C9) that changes to white (#FFFFFF) when hovered. The buttons are designed with the Press Start 2P font, offering a consistent retro gaming style. The buttons are also padded for better user interaction.

***
#### `LoseScreen.fxml`
**Purpose:** This FXML file defines the layout and structure for the Lose Screen in a JavaFX application:

1. VBox Layout: The root layout is a VBox, which stacks the elements vertically with a center alignment and 15px spacing between them.


2. Label: A Label displays the message "YOU HAVE LOST!" with a custom style applied (lose-text), which is defined in the associated lose_styles.css file. The label is centrally aligned.


3. HBox Layout: Below the label, an HBox is used to horizontally arrange two buttons with 10px spacing between them:
   - Main Menu Button: This button allows the user to return to the main menu when clicked.
   - Start Over Button: This button allows the user to restart the game when clicked.

Both buttons are styled using the game-button class from the lose_styles.css file.

***
#### `MatchType.fxml`
**Purpose:** This FXML layout is for a Match Type Selection screen in a JavaFX application, where the user can choose to either host a new game, join an existing game by entering a match ID, or go back to the home screen. Here's an overview of the structure and functionality:

This layout is centered around an engaging game selection interface with clear options for users to host or join games. The visual style is bright and retro, consistent with the game’s theme, and it's designed to be interactive and user-friendly.
***
#### `MenuPopup.fxml`
**Purpose:** This FXML layout represents a popup menu in a game, likely used for actions like "Resign", "Request Draw", and "Cancel" during a match. Here's a breakdown of the structure and its functionality:

This layout makes the interface clean and functional, with large, easily clickable buttons and a retro game aesthetic that adds to the visual appeal and user engagement.
***
#### `MFAPopup.fxml`

**Purpose:** This FXML layout represents a Multi-Factor Authentication (MFA) Popup interface where users can input their MFA code. It includes a Label prompting the user to enter the code, a TextField for input, and a Submit button to verify the code, styled with simple padding and font adjustments to enhance usability and clarity. The popup is centered and has a clean white background for a streamlined user experience.
***
#### `P1Checkers.fxml`
**Purpose:** This FXML layout represents a Checkers game interface with a visually engaging design. It features a game board implemented using a GridPane filled with checker pieces, styled with images. The layout includes interactive elements such as menu, chat buttons, and labels for Player 1 and Player 2. The interface is set against a dynamic background with options for navigating to different game functions, providing an immersive user experience for the checkers game.
***
#### `P1Chess.fxml`
**Purpose:** This FXML layout is designed for a Chess game interface where a player can promote their pawn to one of four pieces: Knight, Bishop, Rook, or Queen. The interface is visually styled with a desert background and features a chessboard in the center, along with interactive buttons for pawn promotion. A pop-up appears when a pawn is promoted, offering choices for the user to select the new piece. The design also includes labels for Player 1 and Player 2, and navigation buttons like menu and chat for a comprehensive gaming experience.
***
#### `P2Checkers.fxml`
**Purpose:** This FXML layout represents the user interface for a Checkers game with a forest-themed background and a checkered game board centered on the screen. The game board is embedded in a GridPane, where each cell is designed to hold an image of a checker piece, and the board is rotated to create a mirrored view for Player 2. The interface also includes a menu button and a chat button for game navigation, alongside labels showing the names of Player 1 and Player 2. The layout is visually structured for an engaging multiplayer game experience.

***
#### `P2Chess.fxml`
**Purpose:** This FXML layout represents the Checkers game interface for Player 2, featuring a desert-themed background and a blue checkered game board. The GridPane holds the game pieces, and it is rotated for Player 2's perspective. Additionally, there is a pawn promotion popup with buttons for choosing a Knight, Bishop, Rook, or Queen upon promoting a pawn. The interface also includes menu and chat buttons, as well as labels displaying the names of Player 1 and Player 2, providing an interactive and engaging gaming experience.

***
#### `Signup.fxml`
**Purpose:** This FXML layout is for the sign-up screen of a game, featuring a blue gradient background with images of twinkling stars and a chessboard. The interface includes text inputs for email, username, and password, each with distinct rounded text fields and a submit button. There's also a back button to return to the home screen, a login prompt for users who already have an account, and a guest login option for those who wish to play without creating an account. The design uses retro-style fonts and drop shadows to enhance the visual appeal, giving the UI a playful, game-like feel.

***
#### `Start.fxml`
**Purpose:** This FXML layout represents the start screen for a game with a retro aesthetic. It features a gradient blue background and includes several animated and static images like twinkling stars and a chessboard, setting the stage for an interactive experience. Prominent on the screen is a large "ONLINE MULTIPLAYER BOARD GAME" text in a retro font, paired with images of game pieces like a purple knight and a white rook.

***
#### `styles.css`
**Purpose:** This class defines the styling for the win screen of a game. It sets a cloud-themed background with a retro font ("Press Start 2P") for text elements. The "YOU WIN" text is styled with a bold gold color, while the buttons are given a transparent background with subtle hover effects that change the text color to provide an interactive experience.

***
#### `TicTacToe.fxml`
**Purpose:** This FXML layout defines the interface for a Tic-Tac-Toe game screen, featuring a mountain and star background with a central game grid where players can click to place their marks. It includes player name labels at the top, a menu button for navigation, and a chat button for communication, along with interactive elements such as clickable grid cells to play the game. The design is enhanced with image effects and custom fonts for a retro aesthetic.

***
#### `Userpopup.fxml`
**Purpose:** This FXML layout defines a stylish menu popup for a game interface with a TetrisCat theme. The design features a background gradient, a central container with the TetrisCat logo, and interactive elements such as a "SOS" button and a catchphrase beneath the cat icon. It also includes subtle effects, like drop shadows and gradient borders, to create an engaging and visually appealing user interface.

***
#### `UserProfile.fxml`
**Purpose:** This FXML layout defines a user profile interface featuring an interactive tab pane where users can view their overview, match history, and friend list. The profile is visually enhanced with elements like a gradient background, shadow effects, and circular avatars, while the match history is displayed with a scrollable list of past games, showing win/loss/draw results, opponent names, and game statistics. This design provides a sleek and engaging way to manage and view user data in a game interface.

***
#### `UserSetting.fxml`
**Purpose:** This FXML layout defines a user settings page with a profile editor, allowing users to customize their display name, password, and privacy settings. The page features a dual-column design, with a left panel displaying the user's avatar and bio, and a right panel containing editable fields for the user’s credentials, privacy toggles for stats and friend visibility, and buttons for changing the avatar and banner. The layout combines a retro gaming style with vibrant colors, rounded corners, and a clean, user-friendly interface.
***
#### `WinScreen.fxml`
**Purpose:** This FXML layout defines a win screen for a game, featuring a central "YOU WIN!!" label in a bold, styled font, with two buttons positioned below. The buttons allow users to either return to the main menu or restart the game. The layout uses a VBox to arrange the elements vertically with some spacing, and a HBox to place the buttons side by side, all styled through an external CSS file for a consistent, polished look.
***

#### `AdminController.java`
**Purpose:** This Java class defines the AdminController for managing an admin interface in a JavaFX application. It includes functionality for an administrator to interact with user data by finding, deleting, and updating user information, as well as clearing user stats. The application integrates FXML for the UI and JavaFX for scene management, transitioning, and event handling. There are placeholders for database interaction (such as querying user accounts and updating information), but the actual database connection and logic need to be implemented. Additionally, the AdminController uses custom transitions for button interactions and loads a custom font for the UI.
***
#### `Connect4Controller.java`
**Purpose:** The Connect4Controller class is a JavaFX controller for managing the logic and user interactions in a Connect4 game application. It includes functionality for handling user input (e.g., clicking on columns to drop pieces), updating the game board, and switching between game states such as win, draw, or continuing the game. The class also manages visual elements like showing the player's piece and handling animations for hovering over columns, as well as integrating with the main menu and chat functionality. The game is initialized, and transitions are set up for user interaction, such as displaying a hand animation when hovering over columns to place pieces.
***
#### `DrawScreenController.java`
**Purpose:** The DrawScreenController class is responsible for managing the actions on the "Draw Screen" in a JavaFX application, such as handling the "Start Over" and "Main Menu" button events. When the user clicks on these buttons, the class either restarts the game or navigates back to the main menu by switching the scene. It uses the SceneManager class to manage scene transitions and includes basic event handling and stage manipulation.
***
#### `GameSelectController.java`
**Purpose:** The GameSelectController class manages the game selection screen in a JavaFX application. It allows users to select different games (Connect 4, TicTacToe, Chess, Checkers), displays a waiting popup when a game is chosen, and handles scene transitions to the respective game screens. The class also includes an animation feature that updates the waiting message with dots to indicate that the player is waiting for a match, and it provides buttons for navigation to the user profile and leaderboard screens.
***
#### `HelpScontroller.java`
**Purpose:** The HelpController class handles the help screen functionality in a JavaFX application. It displays a help popup when triggered, allowing users to access help content, and includes functionality to close the help window when the user clicks the close button. The class also defines the main method to launch the application and sets up the scene for the help screen.
***
#### `LeaderboardController.java`
**Purpose:** The LeaderboardController class manages the functionality of a leaderboard screen in a JavaFX application, allowing users to view game leaderboards based on different criteria like ELO, win rate, and additional statistics. It enables the selection of different game types, sorting options, and views (global or friends-only), and populates a table with leaderboard data. The class also provides error handling for situations like when a user is not logged in and includes methods for changing the leaderboard display and interacting with various UI components.
***
#### `LoginController.java`
**Purpose:** The LoginController class manages the login functionality in a JavaFX application, handling user authentication via username and password. It interacts with a database to verify the user's credentials, and upon successful login, triggers Multi-Factor Authentication (MFA) through a popup. The class also provides navigation between screens, including switching to the game selection screen or admin panel based on user credentials, and managing UI elements like buttons and text fields.
***
#### `LoseScreenController.java`
**Purpose:** The LoseScreenController class manages the functionality of the loss screen in a JavaFX application. It provides two buttons: one for restarting the game and another for navigating to the main menu. When the user clicks either button, the appropriate action is triggered, such as restarting the game or returning to the main menu, using scene transitions managed by SceneManager.

***
#### `Main.java`
**Purpose:** The Main class is the entry point for the JavaFX application, which manages the initialization and display of the primary stage. It registers the starting screen using SceneManager.registerScenes("/screens/Start.fxml"), then loads and shows a modal pop-up (like DrawScreen, WinScreen, or LoseScreen) using FXMLLoader and Stage to create a separate window that blocks interaction with the main window until closed. The modal window's purpose is to display the outcome of a game before the user can interact further.
***
### MatchTypeController.java
- **[MatchmakingHandlerWatcher.java](#matchmakinghandlerwatcherjava)**
- **[MatchTypeController.java](#matchtypecontrollerjava)**
***
#### `MatchmakingHandlerWatcher.java`
**Purpose:** The MatchmakingHandlerWatcher class is a background thread responsible for monitoring the matchmaking process in a game. It continuously checks the MatchmakingHandler's state and, once a match is found, retrieves relevant game and player data to start the game UI using the provided MatchTypeController. The thread pauses for one second between checks to avoid overwhelming the system, ensuring smooth operation during matchmaking.
***
#### `MatchTypeController.java`
**Purpose:** The MatchTypeController class manages the user interface and functionality for selecting and hosting games in the application. It handles the display of game types (e.g., Chess, Checkers, TicTacToe, Connect4), allows players to either host a game or join an existing match using a room code, and facilitates matchmaking between players. Additionally, it manages the UI transitions between the game selection screen and the individual game screens, and it integrates the matchmaking process using the MatchmakingHandler.
***
#### `MenuPopupController.java`
**Purpose:**  The MenuPopupController class is responsible for managing the popup menu during gameplay. It handles the display and functionality of buttons like close, draw, and resign, allowing users to interact with the game during a match. The class also manages the opening and closing of the popup window, including setting up the appropriate scene, fonts, and styles for the game.

***
#### `P1CheckersController.java`
**Purpose:** The P1CheckersController class handles the gameplay logic for the first player in a checkers game. It manages player turns, updates the board, and controls game events such as moves, draws, or wins. It also interacts with the user interface, displaying the correct images for checkers pieces and updating labels to reflect the current player's turn.
***
#### `P1ChessController.java`
**Purpose:** The P1ChessController class is responsible for managing the gameplay logic of the first player in a chess game. It handles user input, such as moving pieces, and updates the game board accordingly, reflecting the changes on the graphical interface. Additionally, it includes logic for pawn promotion and ensures that each player's turn and piece movements are displayed correctly, while also managing the interaction with the game's UI components.
***
#### `P2CheckersController.java`
**Purpose:** The P2CheckersController class manages the second player's interactions in a checkers game. It updates the game board by reflecting each player's moves and handles the game state, including checking for a win, draw, or ongoing turns. The controller allows for piece movement, updates player labels for turn tracking, and facilitates gameplay events like drawing or resigning. It also includes functionality for initializing the game board and managing UI interactions such as showing popups and managing chat features.
***
#### `P2ChessController.java`
**Purpose:** The P2ChessController class manages the second player's interactions in a chess game. It updates the chessboard by reflecting each player's moves, handles piece promotions for pawns reaching the opposite end, and checks for game outcomes such as a win or draw. The controller handles piece movement, pawn promotion, game state updates, and player turn indicators, while also managing UI elements like piece images, player labels, and interactions with game popups.
***
#### `SceneManager.java`
**Purpose:** The SceneManager class handles the management of scene transitions in a JavaFX application. It registers scenes by their FXML paths and provides a method to switch between scenes with smooth fade-in and fade-out transitions. The class ensures that when switching scenes, the current scene fades out before transitioning to the new one, enhancing the user experience.
***
#### `SignUpController.java`
**Purpose:** The SignUpController class is responsible for handling the sign-up process in a JavaFX application. It provides functionality for users to input their email, username, and password to create a new account. The class includes form validation, account creation logic, and navigation to other screens like the login page or game selection page after successful account creation.
***
#### `StartController.java`
**Purpose:** The StartController class manages the start screen of a JavaFX application, where users can navigate to different options such as the sign-up page, leaderboard, and help section. It handles the visual setup, including loading a GIF background and setting the window title, while providing functionality to switch scenes (like to sign-up or leaderboard). Additionally, it includes animations for interactive buttons and integrates external resources like fonts and images for a dynamic user experience.

***
#### `styles.css`          
**Purpose:** This is a CSS file for styling a JavaFX application, primarily focusing on creating a retro, pixelated aesthetic using custom fonts like "Press Start 2P" and "Pixelite." It defines hover animations for buttons and elements (such as loginHoverButton and hoverCartridge), ensuring smooth transitions. The stylesheet also customizes tab headers, scroll bars, labels, and other UI components to match the overall theme, including transparent backgrounds and consistent font usage for headers and subheaders. Additionally, the styling incorporates subtle animations and visual effects to enhance the user experience, such as scaling and translating on hover.
***
#### `TicTacToeController.java`
**Purpose:** This class, TicTacToeController, is responsible for managing the game logic and user interface for a Tic-Tac-Toe game in a JavaFX application. It handles user interactions such as clicking on the grid to make moves, switching turns between players, updating the board with X and O symbols, and checking for win conditions. The class also manages the visual appearance of the game by updating labels, controlling the visibility of pieces, and handling UI transitions between game states.
***
#### `UserProfileController.java`
**Purpose:** This class, UserProfileController, manages the user profile interface in a JavaFX application. It handles the display of user information, such as the username, avatar, and stats, while providing navigation to different sections such as matches and settings. The class also integrates UI interactions like hover effects on profile panes and a popup for user actions, using transitions to enhance the visual experience.
***
#### `UserSettingsController.java`
**Purpose:** The UserSettingsController class is a simple JavaFX application that initializes and displays the user settings screen. It loads the UI layout from an FXML file, sets the window size, and displays it with the title "User Settings." The class is responsible for launching the application and rendering the settings interface.
***
#### `UtilityManager.java`
**Purpose:** The UtilityManager class provides a set of utility methods for adding animations and hover effects to various UI components in the application. These methods include functionality for scaling buttons, translating items, changing text colors on hover, and displaying popups. It also handles events like opening help popups, controlling chat, and offering smooth visual transitions for user interactions with elements like game cartridges, text labels, and user profile panes.
***
#### `WinScreenController`
**Purpose:** The WinScreenController class manages the UI behavior for the "You Win" screen in the application. It handles user interactions such as restarting the game or navigating to the main menu when the respective buttons are clicked. The handleStartOver method currently outputs a message to the console, and the handleMainMenu method switches the scene back to the start screen, using the SceneManager to facilitate the transition.

***
## Test Folder

***
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
***
### assets     
**Purpose:** All sprites and Screen used for the website. **[omgame.club](https://omgame.club/)** 
****
#### `all.css`
**Purpose:** This CSS file provides consistent styling for the web application, including smooth scrolling, interactive button effects, and popups. It also defines the layout for key elements such as headers, footers, and chat interfaces with animations and hover effects. The file enhances the user experience with visually appealing transitions, scalable buttons, and modals for improved navigation.
***
#### `checkers.css`
**Purpose:** This CSS file styles a chessboard layout with alternating light and dark squares, defining their size, grid structure, and piece placement. It includes interactive elements like buttons and highlighted squares with hover effects for a more engaging user experience. The overall design ensures a clean, modern look with smooth transitions and easy navigation.
***
#### `checkers.html`
**Purpose:** This HTML document sets up a tutorial page for checkers promotion and capture, including a navigation bar with links to the home, help, and store pages. It features a checkers board interface with a "Reset Board" button, and an overlay popup for additional guidance or SOS alerts. External CSS and JavaScript files are linked to style the page and manage game interactions.
***
#### `checkers.js`
**Purpose:** This JavaScript class sets up a checkers tutorial game, allowing the player to interact with the board, move pieces, and capture the opponent's pieces. It includes game logic for piece movement, promotion to a king, and enemy AI movement, while also providing visual feedback through popups and highlights. The game is interactive, with a responsive design that includes a reset function and user-specific login display.
***
#### `chess.css`
**Purpose:** This CSS class defines the layout and style for a checkers game tutorial page, including the board, pieces, and interactive elements. It provides a clean, responsive design with smooth transitions for the game board and action buttons. The buttons have hover effects, and the board and pieces are styled for an engaging user experience, with hover highlights and a popup feature for interactive guidance.
***
#### `chess.html`
**Purpose:** This HTML file provides the structure for a Chess tutorial page, including a header, informational sections about chess pieces and movements, and a chess board for interactive play. It also features a popup with a Tetris Cat GIF and SOS message for tutorial assistance, and buttons to start the tutorial or reset the game. The page includes external CSS and JavaScript files for styling and functionality, enhancing the user experience with smooth interactions and game reset capabilities.
***
#### `chess.js`
**Purpose:** This JavaScript code implements a chess tutorial game that walks the user through basic chess movements and strategies. It includes functions to create and manage a chessboard, handle piece movements, and provide instructional popups at various stages of the tutorial. The tutorial progresses through phases, such as pawn movement, promotion, and checkmate strategies, while updating the board and guiding the user through capturing and moving pieces.
***
#### `connect4.css`
**Purpose:** This CSS defines the styling for a Connect Four game interface. The layout consists of a 7x6 grid where each cell is styled to appear circular with a subtle inner shadow, and each piece in the game is represented by a circle with color transitions for the red and yellow discs. Additional styles enhance the visual appearance with rounded corners, shadows, hover effects on buttons, and smooth transitions for moving the pieces.
***
#### `connect4.html`
**Purpose:** This HTML file sets up the basic structure for a Connect Four game. It includes a header with navigation links, a dynamic board for the game, a button to reset the game, and a popup container for tutorials or messages. The page links to external stylesheets and JavaScript files to manage the game's appearance and functionality, with a special popup for additional interactions.
***
#### `connect4.js`
**Purpose:** This JavaScript file handles the logic for the Connect Four game. It initializes the game board, handles user and AI moves, checks for a vertical win, and provides a popup tutorial or feedback on the current state of the game. The file also manages the interactivity of the board, enabling and disabling columns for moves, and updating the UI accordingly.
***
#### `help.css`      
**Purpose:** This CSS file styles the "Help" section of the website, creating a clean and user-friendly interface for displaying help content. It includes responsive, grid-based layout for help cards, with interactive hover effects on the cards and buttons. The design utilizes soft colors and shadow effects to create a modern and approachable appearance.
***
#### `help.html`
**Purpose:** This HTML file provides the structure for a "Help" page, offering instructions for various games such as Chess, Checkers, TicTacToe, Connect4, Tetris, and Monopoly. It includes interactive cards for each game, a "Coming Soon" popup for games still in development, and a chat support feature. The page uses smooth transitions, popups, and a fixed chat button to enhance user experience.
***
#### `index.css`
**Purpose:** This CSS file styles a webpage with a visually striking layout, including a hero section with a gradient background, large heading, and call-to-action button. It also defines styles for a "vision" section with a background image, as well as cards and grids for displaying content in a clean, responsive format. Hover effects are added for interactive elements, enhancing the user experience with smooth transitions and scaling effects.
***
#### `index.html`
**Purpose:** This HTML page serves as the homepage for the Online Multiplayer Games (OMG) platform, providing an introduction to the site and its core features. It includes a hero section with a call-to-action, various feature cards highlighting key platform benefits, and a section outlining the platform's vision. The page also integrates a live chat support feature, a popup for help requests, and various animations to enhance user engagement.
***
#### `login.css`
**Purpose:** This CSS styles a clean and modern login page with a gradient background and a centered login card. The login card includes input fields for username and password, a password visibility toggle, a submission button with hover effects, and sections for error and success messages. It also features a back button and responsive design elements for a user-friendly login experience.
***
#### `login.html`
**Purpose:** This HTML structure represents a login page for the "Online Multiplayer Games - OMG" platform. It includes a form where users can input their username and password, with a toggle feature to reveal or hide the password. The page also features a back button, a login button, a message area for feedback, and a link for users to sign up if they don't have an account.
***
#### `login.js`
**Purpose:** This JavaScript code handles user authentication for the login page, allowing users to sign in with their credentials stored in localStorage or default data. It supports the visibility toggle for the password field and provides feedback messages on login success or failure. Additionally, it checks if the user is already logged in, displaying a personalized greeting and logout option, while allowing users to log in by pressing the "Enter" key.
***
#### `scripts.js`
**Purpose:** This JavaScript code manages the chat interface and a scrolling progress bar. It tracks the user's scroll position and updates the scroll progress bar width accordingly. Additionally, it handles the display and interaction of a chat popup, allowing users to send messages, receive automated bot replies, and toggle the visibility of the chat. The script also manages the user login state, displaying the logged-in user's name if available.
***
#### `signup.css`
**Purpose:** This CSS styles a user login page with a clean, modern design. It defines a card layout for the login form, with input fields, buttons, and a password toggle feature. The styling includes hover effects, background gradients, and responsiveness, ensuring a user-friendly and visually appealing experience.
***
#### `signup.html`
**Purpose:** This HTML page is for a user sign-up form, allowing users to create an account on the Online Multiplayer Games (OMG) platform. It includes fields for choosing a username, entering an email, and setting a password with confirmation. The form features password strength checking, terms acceptance, and a toggle to show or hide the password fields, along with a link to the login page for existing users.
***
#### `signup.js`
**Purpose:** This JavaScript code manages the sign-up process for users, including validating input, checking password strength, and handling user data storage. It checks that all fields are completed, validates the email format, ensures the passwords match, and confirms agreement to terms before saving the user's information to local storage. The script also supports toggling the visibility of the password and handles the "Enter" key to submit the form for a smoother user experience.
***
#### `store.css`
**Purpose:** This CSS defines the styling for an online store interface, with a responsive grid layout for items, tabbed navigation, and modal windows for item purchases. It includes hover effects, transition animations, and a consistent design for buttons, cards, and modals. Additionally, it styles a dynamic Stripe payment modal and ensures an interactive user experience with various visual feedbacks such as scaling effects and highlighted elements.
***
#### `store.html`
**Purpose:** This HTML code structures the "Store" page for an online multiplayer game platform, featuring sections for Loot Boxes, Ranks, and Skins. It includes interactive elements such as a modal for item viewing, a chat popup for support, and a fake Stripe payment modal for processing payments. The page also features tab navigation, a responsive grid layout for product display, and buttons for purchasing items or learning more about them.
***
#### `store.js`
**Purpose:** This JavaScript code handles the logic for a store page, including interactions with the Stripe payment modal, product modals, and tab navigation. It enables users to view product details, select quantities, and process payments with a modal interface. Additionally, it handles the dynamic updating of prices based on quantity, ensures the correct modal is displayed, and provides validation for custom quantity inputs and payment processing.
***
#### `tictactoe.css`
**Purpose:** This CSS style is designed for a Tic-Tac-Toe game interface. It creates a responsive grid layout for the game board, with stylized cells that change appearance when hovered over or clicked. It also defines styles for the buttons and interactive elements, with hover effects for better user interaction, and a .disabled class for inactive cells.
***
#### `tictactoe.html`
**Purpose:** This HTML structure sets up the layout for a Tic-Tac-Toe game. It includes a header with navigation links, a dynamic board for the game, and a "Reset Board" button. Additionally, there is a popup box that provides in-game messages, such as game instructions or alerts.
***
#### `tictactoe.js`
**Purpose:** his JavaScript code sets up and controls a Tic-Tac-Toe game. It generates a board, allows the user to make moves by clicking on cells, and handles game logic, including win conditions. It also enables a popup system to guide users and display game progress, such as instructions and results.
***
###### **Notice most of the method class description where made with the help of chatgpt**
***
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