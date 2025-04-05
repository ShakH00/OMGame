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

# Step 3: Open phpMyadmin
- Since mac is cool it makes you do less work
- Open any browser.
- Enter: http://localhost/phpmyadmin/
- You should now see the phpMyAdmin dashboard!

# Step 4: Making it work with java
- Download mysql connector from https://dev.mysql.com/downloads/connector/j/
- Choose platform independent operating system.
- Download the ZIP file, extract and you will see mysql-connector-j-<version>.jar, this is the file you'll use in your Java Project
- Go to File > Project Structure > Libraries 
- Click “+” → Java, then select the JAR file. 
- Apply & OK.

## I WILL UPDATE THIS DOCUMENTATION WITH STATETEMENTS TO CREATE OUR DATABASE AND TABLES ONCE ME AND ELIJAH MEET UP TMRW


# To create our DATABASE!!

1. After opening phpMyAdmin http://localhost/phpmyadmin/ go to the SQL option should be in the top nav bar.
2.  