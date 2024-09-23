FROM openjdk:17-jdk-slim AS build

WORKDIR /app

ARG version=1.0.0
ARG artifactId=bookShopDemo

COPY pom.xml .
COPY src src

RUN mvn package --no-transfer-progress

FROM build
COPY --from=build /app/target/${artifactId}-${version}.jar app.jar

FROM openjdk:17-jre-slim
VOLUME /tmp
COPY app.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
