# syntax=docker/dockerfile:1

FROM eclipse-temurin:21-jdk AS builder

WORKDIR /app

COPY gradlew gradlew.bat ./
COPY gradle ./gradle
COPY build.gradle settings.gradle ./
COPY src ./src

RUN --mount=type=cache,target=/root/.gradle \
    ./gradlew bootJar --no-daemon

FROM eclipse-temurin:21-jre AS runtime

ARG DD_JAVA_AGENT_VERSION=1.66.0

WORKDIR /app

ADD https://repo1.maven.org/maven2/com/datadoghq/dd-java-agent/${DD_JAVA_AGENT_VERSION}/dd-java-agent-${DD_JAVA_AGENT_VERSION}.jar ./dd-java-agent.jar
COPY --from=builder /app/build/libs/toyvillage.jar ./toyvillage.jar

EXPOSE 8080

ENTRYPOINT ["java", "-javaagent:/app/dd-java-agent.jar", "-Duser.timezone=Asia/Seoul", "-Dspring.profiles.active=prod", "-jar", "toyvillage.jar"]
