FROM node:14-alpine AS node-build
WORKDIR /app
COPY front/webclipboard .
RUN yarn install
RUN CI=false yarn run build

FROM amazoncorretto:8-al2-jdk AS build
WORKDIR /app
COPY . .
ARG OUTPUT_DIR=src/main/resources/static
RUN mkdir -p $OUTPUT_DIR
COPY --from=node-build /app/build $OUTPUT_DIR
RUN ./gradlew clean build --exclude-task test

FROM amazoncorretto:8
ENV STORAGE_PATH=/data
RUN mkdir /data
WORKDIR /app
EXPOSE 80
ENV TZ=Asia/Seoul`
ARG JAR_FILE=build/libs/*.jar
COPY --from=build /app/${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=production", "/app/app.jar"]