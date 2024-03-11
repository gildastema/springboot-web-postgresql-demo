FROM openjdk:17-jdk-alpine as builder
RUN mkdir -p app/source
COPY . app/source
WORKDIR app/source
RUN chmod +x ./gradlew
RUN ./gradlew  build -x test

FROM openjdk:17-jdk-alpine
COPY --from=builder /app/source/build/libs/*.jar ./app/app.jar
COPY --from=builder /app/source/.docker/entrypoint.sh entrypoint.sh
CMD chmod +x entrypoint.sh
EXPOSE 8080
ENTRYPOINT ["./entrypoint.sh"]