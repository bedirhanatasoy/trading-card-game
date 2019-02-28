# Trading Card Game

This is a simple trading card game which its game logic is similar to Hearthstone. 

The game is implemented both languages Java and Kotlin and both are identical.

Technology stack includes:

* Java
* Kotlin
* Groovy - Spock framework for testing

## How to run 

* Clone this repository 
```
git clone https://github.com/bedirhanatasoy/trading-card-game
```
* Just run the following command
```
./gradlew run
```

## How to create a JAR file and run it 

* Run the following command to create the jar file.
```
./gradlew clean build jar
```
* The jar file must be created in `build/libs` path
* Then, run the following command
```
java -jar build/libs/card-1.0-SNAPSHOT.jar
```

## How to run tests

* Run the following command to run all tests
```
./gradlew test
