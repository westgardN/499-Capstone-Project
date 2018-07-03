# README #

How to build and run PRIM

### What is this repository for? ###

* PRIM is Public Relations Interaction Management and is used for managing your social media presence.
* Current version is 0.9.0
* [Learn Markdown](https://bitbucket.org/tutorials/markdowndemo)

### How do I get set up? ###

If you don't have the source you first need to clone the repository.

* git clone https://bitbucket.org/partyofv/ics499_project_alpha.git

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

