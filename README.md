<h1> TSG Projects</h1>
Projects completed during my time as a Java Apprentice in the Software Guild
<hr>
<h3>BasicJavaConcepts</h3>
Assesment demonstrates proficiency in basic Java syntax involving console input and output, variables, flow of control statements, and expressions.

<h5>DogGenetics</h5>
Programs asks user for the name of their dog, which then generates a fake DNA background report of their pet. The report is printed to the console and includes a random assigned percentage to 5 dog breeds, which adds up to 100%.
<h5>HealthyHearts</h5>
Simple calculator that asks user for their age in order to calculate and display the healthy heart rate range for their age.
<h5>RockPaperSissors</h5>
A Java console application for a Rock paper scissors game that first asks a user how many rounds they would like to play.
For each round, the program asks the user for their round choice(Rock,Paper, or Scissors), the computer randomly chooses an option as well, and then displays the results of the round.
Once all rounds have been played, the final results are displayed(ties, wins, losses) and an overall winner is declared.
<h5>SummativeSums</h5>
A program that adds all ints of a 1-dimenional array together and returns the result.
<hr>
<h3>DVDLibrary</h3>
A program that allows a user to manage and store a collection of DVDs.
Implements MVC design pattern and dependency injection.
Performs all CRUD functions to allow users to view, delete, update, and add DVDs to the library. A user can search for a DVD by title. The application uses a List to hold data in memory and can read and write data into a File in Java by using java.io package classes. Implements exception handling.
<hr>
<h3>FlooringMastery</h3>
Java console application done using NetBeans IDE involving console input and output.

Wrote a program for a fictional flooring company to manage customer orders.
Implements N-tier/MVC architecture, including the use of a service layer, Spring dependency injection, exception handling, and Unit Testing with JUnit.
The application follows a CRUD design pattern, which allow the company to create, read, update, and delete orders in the system. Data is persisted through file IO and DAO data in memory is stored in a List. A new order file is created for each sales day and a user can export all active order files to  the MainDataExport file.
<hr>
<h3> Hotel Reservation Schema-MySQL</h3>
Designed and build a database using MySQL Workbench to serve as a hotel reservation tracking system. The system stores reservations, guest information, and room information(type, cost, occupancy, amenities). Wrote SQL queries to retrieve and organize the data.
<hr>
<h3>RESTGuessTheNumberGame</h3>
Created a Spring Boot REST Application using JDBC template to access the database. The application allows a user to play a number guessing game(also known as "Bulls and Cows") using service components to store and apply the game rules. In each game, a random unique 4-digit number is generated. For each round, the user makes a 4-digit number guess and is then told the exact and partial digit matches. All HTTP requests were handled with a Spring MVC Controller, the controller handler methods were configured via annotations with the appropriate URL and HTTP method. Dao interfaces and service layer were tested thoroughly using a test database.  
<p></p>
<b>You will need the following REST endpoints to play the game:</b>

<ul>-"begin" - POST – Starts a game, generates an answer, and sets the game to in progress. Returns a game Id, which you then use to make your guess.</ul>
<ul>-"guess" – POST – Makes a guess by passing the guess and gameId in as JSON. The program calculates the results of the guess and marks the game as finished if your guess is correct. </ul>
<ul>-"game" – GET – Returns a list of all games. Answer will only be shown once you guess all 4 digits meaning the game is finished.</ul>
<ul>-"game/{gameId}" - GET – Returns your specific game based on your ID. </ul>
<ul>-"rounds/{gameId} – GET – Returns a list of rounds for your specified game ID, which is sorted by time.</ul>

<hr>
<h3>SuperSightings-SpringBootWebApp</h3>
A Spring Boot Web Application themed on Super Hero Sightings.
Project uses JDBC template to access the database and Thymeleaf as a template engine to combine data from the back-end with the HTML on the front-end to display the data. Implements Spring MVC architecture, exception handling, and unit testing though jUnit. HTML, CSS, Bootstrap, and javascript were used to complete the front-end. Data is persisted through SQL database. Validated form input using Spring Boot validation annotations and Thymeleaf conditionals.
<p></p>
The web application performs all CRUD functions, which allow a user to view, delete, update, and add superhero sightings, organizations, locations, and the actual superperson. Incorporated Google Maps for each individual location and sighting using Google Maps APIs and Bing Maps in the Home page for the latest sightings. Allowed a user to submit a URL to display a picture for a superperson's profile.
<hr>

<h3>VendingMachine-Spring</h3>
Java console application done using NetBeans IDE involving console input and output.
Program that serves as a virtual vending machine that allows a user to insert money, view and vend items, keeps track of item inventory through a text file, dispenses change, and documents all vending activity to an audit log. Application prompts user to insert more money when their current balance is not sufficient and to choose a different vending option when their chosen item is out of stock. Implements MVC architecture with the addition to a service layer, Spring dependency injection, exception handling, and JUnit testing. Data is persisted through file IO. 
<hr>
<h3>VendingMachineREST</h3>
Built a dynamic user-interface consuming a REST API using HTML, CSS, JavaScript, jQuery, and Bootstrap to serve as a vending machine web-page. The end product was a fully-functional vending machine REST Web Service that displays all items and their information, allows a user to add money and purchase items, displays user's current balance and returns and displays change in coins. 
<p></p>
Used JavaScript to handle browser events(clicks) to execute JavaScript methods. Used jQuery AJAX Methods to exchange data with the server, and update parts of the web page like GET requests to request and retrieve vending items and POST requests to vend an item. The application handles JSON appropriately in both success and failure cases and retrieves the error status and message to display it out in the web-page in the event that an item is out of stock or the user does not have sufficient funds.
<hr>
<h3>Blog Management System</h3>
Project in web development and database design done using NetBeans IDE and MySQL Workbench. Project implements JPA, a Spring MCV architectural design pattern, Spring Security, and Thymeleaf.
A Blog/Content Management System for a small company that allows them to manage a blog website.
This project was build in collaboration with another Java Apprentice in my cohort. Project work was divided into designated tasks/Web-page features and our group was successful due to our constant communication and involvement. 
<p></p>
Application incorporates TinyMCE editor for when a user creates or edits a blog post. The Admin can create, edit, delete, and view all active users. Only designated users in the system with login credential can create, edit, or delete blog posts or static pages. Anonymous users can view all published blogs and static pages and can leave comments on a blog post. All users can search published blog posts by hashtag, blog title, or author.
<p></p>
<p></p>
I helped with setting up two of the application's entities with the appropriate annotations. On the web side of things (wrote html for creating and editing a blog, displaying all blogs, displaying a single static page, blog comments, displaying and editing hashtags, the home page, and styling). Worked on the Article, Hashtag, and Comment controller to create the mappings for pages. Validated form input using Spring Boot validation annotations and Thymeleaf conditionals for creating/editing a blog, editing a hashtag, and creating/editing users.
