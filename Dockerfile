FROM maven as BUILDER
WORKDIR /build/
COPY pom.xml /build/
COPY src /build/src/
RUN mvn clean package
COPY target/movie-0.0.1-SNAPSHOT.jar target/application.jar

FROM openjdk
WORKDIR /app/
COPY --from=BUILDER /build/target/application.jar /app/
CMD java -jar /app/application.jar