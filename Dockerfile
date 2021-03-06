FROM adoptopenjdk/openjdk11:ubi

ARG JAR_FILE=target/*.jar

ENV BOT_NAME=test.tbot_community_bot
ENV BOT_TOKEN=1234143124:ADADgggfgAA25253adad_uso
ENV BOT_DB_USERNAME=tbot_db_user
ENV BOT_DB_PASSWORD=tbot_db_password

COPY ${JAR_FILE} app.jar
#Does it have to be to aline? Don't remember to see it later.
ENTRYPOINT [ "java", "-Dspring.datasource.password=${BOT_DB_PASSWORD}", "-Dbot.username=${BOT_NAME}", "-Dbot.token=${BOT_TOKEN}", "-Dspring.datasource.username=${BOT_DB_USERNAME}", "-jar", "/app.jar"]