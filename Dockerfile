FROM adoptopenjdk/openjdk11:ubi

ARG JAR_FILE=target/*.jar

ENV BOT_NAME=test.tbot_community_bot
ENV BOT_TOKEN=1234143124:ADADgggfgAA25253adad_uso

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-Dbot.username=${BOT_NAME}","-Dbot.token=${BOT_TOKEN}", "-jar", "/app.jar"]