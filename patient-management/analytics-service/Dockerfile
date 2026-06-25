FROM maven:3.9.9-eclipse-temurin-21 AS builder

WORKDIR /app

# Copy both services
COPY patient-service ./patient-service
COPY analytics-service ./analytics-service

# Install patient-service into local .m2 cache first
RUN mvn -f patient-service/pom.xml clean install -DskipTests

# Now build analytics-service (patient-service jar is in .m2 now)
RUN mvn -f analytics-service/pom.xml clean package -DskipTests

FROM eclipse-temurin:21-jdk AS runner

WORKDIR /app

COPY --from=builder /app/analytics-service/target/analytics-service-0.0.1-SNAPSHOT.jar ./app.jar

EXPOSE 4002

ENTRYPOINT ["java", "-jar", "app.jar"]