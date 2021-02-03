FROM adoptopenjdk/openjdk11:latest
COPY build/libs/ms-currency-test-1.0.0.jar ms-currency.jar
CMD ["java", "-jar", "build/ms-currency.jar"]