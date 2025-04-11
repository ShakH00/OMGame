# Step 1: Download & Install XAMPP
- Go to the official XAMPP website. https://www.apachefriends.org/download.html
- Click Download for your operating system (Windows, macOS, or Linux).
- Run the installer after downloading.
- Follow the installation wizard:
- Click Next on all steps (you can leave default options checked).
- After installation, launch the XAMPP Control Panel.

# Step 2: Start Apache and MySQL Servers
- Open the XAMPP Control Panel. (Mac should be an app called managerosx(with gear logo))
- Click Start next to:
- Apache (your local web server)
- MySQL (your local database server)
- Make sure both have green indicators (running).
  - Apache running = Your web server is active.
  - MySQL running = Your database is ready.

# Step 3: Open phpmyadmin
- Open any browser.
- Enter: http://localhost/phpmyadmin/
- You should now see the phpMyAdmin dashboard!

# Step 4: Download MySQL Connector
- Download mysql connector from https://dev.mysql.com/downloads/connector/j/
- Choose platform independent operating system.
- Download the ZIP file, extract and you will see mysql-connector-j-<version>.jar, this is the file you'll use in your Java Project
- Go to File > Project Structure > Libraries 
- Click “+” → Java, then select the JAR file. 
- Apply & OK.

# Step 5: Initialize Database
1. After opening phpMyAdmin http://localhost/phpmyadmin/ go to the SQL option should be in the top nav bar.
2. Enter these SQL Statements separately
   - Create Database OMGAMEDB;
   - CREATE TABLE Accounts(
     ID INT AUTO_INCREMENT PRIMARY KEY,
     Username VARCHAR (255) NOT NULL UNIQUE,
     Email VARCHAR (255) NOT NULL UNIQUE,
     Password VARCHAR (255) NOT NULL,
     Friends VARCHAR (1024) NOT NULL,
     Statistics TEXT,
     MatchHistory TEXT
     )
   - CREATE TABLE Matchmaking(
     ID INT PRIMARY KEY,
     STATE VARCHAR(255),
     GAME VARCHAR(255),
     START_TIME FLOAT(32),
     RECENT_TIME FLOAT(32),
     ELO INT,
     ELO_RANGE INT,
     OPPONENT_ID INT,
     NETWORKING_INFO VARCHAR(255),
     ROOM_CODE VARCHAR(255),
     PLAYER_NO INT
     )
3. Then you should be able to see your database in the left side bar and your tables inside!

# NOTE
- We set ID to autoincrement so the database assigns an id to each account that gets saved to the database
- Username and email are set to unique