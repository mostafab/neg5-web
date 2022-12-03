FROM maven:3.6.3-jdk-8 as build
COPY . .
RUN mvn -B -f pom.xml clean package

FROM amazoncorretto:8u352-al2
COPY --from=build ./service/target/neg5.service-1.0-SNAPSHOT-jar-with-dependencies.jar .

EXPOSE 1337
ENTRYPOINT java -Xmx128m -jar neg5.service-1.0-SNAPSHOT-jar-with-dependencies.jar