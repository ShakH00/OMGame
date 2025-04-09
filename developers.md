# Developers Guide
This guide provides step-by-step instructions for setting up the essential tools and technologies required for the development environment. Follow each section carefully to ensure proper installation and configuration.

## _**JavaFX Installation**_

### Prerequisites

Before installing JavaFX, ensure that you have the following prerequisites:

- **Java JDK**: JavaFX requires a Java Development Kit (JDK) version 8 or above.
    - Download the JDK from the [official Oracle website](https://www.oracle.com/java/technologies/javase-downloads.html) or [AdoptOpenJDK](https://adoptopenjdk.net/).
    - Ensure that the `JAVA_HOME` environment variable is set correctly, and Java is added to the system's `PATH`.

### Installation Steps

#### 1. Download JavaFX SDK

Go to the official [JavaFX website](https://openjfx.io/) and download the latest JavaFX SDK version compatible with your operating system.

- **Windows**: Choose the `.zip` file.
- **Mac OS**: Choose the `.tar.gz` file.
- **Linux**: Choose the `.tar.gz` file.
---

#### 2. Extract JavaFX SDK

Once the download is complete:

- **Windows**: Extract the `.zip` file to a desired location on your system.
- **Mac/Linux**: Extract the `.tar.gz` file to a desired location on your system.

---

#### 3. Configure Your IDE

If you're using an Integrated Development Environment (IDE) like IntelliJ IDEA, Eclipse, or Visual Studio Code, configure it to include JavaFX libraries.

---

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

---

## _Database Installation_

### Prerequisites
- Update based on Nova's server or XAMPP

## Installation Steps

### 1. Download and Install XAMPP

1. Go to the official [XAMPP website](https://www.apachefriends.org/download.html).
2. Download the version for your operating system (Windows, macOS, or Linux).
3. Run the installer.
4. Follow the installation wizard:
    - Click **Next** on all prompts (you may leave all default components selected).
5. Once installed, open the **XAMPP Control Panel**.
    - On macOS, launch the `manager-osx` app.

---

### 2. Start Apache and MySQL Servers

1. In the XAMPP Control Panel, click **Start** next to:
    - **Apache** – This launches your local web server.
    - **MySQL** – This starts your local MySQL database.
2. Ensure both services are running. A green status indicator should appear for each.

---

### 3. Open phpMyAdmin

1. Open your web browser.
2. Navigate to: [http://localhost/phpmyadmin/](http://localhost/phpmyadmin/)
3. You should see the phpMyAdmin dashboard interface.

---

### 4. Download and Add MySQL Connector

1. Download the MySQL Connector J from the [official website](https://dev.mysql.com/downloads/connector/j/).
2. Choose **Platform Independent** and download the `.zip` file.
3. Extract the archive and locate the `mysql-connector-j-<version>.jar` file.
4. In your Java project:
    - Go to **File > Project Structure > Libraries**
    - Click **+** → **Java**, then select the JAR file.
    - Click **Apply** and then **OK**.

---

### 5. Initialize the Database

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

## _Jakarta Installation_
