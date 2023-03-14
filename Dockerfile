ARG PORT=8080

FROM mysql:latest
ARG TARGETPLATFORM=linux/arm/v7


FROM gradle:7.2.0-jdk17 as BUILD
COPY . /src
WORKDIR /src
RUN gradle wrapper
RUN chmod +x gradlew
RUN ./gradlew build -x test --stacktrace

FROM openjdk:17-oracle
COPY --from=BUILD /src/build/libs/ratatouille23_v10-0.0.1.jar /bin/runner/ratatouille23_v10-0.0.1.jar
WORKDIR /bin/runner
EXPOSE ${PORT}
CMD ["java", "-jar", "ratatouille23_v10-0.0.1.jar"]
