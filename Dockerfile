
ARG PORT=8080

FROM gradle:7.2.0-jdk17 as BUILD
COPY . /src
WORKDIR /src
RUN gradle wrapper
RUN chmod +x gradlew
RUN ./gradlew build -x test --stacktrace

FROM openjdk:17-oracle
COPY --from=BUILD /src/build/libs/ratatouille23_v10.jar /bin/runner/ratatouille23_v10.jar
WORKDIR /bin/runner
EXPOSE ${PORT}
CMD ["java", "-jar", "ratatouille23_v10.jar"]

#








#FROM openjdk:17
#COPY build/libs/ratatouille23_v10-0.0.1.jar ratatouille23_v10-0.0.1.jar
#EXPOSE 8080
#CMD ["java","-jar","ratatouille23_v10-0.0.1.jar"]
