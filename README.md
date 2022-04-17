# WEALTH PILOT INTERVIEW TASK

## Task description

```
The wealthpilot soccer league is about to start. 
Your task is to write an application, that plans the games of the next season.
The teams can be read from the file soccer.json
When the plan for the season is created the application displays it in the console like that:
Date            Team 1         Team 2
11.12.2018  Erzgebirge Aue     Kreuther Fürth
18.12.2018  Holstein Kiel          Dynamo Dresden
Die season starts on the 05.03.2022
Games are played on Saturdays 17:00
After all, teams have played against each other they have a 3 weeks break.
After the break, they play again, in the same order as in round 1, but at the opponent's home. So team 1 becomes team 2 and vice versa.
As many games as possible should take place on one weekend.
```

### Build

Java 16 is required

```shell
./gradlew build
```

(if gradlew doesn't exist, run ``gradle wrapper`` first)

### Run

```shell
./gradlew bootRun -Pargs='--spring.config.location=src/main/resources/application.yaml'
```

To build local docker image (you need docker to be launched) run

```
gradle jibDockerBuild
```

To run docker image

```
docker run -d wealthpilot:1.0-SNAPSHOT
```
