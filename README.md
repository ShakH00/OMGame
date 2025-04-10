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
* **[Git Log & Git Link](#git-log--git-link)**
  - LATER
* **[Team](#team)**
  - Later
*** 


# Installation

To launch OMG it would require an installation of javafx and the database our company as used.
* Steps to install [JavaFx](#javafx-installation-instructions) 
* Steps to install [Database](#step-1-download--install-xampp)
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
## Step 1: Download & Install XAMPP
- Go to the official XAMPP website. https://www.apachefriends.org/download.html
- Click Download for your operating system (Windows, macOS, or Linux).
- Run the installer after downloading.
- Follow the installation wizard:
- Click Next on all steps (you can leave default options checked).
- After installation, launch the XAMPP Control Panel.

## Step 2: Start Apache and MySQL Servers
- Open the XAMPP Control Panel. (Mac should be an app called managerosx(with gear logo))
- Click Start next to:
- Apache (your local web server)
- MySQL (your local database server)
- Make sure both have green indicators (running).
    - Apache running = Your web server is active.
    - MySQL running = Your database is ready.

## Step 3: Open phpMyadmin
- Since mac is cool it makes you do less work
- Open any browser.
- Enter: http://localhost/phpmyadmin/
- You should now see the phpMyAdmin dashboard!

## Step 4: Making it work with java
- Download mysql connector from https://dev.mysql.com/downloads/connector/j/
- Choose platform independent operating system.
- Download the ZIP file, extract and you will see mysql-connector-j-<version>.jar, this is the file you'll use in your Java Project
- Go to File > Project Structure > Libraries
- Click “+” → Java, then select the JAR file.
- Apply & OK.

Thank you for choosing

<img src="src/images/omg_title.png" alt="drawing" width="80"/>

### I WILL UPDATE THIS DOCUMENTATION WITH STATETEMENTS TO CREATE OUR DATABASE AND TABLES ONCE ME AND ELIJAH MEET UP TMRW

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

<img src="src/images/omg_title.png" alt="drawing" width="80"/>


***

## Documentations 

- **[Contract](#contract-)**
  - **[SENG300AccountabilityContract.pfd](#seng300accountabilitycontractpdf)**


- **[Game Logic](#game-logic-)**
  - **[GameLogicPlanning.pdf]()**
  - **[use_case_descriptions.pdf](#use_case_descriptionspdf)**


- **[GUI](#gui-)**
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
The GameLogicPlanning.pdf outlines a structured development plan divided into five sprints to implement and test logic for several turn-based games including Chess, Checkers, Connect Four, and Tic Tac Toe. The team focused on classes and use case diagram creation, core gameplay implementation, and feature integration, while assigning specific components and responsibilities to different members to ensure modular and agile development.

#### `use_case_descriptions.pdf`
This document outlines the use case scenarios for various gameplay mechanics in a multiplayer strategy game system, focusing on features such as playing a game, moving pieces, and performing specific actions like castling, en passant, and offering a draw. Each use case details the actors involved, preconditions, triggers, scenarios, postconditions, exceptions, and priority, aiming to ensure the game operates smoothly and realistically according to chess, checkers, Connect 4, and tic-tac-toe rules.
***

### GUI
GUI documentation developed during the initial phase of the project.
- **[gui_design_ideas.pdf](#gui_design_ideaspdf)**
- **[gui_v1_designs.pdf](#gui_v1_designspdf)**
#### `gui_design_ideas.pdf`
Rough concept of the main layout/design of the game. 
#### `gui_v1_designs.pdf`
Polished version of main layout/design of the game.
***

### Networking  
Networking documentation developed during the initial phase of the project.
- **[ErrorHandlingImplementation.md](#errorhandlingimplementationmd)**
- **[meetingNotes.md](#meetingnotesmd)**
- **[NetworkingConcepts.md](#networkingconceptsmd)**
- **[NetworkingFunctionsDocs.md](#networkingfunctionsdocsmd)**
- **[NetworkingSystemOverview.md](#networkingsystemoverviewmd)**
- **[use_case_descriptions.pdf](#use_case_descriptionspdf-)**

#### `ErrorHandlingImplementation.md`
This document outlines the finalized error handling and failure recovery strategies implemented in the multiplayer system, including disconnection detection, reconnection workflows, and turn synchronization. It also details future-ready features like retry logic for move transmission and safeguards to maintain game integrity and prevent exploits.
#### `meetingNotes.md`
The team meeting on March 26 covered recent progress, including updates to documentation, diagrams, and code stubs, as well as leadership and coordination efforts. Upcoming goals focus on finalizing planning, preparing for code implementation (especially networking and chat features), and ensuring equal contributions from all members ahead of the demo.
#### `NetworkingConcepts.md`
This document outlines how game logic and networking interact in the multiplayer system, detailing client-side implementation, data structures like GameState, Board, and GameRules, and how they're synchronized over the network. It also proposes a tentative matchmaking and session management plan while emphasizing modularity, future enhancements, and the need for team-wide alignment before finalizing any implementations.
#### `NetworkingFunctionsDocs.md`
This document consolidates all networking-related components and stubs for P3, including server-client architecture, turn-sync logic across all four games, and placeholder systems for chat and matchmaking. It serves as a comprehensive guide for developers and integration teams, detailing current functionality, planned upgrades, and how networking is simulated via Networking.java.
#### `NetworkingSystemOverview.md`
This document finalizes the networking system for P3, detailing the dual-layer architecture—live server-client flow and stubbed multiplayer simulation via Networking.java. It outlines implementation across all games, integration with other teams (GUI, Logic, Auth, Leaderboard), and prepares for future features like matchmaking and reconnection. All core networking stubs are fully functional and documented, making the system modular, testable, and demo-ready.
#### `use_case_descriptions.pdf `
The use case descriptions outline the steps for player connection, gameplay, and connection management in a multiplayer game. They cover scenarios like connecting to the server, matchmaking, making moves, synchronizing game state, handling disconnections, and processing game logic to ensure smooth gameplay.
***
### Profile Matchmaking  
Profile & Matchmaking documentation developed during the initial phase of the project.
- **[matchmaking.md](#matchmakingmd)**
- **[README.md](README.md)**
- **[use_case_descriptions_01.pdf](#use_case_descriptions_01pdf)**
- **[use_case_descriptions_02.pdf](#use_case_descriptions_02pdf)**
- **[use_case_descriptions_03.pdf](#use_case_descriptions_03pdf)**
- **[use_case_descriptions_05.png](#use_case_diagram_05png)**

#### `matchmaking.md`
The matchmaking system adjusts player Elo based on their rating, with a default range of ±100 Elo, expanding over time if no match is found. Elo adjustments are game-specific, with higher K-factors for strategic games like Chess and lower K-factors for simpler games like Tic-Tac-Toe, ensuring fair competition while limiting rapid rating inflation.
#### `README.md`
The Profile & Matchmaking Team has documented the matchmaking process and created class structure, sequence, and use case diagrams for various aspects, such as account creation, matchmaking, and leaderboard generation. These diagrams and related documents help illustrate the key components of the system, including authentication, password encryption, multi-factor authentication, and managing friends.
#### `use_case_descriptions_01.pdf`
The use cases describe various matchmaking and game interaction scenarios. Players can queue for matches, host or join private matches using a match ID, select the game to play, and even spectate ongoing matches with a match ID. Key priorities include supporting core functionality like game selection and matchmaking, while features like spectating and private matches have lower priority due to being optional or for specific player preferences.
#### `use_case_descriptions_02.pdf`
The use cases describe account creation, editing, guest account functionality, and identity verification processes. Players can create and edit accounts, play as guests, and verify their identity when logging in, with account creation and guest play being high-priority, frequently used features, while identity verification is lower priority and less frequent.
#### `use_case_descriptions_03.pdf`
These use cases cover the functionality for players to search for other players, send friend requests, manage incoming requests, and remove friends. While not essential to core gameplay, these features enhance social interaction and are frequently used to build and manage a player's friend network.
#### `use_case_diagram_05.png`
The use case diagrams shows a rough overview of authentications player to system steps.
***
#### `Project_Planning.pdf`
A detailed project planning document for various teams working on different aspects of a game development project. Each team, such as the Game Logic, GUI, Authentication/Profile, Leaderboard/Matchmaking, Networking, and Integration teams, has their own set of objectives and tasks, with timelines and statuses.
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
  - **[integration](#integation)**
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
Every class diagrams from all teams.
***
###  Game Logic⠀         ⠀⠀
#### `class_diagram.png`    
Game's logic class diagram.
***
#### `use_case_diagram.png`         
Game's logic use case diagrams.
***
###  GUI⠀ ⠀⠀           
#### `assests`
Screens and Sprites that will be used in the main game.
- **[screens](#screens)**
- **[sprites](#sprites)**
***
#### `screens`
All screens that will be implemented in the project.
***
#### `sprites`
All sprites that will be implemented in the project.
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
#### `integration`
Images on how to integrate each part
- **[auth_mm.png](#auth_mmpng)**
- **[gameLogic.png](#gamelogicpng)**
- **[gui.png](#guipng)**
- **[networking.png](#networkingpng)**
***
#### `auth_mm.png`
Integration diagram with the leaderboard, matchmaking and authentication with buttons.
***
#### `gameLogic.png`
Integration diagram with the game's included with buttons.
***
#### `gui.png`
Integration diagram with the gui and buttons.
***
#### `networking.png`
Integration diagram with the networking and database.
****
#### `(DRAFT)error_handling_1 mermaid diagram.png`

#### `(DRAFT)error_handling_2 mermaid diagram.png`
#### `DRAFT_use_case.png`
#### `figjamLink.txt`
#### `gameClassDiagram.png`
#### `gameProcess.png`
#### `player_and_localSetup.png`
#### `tentativeGameStateTransistionDiagarm.png`

***
###  Profile Matchmaking⠀        
#### `class_structure_diagrams`
Holds every class structure from Profile and Matchmaking team.
#### `sequence_diagrams`
Holds every sequence diagrams from Profile and Matchmaking team.
#### `use_case_diagarms`
Holds every sequence diagrams from Profile and Matchmaking team. 

***

## Code Organization
***
### Account
***
### Authentication
***
_* CAPTCHAAuthentication.java_
  * Simple CAPTCHA system to verify that a human is interacting with the application.
    This helps support math, image, and text based CAPTCHA. This was integrated within the Authentication folder to prevent bots from entering the system.
  _* MATH CAPTCHA:_
    * A random equation is generated for the user to answer
  _* TEXT CAPTCHA:_
    * A random set of words are generated for the user to answer
  _* IMAGE CAPTCHA:_
    * A random image is chosen from one of the CAPTCHAImages folder to be output to user

_* MFAAuthentication.java_
  * Simple MFA is integrated within our system. A random 6-digit code is generated and sent to the users email, the user receives the email on our system, enters in the code to be verified.
  * However, before a verification code is sent to the users email, the database checks if the email exists on file first. Then proceeds with further steps.

_* MFAAuthenticationV2.java_
  * Sends a code to user's email, user enter's in the code, if the code is incorrect an Exception is thrown

_* MFAInputPopup.java_
  * This class creates a JavaFX pop-up dialog for entering an MFA code. It verifies the input with the expected code, it displays a success or error an message, and returns the result. It will return null if the code is incorrect.

_* EmailSender.java_
  * This class sends a 6-digit verification code via email using the Jakarta Mail API. This class connects the Gmail's SMTP server with an app-specific
    password and then sends the code to the users email. Used for MFA verification.

_* Admin.java_
  * This class provides backend functionality for managing and modifying a users account within our system
    Users can update their email, passwords, usernames and more while interacting with the database
### Com.Example
***
### Database
***
### Game
***
### Images
***
### Leaderboard
***
### Matchmaking
***
### Networking
***
### Resources.Fonts
***
### Screens
## Test Folder

## Git Log & Git link

## Team