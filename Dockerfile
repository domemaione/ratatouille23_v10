FROM openjdk:17
COPY build/libs/ratatouille23_v10-0.0.1.jar ratatouille23_v10-0.0.1.jar
EXPOSE 8080
CMD ["java","-jar","ratatouille23_v10-0.0.1.jar"]
