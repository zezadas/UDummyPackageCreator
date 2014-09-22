UDummyPackageCreator
====================
<code>java -jar UDummyPackageCreator OPTIONS FILE [URL]</code>

A Dummy Package Creator for Ubuntu and Debian based distros, written in Java.

This program can send packages to a Debian/Ubuntu repository (like Launchpad).

## First version
* First executable version
* For use in terminal, in interactive mode

## Second version
* Non-interactive method

### Instructions
* Create a new folder
* Change your working directory to the JAR file location, and execute 'java -jar UDummyPackageCreator.jar'
* Run the progam with one of the options

#### Demo
<code>java  -jar UDummyPackageCreator.jar -b filename</code><p></p>
<code>java  -jar UDummyPackageCreator.jar -g filename ppa:someppa.com/ppa</code>

## Known bugs (aka missing features)
* The program doesn't recognize if a newly created file or existing file was edited; This will be implemented on later version;
