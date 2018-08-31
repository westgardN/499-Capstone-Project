# README #

How to build and run PRIM

### What is this repository for? ###

* PRIM is Public Relations Interaction Management and is used for managing your social media presence.
* Current version is 0.9.0
* [Learn Markdown](https://bitbucket.org/tutorials/markdowndemo)

### How do I get set up? ###

If you don't have the source you first need to clone the repository.

* git clone https://bitbucket.org/partyofv/ics499_project_alpha.git

### Setup Prim MySQL Database ###

* Run the setup_prim_db_mysql.sql, located in the scripts folder in the root of the project, on your MySQL server to create the prim database and user.
* If your MySQL server is not on the same machine that you will be running PRIM, then please update the application.properties file, located under src/main/resources, with the connection information to your MySQL server.
* You must configure your MySQL server to support 4-byte UTF-8 characters. This is so that emojis are properly stored and displayed. Follow this guide on how to do this: https://mathiasbynens.be/notes/mysql-utf8mb4

### Building PRIM ###

On Windows:

* Open a command window and navigate to the root of the project folder
* gradlew build

On *nix based systems:

* Open a shell window and navigate to the root of the project folder
* gradle build

The above will build PRIM using Gradle and execute any tests in the project.

### Running PRIM ###

On Windows:

* Open a command window and navigate to the root of the project folder
* gradlew bootRun

On *nix based systems:

* Open a shell window and navigate to the root of the project folder
* gradle bootRun

The above will run PRIM. Once it is running you can open a browswer and navigate to http://localhost:8080 to use PRIM

* Login with username primadmin and a password of abc125

### Using Eclipse ###

Because the project is Gradle based you can easily contribute using any development environment.

That being said, the preferred IDEs are Eclipse and IntelliJ.

IntelliJ just works and so there is no explanation needed on how to setup the project in it.

For Eclipse, you must have the Buildship Gradle Integration 2.0 or later installed. This plugin can be installed through the Eclipse Marketplace.

Once you have that plug-in installed, click File->Import and select Gradle as the type of project to import.

Point to the root folder of the repository you cloned earlier and simply click Next / Finish to import the Prim project.

### Using Netbeans ###

Unfortunately, I haven't spent much time trying to get the Netbeans IDE to work with the project and so I can't recommend that you use it. That being said, if you can get it to work more power to you!

### Other IDEs and Editors ###

Visual Studio Code works great and I am sure there are others that work as well.

### Notes on Checking in Changes ###

Under no circumstances should any IDE project files being checked in to the git repository. Just don't do it.
