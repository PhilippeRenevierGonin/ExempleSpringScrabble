FROM openjdk:11-jdk-slim
ARG JAR_FILE=/target/joueur-1.jar
COPY ${JAR_FILE} joueur.jar
ENTRYPOINT java -Dserver.port=$PORT -jar /joueur.jar $LANCEMENT