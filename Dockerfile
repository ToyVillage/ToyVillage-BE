# syntax=docker/dockerfile:1

FROM eclipse-temurin:21-jre

ARG DD_JAVA_AGENT_VERSION=1.66.0

WORKDIR /app

ADD https://repo1.maven.org/maven2/com/datadoghq/dd-java-agent/${DD_JAVA_AGENT_VERSION}/dd-java-agent-${DD_JAVA_AGENT_VERSION}.jar ./dd-java-agent.jar
COPY build/libs/toyvillage.jar ./toyvillage.jar

EXPOSE 8080

ENTRYPOINT ["java", "-javaagent:/app/dd-java-agent.jar", "-Duser.timezone=Asia/Seoul", "-Dspring.profiles.active=prod", "-jar", "toyvillage.jar"]
