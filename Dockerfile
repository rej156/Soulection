FROM adzerk/boot-clj

ADD . /app
RUN cd /app

WORKDIR /app/target
EXPOSE 8080
CMD ["java", "-jar", "soulection-0.10.0-SNAPSHOT.jar"]
