FROM java:8
EXPOSE 80 443
ENV TZ=Asia/Seoul
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=production", "/app.jar"]