FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY "build/libs/javaserver-0.0.1-SNAPSHOT.jar" "javaserver-0.0.1-SNAPSHOT.jar"
ENTRYPOINT ["java","-jar","/javaserver-0.0.1-SNAPSHOT.jar"]
EXPOSE 8080