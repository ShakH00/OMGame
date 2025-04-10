# ![tetriscat](src/images/sprites/tetrisCatIcon.png) seng300-project ![tetriscat](src/images/sprites/tetrisCatIcon.png)

seng300-project is our recreation of an <ins> online multiplayer game</ins>, or OMG for short.
***
## Table of Contents:   

### Project Layout:
* **[Installation](#Installation)**
    - **[JavaFx Installation Instructions](#javafx-installation-instructions)**
    - **[DataBase Instructions](#step-1-download--install-xampp)**
    - **[Jakarta]()** waiting until it reaches main


* **[Document Folder](#documentations-)**
  - **[Contract](#contract-)**
  - **[Game Logic](#game-logic-)**
  - **[GUI](#gui-)**
  - **[Networking](#networking-)**
  - **[Profile Matchmaking](#profile-matchmaking-)**
  - **[Use Case Diagrams](#use-case-diagrams-)**


* **[Diagrams](diagrams)**
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
*** 


# Installation

To launch OMG it would require an installation of javafx and the database our company as used.
* Steps to install [JavaFx](#javafx-installation-instructions) 
* Steps to install [Database](#step-1-download--install-xampp)

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

# Step 1: Download & Install XAMPP
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

### I WILL UPDATE THIS DOCUMENTATION WITH STATETEMENTS TO CREATE OUR DATABASE AND TABLES ONCE ME AND ELIJAH MEET UP TMRW
***

## Documentations 

- **[Contract](#contract-)**
  - **[SENG300AccountabilityContract.pfd](#seng300accountabilitycontractpdf)**
- **[Game Logic](#game-logic-)**
  - **[GameLogicPlanning.pdf]()**
- **[GUI](#gui-)**
- **[Networking](#networking-)**
- **[Profile Matchmaking](#profile-matchmaking-)**
- **[Use Case Diagrams](#use-case-diagrams-)**
***
### Contract 
 #### SENG300AccountabilityContract.pdf
Companies contract that everyone signed and was created by Project Manager and the Team Leads
***
### Game Logic 
***
#### GameLogicPlanning.pdf
#### use_case_descriptions.pdf
***
### GUI   
#### gui_design_ideas.pdf
#### gui_v1_designs.pdf
***
### Networking  
#### ErrorHandlingImplementation.md
#### meetingNotes.md
#### NetworkingConcepts.md
#### NetworkingFunctionsDocs.md
#### NetworkingSystemOverview.md
#### use_case_descriptions.pdf

***
### Profile Matchmaking  
***
### Use Case Diagrams  
***
## Diagrams Folder
***
###  Abstract               ⠀⠀
***
###  Game Logic⠀         ⠀⠀
***
###  GUI⠀ ⠀⠀           
***
###  Networking⠀         ⠀⠀
***
###  Profile Matchmaking⠀        
***

## Code Organization
***
### Account
***
### Authentication
***
* CAPTCHAAuthentication.java
  * Simple CAPTCHA system to verify that a human is interacting with the application.
    This helps support math, image, and text based CAPTCHA. This was integrated within the Authentication folder to prevent bots from entering the system.
  * MATH CAPTCHA:
    * A random equation is generated for the user to answer
  * TEXT CAPTCHA:
    * A random set of words are generated for the user to answer
  * IMAGE CAPTCHA:
    * A random image is chosen from one of the CAPTCHAImages folder to be output to user

* MFAAuthentication.java
  * Simple MFA is integrated within our system. A random 6-digit code is generated and sent to the users email, the user receives the email on our system, enters in the code to be verified.
  * However, before a verification code is sent to the users email, the database checks if the email exists on file first. Then proceeds with further steps.

* MFAAuthenticationV2.java
  * Sends a code to user's email, user enter's in the code, if the code is incorrect an Exception is thrown

* MFAInputPopup.java
  * This class creates a JavaFX pop-up dialog for entering an MFA code. It verifies the input with the expected code, it displays a success or error an message, and returns the result. It will return null if the code is incorrect.

* EmailSender.java
  * This class sends a 6-digit verification code via email using the Jakarta Mail API. This class connects the Gmail's SMTP server with an app-specific
    password and then sends the code to the users email. Used for MFA verification.

* Admin.java
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