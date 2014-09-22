UDummyPackageCreator
====================

UDummyPackageCreator
Usage: udummy [OPTIONS] [FILE] 
Usage: udummy [OPTIONS] [FILE] [URL] 
-oX    Open existing specfile (to be used with "-b" or "-g")
-b    Build package only
-g    Build package and send


A Dummy Package Creator for Ubuntu and Debian based distros, written in Java.

This program can send packages to a Debian/Ubuntu repository (like Launchpad)
It was intendeed for personal use, but I think it may be useful for some of you.

## First version
* First executable version
* For use in terminal, in interactive mode

## Second version
* Non-interactive method

### Instructions
* Create a new folder
* Change your working directory to the JAR file location, and execute 'java -jar UDummyPackageCreator.jar'
* Follow the interactive program flow (Step one to Step 2, and vice-versa)
